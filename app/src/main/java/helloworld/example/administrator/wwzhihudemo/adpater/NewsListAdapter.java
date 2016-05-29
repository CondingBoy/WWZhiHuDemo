package helloworld.example.administrator.wwzhihudemo.adpater;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import helloworld.example.administrator.wwzhihudemo.R;
import helloworld.example.administrator.wwzhihudemo.asyncTask.ImageLoader;
import helloworld.example.administrator.wwzhihudemo.domain.News;
import helloworld.example.administrator.wwzhihudemo.util.DateFomatUtil;
import helloworld.example.administrator.wwzhihudemo.view.MyListView;

/**
 * 新闻列表的数据适配器
 * Created by Administrator on 2016/5/28.
 */
public class NewsListAdapter extends BaseAdapter {

    private final ArrayList<News.NewsDetail> mData;
    private Context mContext;
    private final LayoutInflater mInflater;
    private final ImageLoader mImageLoader;

    public NewsListAdapter(ArrayList<News.NewsDetail> data,Context context,MyListView listView) {
        mData = data;
        mContext=context;
        mInflater = LayoutInflater.from(mContext);
        mImageLoader = new ImageLoader(listView);
    }
    public void refreshData(ArrayList<News.NewsDetail> data){
        //清除原来的数据，加载新数据
        mData.clear();
        mData.addAll(data);
        this.notifyDataSetChanged();
//        Log.e("TAG5",data.toString());
    }
    public void refreshLoadMore(ArrayList<News.NewsDetail> data){
        mData.addAll(data);
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public News.NewsDetail getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News.NewsDetail detail = getItem(position);
        //判断NewsDetail的type类型，来决定item
        if(detail.type.equals("0")){
            ViewHolder holder =null;
            //判断convertview的类型，若convertview为null或者convertview是R.layout.list_tab这个布局时
            //需要重新填充布局
            if((convertView!=null&&convertView.getTag()==null)||convertView==null){
                convertView = mInflater.inflate(R.layout.list_item,parent,false);
                holder=new ViewHolder();
                holder.textView= (TextView) convertView.findViewById(R.id.tv_title);
                holder.imageView= (ImageView) convertView.findViewById(R.id.iv_itemicon);
                convertView.setTag(holder);
            }else {
                holder= (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(detail.title);
            //异步加载图片
            String imageUrl = detail.images[0];
            Log.e("TAG1" ,"posion: "+position+",url:"+imageUrl );
            holder.imageView.setTag(imageUrl);
            mImageLoader.loadImage(imageUrl,holder.imageView);
        }else {
            convertView = mInflater.inflate(R.layout.list_tab,parent,false);
            TextView tab = (TextView) convertView.findViewById(R.id.tv_list_tab_item);
            //若日期是今天
            String todayTime = DateFomatUtil.getTime(new Date());
//            Log.e("TAG6","today:"+todayTime+",time:"+detail.time);
            if(todayTime.equals(detail.time)){
                tab.setText("今日热闻");
            }else{
                tab.setText(detail.time);
            }
        }

        return convertView;
    }
    class ViewHolder{
        public TextView textView;
        public ImageView imageView;
    }
}
