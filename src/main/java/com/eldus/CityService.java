package com.eldus;

public interface CityService {
    String findRandomCity(String cityName, String uuid);

    boolean isReal(String cityName, String uuid);

    void newGameTable(String uuid);

    void deleteCity(String city, String uuid);

}
