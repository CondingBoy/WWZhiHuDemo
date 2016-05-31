package helloworld.example.administrator.wwzhihudemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/5/31.
 */
public class MyDatebaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_SQL = "create table favoriteNews(" +
            "_id integer primary key autoincrement," +
            "newsID,image,title,type,time)";
    public MyDatebaseHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //第一次执行时，创建数据库
        db.execSQL(TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
