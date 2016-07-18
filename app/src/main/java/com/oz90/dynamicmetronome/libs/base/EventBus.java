package com.oz90.dynamicmetronome.libs.base;

/**
 * Created by Orlando on 20/06/2016.
 */
public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    Boolean isRegistered(Object subscriber);
    void post(Object event);
}
