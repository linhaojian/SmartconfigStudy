package com.lhj.adsmartconfig.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by Administrator on 2018/3/9.
 */

public class UdpAdConClient {
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;

    public UdpAdConClient(){
        try {
            datagramSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void sendData(byte[] buff, int bufflength,InetAddress inetAddress,int port){
        try {
            datagramPacket = new DatagramPacket(buff,bufflength,inetAddress,port);
            if(datagramSocket!=null&&!datagramSocket.isClosed()){
                datagramSocket.send(datagramPacket);
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    public void close(){
        if(datagramSocket!=null){
            datagramSocket.close();
        }
    }


}
