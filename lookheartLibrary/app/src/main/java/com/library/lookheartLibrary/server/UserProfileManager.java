package com.library.lookheartLibrary.server;

public class UserProfileManager {

    private static UserProfileManager instance;

    private UserProfile userProfile;
    private String[] guardianNumbers;

    public static UserProfileManager getInstance() {
        if (instance == null) {
            instance = new UserProfileManager();
        }
        return instance;
    }

    public UserProfile getUserProfile(){
        return userProfile;
    }

    public void setUserProfile(UserProfile myUserProfile) {
        userProfile = myUserProfile;
    }

    public String[] getGuardianNumbers() {
        return guardianNumbers;
    }


    public void setGuardianNumbers(String[] numbers) {
        guardianNumbers = numbers;
    }
}


