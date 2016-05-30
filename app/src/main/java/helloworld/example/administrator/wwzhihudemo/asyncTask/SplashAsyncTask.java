package helloworld.example.administrator.wwzhihudemo.asyncTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import helloworld.example.administrator.wwzhihudemo.domain.SplashInfo;
import helloworld.example.administrator.wwzhihudemo.http.HttpRequest;

/**
 * Created by Administrator on 2016/5/30.
 */
public class SplashAsyncTask extends AsyncTask<String,Void,SplashInfo> {

    private final ImageView mImageView;
    private final TextView mTextView;

    public SplashAsyncTask(ImageView imageView,TextView textView){
        mImageView = imageView;
        mTextView = textView;
    }
    @Override
    protected SplashInfo doInBackground(String... params) {
        String result = HttpRequest.requestJSonResult(params[0]);
        Gson gson = new Gson();
        SplashInfo splashInfo = gson.fromJson(result,SplashInfo.class);
        return splashInfo;
    }

    @Override
    protected void onPostExecute(SplashInfo splashInfo) {
        super.onPostExecute(splashInfo);
        //加载图片,设置tag
        mImageView.setTag(splashInfo.img);
        new ImageloaderForPager().loadPagerImage(splashInfo.img,mImageView);
        Log.e("TAG7","image_url3:"+splashInfo.img);
        mTextView.setText(splashInfo.text);
    }
}
