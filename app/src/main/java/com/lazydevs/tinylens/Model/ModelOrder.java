package com.lazydevs.tinylens.Model;

public class ModelOrder {

    String ContactNo,OrderedImageUrl,OrderProductType,OrderId,OrderDescription,OrderDate,OrderStatus,PhotoOwnerId,BuyerId;


    public ModelOrder(String contactNo, String orderedImageUrl, String orderProductType, String orderId, String orderDescription, String orderDate, String orderStatus, String photoOwnerId, String buyerId) {
        ContactNo = contactNo;
        OrderedImageUrl = orderedImageUrl;
        OrderProductType = orderProductType;
        OrderId = orderId;
        OrderDescription = orderDescription;
        OrderDate = orderDate;
        OrderStatus = orderStatus;
        PhotoOwnerId = photoOwnerId;
        BuyerId = buyerId;
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

    public void setOrderProductType(String orderProductType) {
        OrderProductType = orderProductType;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrderDescription() {
        return OrderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        OrderDescription = orderDescription;
    }

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

}
