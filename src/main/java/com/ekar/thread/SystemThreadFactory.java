package com.ekar.thread;

public interface SystemThreadFactory<T extends Thread> {

    T newThread();
}
