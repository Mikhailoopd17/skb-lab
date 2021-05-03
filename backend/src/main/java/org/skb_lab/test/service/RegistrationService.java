package org.skb_lab.test.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.skb_lab.test.dao.UserDataDAO;
import org.skb_lab.test.pojo.RequestBean;
import org.skb_lab.test.pojo.emun.ActionType;
import org.skb_lab.test.pojo.emun.ObjectType;
import org.skb_lab.test.utils.Utils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistrationService {
    private ObjectMapper objectMapper;
    private UserDataDAO userDataDAO;
    private QueueService queueService;

    public RegistrationService(ObjectMapper objectMapper, UserDataDAO userDataDAO, QueueService queueService) {
        this.objectMapper = objectMapper;
        this.userDataDAO = userDataDAO;
        this.queueService = queueService;
    }

    /**
     * метод принимает заявку, и добавляет ее в очередь
     * @param bean
     * @throws Exception
     */
    public void registration(RequestBean bean) throws Exception {
        bean.setCreated(LocalDateTime.now());
        bean.setPassword(Utils.getHex(bean.getPassword()));
        userDataDAO.save(bean);
        queueService.addToQueue(ObjectType.REQUEST, ActionType.MESSAGING, objectMapper.writeValueAsString(bean));
    }

    /**
     * проверка уникальности логина
     * @param login
     * @return
     */
    public boolean isExistsLogin(String login) {
        return userDataDAO.existsByLogin(login);
    }
}
