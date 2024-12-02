package com.example.ordermanagement.service;

import com.example.ordermanagement.model.Order;
import com.example.ordermanagement.model.ShippingInfo;
import com.example.ordermanagement.repository.OrderRepository;
import com.example.ordermanagement.repository.ShippingInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShippingInfoRepository shippingInfoRepository;

    // 獲取所有訂單
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // 根據篩選條件查詢訂單
    public List<Order> getOrdersByFilters(Long buyerId, Long sellerId, String status) {
        if (buyerId != null && sellerId != null && status != null) {
            return orderRepository.findByBuyerIdAndSellerIdAndStatus(buyerId, sellerId, status);
        } else if (buyerId != null && sellerId != null) {
            return orderRepository.findByBuyerIdAndSellerId(buyerId, sellerId);
        } else if (buyerId != null && status != null) {
            return orderRepository.findByBuyerIdAndStatus(buyerId, status);
        } else if (sellerId != null && status != null) {
            return orderRepository.findBySellerIdAndStatus(sellerId, status);
        } else if (buyerId != null) {
            return orderRepository.findByBuyerId(buyerId);
        } else if (sellerId != null) {
            return orderRepository.findBySellerId(sellerId);
        } else if (status != null) {
            return orderRepository.findByStatus(status);
        } else {
            return getAllOrders();
        }
    }

    // 根據ID獲取訂單詳情
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // 更新訂單詳情（支持多字段更新）
    public boolean updateOrderDetails(Long orderId, String paymentStatus, String shippingStatus, String status) {
        return orderRepository.findById(orderId).map(order -> {
            // 棄單後禁止修改
            if ("Canceled".equals(order.getStatus())) {
                throw new IllegalStateException("訂單已被棄單，無法修改");
            }

            // 更新付款情形
            if (paymentStatus != null) {
                order.setPaymentStatus(paymentStatus);
            }

            // 更新物流情形
            if (shippingStatus != null) {
                order.setShippingStatus(shippingStatus);
            }

            // 更新訂單狀態
            if (status != null) {
                order.setStatus(status);
            }

            orderRepository.save(order);
            return true;
        }).orElse(false);
    }

    // 更新訂單並同步物流信息狀態
    public boolean updateOrderAndShipping(Long orderId, String newStatus) {
        return orderRepository.findById(orderId).map(order -> {
            // 棄單後禁止修改
            if ("Canceled".equals(order.getStatus())) {
                throw new IllegalStateException("訂單已被棄單，無法修改");
            }

            // 更新訂單狀態
            order.setStatus(newStatus);
            orderRepository.save(order);

            // 同步更新物流信息狀態
            List<ShippingInfo> shippingInfos = shippingInfoRepository.findByOrderId(orderId);
            for (ShippingInfo shippingInfo : shippingInfos) {
                shippingInfo.setStatus(newStatus);
                shippingInfoRepository.save(shippingInfo);
            }
            return true;
        }).orElse(false);
    }

    // 棄單功能
    public boolean cancelOrder(Long orderId) {
        return orderRepository.findById(orderId).map(order -> {
            if ("Canceled".equals(order.getStatus())) {
                throw new IllegalStateException("訂單已被標記為棄單");
            }

            // 標記訂單為棄單
            order.setStatus("Canceled");
            orderRepository.save(order);

            // 同步更新物流信息為棄單狀態
            List<ShippingInfo> shippingInfos = shippingInfoRepository.findByOrderId(orderId);
            for (ShippingInfo shippingInfo : shippingInfos) {
                shippingInfo.setStatus("Canceled");
                shippingInfoRepository.save(shippingInfo);
            }
            return true;
        }).orElse(false);
    }
}
