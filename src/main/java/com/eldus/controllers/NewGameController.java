package com.eldus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
public class NewGameController {

    @RequestMapping(value = "/newGame", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String printGame(ModelMap model) {
        return "mainMenu";
    }

    @RequestMapping(value = "/newGame", method = RequestMethod.POST)
    public ModelAndView method() {

        return new ModelAndView("redirect:" + "http://localhost:8080/game?id=" + UUID.randomUUID().toString());
    }


}
