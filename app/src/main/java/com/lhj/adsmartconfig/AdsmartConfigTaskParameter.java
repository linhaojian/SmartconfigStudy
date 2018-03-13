package com.lhj.adsmartconfig;

import android.text.TextUtils;
import android.util.Log;

import com.lhj.adsmartconfig.pojo.AdsmartConfigEntity;
import com.lhj.adsmartconfig.util.CRC8;

import java.util.Arrays;

/**
 * Created by Administrator on 2018/3/9.
 */

public class AdsmartConfigTaskParameter {
    private AdsmartConfigEntity adsmartConfigEntity;
    private int guideLimteCount = 6;
    private int combinationLimteCount = 12;
    private int intervalMillisecond = 10;
    private int intervalGudieComCount = 10;
    private int targetPort = 7203;
    private int guideFirstIp = 235;
    private int combinationFirstIp = 235;
    private int guidiecombinationIntervalMillisecond = 50;

    public AdsmartConfigTaskParameter(){
        adsmartConfigEntity = new AdsmartConfigEntity();
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

    public String[] getGuideIp() {
        String type = adsmartConfigEntity.getType();
        byte[] typebuff = type.getBytes();
        int lengthThree = (typebuff.length/3)==0?3:(typebuff.length%3)==0?(typebuff.length):(typebuff.length+(3-(typebuff.length%3)));
        byte[] typebuff2Three = new byte[lengthThree];
        System.arraycopy(typebuff,0,typebuff2Three,0,typebuff.length);
        int lengthSb = (typebuff2Three.length/3)==0?1:(typebuff2Three.length/3);
        String[] stringbuff = new String[lengthSb];
        int stringCount = 0;
        for(int i=0;i<typebuff2Three.length;){
            stringbuff[stringCount] = guideFirstIp+"."+typebuff2Three[i]+"."+typebuff2Three[i+1]+"."+typebuff2Three[i+2];
            i=i+3;
            stringCount++;
        }
        return stringbuff;
    }

    private int[] dealCombinaBuffIp(String ip,byte[] pwbuff){
        int[] ipbuff = new int[4];
        int comdataLength = (pwbuff.length+ipbuff.length+2);
        byte[] crcbuff = new byte[comdataLength-1];
        crcbuff[0] = (byte) comdataLength;
        for(int i=0;i<ip.split("\\.").length;i++){
            ipbuff[i] = Integer.parseInt(ip.split("\\.")[i]);
            crcbuff[i+1] = (byte) Integer.parseInt(ip.split("\\.")[i]);
        }
        comdataLength = (comdataLength%2==0)?comdataLength: (comdataLength + 1);
        //length + ip + pw + crccode
        int[] comdiBuff = new int[comdataLength];
        comdiBuff[0] = comdataLength;
        System.arraycopy(ipbuff,0,comdiBuff,1,ipbuff.length);
        for(int i=0;i<pwbuff.length;i++){
            comdiBuff[i+1+ipbuff.length] = pwbuff[i] & 0xff;
            crcbuff[i+1+ipbuff.length] = (byte) (pwbuff[i] & 0xff);
        }
        CRC8 crc8 = new CRC8();
        int crc8com = crc8.calcCrc8(crcbuff);
        comdiBuff[1+ipbuff.length+pwbuff.length] = crc8com;
        return comdiBuff;
    }

    public String[] getCombinationIp() {
        // 235.serial.data.data
        byte[] pwbuff = TextUtils.isEmpty(adsmartConfigEntity.getPw())?new byte[0]:adsmartConfigEntity.getPw().getBytes();
        int[] combuff = dealCombinaBuffIp(adsmartConfigEntity.getLocalIp(),pwbuff);
        int ipslength = (combuff.length/2) + (combuff.length%2);
        String[] ips = new String[ipslength];
        int comConut = 0;
        for(int i=0;i<ipslength;i++){
            int b1 = i;
            int b2 = (combuff[comConut]&0xff);
            int b3 = (combuff[comConut+1]&0xff);
            ips[i] = combinationFirstIp+"."+b1+"."+b2+"."+b3;
            comConut=comConut+2;
        }
        return ips;
    }
}
