package com.chaqianma.jd.model;

import java.io.Serializable;

/**
 * Created by zhangxd on 2015/7/21.
 */
public class LocationInfo extends ErrorInfo implements Serializable {
    private double latitude = .0d;
    private double longitude =.0d;
    private float radius = .0f;
    private String address = null;
    private String province=null;
    private String city=null;
    private String speed = null;
    private String satellite = null;
    private String direction = null;
    private String operationers = null;
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getSatellite() {
        return satellite;
    }

    public void setSatellite(String satellite) {
        this.satellite = satellite;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getOperationers() {
        return operationers;
    }

    public void setOperationers(String operationers) {
        this.operationers = operationers;
    }
}
