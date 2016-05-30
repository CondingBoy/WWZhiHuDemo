package helloworld.example.administrator.wwzhihudemo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


/**
 * Created by Administrator on 2016/5/30.
 */
public class NetworkCheckUtil {
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    public static void showMessage(Context context){
        Toast.makeText(context, "网络不可用，请检查连接是否正常", Toast.LENGTH_SHORT).show();
    }
}
