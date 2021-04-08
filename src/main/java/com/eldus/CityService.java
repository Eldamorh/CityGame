package com.eldus;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CityService {

    private final JdbcTemplate jdbcTemplate;

    public CityService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void addCity(City city) {
        jdbcTemplate.execute("INSERT INTO cities VALUES('" + city.getId() + "', '" + city.getName() + "');");
    }

    public String findRandomCity(String cityName, String uuid) {
        if (!isReal(cityName, uuid)) {
            return "Такого города не существует!";
        }
        deleteCity(cityName, uuid);
        List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT name FROM game" + uuid + " WHERE name LIKE '"
                + lastLetterCheck(cityName)
                + "%' LIMIT 1;");


        if (list.isEmpty()) {
            jdbcTemplate.execute("DROP TABLE game" + uuid);
            return "Сдаюсь";
        }
        deleteCity(list.get(0).get("name").toString(), uuid);
        return list.get(0).get("name").toString();
    }

    public boolean isReal(String cityName, String uuid) {
        if (jdbcTemplate.queryForList("SELECT name FROM game" + uuid + " WHERE name = '" + cityName + "';").isEmpty()) {
            return false;
        }
        return true;
    }

    public void newGameTable(String uuid) {
        if (jdbcTemplate.queryForList("SELECT name FROM sqlite_master WHERE type='table' AND name='game" + uuid + "';").isEmpty()) {
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS game" + uuid + "(name TEXT);");

            jdbcTemplate.execute("INSERT INTO game" + uuid + " SELECT name FROM cities;");
        }
    }

    public void deleteCity(String city, String uuid) {
        jdbcTemplate.execute("DELETE FROM game" + uuid + " WHERE name='" + city + "';");
    }

    public List<String> getCities() {
        List<String> cities = new ArrayList<>();
        List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM cities;");
        for (Map<String, Object> map : list) {
            cities.add(map.get("name").toString());
        }
        return cities;
    }


    public char lastLetterCheck(String cityName) {
        if (cityName.charAt(cityName.length() - 1) == 'ь' || cityName.charAt(cityName.length() - 1) == 'ы') {
            return Character.toUpperCase(cityName.charAt(cityName.length() - 2));
        } else {
            return Character.toUpperCase(cityName.charAt(cityName.length() - 1));
        }

    }
}
