package helloworld.example.administrator.wwzhihudemo.asyncTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.InputStream;
import java.io.PipedReader;

import helloworld.example.administrator.wwzhihudemo.http.HttpRequest;
import helloworld.example.administrator.wwzhihudemo.view.MyListView;

/**
 * Created by Administrator on 2016/5/28.
 */
public class ImageLoader  {
    private LruCache<String,Bitmap> mCache;
// 1   private  MyListView mListView;
    private  ListView mListView;


// 1   public ImageLoader(MyListView listView){
//        mListView = listView;
//        //初始化缓存
//        //获取当前应用的最大内存
//        long maxMemory = Runtime.getRuntime().maxMemory();
//        mCache=new LruCache<String,Bitmap>((int) (maxMemory/4)){
//            @Override
//            protected int sizeOf(String key, Bitmap value) {
//                //返回占用的内存
//                return value.getByteCount();
//            }
//        };
//    }
    public ImageLoader(ListView listView){
        mListView = listView;
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
    //加载图片
    public void loadImage(String url,ImageView imageView){
        //先从缓存中取
        Bitmap bitmap = getFromCache(url);
        if(bitmap==null){
            //缓存中没有
            ImageAsyncTask task = new ImageAsyncTask(url);
            task.execute(url);
        }else{
            //缓存中有图片
//              1.这段语句在手指刚开始下滑的时候，imageview为null，所以就不会设置imageview的图片，
//                所以出现下滑时，最上面出现的图片是与最下面被滑过去的图片相同，目前自己认为，
//                在手指刚开始下滑时，listview调用getview方法，但这时，因为这个item还没有显示，
//                还不算是listview的子view，所以不能通过findviewwithtag找到，所以为imageview为空，
//                跳过了这句话的执行，没有给ImageView重新设置图片，依然显示的是复用的convertview之前的图片
//                造成图片显示错位，尽快查资料验证
//            ImageView imageView= (ImageView) mListView.findViewWithTag(url);
//                if(imageView!=null){
//                    imageView.setImageBitmap(bitmap);
//                }

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
    //异步任务
    class ImageAsyncTask extends AsyncTask<String,Void,Bitmap>{

        private  String mUrl;

        public ImageAsyncTask(String url){
            mUrl = url;
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
            ImageView imageView = (ImageView) mListView.findViewWithTag(mUrl);
            if(bitmap!=null&&imageView!=null){
                imageView.setImageBitmap(bitmap);
            }
        }
    }

}
