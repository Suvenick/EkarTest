package com.ekar.thread;

import com.ekar.shared.SharedCounter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class ProducerThreadFactory implements SystemThreadFactory<ProducerThread> {

    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    private final SharedCounter counter;

    @Override
    public ProducerThread newThread() {
        return new ProducerThread("PRODUCER-" + COUNTER.incrementAndGet(), counter);
    }
}
