#  SmartconfigStudy
## 自定义smartconfig框架与功能

### SmartConfig 概念：

   智能硬件处于混杂模式下,监听网络中的所有报文;手机APP将SSID和密码编码到UDP报文中,通过广播包或组播报发送,智能硬件接收到UDP报文后解码,得到正确的SSID和密码,然后主动连接指定SSID的路由,完成连接。

### SmartConfig 原理：

    1、手机连上 WiFi，开启 APP 软件，点击"添加新设备"，进入配置界面，输入手机所在 WiFi 密码，请求配网type
    2、智能硬件开启混杂模式监听所有网络数据包（sniffer）
    3、手机通过广播或者组播循环发送 password/type
    4、硬件设备通过 UDP 包（长度）获取配置信息捕捉到 password/type，连接路由器，然后设备就进网成功。