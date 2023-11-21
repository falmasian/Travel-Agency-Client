package com.flightagencyclient.dto;

public class CityDto {
    /**
     * نام شهر
     */
    String cityName;

    public CityDto() {
    }

    public CityDto(String cityName) {
        this.cityName = cityName;
    }


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "CityDto{" + "\n" +
               "    cityName=" + cityName + ",\n" +
               '}' + "\n";
    }
}
