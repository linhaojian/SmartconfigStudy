package com.lhj.adsmartconfig;

/**
 * Created by Administrator on 2018/3/9.
 */

public interface IAdsmartConfigTask {

    IAdsmartConfigTask setSSid(String ssid);

    IAdsmartConfigTask setPw(String pw);

    IAdsmartConfigTask setResults(OnAdConOnResults onAdConOnResults);

    IAdsmartConfigTask start();

    IAdsmartConfigTask interrupt();

    IAdsmartConfigTask setType(String type);

}
