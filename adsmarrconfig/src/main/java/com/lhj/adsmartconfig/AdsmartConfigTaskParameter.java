package com.lhj.adsmartconfig;

import android.text.TextUtils;
import android.util.Log;


import com.lhj.adsmartconfig.pojo.AdsmartConfigEntity;
import com.lhj.adsmartconfig.pojo.ClientEnity;
import com.lhj.adsmartconfig.pojo.CombinationData;
import com.lhj.adsmartconfig.pojo.GuideData;
import com.lhj.adsmartconfig.util.CRC8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/3/9.
 */

public class AdsmartConfigTaskParameter {
    private AdsmartConfigEntity adsmartConfigEntity;
    private int guideLimteCount = 5;
    private int combinationLimteCount = 1;
    private int intervalMillisecond = 50;
    private int intervalGudieComCount = 10;
    private int targetPort = 7203;
    private int guideFirstIp = 235;
    private int combinationFirstIp = 235;
    private int guidiecombinationIntervalMillisecond = 30;
    private int receiveServerCount = 3;
    private ArrayList<String> executeList;
    private String broadcast = "255.255.255.255";

    public AdsmartConfigTaskParameter(){
        adsmartConfigEntity = new AdsmartConfigEntity();
        executeList = new ArrayList<>();
    }

    public AdsmartConfigEntity getAdsmartConfigEntity() {
        return adsmartConfigEntity;
    }

    public int getGuideLimteCount() {
        return guideLimteCount;
    }

    public void setGuideLimteCount(int guideLimteCount) {
        this.guideLimteCount = guideLimteCount;
    }

    public int getCombinationLimteCount() {
        return combinationLimteCount;
    }

    public void setCombinationLimteCount(int combinationLimteCount) {
        this.combinationLimteCount = combinationLimteCount;
    }

    public int getIntervalMillisecond() {
        return intervalMillisecond;
    }

    public void setIntervalMillisecond(int intervalMillisecond) {
        this.intervalMillisecond = intervalMillisecond;
    }

    public int getTargetPort() {
        return targetPort;
    }

    public void setTargetPort(int targetPort) {
        this.targetPort = targetPort;
    }

    public int getGuidiecombinationIntervalMillisecond() {
        return guidiecombinationIntervalMillisecond;
    }

    public void setGuidiecombinationIntervalMillisecond(int guidiecombinationIntervalMillisecond) {
        this.guidiecombinationIntervalMillisecond = guidiecombinationIntervalMillisecond;
    }

    public int getIntervalGudieComCount() {
        return intervalGudieComCount;
    }

    public void setIntervalGudieComCount(int intervalGudieComCount) {
        this.intervalGudieComCount = intervalGudieComCount;
    }

    public int getReceiveServerCount() {
        return receiveServerCount;
    }

    public void setReceiveServerCount(int receiveServerCount) {
        this.receiveServerCount = receiveServerCount;
    }

    public boolean addListForResult(String ip,String mac){
        boolean macExist = false;
        if((!adsmartConfigEntity.getLocalIp().equals(ip))&&(!TextUtils.isEmpty(mac))){
            if(executeList!=null&&!executeList.contains(mac)){
                executeList.add(mac);
                macExist = true;
            }
        }
        return macExist;
    }

    public ClientEnity getGuideIp() {
        byte guideDataDefault = 1;
        ClientEnity clientEnity = new ClientEnity();
        List<byte[]> list = new ArrayList<>();
        String type = adsmartConfigEntity.getType();
        byte[] typebuff = type.getBytes();
        int lengthThree = (typebuff.length/3)==0?3:(typebuff.length%3)==0?(typebuff.length):(typebuff.length+(3-(typebuff.length%3)));
        byte[] typebuff2Three = new byte[lengthThree];
        System.arraycopy(typebuff,0,typebuff2Three,0,typebuff.length);
        int lengthSb = (typebuff2Three.length/3)==0?1:(typebuff2Three.length/3);
//        String[] stringbuff = new String[lengthSb+(typebuff.length*3)+1];
        String[] stringbuff = new String[lengthSb+(typebuff.length)+1];
        // 组播
        int stringCount = 0;
        for(int i=0;i<typebuff2Three.length;){
            stringbuff[stringCount] = guideFirstIp+"."+typebuff2Three[i]+"."+typebuff2Three[i+1]+"."+typebuff2Three[i+2];
            i=i+3;
            stringCount++;
            GuideData guideData = new GuideData();
            list.add(guideData.getGuideBuff(guideDataDefault));
        }
        //广播
        for(int i=0;i<1;i++){
            stringbuff[stringCount] = broadcast;
            GuideData guideData = new GuideData();
            list.add(guideData.getGuideBuff(i));
            stringCount++;
        }
//        byte[] bcbuff = new byte[typebuff.length*3];
//        CRC8 crc8 = new CRC8();
//        int bcbuffCount = 0;
//        for(int i=0;i<bcbuff.length;i=i+3){
//            bcbuff[i] = (byte) bcbuffCount;
//            bcbuff[i+1] = typebuff[bcbuffCount];
//            bcbuff[i+2] = crc8.calcCrc8(new byte[]{bcbuff[i],bcbuff[i+1]});
//            bcbuffCount++;
//        }
        int typebuffCount = 0;
        int comDataLength = 0;
        for(;stringCount<stringbuff.length;stringCount++){
            stringbuff[stringCount] = broadcast;
            GuideData guideData = new GuideData();
            comDataLength = typebuff[typebuffCount] & 0xff;
            list.add(guideData.getGuideBuff(comDataLength));
            typebuffCount++;
        }
        clientEnity.setIps(stringbuff);
        clientEnity.setDatas(list);
        return clientEnity;
    }

