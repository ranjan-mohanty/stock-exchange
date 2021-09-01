package com.demo.stock.exchange.model;

import lombok.Data;

@Data
public class ExecutedOrder {
    private String orderId;

    private Double sellPrice;

    private Integer quantity;

    private String sellOrderId;

    public ExecutedOrder(String orderId, Double sellPrice, Integer quantity, String sellOrderId) {
        this.orderId = orderId;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
        this.sellOrderId = sellOrderId;
    }

    @Override
    public String toString() {
        return orderId + " " + sellPrice + " " + quantity + " " + sellOrderId;
    }
}
