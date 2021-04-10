package com.eldus;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CityServiceDbImpl implements CityService {

    private final JdbcTemplate jdbcTemplate;
    private final static String findCitySQL = "SELECT name FROM game%s WHERE name LIKE \'%s%%\' LIMIT 1;";
    private final static String isReal = "SELECT name FROM game%s WHERE name = '%s';";
    private final static String tableChecker = "SELECT name FROM sqlite_master WHERE type='table' AND name='game%s';";


    public CityServiceDbImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String findRandomCity(String cityName, String uuid) {
        if (!isReal(cityName, uuid)) {
            return "Такого города не существует!";
        }
        deleteCity(cityName, uuid);
        List<Map<String, Object>> list = jdbcTemplate
                .queryForList(String.format(findCitySQL, uuid, lastLetterCheck(cityName)));


        if (list.isEmpty()) {
            jdbcTemplate.execute(String.format("DROP TABLE game%s", uuid));
            return "Сдаюсь";
        }
        deleteCity(list.get(0).get("name").toString(), uuid);
        return list.get(0).get("name").toString();
    }

    @Override
    public boolean isReal(String cityName, String uuid) {
        return !jdbcTemplate.queryForList(String.format(isReal, uuid, cityName)).isEmpty();
    }

    @Override
    public void newGameTable(String uuid) {
        if (jdbcTemplate.queryForList(String.format(tableChecker, uuid)).isEmpty()) {
            jdbcTemplate.execute(String.format("CREATE TABLE IF NOT EXISTS game%s(name TEXT);", uuid));
            jdbcTemplate.execute(String.format("INSERT INTO game%s SELECT name FROM cities;", uuid));
        }
    }

    @Override
    public void deleteCity(String city, String uuid) {
        jdbcTemplate.execute(String.format("DELETE FROM game%s WHERE name='%s';", uuid, city));
    }


    public char lastLetterCheck(String cityName) {
        if (cityName.charAt(cityName.length() - 1) == 'ь' || cityName.charAt(cityName.length() - 1) == 'ы') {
            return Character.toUpperCase(cityName.charAt(cityName.length() - 2));
        } else {
            return Character.toUpperCase(cityName.charAt(cityName.length() - 1));
        }

    }
}