    private int[] dealCombinaBuffIp(String ip,byte[] pwbuff,String mac){
        int[] ipbuff = new int[4];
        int[] macbufff = new int[6];
        int comdataLength = (pwbuff.length+ipbuff.length+macbufff.length+2);
        byte[] crcbuff = new byte[comdataLength-1];
        crcbuff[0] = (byte) comdataLength;
        // 将mac数据存储到buff里面
        for(int i=0;i<macbufff.length;i++){
            macbufff[i] = Integer.parseInt(mac.split(":")[i],16);
            crcbuff[i+1] = (byte) macbufff[i];
        }
        // 将ip数据存储到buff里面
        for(int i=0;i<ipbuff.length;i++){
            ipbuff[i] = Integer.parseInt(ip.split("\\.")[i]);
            crcbuff[i+1+macbufff.length] = (byte) ipbuff[i];
        }
        //length + mac + ip + pw + crccode
        int[] comdiBuff = new int[comdataLength];
        comdiBuff[0] = comdataLength;
        System.arraycopy(macbufff,0,comdiBuff,1,macbufff.length);
        System.arraycopy(ipbuff,0,comdiBuff,1+macbufff.length,ipbuff.length);
        for(int i=0;i<pwbuff.length;i++){
            comdiBuff[i+1+ipbuff.length+macbufff.length] = pwbuff[i] & 0xff;
            crcbuff[i+1+ipbuff.length+macbufff.length] = (byte) comdiBuff[i+1+ipbuff.length+macbufff.length];
        }
        CRC8 crc8 = new CRC8();
        int crc8com = crc8.calcCrc8(crcbuff);
        comdiBuff[1+ipbuff.length+pwbuff.length+macbufff.length] = crc8com;
        return comdiBuff;
    }

    public ClientEnity getCombinationIp() {
        // 235.serial.data.data
        byte combinationDataDefault = 1;
        ClientEnity clientEnity = new ClientEnity();
        List<byte[]> list = new ArrayList<>();
        byte[] pwbuff = TextUtils.isEmpty(adsmartConfigEntity.getPw())?new byte[0]:adsmartConfigEntity.getPw().getBytes();
        int[] combuff = dealCombinaBuffIp(adsmartConfigEntity.getLocalIp(),pwbuff,adsmartConfigEntity.getRouterMac());
        int ipslength = (combuff.length/2) + (combuff.length%2);
        int bclength = combuff.length*3;
        String[] ips = new String[ipslength+bclength];
        // 组播
        int comConut = 0;
        for(int i=0;i<ipslength;i++){
            int b1 = i;
            int b2 = (combuff[comConut]&0xff);
            int b3 = 0;
            if((comConut+1)<combuff.length){
                b3 = (combuff[comConut+1]&0xff);
            }
            ips[i] = combinationFirstIp+"."+b1+"."+b2+"."+b3;
            CombinationData combinationData = new CombinationData();
            list.add(combinationData.getCombinationBuff(combinationDataDefault));
            comConut=comConut+2;
        }
        //广播
        byte[] bcbuff = new byte[bclength];
        CRC8 crc8 = new CRC8();
        int bcbuffCount = 0;
        for(int i=0;i<bcbuff.length;i=i+3){
            bcbuff[i] = (byte) bcbuffCount;
            bcbuff[i+1] = (byte) combuff[bcbuffCount];
            bcbuff[i+2] = crc8.calcCrc8(new byte[]{bcbuff[i],bcbuff[i+1]});
            bcbuffCount++;
        }
        comConut = 0;
        int comDataLength = 0;
        for(int i =ipslength;i<ips.length;i++){
            ips[i] = broadcast;
            CombinationData combinationData = new CombinationData();
            comDataLength = bcbuff[comConut] & 0xff;
            list.add(combinationData.getCombinationBuff(comDataLength));
            comConut++;
        }
        clientEnity.setDatas(list);
        clientEnity.setIps(ips);
        return clientEnity;
    }
}
