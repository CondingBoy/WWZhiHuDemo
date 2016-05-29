package helloworld.example.administrator.wwzhihudemo.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import helloworld.example.administrator.wwzhihudemo.MainActivity;
import helloworld.example.administrator.wwzhihudemo.R;

/**
 * 封装一个viewpager作为headerview
 * Created by Administrator on 2016/5/28.
 */
public class MyListView extends ListView {

    private final MyViewPager viewPager;
    private int currentItem=1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int pagerCount = viewPager.getAdapter().getCount();

            viewPager.setCurrentItem(currentItem);
            currentItem++;

            if (currentItem > pagerCount) {
                currentItem = 1;
            }
            this.sendEmptyMessageDelayed(0, 2000);
            Log.e("TAG4","current:"+currentItem+"pagercount:"+pagerCount);
        }
    };

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewHeader = inflater.inflate(R.layout.list_header_item, null);
        viewPager = (MyViewPager) viewHeader.findViewById(R.id.vp_header);
        addHeaderView(viewHeader);
    }

    public void setViewPagerAdapter(PagerAdapter adapter) {
        viewPager.setAdapter(adapter);
        mHandler.sendEmptyMessageDelayed(0, 2000);//让viewpager轮播
        //当viewpager获得焦点时停止轮播
       viewPager.setOnFocusChangeListener(new OnFocusChangeListener() {
           @Override
           public void onFocusChange(View v, boolean hasFocus) {
               if(hasFocus){
                   mHandler.removeCallbacksAndMessages(null);
               }else{
                   mHandler.sendEmptyMessageDelayed(0,2000);

               }
           }
       });
    }

}
