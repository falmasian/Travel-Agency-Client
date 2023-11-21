package com.flightagencyclient.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CancellingResponseDto {

    private float cost;

    public CancellingResponseDto(float cost) {
        this.cost = cost;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "CancellingResponseDto{" +
               "cost=" + cost +
               '}';
    }
}
