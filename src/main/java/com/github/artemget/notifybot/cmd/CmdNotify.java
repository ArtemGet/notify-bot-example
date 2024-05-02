package com.github.artemget.notifybot.cmd;

import com.github.artemget.notifybot.domain.Notify;
import com.github.artemget.notifybot.domain.User;
import com.github.artemget.notifybot.domain.Users;
import com.github.artemget.teleroute.command.Cmd;
import com.github.artemget.teleroute.send.Send;
import com.github.artemget.teleroute.send.SendBatch;
import com.github.artemget.teleroute.telegrambots.send.SendMessageWrap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public final class CmdNotify implements Cmd<Users, AbsSender> {
    @Override
    public Optional<Send<AbsSender>> execute(final Users users) {
        return Optional.of(
            new SendBatch<>(
                this.run(users)
            )
        );
    }

    private List<Send<AbsSender>> run(final Users users) {
        return users.toNotify()
            .stream()
            .flatMap(user -> user.notifys()
                .stream()
                .map(notify -> this.run(user, notify))
            ).collect(Collectors.toList());
    }

    private Send<AbsSender> run(final User user, final Notify notify) {
        return new SendMessageWrap<>(
            new SendMessage(
                user.id().toString(),
                notify.text()
            )
        );
    }
}
