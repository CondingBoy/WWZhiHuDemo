package helloworld.example.administrator.wwzhihudemo.asyncTask;

import android.content.Context;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import helloworld.example.administrator.wwzhihudemo.adpater.NewsListAdapter;
import helloworld.example.administrator.wwzhihudemo.domain.News;
import helloworld.example.administrator.wwzhihudemo.http.HttpRequest;
import helloworld.example.administrator.wwzhihudemo.view.MyListView;

/**
 * Created by Administrator on 2016/5/28.
 */
public class NewsListTask extends AsyncTask<String, Void, News> {

    private final OnDataPreparedListener mListener;

    public NewsListTask(OnDataPreparedListener listener){
        mListener = listener;
   }

    /**
     *
     * @param params 第一个参数为url类型，today或before，若为before，则要传入第二个参数，为日期字符串
     * @return
     */
    @Override
    protected News doInBackground(String... params) {
        String url=null;
        if(params[0].equals(HttpRequest.NEWS_TYPE_TODAY)){
             url = HttpRequest.NESLISTURL;
        }else if (params[0].equals(HttpRequest.NEWS_TYPE_BEFORE)){
            url = HttpRequest.NESBEFORELISTURL+params[1];
        }else{
            new RuntimeException("传入的参数不正确");
        }
        Log.e("TAG6","参数正确1");
        String jsonString=HttpRequest.requestJSonResult(url);
        Gson gson = new Gson();
        News news = gson.fromJson(jsonString,News.class);
        return news;
    }

    @Override
    protected void onPostExecute(News news) {
        super.onPostExecute(news);
        mListener.onDataPrepared(news);
//        Log.e("TAG1", "onPostExecute: Javabean"+news );
    }
    public interface OnDataPreparedListener{
        void onDataPrepared(News news);
    }
}
