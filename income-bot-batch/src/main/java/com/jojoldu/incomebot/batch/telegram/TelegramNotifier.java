package com.jojoldu.incomebot.batch.telegram;

import com.jojoldu.incomebot.batch.telegram.config.TelegramProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class TelegramNotifier {
    private static final String URL = "https://api.telegram.org/bot%s/sendMessage";

    private final RestTemplate restTemplate;
    private final TelegramProperties telegramProperties;

    public TelegramResponse notify (TelegramMessage message) {
        String sendUrl = String.format(URL, telegramProperties.getKey());
        HttpEntity<TelegramMessage> request = new HttpEntity<>(message, createAuthHeaders());
        try{
            ResponseEntity<TelegramResponse> result = restTemplate.exchange(sendUrl, HttpMethod.POST, request, TelegramResponse.class);
            return result.getBody();
        } catch (HttpClientErrorException hce) {
            log.error("Telegram 요청이 실패했습니다. url={}, requestDto={}, errorResponseBody={} ", sendUrl, request.getBody(), hce.getResponseBodyAsString(), hce);
            throw new IllegalStateException("Telegram 요청이 실패했습니다");
        } catch (Exception e) {
            log.error("Telegram 요청이 실패했습니다. url={}, requestDto={}", sendUrl, request.getBody(), e);
            throw new IllegalStateException("Telegram 요청이 실패했습니다");
        }
    }

    private HttpHeaders createAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return headers;
    }
}
