package com.lhj.adsmartconfig.udp;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

/**
 * Created by Administrator on 2018/3/9.
 */

public class UdpAdConServer {
    private DatagramSocket socket;
    private byte[] buff;
    private ReceiveEntity receiveEntity;

    public UdpAdConServer(int port){
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public ReceiveEntity receiveBuff(){
        try {
            receiveEntity = new ReceiveEntity();
            buff = new byte[64];
            DatagramPacket datagramPacket = new DatagramPacket(buff,buff.length);
            if(socket!=null&&!socket.isClosed()){
                socket.receive(datagramPacket);
            }
            receiveEntity.datas = buff;
            receiveEntity.ip = datagramPacket.getAddress().getHostAddress();
            receiveEntity.port = datagramPacket.getPort();
            return receiveEntity;
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return null;
    }

    public void stopServer(){
        if(socket!=null){
            socket.close();
        }
    }

    public class ReceiveEntity{
        byte[] datas;
        String ip;
        int port;

        public byte[] getDatas() {
            return datas;
        }

        public void setDatas(byte[] datas) {
            this.datas = datas;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }
}
