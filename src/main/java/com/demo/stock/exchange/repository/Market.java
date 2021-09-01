package com.demo.stock.exchange.repository;

import com.demo.stock.exchange.model.ExecutedOrder;
import com.demo.stock.exchange.model.Order;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class Market {
    private static Map<String, TreeMap<Double, PriorityQueue<Order>>> stocksData;

    public Market() {
        stocksData = new HashMap<>();
    }

    public void addSellOrder(Order order) {
        TreeMap<Double, PriorityQueue<Order>> stocks = stocksData
                .computeIfAbsent(order.getStockName(), k -> new TreeMap<>());
        PriorityQueue<Order> sellOrders = stocks.computeIfAbsent(order.getPrice(), sellOrder -> new PriorityQueue<>());
        sellOrders.add(order);
    }

    public ExecutedOrder executeOrder(Order buyOrder, PriorityQueue<Order> buyOrders) {

        TreeMap<Double, PriorityQueue<Order>> sellOrdersMap = stocksData.get(buyOrder.getStockName());
        if (sellOrdersMap == null || sellOrdersMap.isEmpty()) {
            return null;
        }
        for (Entry<Double, PriorityQueue<Order>> sellOrderEntry : sellOrdersMap.entrySet()) {
            if (sellOrderEntry.getKey() > buyOrder.getPrice()) {
                break;
            } else {
                Order sellOrder = sellOrderEntry.getValue().poll();
                if (sellOrder == null) {
                    return null;
                }

                //Calculating the quantity
                int qty;
                if (sellOrder.getQuantity() < buyOrder.getQuantity()) {
                    qty = sellOrder.getQuantity();
                    buyOrder.setQuantity(buyOrder.getQuantity() - sellOrder.getQuantity());
                    buyOrders.add(buyOrder);
                } else if (sellOrder.getQuantity() > buyOrder.getQuantity()) {
                    qty = buyOrder.getQuantity();
                    sellOrder.setQuantity(sellOrder.getQuantity() - buyOrder.getQuantity());
                    sellOrderEntry.getValue().add(sellOrder);
                } else {
                    qty = buyOrder.getQuantity();
                }
                return new ExecutedOrder(buyOrder.getOrderId(), sellOrder.getPrice(), qty, sellOrder.getOrderId());
            }
        }
        return null;
    }
}