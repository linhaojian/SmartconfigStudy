package com.lhj.adsmartconfig;

import android.content.Context;

import com.lhj.adsmartconfig.task._AdasmartConfigTask;
import com.lhj.adsmartconfig.util.AdConNetUtil;


/**
 * Created by Administrator on 2018/3/9.
 */

public class AdsmartConfigTask implements IAdsmartConfigTask {
    private AdsmartConfigTaskParameter adsmartConfigTaskParameter;
    private _AdasmartConfigTask _adasmartConfigTask;
    private Context context;

    public AdsmartConfigTask(Context context){
        this.context = context;
        adsmartConfigTaskParameter = new AdsmartConfigTaskParameter();
        _adasmartConfigTask = new _AdasmartConfigTask(adsmartConfigTaskParameter);
    }

    @Override
    public IAdsmartConfigTask setSSid(String ssid) {
        adsmartConfigTaskParameter.getAdsmartConfigEntity().setSsid(ssid);
        return this;
    }

    @Override
    public IAdsmartConfigTask setPw(String pw) {
        adsmartConfigTaskParameter.getAdsmartConfigEntity().setPw(pw);
        return this;
    }

    @Override
    public IAdsmartConfigTask setResults(OnAdConOnResults onAdConOnResults) {
        _adasmartConfigTask.setResults(onAdConOnResults);
        return this;
    }

    @Override
    public IAdsmartConfigTask start() {
        adsmartConfigTaskParameter.getAdsmartConfigEntity().setLocalIp(AdConNetUtil.getLocalInetAddress(context));
        adsmartConfigTaskParameter.getAdsmartConfigEntity().setRouterMac(AdConNetUtil.getConnectedWifiMacAddress(context));
        _adasmartConfigTask.execute();
        return this;
    }

    @Override
    public IAdsmartConfigTask interrupt() {
        _adasmartConfigTask.interrupt();
        return this;
    }

    @Override
    public IAdsmartConfigTask setType(String type) {
        adsmartConfigTaskParameter.getAdsmartConfigEntity().setType(type);
        return this;
    }
}
