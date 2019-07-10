package fictionstudios.com.distinguishapp;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.Collections;

public class SingletonRoom {

    private  Context mContext;

    private  static SingletonRoom mInstance;

    private AppDatabase database;

    private SingletonRoom(Context context)
    {
        this.mContext=context;

        database= Room.databaseBuilder(mContext,AppDatabase.class,"distinguish").fallbackToDestructiveMigration().build();
    }

    public static synchronized SingletonRoom getInstance(Context context)
    {
        if (mInstance==null)
        {
            mInstance=new SingletonRoom(context);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return database;
    }
}
