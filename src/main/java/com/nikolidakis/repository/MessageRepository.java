package com.nikolidakis.repository;

import com.nikolidakis.models.Message;
import com.nikolidakis.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findByReceiver(User receiver);

    List<Message> findBySender(User sender);

}
