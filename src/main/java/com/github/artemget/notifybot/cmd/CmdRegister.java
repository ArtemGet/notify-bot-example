package com.github.artemget.notifybot.cmd;

import com.github.artemget.notifybot.domain.Users;
import com.github.artemget.teleroute.command.Cmd;
import com.github.artemget.teleroute.send.Send;
import java.util.Optional;
import lombok.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Value
@Component
public class CmdRegister implements Cmd<Update, AbsSender> {
    private static final String REGISTER_RESPONSE = "Добро пожаловать, вызови команду /help, чтобы понять, как мной пользоваться";
    Users users;

    @Override
    public Optional<Send<AbsSender>> execute(final Update update) {
        this.users.add(update.getMessage().getFrom().getId());
        return new CmdTxt(CmdRegister.REGISTER_RESPONSE).execute(update);
    }
}
