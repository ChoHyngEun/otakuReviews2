package com.example.ex3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/map")
public class MapController {

    @GetMapping("/paik")
    public String showPaikMap(Model model) {
        return "map_paik";
    }

    @GetMapping("/ediya")
    public String showEdiyaMap(Model model) {
        return "map_ediya";
    }

    @GetMapping("/starbucks")
    public String showStarbucksMap(Model model) {
        return "map_starbucks";
    }

    @GetMapping("/mega")
    public String showMegaMap(Model model) {
        return "map_mega";
    }
}
