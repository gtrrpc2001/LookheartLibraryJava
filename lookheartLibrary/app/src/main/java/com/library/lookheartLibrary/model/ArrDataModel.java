package com.library.lookheartLibrary.model;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ArrDataModel {
    private String time;
    private String timezone;
    private String bodyState;
    private String arrType;
    private Double[] ecgData;

    public ArrDataModel(String data) {
        String[] parts = data.split(",");

        this.time = parts[0];
        this.timezone = parts[1];
        this.bodyState = parts[2];
        this.arrType = parts[3];

        ecgData = Arrays.stream(parts, 4, parts.length)
                .map(Double::parseDouble)
                .toArray(Double[]::new);
    }

    public  Double[] setResultPeakData(Double[] prefixEcgData) {
        return ArrayUtils.addAll(prefixEcgData, ecgData);
    }

    // Getter
    public String getTime() {
        return time;
    }

    public String getTimeZone() {
        return timezone;
    }

    public String getBodyState() {
        return bodyState;
    }

    public String getArrType() {
        return arrType;
    }

    public Double[] getEcgData() {
        return ecgData;
    }

}
