package org.skb_lab.test.dao;

import org.skb_lab.test.pojo.RequestBean;
import org.springframework.data.repository.CrudRepository;

public interface UserDataDAO extends CrudRepository<RequestBean, Integer> {

    boolean existsByLogin(String login);
}
