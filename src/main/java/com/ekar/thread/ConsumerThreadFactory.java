package com.ekar.thread;

import com.ekar.shared.SharedCounter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class ConsumerThreadFactory implements SystemThreadFactory<ConsumerThread> {

    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    private final SharedCounter counter;

    @Override
    public ConsumerThread newThread() {
        return new ConsumerThread("CONSUMER-" + COUNTER.incrementAndGet(), counter);
    }
}
