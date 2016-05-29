package helloworld.example.administrator.wwzhihudemo.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import helloworld.example.administrator.wwzhihudemo.util.ParseInputStream;

/**
 * Created by Administrator on 2016/5/28.
 */
public class HttpRequest {
    public static final String NESLISTURL = "http://news-at.zhihu.com/api/4/news/latest";
    public static final String NESBEFORELISTURL = "http://news.at.zhihu.com/api/4/news/before/";
    public static final String NEWS_TYPE_TODAY = "today";
    public static final String NEWS_TYPE_BEFORE = "before";
    public static String requestJSonResult(String url) {
        HttpURLConnection connection = null;
        try {
            URL mUrl = new URL(url);
            connection = (HttpURLConnection) mUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            InputStream inputStream = connection.getInputStream();
            String result = ParseInputStream.getStringFromStream(inputStream);
            inputStream.close();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    public static Bitmap requestForBitmap(String url) {
        HttpURLConnection connection = null;
        try {
            URL mUrl = new URL(url);
            connection = (HttpURLConnection) mUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            connection.disconnect();
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
