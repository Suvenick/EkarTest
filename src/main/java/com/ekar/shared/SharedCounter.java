package com.ekar.shared;

import com.ekar.dao.EventAuditRepository;
import com.ekar.service.EventService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SharedCounter {

    private final EventAuditRepository eventAuditRepository;
    private final EventService eventService;

    private volatile int value;
    private volatile boolean closed;

    private final Object lock = new Object();

    public SharedCounter(@Value("${init.value}") int value,
                         EventAuditRepository eventAuditRepository,
                         EventService eventService) {
        this.value = value;
        this.closed = false;
        this.eventAuditRepository = eventAuditRepository;
        this.eventService = eventService;
    }

    public void set(int newValue) {
        if (newValue > 100 || newValue < 0){
            throw new IllegalArgumentException("Value should be between 0 and 100");
        }

        synchronized (lock) {
            value = newValue;
            closed = false;
        }

        eventAuditRepository.save(eventService.newCounter(newValue));
    }

    public OperationResult get() {
        return new OperationResult(value, true);
    }

    public OperationResult increment() {
        synchronized (lock) {
            if (closed){
                return new OperationResult(value , false);
            }

            if (value < 100) {
                value++;
                checkLimits();
                return new OperationResult(value , true);
            } else {
                return new OperationResult(value , false);
            }
        }
    }

    public OperationResult decrement() {
        synchronized (lock) {
            if (closed){
                return new OperationResult(value , false);
            }

            if (value > 0) {
                value--;
                checkLimits();
                return new OperationResult(value , true);
            } else {
                return new OperationResult(value , false);
            }
        }
    }

    private void checkLimits(){
        if (value == 0 || value == 100) {
            closed = true;
            eventAuditRepository.save(eventService.endProcessing());
        }
    }

    @RequiredArgsConstructor
    @Getter
    public static class OperationResult {
        private final int value;
        private final boolean success;
    }

}
