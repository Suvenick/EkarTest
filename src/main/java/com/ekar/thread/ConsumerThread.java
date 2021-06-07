package com.ekar.thread;

import com.ekar.shared.SharedCounter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsumerThread extends BaseSystemThread {

    public ConsumerThread(String name, SharedCounter counter) {
        super(name, counter);
    }

    @Override
    protected void action() {
        SharedCounter.OperationResult result = counter.decrement();
        if (result.isSuccess()) {
            log.info(getName() + " decremented counter. New value is " + result.getValue());
        } else {
            stopExecution();
        }
    }

}
