package com.flightagencyclient.dto;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class AllCitiesResponse {

    /**
     * لیست شهر ها
     */
    private List<CityDto> cityDtoList;

    public AllCitiesResponse(List<CityDto> cityDtoList) {
        this.cityDtoList = cityDtoList;
    }

    public List<CityDto> getCityDtoList() {
        return cityDtoList;
    }

    public void setCityDtoList(List<CityDto> cityDtoList) {
        this.cityDtoList = cityDtoList;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AllCitiesResponse{" );
        for(CityDto cityDto : cityDtoList){
            stringBuilder.append(cityDto.toString());
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}