package org.skb_lab.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.skb_lab.test.pojo.QueueItem;
import org.skb_lab.test.pojo.RequestBean;
import org.skb_lab.test.pojo.emun.ActionType;
import org.skb_lab.test.pojo.emun.ObjectType;
import org.skb_lab.test.pojo.emun.QueueState;

abstract class BaseTest {


    static RequestBean getRequest() {
        RequestBean requestBean = new RequestBean();
        requestBean.setFirstName("Gomer");
        requestBean.setLastName("Simpson");
        requestBean.setEmail("simpson@gmail.com");
        requestBean.setLogin("yahoo!");
        requestBean.setPassword("123");
        return requestBean;
    }

    static QueueItem getQueueItem() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        QueueItem queueItem = new QueueItem();
        queueItem.setFailCount(0);
        queueItem.setActionType(ActionType.MESSAGING);
        queueItem.setQueueState(QueueState.NEW);
        queueItem.setObjectData(objectMapper.writeValueAsString(getRequest()));
        queueItem.setObjectType(ObjectType.REQUEST);
        return queueItem;
    }
}
