package fictionstudios.com.distinguishapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class PostViewModel extends ViewModel {

    private LiveData<List<PostModel>> posts;

    Context context;
    public LiveData<List<PostModel>> getPosts(Context context)
    {
        this.context=context;
        if (posts==null)
        {
            posts=new LiveData<List<PostModel>>(){};
            posts=SingletonRoom.getInstance(context).getAppDatabase().getPostDao().getAllPosts();
        }
        return posts;
    }


    public void  insertPosts(final Context context, final PostModel post)
    {
        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected Void doInBackground(Void... voids) {
                SingletonRoom.getInstance(context).getAppDatabase().getPostDao().insert(post);
                return null;
            }
        }.execute();
    }
}
