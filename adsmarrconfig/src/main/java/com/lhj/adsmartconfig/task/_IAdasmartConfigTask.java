package com.lhj.adsmartconfig.task;

import com.lhj.adsmartconfig.OnAdConOnResults;
import com.lhj.adsmartconfig.pojo.ClientEnity;

/**
 * Created by Administrator on 2018/3/9.
 */

public interface _IAdasmartConfigTask {

    ClientEnity dealGuideData();

    ClientEnity dealCombinationData();

    void startClient();

    void startServer();

    void responServer(String mac, String ip, int port);

    void execute();

    void interrupt();

    void setResults(OnAdConOnResults onAdConOnResults);
}
