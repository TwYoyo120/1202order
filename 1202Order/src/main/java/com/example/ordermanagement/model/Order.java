package com.example.ordermanagement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders") // 小寫表名，避免大小寫敏感問題
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number", nullable = false, unique = true)
    private String orderNumber; // 訂單編號

    @Column(name = "status", nullable = false)
    private String status; // 訂單狀態

    @Column(name = "total", nullable = false)
    private Double total; // 總金額

    @Column(name = "buyer_id", nullable = false)
    private Long buyerId; // 買家ID

    @Column(name = "seller_id", nullable = false)
    private Long sellerId; // 賣家ID

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate; // 下單時間

    @Column(name = "product_id")
    private Long productId; // 商品 ID

    @Column(name = "product_name")
    private String productName; // 商品名稱

    @Column(name = "quantity")
    private Integer quantity; // 商品數量

    @Column(name = "payment_status")
    private String paymentStatus; // 付款情形

    @Column(name = "shipping_status")
    private String shippingStatus; // 物流情形

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }
}
