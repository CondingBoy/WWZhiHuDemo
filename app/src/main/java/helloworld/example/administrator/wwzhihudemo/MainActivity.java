package helloworld.example.administrator.wwzhihudemo;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Date;
import java.util.zip.DataFormatException;

import helloworld.example.administrator.wwzhihudemo.activity.NewsDetailActivity;
import helloworld.example.administrator.wwzhihudemo.adpater.MyPagerAdapter;
import helloworld.example.administrator.wwzhihudemo.adpater.NewsListAdapter;
import helloworld.example.administrator.wwzhihudemo.asyncTask.NewsListTask;
import helloworld.example.administrator.wwzhihudemo.domain.News;
import helloworld.example.administrator.wwzhihudemo.http.HttpRequest;
import helloworld.example.administrator.wwzhihudemo.util.DateFomatUtil;
import helloworld.example.administrator.wwzhihudemo.util.NetworkCheckUtil;
import helloworld.example.administrator.wwzhihudemo.view.MyListView;
import helloworld.example.administrator.wwzhihudemo.view.MyRefreshLayout;

public class MainActivity extends AppCompatActivity {
    //用来记录上拉加载更多的日期
    private String currentDate=null;
    private MyRefreshLayout refreshLayout;
    private MyListView mListView;
    private NewsListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("首页");
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        //监听刷新事件
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                //刷新操作
                NewsListTask task = new NewsListTask(new NewsListTask.OnDataPreparedListener() {
                    @Override
                    public void onDataPrepared(News news) {
                        //重置当前日期
                        currentDate = news.date;
                        News.NewsDetail newsDetail = new News().new NewsDetail();
                        newsDetail.type = "1";
                        newsDetail.time = DateFomatUtil.getTime(news.date);
                        //将这个对象加入数据集合中作为tab
                        news.stories.add(0, newsDetail);
                        //若mAdapter为空，说明程序启动时无网络，没有初始化，所以在刷新时要重新初始化操作
                        if(mAdapter==null){
                            mAdapter = new NewsListAdapter(news.stories,MainActivity.this,  mListView);
                            mListView.setAdapter(mAdapter);
                            MyPagerAdapter myPagerAdapter = new MyPagerAdapter(news.top_stories,MainActivity.this);
                            mListView.setViewPagerAdapter(myPagerAdapter);
                        }else{

                            mAdapter.refreshData(news.stories);
                        }
                        Log.e("TAG5", "刷新20：" + news.stories.toString());
                        refreshLayout.setRefreshing(false);
//                        Log.e("TAG5", DateFomatUtil.getTime(news.date) + "string");


                    }
                });
                //检查网络状态
                if(NetworkCheckUtil.isNetworkConnected(MainActivity.this)){
                    task.execute(HttpRequest.NEWS_TYPE_TODAY);
                }else{
                    NetworkCheckUtil.showMessage(MainActivity.this);
                }
            }
        });
        //监听listview滑动事件
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.e("TAG6","lastPosition:"+mListView.getLastVisiblePosition()+",datacount:"+mListView.getCount());
                if(mListView.getLastVisiblePosition()==mListView.getCount()-1){
                    //当访问到最后一个时，加载更多
                    NewsListTask task = new NewsListTask(new NewsListTask.OnDataPreparedListener() {
                        @Override
                        public void onDataPrepared(News news) {
//                            Log.e("TAG6","加载更多："+news.toString());
                            //重置当前日期
                            currentDate=news.date;
                            News.NewsDetail newsDetail =new News().new NewsDetail();
                            newsDetail.type="1";
                            newsDetail.time=DateFomatUtil.getTime(news.date);
                            //将这个对象加入数据集合中作为tab
                            news.stories.add(0,newsDetail);
                            mAdapter.refreshLoadMore(news.stories);
//                            Log.e("TAG6", "加载更多：" + news.stories.toString());



                        }
                    });
                    //检查网络状态
                    if(NetworkCheckUtil.isNetworkConnected(MainActivity.this)){
                        task.execute(HttpRequest.NEWS_TYPE_BEFORE,currentDate);
                    }else{
                        NetworkCheckUtil.showMessage(MainActivity.this);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                Log.e("TAG6","第一个可访问的item："+firstVisibleItem);
                News.NewsDetail detail=null;
                if(firstVisibleItem==0){
                    MainActivity.this.setTitle("首页");
                }else  if((detail=mAdapter.getItem(firstVisibleItem-1)).type.equals("1")){
                    //当前可看到的第一个item不是headerview，判断类型, 因为listview中有headerview，所以要减去1
                   String todayTime= DateFomatUtil.getTime(new Date());
                    if(detail.time.equals(todayTime)){
                        MainActivity.this.setTitle("今日要闻");
                    }else {
                        MainActivity.this.setTitle(detail.time);
                    }
                }
            }
        });
        //监听listview的点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //得到与当前点击项对应的数据,Id就是当前点击项在adapter数据中的位置
                News.NewsDetail detail = mAdapter.getItem((int) id);
                NewsDetailActivity.startActivity(MainActivity.this,detail);
            }
        });
    }

    //初始化视图
    private void initView() {
        refreshLayout = (MyRefreshLayout) findViewById(R.id.swipRefresh);
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mListView = (MyListView) findViewById(R.id.lv_news);

    }

    //初始化数据
    private void initData() {
        NewsListTask task = new NewsListTask(new NewsListTask.OnDataPreparedListener() {
            @Override
            public void onDataPrepared(News news) {
                currentDate=news.date;
                News.NewsDetail newsDetail =new News().new NewsDetail();
                newsDetail.type="1";
                newsDetail.time=DateFomatUtil.getTime(news.date);
                //将这个对象加入数据集合中作为tab
                news.stories.add(0,newsDetail);
                mAdapter = new NewsListAdapter(news.stories,MainActivity.this,  mListView);
                mListView.setAdapter(mAdapter);
                MyPagerAdapter myPagerAdapter = new MyPagerAdapter(news.top_stories,MainActivity.this);
                mListView.setViewPagerAdapter(myPagerAdapter);
            }
        });
        //检查网络状态
        if(NetworkCheckUtil.isNetworkConnected(this)){
            task.execute(HttpRequest.NEWS_TYPE_TODAY);
        }else{
            NetworkCheckUtil.showMessage(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
