package fictionstudios.com.distinguishapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentsBottomSheets extends BottomSheetDialogFragment {

    private static final String TAG = "CommentsBottomSheets";


    Context mContext;
   private FirebaseUser user;
   RecyclerView mRecyclerView;
   CommentsRecyclerAdapter adapter;
   List<CommentModel> mlist;
    EditText commentEditText;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.comments_bottom_sheets_layout,container,false);
         commentEditText =view.findViewById(R.id.comments_edittext);
        final RelativeLayout parent = (RelativeLayout)view.findViewById(R.id.parent_layout);

         mRecyclerView=view.findViewById(R.id.recyclerview_comments);
         mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
         mRecyclerView.setHasFixedSize(true);
        view.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        Rect r = new Rect();
                        view.getWindowVisibleDisplayFrame(r);
                        int screenHeight = view.getRootView().getHeight();
                        int keypadHeight = screenHeight - r.bottom;

                        Log.d(TAG, "keypadHeight = " + keypadHeight);

                        if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                            Log.d(TAG, "onGlobalLayout: Keyboard SHown");
                            ViewGroup.LayoutParams params=mRecyclerView.getLayoutParams();
                            params.height=50;
                            mRecyclerView.setLayoutParams(params);
                        }
                        else {
                            Log.d(TAG, "onGlobalLayout: Keyboard Dismissed");
                            mRecyclerView.getLayoutParams().height=ViewGroup.LayoutParams.WRAP_CONTENT;
                            //RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) view.getLayoutParams();
                            //params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                            // keyboard is closed
                        }
                    }
                });
         user=FirebaseAuth.getInstance().getCurrentUser();
        retreiveComments();

        Button button=view.findViewById(R.id.send_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment=commentEditText.getText().toString().trim();
                if (comment.equals(""))
                {
                    return;
                }
                if (user==null)
                {
                    Toast.makeText(mContext,"Please Sign In To Comment",Toast.LENGTH_SHORT).show();
                    return;
                }
                addComment(comment);
            }
        });
        return view;
    }


    private void retreiveComments() {
        try {
            Bundle bundle=getArguments();
            String postId=bundle.getString("postid");
            Log.d(TAG, "retreiveComments: "+postId);
            Retrofit retrofit=new Retrofit.Builder().baseUrl("https://192.168.1.105/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getUnsafeOkHttpClient().build())
                    .build();
            ApiService service=retrofit.create(ApiService.class);
            service.getComments(postId).enqueue(new Callback<List<CommentModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<CommentModel>> call, @NonNull Response<List<CommentModel>> response) {
                    mlist=response.body();
                    adapter=new CommentsRecyclerAdapter(mContext,mlist);
                    mRecyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(@NonNull Call<List<CommentModel>> call, @NonNull Throwable t) {
                    Log.d(TAG, "onFailure: "+t.getMessage());
                }
            });

        }catch (NullPointerException e)
        {
            Log.d(TAG, "retreiveComments: "+e.getMessage());
        }

    }

    private void addComment(String comment) {
        String email=user.getEmail();
        String name=user.getDisplayName();
        String imageUrl=String.valueOf(user.getPhotoUrl());
        assert getArguments() != null;
        Bundle bundle=getArguments();
        String postid=bundle.getString("postid");
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.105/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiService service = retrofit.create(ApiService.class);
        service.getComment(name, comment,postid, email, imageUrl).enqueue(new Callback<CommentModel>() {
            @Override
            public void onResponse(@NonNull Call<CommentModel> call, @NonNull Response<CommentModel> response) {
                CommentModel model = response.body();
                mlist.add(model);
                commentEditText.setText("");
                adapter.notifyDataSetChanged();
                mRecyclerView.scrollToPosition(mlist.size()-1);
            }

            @Override
            public void onFailure(@NonNull Call<CommentModel> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
    public static OkHttpClient.Builder getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
