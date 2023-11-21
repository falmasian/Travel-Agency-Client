package com.flightagencyclient.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PaymentResponseDto {

    private float cost;

    public PaymentResponseDto(float cost) {
        this.cost = cost;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "PaymentResponseDto{" +
               "cost=" + cost +
               '}';
    }
}
