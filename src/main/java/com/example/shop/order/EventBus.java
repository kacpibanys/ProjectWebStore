package com.example.shop.order;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class EventBus {
    private final Map<Class<?>, List<Consumer<ShopEvent>>> subscribers = new ConcurrentHashMap<>();

    public <T extends ShopEvent> void subscribe(Class<T> eventType, Consumer<T> listener) {
        List<Consumer<ShopEvent>> list = subscribers.computeIfAbsent(eventType, t -> new CopyOnWriteArrayList<>());
        list.add(event -> listener.accept(eventType.cast(event)));
    }

    public void publish(ShopEvent event) {
        List<Consumer<ShopEvent>> interested = subscribers.getOrDefault(event.getClass(), List.of());

        for (Consumer<ShopEvent> listener : interested) {
            try {
                listener.accept(event);
            } catch (RuntimeException e) {
                System.err.println("Subscriber error at: " + event.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
    }
}
