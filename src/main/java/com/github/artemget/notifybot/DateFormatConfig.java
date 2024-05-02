package com.github.artemget.notifybot;

import java.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DateFormatConfig {

    @Bean
    DateTimeFormatter provide() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm");
    }
}
