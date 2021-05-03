package org.skb_lab.test.messaging;

import lombok.SneakyThrows;
import org.skb_lab.test.pojo.ResponseBean;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

@Service
public class MessageServiceImpl implements MessageService {
    private final Logger log = Logger.getLogger(getClass().getName());

    @SneakyThrows
    private static void sleep() {
        Thread.sleep(TimeUnit.MINUTES.toMillis(1));
    }

    private static boolean shouldSleep() {
        return new Random().nextInt(10) == 1;
    }

    private static boolean shouldThrowTimeout() {
        return new Random().nextInt(10) == 1;
    }

    @Override
    public <T> Integer send(T msg) {
        return new Random().nextInt();
    }

    @Override
    public ResponseBean receive(Integer messageId) throws TimeoutException {
        if(shouldThrowTimeout()) {
            sleep();
            throw new TimeoutException("Timeout!");
        }

        if(shouldSleep()) {
            sleep();
        }
        return ResponseBean.getDefaultResponse();
    }

    @Override
    public <R> ResponseBean doRequest(R request) throws TimeoutException {
        log.info("Message requested to external service!");
        final Integer messageId = send(request);
        return receive(messageId);
    }
}
