package com.flightagencyclient.dto;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class FilterResponseDto {

    private List<FlightDto> flightDtoList;

    public FilterResponseDto(List<FlightDto> flightDtoList) {
        this.flightDtoList = flightDtoList;
    }

    public List<FlightDto> getFlightDtoList() {
        return flightDtoList;
    }

    public void setFlightDtoList(List<FlightDto> flightDtoList) {
        this.flightDtoList = flightDtoList;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AllFilterFlightsResponse{" );
        for(FlightDto flightDto : flightDtoList){
            stringBuilder.append(flightDto.toString());
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
