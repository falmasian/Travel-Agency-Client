package com.flightagencyclient.dto;

import java.util.ArrayList;

public class CancellationDto {

    /**
     * ایدی مشتری
     */
    private String customerId;

    /**
     *   شماره پرواز
     */
    private int flightId;

    /**
     * کد ملی های کنسلی مسافران
     */
    private ArrayList<String> nationalCodes;

    public CancellationDto() {
    }

    public CancellationDto(String customerId,int flightId, ArrayList<String> nationalCodes) {
        this.customerId = customerId;
        this.flightId = flightId;
        this.nationalCodes = nationalCodes;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public ArrayList<String> getNationalCodes() {
        return nationalCodes;
    }

    public void setNationalCodes(ArrayList<String> nationalCodes) {
        this.nationalCodes = nationalCodes;
    }

    public int getFlightId() {
        return flightId;
    }

    @Override
    public String toString() {
        return "CancellationDto{" + "\n" +
               "    customerId=" + customerId + ",\n" +
               "    nationalCodes=" + nationalCodes + "\n" +
               '}' + "\n";
    }
}
