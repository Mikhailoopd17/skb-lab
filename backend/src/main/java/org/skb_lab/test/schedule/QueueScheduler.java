package org.skb_lab.test.schedule;

import org.skb_lab.test.pojo.QueueItem;
import org.skb_lab.test.service.QueueService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class QueueScheduler {
    @Value("${queue.reserve.size:5}")
    private int reserveSize;

    private final Logger log = Logger.getLogger(getClass().getName());

    private QueueService queueService;

    public QueueScheduler(QueueService queueService) {
        this.queueService = queueService;
    }

    @Scheduled(fixedRate = 5 * 1000)
    public void run() {
//        log.info(String.format("Start queue schedule: %s", LocalDateTime.now()));
        List<QueueItem> items = queueService.getProcessQueueItems();
        items.forEach(queueService::process);
        queueService.reserveQueueItem(reserveSize);
    }

}
