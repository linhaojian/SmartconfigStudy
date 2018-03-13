package com.lhj.adsmartconfig.pojo;

import java.util.List;

/**
 * Created by Administrator on 2018/3/9.
 */

public class ClientEnity {
    private List<byte[]> datas;
    private String[] ips;

    public List<byte[]> getDatas() {
        return datas;
    }

    public void setDatas(List<byte[]> datas) {
        this.datas = datas;
    }

    public String[] getIps() {
        return ips;
    }

    public void setIps(String[] ips) {
        this.ips = ips;
    }
}
