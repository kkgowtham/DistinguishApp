package fictionstudios.com.distinguishapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements PostsRecyclerAdapter.OnItemClickListerner,PostsRecyclerAdapter.OnCommentBtnClickListener {

    private Context mContext;
    private static MainFragment mFragment=null;
 private    FirebaseUser user;
  private   boolean a=true;
    private static final String TAG = "MainActivity";
  private   int n=0;
  private   boolean loading = true;
  private   int pastVisiblesItems, visibleItemCount, totalItemCount;
    private  RecyclerView mRecyclerView;
  private   ArrayList<PostModel> mListData=new ArrayList<>();
  private ArrayList<PostModel> mTotalList=new ArrayList<>();
  private   PostsRecyclerAdapter.OnItemClickListerner onItemClickListerner=this;
    private PostsRecyclerAdapter.OnCommentBtnClickListener onCommentBtnClickListener=this;
  private   PostsRecyclerAdapter adapter;
 private    LinearLayoutManager mLayoutManager=new LinearLayoutManager(mContext);
    private ArrayList<String> likeArray=new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main,container,false);
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        if (mAuth!=null) {
            user=mAuth.getCurrentUser();
            if (user != null) {
                String email = user.getEmail();
                getUserLikes(email);
            }
        }
        mRecyclerView=view.findViewById(R.id.recyclerview_posts);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (a) {
            a=false;
            //loadData();
            getFromRoomDatabase();
        }
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loading = false;
                           // loadMoreData();
                        }
                    }
                }
            }
        });



        return view;
    }

    private void getUserLikes(String email) {
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, "http://192.168.1.103/distinguish/readlike.php?email="+email, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i=0;i<response.length();i++)
                        {
                            try {
                                String id=response.getJSONObject(i).getString("postid");
                                likeArray.add(id);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error.getMessage());
            }
        });
        SingleTonVolley.getInstance(mContext).add(request);
    }


    private void loadData() {
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, "http://192.168.1.103/distinguish/read.php?limit1="+n+"&limit2=10", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson=new Gson();
                        TypeToken<ArrayList<PostModel>> typeToken=new TypeToken<ArrayList<PostModel>>(){};
                        mListData=gson.fromJson(String.valueOf(response),typeToken.getType());
                        mTotalList.addAll(mListData);
                        adapter=new PostsRecyclerAdapter(mContext,mTotalList,likeArray,onItemClickListerner,onCommentBtnClickListener);
                        mRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        for (PostModel model:mListData)
                        {
                            Log.d(TAG, "onResponse: "+model.toString());
                            assert getActivity()!=null;
                            PostViewModel viewModel= ViewModelProviders.of(getActivity()).get(PostViewModel.class);
                            viewModel.insertPosts(mContext,model);
                        }
                        n+=10;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error.getMessage());
            }
        });
        SingleTonVolley.getInstance(mContext).add(request);

    }
    private void loadMoreData() {
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, "http://192.168.1.103/distinguish/read.php?limit1="+n+"&limit2=10", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson=new Gson();
                        TypeToken<ArrayList<PostModel>> typeToken=new TypeToken<ArrayList<PostModel>>(){};
                        mListData=gson.fromJson(String.valueOf(response),typeToken.getType());
                        mTotalList.addAll(mListData);
                        adapter.notifyDataSetChanged();
                        for (PostModel model:mListData)
                        {
                            Log.d(TAG, "onResponse: "+model.toString());
                        }
                        loading = true;
                        n+=10;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error.getMessage());
            }
        });
        SingleTonVolley.getInstance(mContext).add(request);
    }
    @Override
    public void onItemClick(int pos) {
        Log.d(TAG, "onItemClick: "+"Item Clicked"+pos);
        PostModel model=mTotalList.get(pos);
        Intent intent=new Intent(mContext,SingleItemViewActivity.class);
        intent.putExtra("data",model);
        startActivity(intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    public static Fragment getInstance()
    {
        if (mFragment==null)
        {
            mFragment=new MainFragment();
        }
        return mFragment;

    }

    @Override
    public void onCommentButtonClick(int pos) {
        Log.d(TAG, "onCommentButtonClick: "+"Comment button Clicked-"+pos);
        PostModel model=mTotalList.get(pos);
        Intent intent=new Intent(mContext,SingleItemViewActivity.class);
        intent.putExtra("data",model);
        intent.putExtra("show",true);
        startActivity(intent);
    }

    public void getFromRoomDatabase()
    {
        assert getActivity()!=null;
        PostViewModel viewModel= ViewModelProviders.of(getActivity()).get(PostViewModel.class);
        viewModel.getPosts(mContext).observe(this, new Observer<List<PostModel>>() {
            @Override
            public void onChanged(List<PostModel> postModels) {
                mTotalList.addAll(postModels);
                adapter=new PostsRecyclerAdapter(mContext,mTotalList,likeArray,onItemClickListerner,onCommentBtnClickListener);
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageView=new ImageView(mContext);
        data.getData();
        imageView.setImageURI();
    }
}

