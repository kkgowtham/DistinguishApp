package fictionstudios.com.distinguishapp;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class SingleItemViewActivity extends AppCompatActivity {

    private static final String TAG = "SingleItemViewActivity";
    private TextView term1Tv,term2Tv;

    private ArrayList<String> list1=new ArrayList<>();
    private RecyclerView term1RecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_view);
        term1RecyclerView=findViewById(R.id.term1_recycler_view);
        term1Tv=findViewById(R.id.term1_textview);
        term2Tv=findViewById(R.id.term2_textview);
        try {
            assert getIntent().getExtras()!=null;
            PostModel model=getIntent().getExtras().getParcelable("data");
            assert model!=null;
            term1Tv.setText(model.getTerm1());
            term2Tv.setText(model.getTerm2());
            setUpRecyclerView(model);
            boolean show=getIntent().getBooleanExtra("show",false);
            if (show)
            {
                CommentsBottomSheets commentsBottomSheets=new CommentsBottomSheets();
                Bundle bundle=new Bundle();
                bundle.putString("postid",model.getId());
                commentsBottomSheets.setArguments(bundle);
                FragmentManager manager=getSupportFragmentManager();
                assert manager != null;
                commentsBottomSheets.show(manager,"commentdata");
                commentsBottomSheets.setAllowEnterTransitionOverlap(true);
            }
        }catch (NullPointerException e)
        {
            Log.d(TAG, "onCreate: "+e.getMessage());
            e.printStackTrace();
        }
    }
    public void setUpRecyclerView(PostModel model)
    {
        String explain1=model.getExplain1();
        String explain2=model.getExplain2();
        try {
            JSONArray array1=new JSONArray(explain1);
            JSONArray array2=new JSONArray(explain2);
            for(int i=0;i<array1.length();i++)
            {
             list1.add(array1.getJSONObject(i).getString("value"));
             list1.add(array2.getJSONObject(i).getString("value"));
             term1RecyclerView.setLayoutManager(new GridLayoutManager(this,2));
             term1RecyclerView.setHasFixedSize(true);
             term1RecyclerView.setAdapter(new ExplainationRecyclerAdapter(list1,this));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
