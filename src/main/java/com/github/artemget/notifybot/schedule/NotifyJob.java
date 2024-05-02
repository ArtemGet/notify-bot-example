package com.github.artemget.notifybot.schedule;

import com.github.artemget.notifybot.cmd.CmdNotify;
import com.github.artemget.notifybot.domain.Users;
import lombok.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Value
@Component
public class NotifyJob {
    Users users;
    CmdNotify notify;

    @Scheduled(cron = "0 * * * * *")
    public void runJob() {
        this.notify.execute(this.users);
    }
}
