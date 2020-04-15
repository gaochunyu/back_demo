package com.cennavi.tp.controller;

import com.cennavi.tp.beans.BaseRtic;
import com.cennavi.tp.utils.GeomtryUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/hello")
public class HelloWorld {


    @ResponseBody
    @RequestMapping("/test")
    public String test() {
        try {
            System.out.println("Hello World!");
        } catch (Exception e) {
            e.getStackTrace();
        }
        return "Hello World!";
    }

}
