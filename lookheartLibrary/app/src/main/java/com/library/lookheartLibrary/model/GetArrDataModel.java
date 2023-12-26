package com.library.lookheartLibrary.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetArrDataModel {
    private String ecg;
    private String arr;

    // Getter
    // Setter
    public String getArr() {
        return arr;
    }

    public void setWritetime(String arr) {
        this.arr = arr;
    }

    public String getEcg() {
        return ecg;
    }

    public void setAddress(String ecg) {
        this.ecg = ecg;
    }

    public Double[] parseToSingleDoubleArray() {
        if (ecg == null || ecg.isEmpty()) {
            return new Double[0]; // 빈 배열 반환
        }

        String[] arrayStrings;
        int totalLength = 0; // 총 길이를 저장할 변수

        // ECG 14개 데이터 사이의 세미 콜론(;) 확인
        if (ecg.contains(";"))
            arrayStrings = ecg.split("\\];\\[");
        else
            arrayStrings = ecg.split("\\]\\[");

        // 각 배열의 길이를 계산
        for (String arrayString : arrayStrings) {
            String[] parts = arrayString
                    .replace("[", "")
                    .replace("]", "")
                    .replace(";", "")
                    .split(",\\s*");

            totalLength += parts.length;
        }

        // 새 배열 생성
        Double[] mergedArray = new Double[totalLength];
        int currentPosition = 0; // 현재 복사 위치

        // 각 배열의 요소들을 새 배열에 복사
        for (String arrayString : arrayStrings) {
            String[] parts = arrayString.replace("[", "").replace("]", "").replace(";", "").split(",\\s*");

            for (String part : parts) {
                mergedArray[currentPosition++] = Double.parseDouble(part);
            }
        }

        return mergedArray;
    }
}
