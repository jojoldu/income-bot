package com.jojoldu.incomebot.batch.notifier.telegram.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "telegram.bot")
public class TelegramProperties {

    private String name;
    private String key;
}
