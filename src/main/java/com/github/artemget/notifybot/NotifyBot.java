package com.github.artemget.notifybot;

import com.github.artemget.teleroute.command.Cmd;
import com.github.artemget.teleroute.command.CmdException;
import com.github.artemget.teleroute.route.Route;
import com.github.artemget.teleroute.send.Send;
import com.github.artemget.teleroute.send.SendException;
import com.github.artemget.teleroute.telegrambots.update.TgBotWrap;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class NotifyBot extends TelegramLongPollingBot {
    private final String name;
    private final Route<Update, AbsSender> route;

    public NotifyBot(
        @Value("${bot.token}") String botToken,
        @Value("${bot.name}") String name,
        @Qualifier("root") Route<Update, AbsSender> route
    ) {
        super(botToken);
        this.name = name;
        this.route = route;
    }

    @Override
    public String getBotUsername() {
        return this.name;
    }

    @Override
    public void onUpdateReceived(final Update update) {
        this.route.route(new TgBotWrap(update))
            .flatMap(cmd -> this.handleExecution(cmd, update))
            .ifPresent(this::handleSend);
    }

    public Optional<Send<AbsSender>> handleExecution(
        final Cmd<Update, AbsSender> cmd,
        final Update update
    ) {
        Optional<Send<AbsSender>> resp;
        try {
            resp = cmd.execute(update);
        } catch (CmdException exception) {
            resp = Optional.empty();
            exception.printStackTrace();
        }
        return resp;
    }

    public void handleSend(final Send<AbsSender> send) {
        try {
            send.send(this);
        } catch (SendException exception) {
            exception.printStackTrace();
        }
    }
}
