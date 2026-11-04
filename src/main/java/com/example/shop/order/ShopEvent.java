package com.example.shop.order;


public sealed interface ShopEvent permits OrderCompletedEvent, OrderStatusChangedEvent {
}

