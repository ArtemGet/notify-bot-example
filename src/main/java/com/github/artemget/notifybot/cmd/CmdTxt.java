package com.github.artemget.notifybot.cmd;

import com.github.artemget.teleroute.command.Cmd;
import com.github.artemget.teleroute.send.Send;
import com.github.artemget.teleroute.telegrambots.send.SendMessageWrap;
import java.util.Optional;
import lombok.Value;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Value
public class CmdTxt implements Cmd<Update, AbsSender> {
    String response;

    @Override
    public Optional<Send<AbsSender>> execute(final Update update) {
        return Optional.of(
            new SendMessageWrap<>(
                new SendMessage(
                    update.getMessage().getFrom().getId().toString(),
                    this.response
                )
            )
        );
    }
}
