package helloworld.example.administrator.wwzhihudemo.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/5/28.
 */
public class ParseInputStream {
    public static String getStringFromStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len = 0;
        byte[] bs = new byte[1024];
        while((len=inputStream.read(bs))!=-1){
            bos.write(bs,0,len);
        }
        return bos.toString();
    }
}
