package org.skb_lab.test.service;

import org.skb_lab.test.dao.QueueItemDAO;
import org.skb_lab.test.exception.UserExceptions;
import org.skb_lab.test.pojo.QueueItem;
import org.skb_lab.test.pojo.emun.ActionType;
import org.skb_lab.test.pojo.emun.ObjectType;
import org.skb_lab.test.pojo.emun.QueueState;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class QueueService {
    @Value("${queue.fail.max.count:5}")
    private int failMaxCount;

    private final Logger log = Logger.getLogger(getClass().getName());

    private QueueItemDAO queueItemDAO;
    private RequestService requestService;

    public QueueService(QueueItemDAO queueItemDAO, RequestService requestService) {
        this.queueItemDAO = queueItemDAO;
        this.requestService = requestService;
    }

    public void addToQueue(ObjectType type, ActionType action, String objectData) {
        queueItemDAO.save(new QueueItem(action, type, objectData, QueueState.NEW, null, 0));
    }

    public List<QueueItem> getProcessQueueItems() {
        return queueItemDAO.getAllByQueueStateIs(QueueState.PROCESS);
    }

    public void process(QueueItem queueItem) {
        try {
            switch (queueItem.getObjectType()) {
                case REQUEST:
                    requestService.process(queueItem);
                    break;
                default:
                    throw new UserExceptions.RestException("Object type not found!");
            }

            switch (queueItem.getActionType()) {
                case MESSAGING:
                    queueItem.setActionType(ActionType.MAIL);
                    break;
                case MAIL:
                    queueItem.setQueueState(QueueState.SUCCESS);
                    break;
                default:
                    throw new UserExceptions.RestException("Action not found!");
            }
            queueItemDAO.save(queueItem);
        } catch (Exception e) {
            /*
            Даем ограниченное количество попыток на ошибк процессинга элементов очереди.
            По превышению максимального числа - помечаем элемент как fail
            */
            log.warning(String.format("Error processing queue item: %s", e.getMessage()));
            if (queueItem.getFailCount() >= failMaxCount) {
                queueItem.setQueueState(QueueState.FAIL);
                queueItem.setMessage(e.getMessage());
            } else {
                queueItem.setFailCount(queueItem.getFailCount() + 1);
            }
            queueItemDAO.save(queueItem);
        }
    }

    public void reserveQueueItem(int size) {
        List<QueueItem> list = queueItemDAO.getAllByQueueStateIs(QueueState.NEW);
        list = list
                .stream()
                .limit(size)
                .peek(queueItem -> queueItem.setQueueState(QueueState.PROCESS))
                .collect(Collectors.toList());
        queueItemDAO.saveAll(list);
    }
}
