package com.ekar.thread;

import com.ekar.shared.SharedCounter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProducerThread extends BaseSystemThread {

    public ProducerThread(String name, SharedCounter counter) {
        super(name, counter);
    }

    @Override
    protected void action() {
        SharedCounter.OperationResult result = counter.increment();
        if (result.isSuccess()) {
            log.info(getName() + " incremented counter. New value is " + result.getValue());
        } else {
            stopExecution();
        }
    }
}
