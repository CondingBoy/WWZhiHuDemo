package helloworld.example.administrator.wwzhihudemo.asyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ResourceBundle;

import helloworld.example.administrator.wwzhihudemo.R;
import helloworld.example.administrator.wwzhihudemo.domain.News;
import helloworld.example.administrator.wwzhihudemo.domain.WebContent;
import helloworld.example.administrator.wwzhihudemo.http.HttpRequest;

/**
 * Created by Administrator on 2016/5/29.
 */
public class NewsDetailAsyncTask extends AsyncTask<String,Void,String>{

    private final WebView mWebView;
    private final Context mContext;
    private final LayoutInflater mInflater;
    private  View mView;

    public NewsDetailAsyncTask(WebView webView,Context context){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mWebView = webView;
    }
    @Override
    protected String doInBackground(String... params) {
        String result = HttpRequest.requestJSonResult(params[0]);
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Gson gson = new Gson();
        WebContent webContent = gson.fromJson(s,WebContent.class);
        String html = convertToHtml(webContent);
        initWebHeader(webContent);
        mWebView.loadDataWithBaseURL(null, html, null, null, null);

        mWebView.addView(mView,0);
        Log.e("TAG7","加载headerview10");
    }
    public String convertToHtml(WebContent webContent){
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("<html>\n" +
                "<head>\n" +
                "<link rel=\"stylesheet\" href=\"").append(webContent.css[0]).append(
                "\" type=\"text/css\">\n" +
                        "\t\t<meta charset=\"utf-8\">\n"
        ).append("</head>\n" +"<body>\n").append(webContent.body).append("</body>\n" +
                "</html>");
        return stringBuilder.toString();
    }
    public void initWebHeader(WebContent webContent){
        mView = mInflater.inflate(R.layout.webview_header,mWebView,false);
        ImageView imageView = (ImageView) mView.findViewById(R.id.iv_webheader);
        //异步加载图片
        imageView.setTag(webContent.image);
        new ImageloaderForPager().loadPagerImage(webContent.image, imageView);
        TextView tvSource = (TextView) mView.findViewById(R.id.tv_sourse);
        tvSource.setText(webContent.image_source);
        TextView tvTitle = (TextView) mView.findViewById(R.id.tv_webheader_title);
        tvTitle.setText(webContent.title);
    }
}
