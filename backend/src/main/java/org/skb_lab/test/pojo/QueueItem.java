package org.skb_lab.test.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.skb_lab.test.pojo.base.BaseEntity;
import org.skb_lab.test.pojo.emun.ActionType;
import org.skb_lab.test.pojo.emun.ObjectType;
import org.skb_lab.test.pojo.emun.QueueState;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QueueItem extends BaseEntity {
    private ActionType actionType;
    private ObjectType objectType;
    @Column(length = 1000)
    private String objectData;
    private QueueState queueState;
    @Column(length = 1000)
    private String message;
    private int failCount;
}
