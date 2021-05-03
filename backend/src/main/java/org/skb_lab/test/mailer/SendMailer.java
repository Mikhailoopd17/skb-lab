package org.skb_lab.test.mailer;

import java.util.concurrent.TimeoutException;

/**
 * Ориентировочный интерфейс мейлера.
 */
public interface SendMailer {
    void sendMail (String toAddress, String messageBody) throws TimeoutException;
}
