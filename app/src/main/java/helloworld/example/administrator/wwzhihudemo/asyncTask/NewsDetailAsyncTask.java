package helloworld.example.administrator.wwzhihudemo.asyncTask;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ResourceBundle;

import helloworld.example.administrator.wwzhihudemo.http.HttpRequest;

/**
 * Created by Administrator on 2016/5/29.
 */
public class NewsDetailAsyncTask extends AsyncTask<String,Void,String>{
    @Override
    protected String doInBackground(String... params) {
        String result = HttpRequest.requestJSonResult(params[0]);
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.e("TAG7","result:"+ s);
    }
}
