package com.porfolio.online_store.dto.order;

import com.porfolio.online_store.dto.user.UserDto;
import com.porfolio.online_store.model.order.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class OrderDto {
    private UUID id;
    private UserDto customer;
    private UserDto seller;
    private OrderStatus status;
    private String shippingAddress;
    private BigDecimal totalPrice;
    private List<OrderItemDto> items = new ArrayList<>();
    private Timestamp shippedAt;
    private Timestamp deliveredAt;
    private String cancellationReason;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
