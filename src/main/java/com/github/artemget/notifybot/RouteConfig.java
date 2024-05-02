package com.github.artemget.notifybot;

import com.github.artemget.notifybot.cmd.CmdRegister;
import com.github.artemget.notifybot.cmd.CmdSetNotify;
import com.github.artemget.notifybot.cmd.CmdTxt;
import com.github.artemget.notifybot.match.MatchChat;
import com.github.artemget.notifybot.match.MatchDateTimeFormat;
import com.github.artemget.notifybot.match.MatchNotRegistered;
import com.github.artemget.teleroute.match.MatchAll;
import com.github.artemget.teleroute.match.MatchCmd;
import com.github.artemget.teleroute.match.MatchTextPart;
import com.github.artemget.teleroute.route.Route;
import com.github.artemget.teleroute.route.RouteDfs;
import com.github.artemget.teleroute.route.RouteFork;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@AllArgsConstructor
@Configuration
public class RouteConfig {
    MatchNotRegistered unregistered;
    MatchDateTimeFormat format;
    MatchChat chat;

    CmdRegister register;
    CmdSetNotify set;

    @Bean("root")
    Route<Update, AbsSender> provide() {
        return new RouteDfs<>(
            this.registerFlow(),
            this.commandFlow()
        );
    }

    private Route<Update, AbsSender> registerFlow() {
        return new RouteFork<>(
            new MatchAll<>(
                this.chat,
                this.unregistered
            ),
            this.register
        );
    }

    private Route<Update, AbsSender> commandFlow() {
        return new RouteFork<>(
            new MatchCmd<>(),
            new RouteDfs<>(
                this.help(),
                this.setNotify()
            )
        );
    }

    private Route<Update, AbsSender> setNotify() {
        return new RouteFork<>(
            new MatchTextPart<>("/set-notify"),
            new RouteFork<>(
                this.format,
                this.set,
                new CmdTxt("Укажите дату и время")
            )
        );
    }

    private Route<Update, AbsSender> help() {
        return new RouteFork<>(
            new MatchTextPart<>("/help"),
            new CmdTxt("""
                Для установки напоминания вызовите команду
                /set-notify в формате: команда dd.MM.yyyy HH.mm текст напоминания
                """)
        );
    }
}
