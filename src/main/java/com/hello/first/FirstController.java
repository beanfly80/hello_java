package com.hello.first;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

//// compile error
//// No primary or single unique constructor found for interface javax.servlet.http.HttpServletResponse
//// solution
//// the Servlet specification in Jakarta EE 8 uses a javax.servlet package
//// but this has changed to jakarta.servlet in EE 9.
//import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FirstController {
    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("home");
    }

    @RequestMapping(value = "/test_static/test", method = {RequestMethod.GET, RequestMethod.POST})
    public String test() {
        return "Hello test1";
    }

    @GetMapping(value = "/chat")
    public ModelAndView chat(HttpServletResponse response) {
        return new ModelAndView("chat");
    }
}