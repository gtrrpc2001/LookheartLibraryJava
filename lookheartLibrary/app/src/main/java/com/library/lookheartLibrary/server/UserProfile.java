package com.library.lookheartLibrary.server;

import com.google.gson.annotations.SerializedName;

public class UserProfile {
    // Gson 응답 파싱
    @SerializedName("eq")
    private String id;

    @SerializedName("eqname")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("userphone")
    private String phone;

    @SerializedName("sex")
    private String gender;

    @SerializedName("height")
    private String height;

    @SerializedName("weight")
    private String weight;

    @SerializedName("age")
    private String age;

    @SerializedName("birth")
    private String birthday;

    @SerializedName("signupdate")
    private String joinDate;

    @SerializedName("sleeptime")
    private String sleepStart;

    @SerializedName("uptime")
    private String sleepEnd;

    @SerializedName("bpm")
    private String activityBPM;

    @SerializedName("step")
    private String dailyStep;

    @SerializedName("distanceKM")
    private String dailyDistance;

    @SerializedName("calexe")
    private String dailyActivityCalorie;

    @SerializedName("cal")
    private String dailyCalorie;

    @SerializedName("alarm_sms")
    private String ecgFlag;

    @SerializedName("differtime")
    private String timeDifference;

    @SerializedName("phone")
    private String guardian;

    // getters
    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getPhone(){
        return phone;
    }

    public String getGender(){
        return gender;
    }

    public String getHeight(){
        return height;
    }

    public String getWeight(){
        return weight;
    }

    public String getAge(){
        return age;
    }

    public String getBirthday(){
        return birthday;
    }

    public String getJoinDate(){
        return joinDate;
    }

    public String getSleepStart(){
        return sleepStart;
    }

    public String getSleepEnd(){
        return sleepEnd;
    }
    public String getActivityBPM(){
        return activityBPM;
    }
    public String getDailyStep(){
        return dailyStep;
    }
    public String getDailyDistance(){
        return dailyDistance;
    }
    public String getDailyCalorie(){
        return dailyCalorie;
    }
    public String getDailyActivityCalorie(){
        return dailyActivityCalorie;
    }
    public String getGuardian(){
        return guardian;
    }

    public String getEcgFlag() {
        return ecgFlag;
    }

    // setter
    public void setId(String value) {
        id = value;
    }
    public void setName(String value) {
        name = value;
    }
    public void setEmail(String value) {
        email = value;
    }
    public void setPhone(String value){
        phone = value;
    }
    public void setGender(String value){
        gender = value;
    }
    public void setHeight(String value){
        height = value;
    }
    public void setWeight(String value){
        weight = value;
    }
    public void setAge(String value){
        age = value;
    }
    public void setBirthday(String value){
        birthday = value;
    }
    public void setJoinDate(String value){
        joinDate = value;
    }
    public void setSleepStart(String value){
        sleepStart = value;
    }
    public void setSleepEnd(String value){
        sleepEnd = value;
    }
    public void setActivityBPM(String value){
        activityBPM = value;
    }
    public void setDailyStep(String value){
        dailyStep = value;
    }
    public void setDailyDistance(String value){
        dailyDistance = value;
    }
    public void setDailyCalorie(String value){
        dailyCalorie = value;
    }
    public void setDailyActivityCalorie(String value){
        dailyActivityCalorie = value;
    }
    public void setGuardian(String value){
        guardian = value;
    }
}

