package com.ekar.thread;

import com.ekar.shared.SharedCounter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public abstract class BaseSystemThread extends Thread {

    private final AtomicBoolean isRunning;
    protected final SharedCounter counter;

    public BaseSystemThread(String name, SharedCounter counter) {
        super(name);
        this.isRunning = new AtomicBoolean(true);
        this.counter = counter;
    }

    @Override
    public final void run() {
        log.info(getName() + " new thread has been started");

        while (isRunning.get()) {
            doDelay(1, TimeUnit.SECONDS);
            action();
        }

        this.onThreadStopped();
    }

    protected abstract void action();

    @SneakyThrows
    private void doDelay(int time, TimeUnit timeUnit) {
        log.info(getName() + " is on pause mode");
        timeUnit.sleep(time);
    }

    public void stopExecution() {
        this.isRunning.set(false);
    }

    protected void onThreadStopped() {
        log.info(getName() + " stop execution ");
    }
}
