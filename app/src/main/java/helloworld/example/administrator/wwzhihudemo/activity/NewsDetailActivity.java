package helloworld.example.administrator.wwzhihudemo.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;

import java.util.ArrayList;

import helloworld.example.administrator.wwzhihudemo.MainActivity;
import helloworld.example.administrator.wwzhihudemo.R;
import helloworld.example.administrator.wwzhihudemo.asyncTask.NewsDetailAsyncTask;
import helloworld.example.administrator.wwzhihudemo.db.MyDbUtil;
import helloworld.example.administrator.wwzhihudemo.domain.News;
import helloworld.example.administrator.wwzhihudemo.http.HttpRequest;
import helloworld.example.administrator.wwzhihudemo.util.NetworkCheckUtil;

/**
 * 展示新闻详情
 * Created by Administrator on 2016/5/29.
 */
public class NewsDetailActivity extends AppCompatActivity {
    private static final String rootURL ="http://news-at.zhihu.com/api/4/news/";
    private WebView webView;
    private News.NewsDetail detail=null;
    private News.TopNewsDetail topNewsDetail=null;
    private boolean isFavorite;
    private MenuItem mFavoriteItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetail);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        if(data.containsKey("data")){
            detail = (News.NewsDetail) data.getSerializable("data");
            isFavorite = MyDbUtil.isFavoriteNews(this,detail.id);
        }else{
            topNewsDetail = (News.TopNewsDetail) data.getSerializable("topnews");
            isFavorite = MyDbUtil.isFavoriteNews(this,topNewsDetail.id);
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
            case R.id.favorite:
                //判断当前新闻是否收藏过
                if(isFavorite){
                    //判断是从listview启动还是viewpager启动
                    if(detail!=null){
                        MyDbUtil.removeFavorityNews(this,detail.id);
                    }else{
                        MyDbUtil.removeFavorityNews(this, topNewsDetail.id);
                    }
                    //改变图标
                    isFavorite=!isFavorite;
                    mFavoriteItem.setIcon(R.mipmap.fav_normal);
                }else{
                    //判断是从listview启动还是viewpager启动
                    if(detail!=null){
                        MyDbUtil.saveFavoriteNews(this,detail);
                    }else{
                        MyDbUtil.saveFavoriteNews(this,topNewsDetail);
                    }

                    //改变图标
                    isFavorite=!isFavorite;
                    mFavoriteItem.setIcon(R.mipmap.fav_active);
                }
                ArrayList<News.NewsDetail>list =MyDbUtil.getFavoriteNews(this);
                Log.e("TAG8","收藏9："+list.toString());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        mFavoriteItem = menu.findItem(R.id.favorite);
        Log.e("TAG8","创建菜单项");
        if(isFavorite){
            mFavoriteItem.setIcon(R.mipmap.fav_active);
        }
        return super.onCreateOptionsMenu(menu);
    }
}
