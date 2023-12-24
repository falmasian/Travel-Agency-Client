package com.flightagencyclient.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReservationResponseDto {

    /**
     * کد پیگیری
     */
    private String tracingCode;

    public ReservationResponseDto(String tracingCode) {
        this.tracingCode = tracingCode;
    }

    public String getTracingCode() {
        return tracingCode;
    }

    public void setTracingCode(String tracingCode) {
        this.tracingCode = tracingCode;
    }

    @Override
    public String toString() {
        return "ReservationResponseDto{" +
               "tracingCode='" + tracingCode + '\'' +
               '}';
    }
}
