package com.cafe24.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GalleryController {
    @RequestMapping("/gallery")
    public String gallery() {
	return "gallery/index";
    }
}
