package com.jwy.jd.model;

import java.io.Serializable;

/**
 * Created by zhangxd on 2015/7/21.
 */
public class LocationInfo implements Serializable {
    private String latitude = null;
    private String lontitude = null;
    private String radius = null;
    private String address = null;
    private String province=null;
    private String city=null;
    private String speed = null;
    private String satellite = null;
    private String direction = null;
    private String operationers = null;
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLontitude() {
        return lontitude;
    }

    public void setLontitude(String lontitude) {
        this.lontitude = lontitude;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
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
