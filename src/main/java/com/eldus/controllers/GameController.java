package com.eldus.controllers;

import com.eldus.CityServiceDbImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class GameController {
    private final CityServiceDbImpl cityServiceDbImpl;
    private Map<String, String> gameSessions;

    public GameController(CityServiceDbImpl cityServiceDbImpl) {
        this.cityServiceDbImpl = cityServiceDbImpl;
        gameSessions = new HashMap<>();
    }


    @RequestMapping(value = "/game", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String printGame(ModelMap model, @RequestParam String id) {
        gameSessions.put(id, null);
        cityServiceDbImpl.newGameTable(id.replace("-", "_"));

        return "hello";
    }

    @PostMapping("/game")
    public String check(ModelMap model, @RequestParam String fname, @RequestParam String id) {
        if (gameSessions.get(id) == null || cityServiceDbImpl.lastLetterCheck(gameSessions.get(id)) == fname.charAt(0)) {
            String s = cityServiceDbImpl.findRandomCity(fname, id.replace("-", "_"));
            if (s.equals("Такого города не существует!")) {
                model.addAttribute("message", s + " " + gameSessions.get(id));
                return "hello";
            }
            if (s.equals("Сдаюсь")) {
                return "win";
            }
            gameSessions.put(id, s);
            model.addAttribute("message", s);
            System.out.println(model.getAttribute("message"));

            return "hello";
        } else {
            model.addAttribute("message", "Неправильный город " + gameSessions.get(id));
            return "hello";
        }
    }

}


