package org.skb_lab.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.skb_lab.test.exception.UserExceptions;
import org.skb_lab.test.mailer.SendMailerImpl;
import org.skb_lab.test.messaging.MessageServiceImpl;
import org.skb_lab.test.pojo.QueueItem;
import org.skb_lab.test.pojo.RequestBean;
import org.skb_lab.test.pojo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class RequestService {
    private final Logger log = Logger.getLogger(getClass().getName());

    private final ObjectMapper objectMapper;
    private final MessageServiceImpl messageService;
    private final SendMailerImpl mailer;

    @Autowired
    public RequestService(ObjectMapper objectMapper, MessageServiceImpl messageService, SendMailerImpl mailer) {
        this.objectMapper = objectMapper;
        this.messageService = messageService;
        this.mailer = mailer;
    }

    public void process(QueueItem queueItem) {
        switch (queueItem.getActionType()){
            case MESSAGING:
                doMessaging(queueItem);
                break;
            case MAIL:
                doSendMail(queueItem);
                break;
            default: throw new UserExceptions.RestException("Action type not found!");
        }
    }

    @SneakyThrows
    private void doSendMail(QueueItem queueItem) {
        RequestBean bean = objectMapper.readValue(queueItem.getObjectData(), RequestBean.class);
        log.info(String.format("Send message to email %s", bean.getEmail()));
        mailer.sendMail(bean.getEmail(), queueItem.getMessage());
    }

    @SneakyThrows
    private void doMessaging(QueueItem queueItem) {
        RequestBean bean = objectMapper.readValue(queueItem.getObjectData(), RequestBean.class);
        log.info(String.format("Messaging process to request %s", bean.toString()));
        ResponseBean responseBean = messageService.doRequest(bean);
        log.info(String.format("Message %s returned from external service", responseBean.getMessage()));
        queueItem.setMessage(responseBean.getMessage());
    }
}
