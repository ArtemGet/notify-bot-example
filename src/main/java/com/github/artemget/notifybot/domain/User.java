package com.github.artemget.notifybot.domain;

import java.time.LocalDateTime;
import java.util.List;

public interface User {
    Long id();
    void addNotification(LocalDateTime time, String text);
    List<Notify> notifys();
}
