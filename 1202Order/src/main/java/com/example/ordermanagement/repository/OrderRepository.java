package com.example.ordermanagement.repository;

import com.example.ordermanagement.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    // 根據狀態查詢訂單
    List<Order> findByStatus(String status);

    // 查詢某買家的所有訂單
    List<Order> findByBuyerId(Long buyerId);

    // 查詢某賣家的所有訂單
    List<Order> findBySellerId(Long sellerId);

    // 根據買家ID、賣家ID、狀態查詢訂單
    List<Order> findByBuyerIdAndSellerIdAndStatus(Long buyerId, Long sellerId, String status);

    // 根據買家ID和狀態查詢訂單
    List<Order> findByBuyerIdAndStatus(Long buyerId, String status);

    // 根據賣家ID和狀態查詢訂單
    List<Order> findBySellerIdAndStatus(Long sellerId, String status);

    // 根據買家ID和賣家ID查詢訂單
    List<Order> findByBuyerIdAndSellerId(Long buyerId, Long sellerId);

    // 根據日期範圍查詢訂單
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
