package org.skb_lab.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.skb_lab.test.dao.QueueItemDAO;
import org.skb_lab.test.dao.UserDataDAO;
import org.skb_lab.test.exception.UserExceptions;
import org.skb_lab.test.mailer.SendMailerImpl;
import org.skb_lab.test.messaging.MessageServiceImpl;
import org.skb_lab.test.pojo.QueueItem;
import org.skb_lab.test.pojo.RequestBean;
import org.skb_lab.test.pojo.ResponseBean;
import org.skb_lab.test.service.QueueService;
import org.skb_lab.test.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(JUnit4.class)
class BackendApplicationTests extends BaseTest {
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private UserDataDAO userDataDAO;
    @Autowired
    private QueueService queueService;
    @Autowired
    private QueueItemDAO queueItemDAO;
    @Autowired
    private SendMailerImpl sendMailer;
    @Autowired
    private MessageServiceImpl messageService;

    @Test
    void register() throws Exception {
        RequestBean bean = getRequest();
        registrationService.registration(bean);
        RequestBean beanFromDb = userDataDAO.findById(bean.getId()).get();
        Assert.isTrue(beanFromDb.equals(bean), "Requests is not equals!");
    }

    @Test
    void addQueue() throws Exception {
        QueueItem queueItem = getQueueItem();
        queueService.addToQueue(queueItem.getObjectType(), queueItem.getActionType(), queueItem.getObjectData());
        List<QueueItem> fromDb = (List<QueueItem>) queueItemDAO.findAll();

        Assert.isTrue(fromDb.stream()
                .map(QueueItem::getObjectData)
                .collect(Collectors.toList())
                .contains(queueItem.getObjectData()), "Requests is not equals!");
    }

    @Test
    void sendEmail() {
        try {
            for(int i = 0; i < 5; i++) {
                sendMailer.sendMail("test@test.re", "test body!");
            }
        } catch (TimeoutException e) {
            throw new RuntimeException("Error!");
        } catch (UserExceptions.RestException r) {
            throw new UserExceptions.RestException("Error!");
        } finally {
            Assert.isTrue(true);
        }
    }

    @Test
    void messageToExternal() {
        ResponseBean responseBean;
        try {
            responseBean = messageService.doRequest(getRequest());
        } catch (TimeoutException e) {
            e.printStackTrace();
            throw new RuntimeException("Error!");
        }
        Assert.isTrue(responseBean.getCode() < 299
                && responseBean.getMessage() != null
                && !responseBean.getMessage().isBlank(), "Error sending message to external service!");
    }
}
