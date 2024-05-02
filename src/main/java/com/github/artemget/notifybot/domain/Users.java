package com.github.artemget.notifybot.domain;

import java.util.List;
import java.util.Optional;

public interface Users {
    void add(Long id);
    Optional<User> get(Long id);
    List<User> toNotify();
}
