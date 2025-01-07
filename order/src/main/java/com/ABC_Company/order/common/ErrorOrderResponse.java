package com.ABC_Company.order.common;

import lombok.Getter;

@Getter
public class ErrorOrderResponse  implements OrderResponse{
    private final String errorMessage;

    public ErrorOrderResponse(String errorMessage){
        this.errorMessage = errorMessage;
    }
}
