package com.flightagencyclient.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReservationDto {
    /**
     * کد ملی
     */
    String nationalCode;

    /**
     * شماره پرواز
     */
    int flightId;

    public ReservationDto(String nationalCode) {
        this.nationalCode = nationalCode;
        this.flightId = 0;
    }

    public ReservationDto(String nationalCode, int flightId) {
        this.nationalCode = nationalCode;
        this.flightId = flightId;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("ReservationDto{" + "\n" + "    nationalCode=")
                .append(nationalCode)
                .append("\n");
        if (flightId != 0) {
            stringBuilder
                    .append("    flightId=")
                    .append(flightId)
                    .append("\n");
        }
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }
}
