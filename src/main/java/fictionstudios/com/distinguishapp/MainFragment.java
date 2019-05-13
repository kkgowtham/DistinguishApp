package fictionstudios.com.distinguishapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class MainFragment extends Fragment implements PostsRecyclerAdapter.OnItemClickListerner,PostsRecyclerAdapter.OnCommentBtnClickListener {

    Context mContext;
    @SuppressLint("StaticFieldLeak")
    private static MainFragment mFragment=null;
    FirebaseUser user;
    boolean a=true;
    private static final String TAG = "MainActivity";
    int n=0;
    boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private  RecyclerView mRecyclerView;
    ArrayList<PostModel> mListData=new ArrayList<>();
    ArrayList<PostModel> mTotalList=new ArrayList<>();
    PostsRecyclerAdapter.OnItemClickListerner onItemClickListerner=this;
    PostsRecyclerAdapter.OnCommentBtnClickListener onCommentBtnClickListener=this;
    PostsRecyclerAdapter adapter;
    LinearLayoutManager mLayoutManager=new LinearLayoutManager(mContext);
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
            loadData();
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
                            loadMoreData();
                        }
                    }
                }
            }
        });



        return view;
    }

    private void getUserLikes(String email) {
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, "http://192.168.1.105/distinguish/readlike.php?email="+email, null,
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
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, "http://192.168.1.105/distinguish/read.php?limit1="+n+"&limit2=10", null,
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
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, "http://192.168.1.105/distinguish/read.php?limit1="+n+"&limit2=10", null,
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
}

