package com.example.example.domain;

public enum StatusOrder {
    /**
     * Заказ создан
     */
    CREATED,
    /**
     * Заказ обработан
     */
    PROCESSED,
    /**
     * Отменен
     */
    CANCELED,

    /**
     * Заказ у доставки
     */
    AT_DELIVERY,

    /**
     * Заказ доставлен
     */
    DELIVERED,

    /**
     * Завершен
     */
    FINISHED
}
