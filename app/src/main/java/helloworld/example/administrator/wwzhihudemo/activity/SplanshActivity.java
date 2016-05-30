package helloworld.example.administrator.wwzhihudemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import helloworld.example.administrator.wwzhihudemo.MainActivity;
import helloworld.example.administrator.wwzhihudemo.R;
import helloworld.example.administrator.wwzhihudemo.asyncTask.SplashAsyncTask;
import helloworld.example.administrator.wwzhihudemo.http.HttpRequest;
import helloworld.example.administrator.wwzhihudemo.util.NetworkCheckUtil;

/**
 * Created by Administrator on 2016/5/30.
 */
public class SplanshActivity extends AppCompatActivity {

    private TextView mTextView;
    private ImageView mImageView;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startActivity(new Intent(SplanshActivity.this, MainActivity.class));
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //消除anctionbar的阴影
        getSupportActionBar().setElevation(0);
        setTitle("");
        initView();
        initData();
    }

    private void initData() {
        if(NetworkCheckUtil.isNetworkConnected(this)){
            SplashAsyncTask task = new SplashAsyncTask(mImageView,mTextView);
            task.execute(HttpRequest.SPLASHINFOURL);
        }else {
            NetworkCheckUtil.showMessage(this);
        }
        mHandler.sendEmptyMessageDelayed(0,3000);

    }

    private void initView() {
        mTextView = (TextView) findViewById(R.id.tv_pic_copyright);
        mImageView = (ImageView) findViewById(R.id.iv_splash);
        Window window = getWindow();
//        //将状态栏的背景改为黑色
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(Color.BLACK);
    }

}
