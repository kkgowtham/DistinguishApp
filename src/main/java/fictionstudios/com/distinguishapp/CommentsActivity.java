package fictionstudios.com.distinguishapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.security.cert.CertificateException;
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

public class CommentsActivity extends AppCompatActivity {

    private static final String TAG = CommentsActivity.class.getSimpleName();


    Context mContext;
    private FirebaseUser user;
    RecyclerView mRecyclerView;
    CommentsRecyclerAdapter adapter;
    List<CommentModel> mlist;
    EditText commentEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        commentEditText =findViewById(R.id.comments_edittext);
        final RelativeLayout parent = (RelativeLayout)findViewById(R.id.parent_layout);

        mRecyclerView=findViewById(R.id.recyclerview_comments);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setHasFixedSize(true);
        parent.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        Rect r = new Rect();
                        parent.getWindowVisibleDisplayFrame(r);
                        int screenHeight = parent.getRootView().getHeight();
                        int keypadHeight = screenHeight - r.bottom;

                        Log.d(TAG, "keypadHeight = " + keypadHeight);

                        if (keypadHeight > screenHeight * 0.15) {
                            Log.d(TAG, "onGlobalLayout: Keyboard SHown");
                            ViewGroup.LayoutParams params=mRecyclerView.getLayoutParams();
                            params.height=50;
                            mRecyclerView.setLayoutParams(params);
                        }
                        else {
                            Log.d(TAG, "onGlobalLayout: Keyboard Dismissed");
                            mRecyclerView.getLayoutParams().height=ViewGroup.LayoutParams.MATCH_PARENT;
                            //RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) view.getLayoutParams();
                            //params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

                        }
                    }
                });
        user= FirebaseAuth.getInstance().getCurrentUser();
        retreiveComments();

        Button button=findViewById(R.id.send_btn);

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

    }
    private void retreiveComments() {
        try {

            String postId=getIntent().getStringExtra("postid");
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

        String postid=getIntent().getStringExtra("postid");
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
