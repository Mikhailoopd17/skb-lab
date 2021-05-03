package org.skb_lab.test.dao;

import org.skb_lab.test.pojo.QueueItem;
import org.skb_lab.test.pojo.emun.QueueState;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QueueItemDAO extends CrudRepository<QueueItem, Integer> {

    List<QueueItem> getAllByQueueStateIs(QueueState queueState);
}
