package com.lazydevs.tinylens.Model;

import com.google.firebase.database.Exclude;

public class ModelOrder {

    String ContactNo;
    String OrderedImageUrl;
    String OrderProductType;
    String OrderId;
    String OrderDescription;
    String OrderDate;
    String OrderStatus;
    String PhotoOwnerId;
    String BuyerId;
    String Quantity;
    String TransactionId;

    @Exclude
    String buyerName, ownerName;

    @Exclude
    public String getBuyerName() {
        return buyerName;
    }

    @Exclude
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    @Exclude
    public String getOwnerName() {
        return ownerName;
    }

    @Exclude
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public ModelOrder() {
    }

    public ModelOrder(String contactNo, String orderedImageUrl, String orderProductType, String orderId, String orderDescription, String orderDate, String orderStatus, String photoOwnerId, String buyerId, String quantity, String transactionId) {
        ContactNo = contactNo;
        OrderedImageUrl = orderedImageUrl;
        OrderProductType = orderProductType;
        OrderId = orderId;
        OrderDescription = orderDescription;
        OrderDate = orderDate;
        OrderStatus = orderStatus;
        PhotoOwnerId = photoOwnerId;
        BuyerId = buyerId;
        Quantity = quantity;
        TransactionId = transactionId;
    }


    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getOrderedImageUrl() {
        return OrderedImageUrl;
    }

    public void setOrderedImageUrl(String orderedImageUrl) {
        OrderedImageUrl = orderedImageUrl;
    }

    public String getOrderProductType() {
        return OrderProductType;
    }

    public void setOrderProductType(String orderProductType) { OrderProductType = orderProductType; }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrderDescription() {
        return OrderDescription;
    }

    public void setOrderDescription(String orderDescription) { OrderDescription = orderDescription; }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getPhotoOwnerId() {
        return PhotoOwnerId;
    }

    public void setPhotoOwnerId(String photoOwnerId) {
        PhotoOwnerId = photoOwnerId;
    }

    public String getBuyerId() {
        return BuyerId;
    }

    public void setBuyerId(String buyerId) {
        BuyerId = buyerId;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getTransactionId() { return TransactionId; }

    public void setTransactionId(String transactionId) { TransactionId = transactionId; }

}
