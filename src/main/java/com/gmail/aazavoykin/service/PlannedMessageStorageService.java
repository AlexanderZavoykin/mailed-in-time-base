package com.gmail.aazavoykin.service;

import com.gmail.aazavoykin.data.enums.PlannedMessageStatus;
import com.gmail.aazavoykin.data.model.PlannedMessage;
import com.gmail.aazavoykin.data.model.PlannedMessageLock;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PlannedMessageStorageService {

    Optional<PlannedMessage> getById(Long id);

    List<PlannedMessage> getAllByStatus(Set<PlannedMessageStatus> statuses);

    PlannedMessage save(PlannedMessage message);

    void remove(PlannedMessage message);

    PlannedMessageLock getLock(Long id);

    void removeLock(PlannedMessageLock lock);

}
