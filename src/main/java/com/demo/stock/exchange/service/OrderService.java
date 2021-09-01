package com.demo.stock.exchange.service;

import com.demo.stock.exchange.repository.Market;
import com.demo.stock.exchange.model.ExecutedOrder;
import com.demo.stock.exchange.model.Order;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

import static com.demo.stock.exchange.model.Order.OrderType.BUY;

public class OrderService {

    private Market market;

    public OrderService() {
        market = new Market();
    }

    public void executeOrder(String orderFilePath) throws Exception {
        List<Order> orders = getOrdersFromFile(orderFilePath);

        PriorityQueue<Order> buyOrders = new PriorityQueue<>();
        for (Order order : orders) {
            if (BUY == order.getOrderType()) {
                ExecutedOrder executedOrder = market.executeOrder(order, buyOrders);
                if (Objects.nonNull(executedOrder)) {
                    System.out.println(executedOrder);
                } else {
                    buyOrders.add(order);
                }
            } else {
                market.addSellOrder(order);
            }
        }

        while (!buyOrders.isEmpty()) {
            Order order = buyOrders.poll();
            ExecutedOrder executedOrder = market.executeOrder(order, buyOrders);
            if (Objects.nonNull(executedOrder)) {
                System.out.println(executedOrder);
            }
        }
    }

    private List<Order> getOrdersFromFile(String orderFilePath) throws Exception {
        if (orderFilePath == null || orderFilePath.length() == 0) {
            return null;
        }
        List<Order> orderList = new ArrayList<>();
        try {
            File file = new File(orderFilePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String currentOrder = reader.readLine();
            while (currentOrder != null) {
                orderList.add(new Order(currentOrder));
                currentOrder = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            throw new Exception("Invalid Order File");
        }
        return orderList;
    }
}
