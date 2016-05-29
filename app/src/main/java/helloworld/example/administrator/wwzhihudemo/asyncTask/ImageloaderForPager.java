package helloworld.example.administrator.wwzhihudemo.asyncTask;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import helloworld.example.administrator.wwzhihudemo.http.HttpRequest;

/**
 * Created by Administrator on 2016/5/28.
 */
public class ImageloaderForPager  {
    private LruCache<String,Bitmap> mCache;
    public ImageloaderForPager(){
        //初始化缓存
        //获取当前应用的最大内存
        long maxMemory = Runtime.getRuntime().maxMemory();
        mCache=new LruCache<String,Bitmap>((int) (maxMemory/4)){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //返回占用的内存
                return value.getByteCount();
            }
        };
    }
    public void loadPagerImage(String url,ImageView imageView){
        //先从缓存中取
        Bitmap bitmap = getFromCache(url);
        if(bitmap==null){
            //缓存中没有
            PagerImageAsyncTask task = new PagerImageAsyncTask(url,imageView);
            task.execute(url);
        }else{
            imageView.setImageBitmap(bitmap);
        }
    }
    private void addToCache(String url,Bitmap bitmap){
        if(getFromCache(url)==null){

            mCache.put(url,bitmap);
        }
    }
    private Bitmap getFromCache(String url){
        return mCache.get(url);
    }
    class PagerImageAsyncTask extends AsyncTask<String,Void,Bitmap>{

        private final String mUrl;
        private final ImageView mImageView;

        public PagerImageAsyncTask(String url,ImageView imageView){
            mUrl = url;
            mImageView = imageView;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap= HttpRequest.requestForBitmap(url);

            //存入缓存
            addToCache(mUrl,bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(mUrl.equals((String)mImageView.getTag())){
                mImageView.setImageBitmap(bitmap);
            }
        }
    }
}
