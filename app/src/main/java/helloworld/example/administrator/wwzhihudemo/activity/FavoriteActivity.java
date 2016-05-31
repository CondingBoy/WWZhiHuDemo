package helloworld.example.administrator.wwzhihudemo.activity;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import helloworld.example.administrator.wwzhihudemo.MainActivity;
import helloworld.example.administrator.wwzhihudemo.R;
import helloworld.example.administrator.wwzhihudemo.adpater.NewsListAdapter;
import helloworld.example.administrator.wwzhihudemo.db.MyDbUtil;
import helloworld.example.administrator.wwzhihudemo.domain.News;

/**
 * Created by Administrator on 2016/5/31.
 */
public class FavoriteActivity extends AppCompatActivity{

    private ListView listView;
    private ArrayList<News.NewsDetail> newsDetails;
    private NewsListAdapter mAdapter;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("我的收藏");
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<News.NewsDetail> tempList = MyDbUtil.getFavoriteNews(this);
        Log.e("TAG9","newsDetails:"+newsDetails.size()+",tempList:"+tempList.size());
        if(newsDetails.size()!=tempList.size()){
            //当点击item，并返回时，这是可能会取消收藏，所以要重新获取数据
            Log.e("TAG9","newsDetails1:"+newsDetails.toString()+",tempList:"+tempList.toString());
//            newsDetails = tempList;
            newsDetails.clear();
            newsDetails.addAll(tempList);
            Log.e("TAG9","newsDetails5:"+newsDetails.toString());
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initData() {
        newsDetails = MyDbUtil.getFavoriteNews(this);
        mAdapter = new NewsListAdapter(newsDetails,this,listView);
        listView.setAdapter(mAdapter);
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.lv_favorite);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //得到与当前点击项对应的数据,Id就是当前点击项在adapter数据中的位置
                News.NewsDetail detail = mAdapter.getItem((int) id);
                NewsDetailActivity.startActivity(FavoriteActivity.this,detail);
            }
        });
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
