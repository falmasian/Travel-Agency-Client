package com.flightagencyclient.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FilterFlightDto {

    /**
     * ایدی شهر مبدا
     */
    private int originId;

    /**
     * ایدی شهر مقصد
     */
    private int destinationId;

    /**
     * تاریخ پرواز
     */
    private java.sql.Date flyDate;

    public FilterFlightDto(int originId, int destinationId, java.sql.Date flyDate) {
        this.originId = originId;
        this.destinationId = destinationId;
        this.flyDate = flyDate;
    }

    public int getOriginId() {
        return originId;
    }

    public void setOriginId(int originId) {
        this.originId = originId;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public java.sql.Date getFlyDate() {
        return flyDate;
    }

    public void setFlyDate(java.sql.Date flyDate) {
        this.flyDate = flyDate;
    }

    @Override
    public String toString() {
        return "FilterFlightDto{" + "\n" +
               "    originId=" + originId + ",\n" +
               "    destinationId=" + destinationId + ",\n" +
               "    flyDate=" + flyDate + "\n" +
               "}\n";
    }
}
