package com.cdx;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Stat_service {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(
                "classpath*:spring/applicationContext-*.xml");
        ac.start();
        System.out.println("启动成功");
        System.in.read();
    }
}
