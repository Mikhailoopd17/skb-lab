package org.skb_lab.test.mailer;

import lombok.SneakyThrows;
import org.skb_lab.test.exception.UserExceptions;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

@Service
public class SendMailerImpl implements SendMailer {
    private final Logger log = Logger.getLogger(getClass().getName());

    @Override
    public void sendMail(String toAddress, String messageBody) throws TimeoutException {
        if (toAddress == null || toAddress.isBlank() || messageBody == null || messageBody.isBlank()) {
            throw new UserExceptions.RestException("Error! email or send body not set!");
        }
        if(shouldThrowTimeout()) {
            sleep();
            throw new TimeoutException("Timeout!");
        }

        if(shouldSleep()) {
            sleep();
        }
        log.info(String.format("Message sent to %s, body %s.", toAddress, messageBody));
    }

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
}
