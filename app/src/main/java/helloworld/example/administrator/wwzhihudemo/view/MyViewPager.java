package helloworld.example.administrator.wwzhihudemo.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * 用来解决viewpager与swipRefresh的滑动冲突
 * Created by Administrator on 2016/5/29.
 */
public class MyViewPager extends ViewPager {
    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int startX;
    private int startY;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        //申请父控件不要拦截事件
//        getParent().requestDisallowInterceptTouchEvent(true);
        Log.e("TAG3","拦截"+ev.getAction());
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                startX = (int) ev.getRawX();
//                startY = (int) ev.getRawY();
//
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int detalX = (int) (ev.getRawX()-startX);
//                int detalY= (int) (ev.getRawY()-startY);
//                // 判断是上下滑动还是左右滑动
//                if(Math.abs(detalX)>Math.abs(detalY)){
////                    //如果水平滑动小于20像素，将事件交给父控件或祖宗控件处理
////                    if(Math.abs(detalX)<20){
////                        getParent().requestDisallowInterceptTouchEvent(false);
////                    }
//                }else {
//                    //上下滑动，让父控件拦截
////                    getParent().requestDisallowInterceptTouchEvent(false);
//                }
//                //如果水平滑动小于20像素，将事件交给父控件或祖宗控件处理
////                if(Math.abs(detalX)>20){
////                    getParent().requestDisallowInterceptTouchEvent(false);
////                }
////                break;
//        }
        return super.dispatchTouchEvent(ev);
    }


}
