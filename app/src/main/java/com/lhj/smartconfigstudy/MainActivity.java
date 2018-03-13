package com.lhj.smartconfigstudy;

import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.espressif.iot.esptouch.EsptouchTask;
import com.espressif.iot.esptouch.udp.UDPSocketClient;
import com.lhj.adsmartconfig.AdsmartConfigTask;
import com.lhj.adsmartconfig.OnAdConOnResults;

import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView wifiname_tv;
    private EditText wifipw_et;
    private LinearLayout lienar;
    private ScrollView scrollView;
    private AdsmartConfigTask adsmartConfigTask;
    private String start = "开始发送",end = "停止发送";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListenrs();
    }

    private void initListenrs() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button.getText().toString().equals(start)){
                    button.setText(end);
                    if(adsmartConfigTask!=null){
                        adsmartConfigTask.interrupt();
                        adsmartConfigTask = null;
                    }
                    adsmartConfigTask = (AdsmartConfigTask) new AdsmartConfigTask(MainActivity.this)
                            .setType("h07")
                            .setPw(wifipw_et.getText().toString())
                            .setSSid(wifiname_tv.getText().toString())
                            .setResults(new OnAdConOnResults() {
                                @Override
                                public void OnAdCoResults(final String ip, final String address) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            addView("ip:"+ip+"  mac:"+address);
                                        }
                                    });
                                }
                            })
                            .start();
                }else{
                    button.setText(start);
                    if(adsmartConfigTask!=null){
                        adsmartConfigTask.interrupt();
                        adsmartConfigTask = null;
                    }
                }
            }
        });
        wifipw_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>=8){
                    button.setEnabled(true);
                }else{
                    button.setEnabled(false);
                }
                return;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initView() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        button = findViewById(R.id.button);
        button.setEnabled(false);
        wifiname_tv = findViewById(R.id.wifiname_tv);
        wifiname_tv.setText(wifiInfo.getSSID());
        wifipw_et = findViewById(R.id.wifipw_et);
        lienar = findViewById(R.id.linear);
        scrollView = findViewById(R.id.scrollView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        wifiname_tv.setText(wifiInfo.getSSID());
    }

    private void addView(String text){
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextColor(Color.RED);
        textView.setTextSize(20);
        lienar.addView(textView);
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }


}
