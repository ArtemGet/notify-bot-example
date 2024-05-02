package com.github.artemget.notifybot.match;

import com.github.artemget.notifybot.domain.Users;
import com.github.artemget.teleroute.match.Match;
import com.github.artemget.teleroute.update.Wrap;
import lombok.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Value
@Component
public class MatchNotRegistered implements Match<Update> {
    Users users;

    @Override
    public Boolean match(final Wrap<Update> wrap) {
        return this.users.get(wrap.src().getMessage().getChatId())
            .isEmpty();
    }
}
