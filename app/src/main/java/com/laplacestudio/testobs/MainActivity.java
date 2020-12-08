package com.laplacestudio.testobs;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView tvLog;
    Button btnDownload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLog=findViewById(R.id.tv_log);
        btnDownload=findViewById(R.id.btn_download);

        btnDownload.setOnClickListener(btnDownloadClickListener());


    }

    private View.OnClickListener btnDownloadClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download();
            }
        };
    }

    private void download(){
        LsObs lsObs=new LsObs();
        String update= lsObs.getUpdate();
        log(update);
    }

    private void log(String s){
        final String logStr = s;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // log date
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                String strDate = formatter.format(curDate);

                // old log
                String strLogs = tvLog.getText().toString().trim();
                if (strLogs.equals("")) {
                    strLogs = strDate + ": " + logStr;
                } else {
                    strLogs += "\r\n" + strDate + ": " + logStr;
                }
                // new log
                tvLog.setText(strLogs);
                // add auto scroll
                tvLog.post(new Runnable() {
                    @Override
                    public void run() {
                        int scrollAmount = tvLog.getLayout().getLineTop(tvLog.getLineCount()) - tvLog.getHeight();
                        tvLog.scrollTo(0, Math.max(scrollAmount, 0));
                    }
                });
            }
        });
    }
}