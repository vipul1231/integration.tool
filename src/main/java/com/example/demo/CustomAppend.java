package com.example.demo;

import org.springframework.stereotype.Component;

public class CustomAppend {

    public CustomAppend() {
        System.out.println("CUSTOM APPEND");
    }

    public String app(String name) {
        System.out.println("Append Called :"+name);
        return name+" append";
    }
}
