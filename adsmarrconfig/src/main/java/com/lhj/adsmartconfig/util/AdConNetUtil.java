package com.lhj.adsmartconfig.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by Administrator on 2018/3/9.
 */

public class AdConNetUtil {
    /**
     * get the local ip address by Android System
     *
     * @param context
     *            the context
     * @return the local ip addr allocated by Ap
     */
    public static String getLocalInetAddress(Context context) {
        WifiManager wm = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wm.getConnectionInfo();
        int localAddrInt = wifiInfo.getIpAddress();
        String localAddrStr = __formatString(localAddrInt);
        return localAddrStr;
    }

    /**
     *  Get the routing MAC
     * @param context
     * @return
     */
    public static String getConnectedWifiMacAddress(Context context){
        String connectedWifiMacAddress = "";
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            WifiInfo info = wifiManager.getConnectionInfo();
            if(info!=null){
                connectedWifiMacAddress = info.getBSSID();
            }
        }
        return connectedWifiMacAddress;
    }


    private static String __formatString(int value) {
        String strValue = "";
        byte[] ary = __intToByteArray(value);
        for (int i = ary.length - 1; i >= 0; i--) {
            strValue += (ary[i] & 0xFF);
            if (i > 0) {
                strValue += ".";
            }
        }
        return strValue;
    }

    private static byte[] __intToByteArray(int value) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            int offset = (b.length - 1 - i) * 8;
            b[i] = (byte) ((value >>> offset) & 0xFF);
        }
        return b;
    }

}
