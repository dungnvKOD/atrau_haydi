package com.atrau.guider_haydi;

import android.app.Application;
import com.atrau.guider_haydi.dto.Conversations;
import com.atrau.guider_haydi.dto.Friends;
import com.atrau.guider_haydi.dto.GuideDto;

public class App extends Application {

    private static App insatnce;
    private double lat;
    private double lon;
    private String token;
    private String paymentType;
    private int idHost;
    private int idTrip;
    private String tripStatus;
    private boolean checkNotifi;
    private Conversations conversations;
    private GuideDto guideDto;
    private Friends friends;
    private String checkMessage;

    @Override
    public void onCreate() {
        super.onCreate();
        insatnce = this;
    }

    public static App getMyInsatnce() {

        String token;
        if (insatnce == null) {
            insatnce = new App();
        }
        return insatnce;
    }

    public GuideDto getGuideDto() {
        return guideDto;
    }

    public void setGuideDto(GuideDto guideDto) {
        this.guideDto = guideDto;
    }

    public String getCheckMessage() {
        return checkMessage;
    }

    public void setCheckMessage(String checkMessage) {
        this.checkMessage = checkMessage;
    }

    public Friends getFriends() {
        return friends;
    }

    public void setFriends(Friends friends) {
        this.friends = friends;
    }


    public Conversations getConversations() {
        return conversations;
    }

    public void setConversations(Conversations conversations) {
        this.conversations = conversations;
    }

    public boolean isCheckNotifi() {
        return checkNotifi;
    }

    public void setCheckNotifi(boolean checkNotifi) {
        this.checkNotifi = checkNotifi;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public int getIdHost() {
        return idHost;
    }

    public void setIdHost(int idHost) {
        this.idHost = idHost;
    }

    public double getLat() {
        return lat;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }
}
