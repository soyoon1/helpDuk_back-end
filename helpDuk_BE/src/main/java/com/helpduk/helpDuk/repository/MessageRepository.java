package com.helpduk.helpDuk.repository;

import com.helpduk.helpDuk.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {
}
