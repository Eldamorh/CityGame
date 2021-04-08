package com.eldus.controllers;

import com.eldus.CityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class GameController {
    private final CityService cityService;
    private Map<String, String> gameSessions;
    public GameController(CityService cityService) {
        this.cityService = cityService;
        gameSessions = new HashMap<>();
    }



    @RequestMapping(value = "/game", method = RequestMethod.GET,produces = "text/plain;charset=UTF-8")
    public String printGame(ModelMap model,@RequestParam(required = true) String id) {
        gameSessions.put(id,null);
        cityService.newGameTable(id.replace("-","_"));

        return "hello";
    }

    @PostMapping("/game")
    public String check(ModelMap model,@RequestParam String fname, @RequestParam(required = true) String id) {
        if(gameSessions.get(id)==null || cityService.lastLetterCheck(gameSessions.get(id))==fname.charAt(0)){
            String s = cityService.findRandomCity(fname,id.replace("-","_"));
            if(s.equals("Такого города не существует!")){
                model.addAttribute("message", s +" " + gameSessions.get(id) );
                return "hello";
            }
            if(s.equals("Сдаюсь")){
                return "win";
            }
            gameSessions.put(id,s);
            model.addAttribute("message",s);
        System.out.println(model.getAttribute("message"));

        return "hello";
        }
        else {
            model.addAttribute("message", "Неправильный город " + gameSessions.get(id) );
            return "hello";
        }
    }

}


