package helloworld.example.administrator.wwzhihudemo.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;

import helloworld.example.administrator.wwzhihudemo.MainActivity;
import helloworld.example.administrator.wwzhihudemo.R;
import helloworld.example.administrator.wwzhihudemo.asyncTask.NewsDetailAsyncTask;
import helloworld.example.administrator.wwzhihudemo.domain.News;
import helloworld.example.administrator.wwzhihudemo.http.HttpRequest;
import helloworld.example.administrator.wwzhihudemo.util.NetworkCheckUtil;

/**
 * Created by Administrator on 2016/5/29.
 */
public class NewsDetailActivity extends AppCompatActivity {
    private static final String rootURL ="http://news-at.zhihu.com/api/4/news/";
    private WebView webView;
    private News.NewsDetail detail=null;
    private News.TopNewsDetail topNewsDetail=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetail);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        if(data.containsKey("data")){
            detail = (News.NewsDetail) data.getSerializable("data");
        }else{
            topNewsDetail = (News.TopNewsDetail) data.getSerializable("topnews");
        }
//        Log.e("TAG6", "接收的数据" + detail.toString());
        initView();
        ActionBar actionBar =getSupportActionBar();
        setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webView);
        NewsDetailAsyncTask task = new NewsDetailAsyncTask(webView,NewsDetailActivity.this);
        //检查网络状态
        if(NetworkCheckUtil.isNetworkConnected(NewsDetailActivity.this)){
            //判断是从iewpager启动还是listview启动
            if(detail!=null){
                task.execute(rootURL + detail.id);
            }else if(topNewsDetail!=null){
                task.execute(rootURL + topNewsDetail.id);
            }
        }else{
            NetworkCheckUtil.showMessage(NewsDetailActivity.this);
        }

    }

    //点击listview启动当前Activity的方法
    public static void startActivity(Context context,News.NewsDetail detail){
        Intent intent = new Intent(context,NewsDetailActivity.class);
        Bundle data = new Bundle();
        data.putSerializable("data",detail);
        intent.putExtras(data);
        context.startActivity(intent);
    }
    //点击viewpager启动当前Activity的方法
    public static void startActivity(Context context,News.TopNewsDetail detail){
        Intent intent = new Intent(context,NewsDetailActivity.class);
        Bundle data = new Bundle();
        data.putSerializable("topnews",detail);
        intent.putExtras(data);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
