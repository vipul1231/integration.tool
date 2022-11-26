package com.example.demo;

import org.springframework.stereotype.Component;

public class CustomAppend {

    public CustomAppend() {
        System.out.printf("CUSTOM APPEND");
    }

    public String app(String name) {
        System.out.println("Append Called");
        return name+" append";
    }
}
