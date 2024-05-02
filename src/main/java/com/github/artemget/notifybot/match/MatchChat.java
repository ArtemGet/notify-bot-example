package com.github.artemget.notifybot.match;

import com.github.artemget.teleroute.match.Match;
import com.github.artemget.teleroute.update.Wrap;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public final class MatchChat implements Match<Update> {
    @Override
    public Boolean match(final Wrap<Update> wrap) {
        return wrap.src().getMessage().getFrom().getId()
            .equals(wrap.src().getMessage().getChatId());
    }
}
