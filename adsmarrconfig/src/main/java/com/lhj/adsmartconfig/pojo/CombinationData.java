package com.lhj.adsmartconfig.pojo;

import com.lhj.adsmartconfig.util.CRC8;

/**
 * Created by Administrator on 2018/3/9.
 */

public class CombinationData {

    public byte[] getCombinationBuff(String ip) {
        // (ip_2 and ip_3 )bytes to crc8 = datalength
        String[] ips = ip.split("\\.");
        byte b1 = (byte) Integer.parseInt(ips[2]);
        byte b2 = (byte) Integer.parseInt(ips[3]);
        CRC8 crc8 = new CRC8();
        int length = crc8.calcCrc8(new byte[]{b1,b2}) & 0xff;
        return new byte[length];
    }

    public byte[] getCombinationBuff(int data){
        return new byte[data];
    }

}
