package com.lhj.adsmartconfig.task;

import com.lhj.adsmartconfig.IAdsmartConfigTask;
import com.lhj.adsmartconfig.OnAdConOnResults;
import com.lhj.adsmartconfig.pojo.ClientEnity;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by Administrator on 2018/3/9.
 */

public interface _IAdasmartConfigTask {

    ClientEnity dealGuideData();

    ClientEnity dealCombinationData();

    void startClient();

    void startServer();

    void execute();

    void interrupt();

    void setResults(OnAdConOnResults onAdConOnResults);
}
