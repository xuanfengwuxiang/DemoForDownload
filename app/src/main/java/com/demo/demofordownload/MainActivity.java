package com.demo.demofordownload;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.aigestudio.downloader.bizs.DLManager;
import cn.aigestudio.downloader.interfaces.IDListener;

public class MainActivity extends AppCompatActivity {


    @InjectView(R.id.bt1)
    Button mBt1;
    @InjectView(R.id.bt2)
    Button mBt2;
    @InjectView(R.id.sb)
    SeekBar mSb;
    @InjectView(R.id.bt3)
    Button mBt3;
    @InjectView(R.id.bt4)
    Button mBt4;
    @InjectView(R.id.sb1)
    SeekBar mSb1;
    @InjectView(R.id.bt5)
    Button mBt5;
    @InjectView(R.id.bt6)
    Button mBt6;
    @InjectView(R.id.sb2)
    SeekBar mSb2;
    @InjectView(R.id.tv)
    TextView mTv;
    @InjectView(R.id.activity_main)
    LinearLayout mActivityMain;
    @InjectView(R.id.tv1)
    TextView mTv1;
    @InjectView(R.id.tv2)
    TextView mTv2;
    @InjectView(R.id.tv3)
    TextView mTv3;
    final String url = "http://112.124.9.200/download/com.jkxy.vod" +
            ".dcrk_1_f31fc1954360cd446bb39f7a5a59cca6.apk";
    final String url2 = "http://221.228.67.155/imtt.dd.qq" +
            ".com/16891/3960E96D4E6EACE1260ACB8B18D6B402.apk";
    final String url3 = "http://112.124.9.200/download/com.jkxy.vod.zxxs_1_d3d2ef912cbc5ec6e1d7540aa43bd701.apk";
    public static int REQUEST_CODE_ASK_INTERNET = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mSb.setMax(100);
        mSb1.setMax(100);
        mSb2.setMax(100);
        mBt1.setOnClickListener(new click());
        mBt2.setOnClickListener(new click());
        mBt3.setOnClickListener(new click());
        mBt4.setOnClickListener(new click());
        mBt5.setOnClickListener(new click());
        mBt6.setOnClickListener(new click());


    }


    class click implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.bt1:

                    download("文件1.apk", url, mSb);


                    break;
                case R.id.bt2:
                    pause(url);
                    break;
                case R.id.bt3:

                    download("文件2.apk", url2, mSb1);


                    break;
                case R.id.bt4:
                    pause(url2);
                    break;
                case R.id.bt5:

                    download("文件3.apk", url3, mSb2);


                    break;
                case R.id.bt6:
                    pause(url3);
                    break;


            }
        }
    }

    public void download(final String fileName, String url, final SeekBar seekBar) {
        String downloadPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        DLManager.getInstance(this).dlStart(url, downloadPath, fileName, new IDListener() {
            @Override
            public void onPrepare() {

            }

            @Override
            public void onStart(final String fileName, String realUrl, final int fileLength) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("下载开始了");
                        Toast.makeText(MainActivity.this, fileName + "下载开始了", Toast.LENGTH_SHORT)
                                .show();
                        seekBar.setMax(fileLength);
                    }
                });

            }

            @Override
            public void onProgress(final int progress) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (fileName.equals("文件1.apk")) {
                            mTv1.setText("进度：" + progress);

                        }
                        if (fileName.equals("文件2.apk")) {
                            mTv2.setText("进度：" + progress);
                        }
                        if (fileName.equals("文件3.apk")) {
                            mTv3.setText("进度：" + progress);
                        }
                        seekBar.setProgress(progress);
                    }
                });
            }

            @Override
            public void onStop(int progress) {

            }

            @Override
            public void onFinish(File file) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, fileName + "下载OK!", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onError(int status, String error) {
                System.out.println("错误为" + error);
            }
        });
    }

    public void pause(String url) {
        DLManager.getInstance(this).dlStop(url);
    }

}
