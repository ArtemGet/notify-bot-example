package com.github.artemget.notifybot.match;

import com.github.artemget.teleroute.match.Match;
import com.github.artemget.teleroute.update.Wrap;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Value
@Component
public class MatchDateTimeFormat implements Match<Update> {
    DateTimeFormatter formatter;

    @Override
    public Boolean match(final Wrap<Update> wrap) {
        return wrap.text()
            .map(this::validate)
            .orElse(false);
    }

    private boolean validate(final String text) {
        boolean valid = false;
        try {
            final String[] split = text.split(" ");
            LocalDateTime.parse(
                split[1] + ' ' + split[2],
                this.formatter);
            valid = true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return valid;
    }
}
