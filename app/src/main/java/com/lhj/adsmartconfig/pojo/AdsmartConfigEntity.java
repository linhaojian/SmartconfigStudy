package com.lhj.adsmartconfig.pojo;

/**
 * Created by Administrator on 2018/3/9.
 */

public class AdsmartConfigEntity {
    private String ssid;
    private String pw;
    private String type;
    private String localIp;
    private String routerMac;

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocalIp() {
        return localIp;
    }

    public void setLocalIp(String localIp) {
        this.localIp = localIp;
    }

    public String getRouterMac() {
        return routerMac;
    }

    public void setRouterMac(String routerMac) {
        this.routerMac = routerMac;
    }

    @Override
    public String toString() {
        return "AdsmartConfigEntity{" +
                "ssid='" + ssid + '\'' +
                ", pw='" + pw + '\'' +
                ", type='" + type + '\'' +
                ", localIp='" + localIp + '\'' +
                ", routerMac='" + routerMac + '\'' +
                '}';
    }
}
