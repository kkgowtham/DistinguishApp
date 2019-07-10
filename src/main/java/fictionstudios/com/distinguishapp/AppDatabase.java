package fictionstudios.com.distinguishapp;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = PostModel.class,version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PostDao getPostDao();


}
