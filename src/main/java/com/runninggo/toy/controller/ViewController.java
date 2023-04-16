package com.runninggo.toy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String main() {
        return "/layout/main";
    }

    @GetMapping("/login/loginForm")
    public String loginForm() {
        return "/member/loginForm";
    }

    @GetMapping("/login/findIdForm")
    public String findIdForm() {
        return "/member/findIdForm";
    }

    @GetMapping("/login/findPassForm")
    public String findPassForm() {
        return "/member/findPassForm";
    }

    @GetMapping("/join/joinForm")
    public String joinForm() {
        return "/member/joinForm";
    }

    @GetMapping("/place/recmndForm")
    public String placeRcmndForm() {
        return "/place/recmndForm";
    }

    @GetMapping("/place/writeForm")
    public String writeForm() {
        return "/place/writeForm";
    }
}
