package com.library.lookheartLibrary.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class dateController {

    public static String dateCalculate(String targetDate, int myDay, boolean check) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(targetDate, formatter);

        if(check){
            // tomorrow
            date = date.plusDays(myDay);
        } else{
            // yesterday
            date = date.minusDays(myDay);
        }

        String resultDate = date.format(formatter);

        return resultDate;

        /*
        java.util.Date와 java.time.LocalDate는 Java의
        서로 다른 날짜/시간 API를 나타내는 클래스로, 서로 호환되지 않음
        */

//        date = LocalDate.parse(targetDate, formatter);
//        formatter = DateTimeFormatter.ofPattern("yyyy");
//        String targetYear = date.format(formatter);
//
//        formatter = DateTimeFormatter.ofPattern("MM");
//        String targetMonth = date.format(formatter);
//
//        formatter = DateTimeFormatter.ofPattern("dd");
//        String targetDay = date.format(formatter);
    }
}
