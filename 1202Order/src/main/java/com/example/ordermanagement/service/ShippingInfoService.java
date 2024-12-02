package com.example.ordermanagement.service;

import com.example.ordermanagement.model.Order;
import com.example.ordermanagement.model.ShippingInfo;
import com.example.ordermanagement.repository.OrderRepository;
import com.example.ordermanagement.repository.ShippingInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShippingInfoService {

    @Autowired
    private ShippingInfoRepository shippingInfoRepository;

    @Autowired
    private OrderRepository orderRepository;

    // 查詢訂單和物流信息的關聯數據
    public List<Map<String, Object>> getOrderShippingInfo(Long orderId, String shippingStatus, Long buyerId) {
        List<Order> orders;

        if (buyerId != null) {
            // 通過買家 ID 查詢訂單
            orders = orderRepository.findByBuyerId(buyerId);
        } else if (orderId != null) {
            // 查詢指定訂單
            orders = orderRepository.findById(orderId)
                    .map(Collections::singletonList)
                    .orElse(Collections.emptyList());
        } else {
            // 獲取所有訂單
            orders = orderRepository.findAll();
        }

        List<Map<String, Object>> results = new ArrayList<>();
        for (Order order : orders) {
            List<ShippingInfo> shippingInfos = shippingInfoRepository.findByOrderId(order.getId());
            for (ShippingInfo shippingInfo : shippingInfos) {
                // 如果有物流狀態篩選條件，過濾數據
                if (shippingStatus == null || shippingInfo.getStatus().equalsIgnoreCase(shippingStatus)) {
                    Map<String, Object> result = new HashMap<>();
                    result.put("order", order);
                    result.put("shippingInfo", shippingInfo);
                    results.add(result);
                }
            }
        }
        return results;
    }

    // 獲取所有物流信息
    public List<ShippingInfo> getAllShippingInfos() {
        return shippingInfoRepository.findAll();
    }

    // 根據狀態篩選物流信息
    public List<ShippingInfo> getShippingInfosByStatus(String status) {
        return shippingInfoRepository.findByStatus(status);
    }

    // 根據訂單ID篩選物流信息
    public List<ShippingInfo> getShippingInfosByOrderId(Long orderId) {
        return shippingInfoRepository.findByOrderId(orderId);
    }

    // 根據買家ID篩選物流信息
    public List<ShippingInfo> getShippingInfosByBuyerId(Long buyerId) {
        // 從訂單中查詢所有該買家的訂單ID
        List<Long> orderIds = orderRepository.findByBuyerId(buyerId).stream()
                .map(Order::getId)
                .toList(); // 使用 Java Stream API 獲取訂單ID列表

        // 查詢物流信息，匹配這些訂單ID
        return shippingInfoRepository.findByOrderIdIn(orderIds);
    }

    // 根據ID獲取物流詳情
    public Optional<ShippingInfo> getShippingInfoById(Long id) {
        return shippingInfoRepository.findById(id);
    }

    // 更新物流信息
    public boolean updateShippingInfo(Long id, ShippingInfo updatedInfo) {
        return shippingInfoRepository.findById(id).map(info -> {
            info.setRecipient(updatedInfo.getRecipient());
            info.setAddress(updatedInfo.getAddress());
            info.setStatus(updatedInfo.getStatus());
            shippingInfoRepository.save(info);
            return true;
        }).orElse(false);
    }
}
