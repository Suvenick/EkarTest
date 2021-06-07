package com.ekar.service;

import com.ekar.dao.EventAuditRepository;
import com.ekar.thread.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ThreadHolderService {

    private final EventAuditRepository auditRepository;
    private final ConsumerThreadFactory consumerThreadFactory;
    private final ProducerThreadFactory producerThreadFactory;
    private final EventService eventService;

    private LinkedList<ConsumerThread> consumerThreads = new LinkedList<>();
    private LinkedList<ProducerThread> producerThreads = new LinkedList<>();

    public synchronized void change(int addConsumers, int addProducers) {
        changeCapacity(consumerThreads, addConsumers, consumerThreadFactory);
        changeCapacity(producerThreads, addProducers, producerThreadFactory);
        auditRepository.save(eventService.newSettingsProcessing(addConsumers, addProducers));
    }

    @SneakyThrows
    private <T extends BaseSystemThread> void changeCapacity(LinkedList<T> registered,
                                                             int diff,
                                                             SystemThreadFactory<T> factory) {
        if (diff == 0) {
            return;
        }

        if (diff > 0) {
            create(registered, factory);
        } else {
            destroy(registered, diff);
        }
    }

    private <T extends BaseSystemThread> void create(LinkedList<T> registered, SystemThreadFactory<T> factory) {
        T thread = factory.newThread();
        thread.start();
        registered.add(thread);
    }

    private <T extends BaseSystemThread> void destroy(LinkedList<T> registered, int diff) {
        for (int index = 0; index < Math.abs(diff); index++) {
            T next = registered.poll();
            if (next != null) {
                next.stopExecution();
            } else {
                break;
            }
        }
    }

    @PreDestroy
    @SneakyThrows
    public void closeThreads() {
        destroy(consumerThreads, Integer.MAX_VALUE);
        destroy(producerThreads, Integer.MAX_VALUE);
        TimeUnit.SECONDS.sleep(10);
    }
}
