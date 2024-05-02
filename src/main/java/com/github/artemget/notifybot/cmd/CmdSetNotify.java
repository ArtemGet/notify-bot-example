package com.github.artemget.notifybot.cmd;

import com.github.artemget.notifybot.domain.Users;
import com.github.artemget.teleroute.command.Cmd;
import com.github.artemget.teleroute.send.Send;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Value
@Component
public class CmdSetNotify implements Cmd<Update, AbsSender> {
    DateTimeFormatter formatter;
    Users users;

    @Override
    public Optional<Send<AbsSender>> execute(final Update update) {
        this.users.get(update.getMessage().getFrom().getId())
            .ifPresent(user -> user.addNotification(
                    this.ejectDate(update.getMessage().getText()),
                    this.ejectNotification(update.getMessage().getText())
                )
            );
        return Optional.empty();
    }

    private LocalDateTime ejectDate(final String text) {
        final String[] split = text.split(" ");
        return LocalDateTime.parse(
            split[1] + ' ' + split[2],
            this.formatter);
    }

    private String ejectNotification(final String text) {
        return Arrays.stream(text.split(" "))
            .skip(3L)
            .collect(Collectors.joining(" "));
    }
}
