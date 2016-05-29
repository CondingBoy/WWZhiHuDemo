package helloworld.example.administrator.wwzhihudemo.adpater;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import helloworld.example.administrator.wwzhihudemo.R;
import helloworld.example.administrator.wwzhihudemo.asyncTask.ImageloaderForPager;
import helloworld.example.administrator.wwzhihudemo.domain.News;

/**
 * Created by Administrator on 2016/5/28.
 */
public class MyPagerAdapter extends PagerAdapter {
    private ArrayList<News.TopNewsDetail> mData;
    private Context mContext;
    private final LayoutInflater inflater;
    private final ImageloaderForPager loader;

    public MyPagerAdapter(ArrayList<News.TopNewsDetail> data,Context context){
        mData=data;
        mContext=context;
        inflater = LayoutInflater.from(mContext);
        loader = new ImageloaderForPager();
        Log.e("TAG2", "MyPagerAdapter: "+mData.toString() );
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        News.TopNewsDetail topNewsDetail = mData.get(position);
        View view = inflater.inflate(R.layout.pager_item, container, false);
        TextView pagerTitle = (TextView) view.findViewById(R.id.tv_topnews_title);
        ImageView pagerIamge = (ImageView) view.findViewById(R.id.iv_topnews_icon);
        pagerTitle.setText(topNewsDetail.title);
        pagerIamge.setTag(topNewsDetail.image);
        loader.loadPagerImage(topNewsDetail.image,pagerIamge);
        ( (ViewPager)container).addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((View) object);
    }
}
