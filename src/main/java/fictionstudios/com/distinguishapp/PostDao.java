package fictionstudios.com.distinguishapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface PostDao
{
    @Query("SELECT * FROM posts order by timestamp desc")
    LiveData<List<PostModel>> getAllPosts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PostModel model);
}
