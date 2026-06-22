package com.porfolio.online_store.model.order;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class OrderCancelRequest {

    private UUID id;
    private String cancellationReason;
}
