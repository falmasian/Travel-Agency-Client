package com.flightagencyclient.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReservationGetDto {

    /**
     * ایدی مشتری
     */
    private String customerId;

    /**
     * شماره پرواز
     */
    private int flightId;

    /**
     * کد پیگیری
     */
    private String trackingCode;

    /**
     * کد ملی مسافر
     */
    private String nationalCode;

    public ReservationGetDto(String customerId, int flightId, String trackingCode, String nationalCode) {
        this.customerId = customerId;
        this.flightId = flightId;
        this.trackingCode = trackingCode;
        this.nationalCode = nationalCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    @Override
    public String toString() {
        return "ReservationGetDto{" + "\n" +
               "    customerId=" + customerId + ",\n" +
               "    flightId=" + flightId + ",\n" +
               "    trackingCode=" + trackingCode + ",\n" +
               "    nationalCode='" + nationalCode + "\n" +
               '}' + "\n";
    }
}
