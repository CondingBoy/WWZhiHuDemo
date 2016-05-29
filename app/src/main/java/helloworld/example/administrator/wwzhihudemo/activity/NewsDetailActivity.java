package helloworld.example.administrator.wwzhihudemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import helloworld.example.administrator.wwzhihudemo.R;
import helloworld.example.administrator.wwzhihudemo.asyncTask.NewsDetailAsyncTask;
import helloworld.example.administrator.wwzhihudemo.domain.News;

/**
 * Created by Administrator on 2016/5/29.
 */
public class NewsDetailActivity extends AppCompatActivity {
    private static final String rootURL ="http://news-at.zhihu.com/api/4/news/";
    private WebView webView;
    private News.NewsDetail detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetail);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        detail = (News.NewsDetail) data.getSerializable("data");
        Log.e("TAG6", "接收的数据" + detail.toString());
        initView();
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webView);
        NewsDetailAsyncTask task = new NewsDetailAsyncTask();
        task.execute(rootURL+detail.id);
        webView.loadUrl(rootURL+detail.id);
    }

    //启动当前Activity的方法
    public static void startActivity(Context context,News.NewsDetail detail){
        Intent intent = new Intent(context,NewsDetailActivity.class);
        Bundle data = new Bundle();
        data.putSerializable("data",detail);
        intent.putExtras(data);
        context.startActivity(intent);
    }
}
