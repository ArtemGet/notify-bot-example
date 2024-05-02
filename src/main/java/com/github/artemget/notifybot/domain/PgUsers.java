package com.github.artemget.notifybot.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class PgUsers implements Users {
    @Override
    public void add(final Long id) {
        throw new UnsupportedOperationException("#add()");
    }
    @Override
    public Optional<User> get(final Long id) {
        throw new UnsupportedOperationException("#get()");
    }
    @Override
    public List<User> toNotify() {
        throw new UnsupportedOperationException("#toNotify()");
    }
}
