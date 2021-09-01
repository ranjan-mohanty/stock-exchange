package com.demo.stock.exchange.model;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Order implements Comparable<Order> {

    private String orderId;

    private String stockName;

    private Double price;

    private Integer quantity;

    private Date orderTime;

    private OrderType orderType;

    @Override
    public int compareTo(Order o) {
        return orderTime.compareTo(o.orderTime);
    }

    public enum OrderType {
        BUY, SELL
    }

    public Order(String orderString) throws ParseException {
        String[] orderDetails = orderString.split(" ");
        this.orderId = orderDetails[0];

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        this.orderTime = timeFormat.parse(orderDetails[1]);
        this.stockName = orderDetails[2];

        if ("buy".equals(orderDetails[3])) {
            this.orderType = OrderType.BUY;
        } else if ("sell".equals(orderDetails[3])) {
            this.orderType = OrderType.SELL;
        }

        this.price = Double.parseDouble(orderDetails[4]);
        this.quantity = Integer.parseInt(orderDetails[5]);
    }
}
