package com.lhj.adsmartconfig.task;

import android.util.Log;

import com.lhj.adsmartconfig.AdsmartConfigTaskParameter;
import com.lhj.adsmartconfig.OnAdConOnResults;
import com.lhj.adsmartconfig.pojo.ClientEnity;
import com.lhj.adsmartconfig.pojo.CombinationData;
import com.lhj.adsmartconfig.pojo.GuideData;
import com.lhj.adsmartconfig.udp.UdpAdConClient;
import com.lhj.adsmartconfig.udp.UdpAdConServer;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2018/3/9.
 */

public class _AdasmartConfigTask implements _IAdasmartConfigTask{
    private AdsmartConfigTaskParameter adsmartConfigTaskParameter;
    private UdpAdConClient udpAdConClient,udpAdRespon;
    private UdpAdConServer udpAdConServer;
    private boolean isInterrupt;
    private boolean isGudie = true;
    private int sendCount;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private OnAdConOnResults onAdConOnResults;

    public _AdasmartConfigTask(AdsmartConfigTaskParameter adsmartConfigTaskParameter){
        this.adsmartConfigTaskParameter = adsmartConfigTaskParameter;
    }

    @Override
    public ClientEnity dealGuideData() {
        return adsmartConfigTaskParameter.getGuideIp();
    }

    @Override
    public ClientEnity dealCombinationData() {
        return adsmartConfigTaskParameter.getCombinationIp();
    }

    @Override
    public void startClient(){
        try {
            udpAdConClient = new UdpAdConClient();
            ClientEnity clientEnityGuide = dealGuideData();
            ClientEnity clientEnityCom = dealCombinationData();
            ClientEnity clientEnity = null;
            while (!isInterrupt) {
                if (isGudie) {
                    clientEnity = clientEnityGuide;
                } else {
                    clientEnity = clientEnityCom;
                }
                int length = clientEnity.getIps().length;
                for (int i = 0; i < length; i++) {
                    udpAdConClient.sendData(
                            clientEnity.getDatas().get(i),
                            clientEnity.getDatas().get(i).length,
                            InetAddress.getByName(clientEnity.getIps()[i]),
                            adsmartConfigTaskParameter.getTargetPort());
                    if((i+1)%3==0){
                        Thread.sleep(adsmartConfigTaskParameter.getGuidiecombinationIntervalMillisecond());
                    }
                    Log.e("linhaojian",
                            "Mac: " + clientEnity.getIps()[i] + " " +
                                    "Port: " + adsmartConfigTaskParameter.getTargetPort() + " " +
                                    "Length: " + (clientEnity.getDatas().get(i).length));
                }
                sendCount++;
                if (isGudie) {
                    if (sendCount > adsmartConfigTaskParameter.getGuideLimteCount()) {
                        isGudie = false;
                        sendCount = 0;
                        Log.w("linhaojian", "---------------------------------------------------------------------------");
                    }
                } else {
                    if (sendCount > adsmartConfigTaskParameter.getCombinationLimteCount()) {
                        isGudie = true;
                        sendCount = 0;
                        Log.w("linhaojian", "---------------------------------------------------------------------------");
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void startServer() {
        udpAdConServer = new UdpAdConServer(adsmartConfigTaskParameter.getTargetPort());
        while (!isInterrupt) {
            if(onAdConOnResults!=null){
                try {
                    UdpAdConServer.ReceiveEntity receiveEntity = udpAdConServer.receiveBuff();
                    if(receiveEntity!=null){
                        byte[] datas = receiveEntity.getDatas();
                        String mac = new String(datas,"ISO-8859-1");
                        if(adsmartConfigTaskParameter.addListForResult(receiveEntity.getIp(),mac)){
                            responServer(mac,receiveEntity.getIp(),receiveEntity.getPort());
                            onAdConOnResults.OnAdCoResults(receiveEntity.getIp(),mac);
                        }
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void responServer(String mac,String ip,int port) {
        try{
            udpAdRespon = new UdpAdConClient();
            for(int i=0;i<adsmartConfigTaskParameter.getReceiveServerCount();i++){
                udpAdRespon.sendData(mac.getBytes(),mac.getBytes().length,InetAddress.getByName(ip),port);
                Thread.sleep(adsmartConfigTaskParameter.getIntervalMillisecond());
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void execute() {
        isInterrupt = false;
        try{
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    startClient();
                }
            });
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    startServer();
                }
            });
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void interrupt() {
        isInterrupt = true;
        if(udpAdConClient!=null){
            udpAdConClient.close();
        }
        if(udpAdConServer!=null){
            udpAdConServer.stopServer();
        }
        if(executorService!=null){
            executorService.shutdown();
        }
    }

    @Override
    public void setResults(OnAdConOnResults onAdConOnResults) {
        this.onAdConOnResults = onAdConOnResults;
    }
}
