package fictionstudios.com.distinguishapp;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class AddPostActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView term1Tv,term2Tv;
    private Button addPointBtn;

    private RecyclerView mRecyclerView1;
    private ArrayList<String> list1=new ArrayList<>();
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        mContext=getApplicationContext();
        mRecyclerView1=findViewById(R.id.recyclerview1_add_posts);
        term1Tv=findViewById(R.id.term1_textview_main);
        term2Tv=findViewById(R.id.term2_textview_main);
        addPointBtn=findViewById(R.id.new_point_btn);
        addPointBtn.setOnClickListener(this);
        showAddTitleDialog();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        ExplainationRecyclerAdapter adapter1=new ExplainationRecyclerAdapter(list1,mContext);
        mRecyclerView1.setHasFixedSize(true);
        mRecyclerView1.setLayoutManager(new GridLayoutManager(mContext,2));
        mRecyclerView1.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
    }

    private void showAddTitleDialog() {
        View view=LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_dialog_add_title,null,false);
        final AlertDialog dialog=new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.AppTheme)).setCancelable(false).setView(view).create();
        dialog.setTitle("Title");
        final EditText term1=view.findViewById(R.id.term1_edittext);
        final EditText term2=view.findViewById(R.id.term2_edittext);
        Button button=view.findViewById(R.id.submit_btn);
        dialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (term1.getText().toString().trim().equals(""))
                {
                    term1.setError("Cannot Be Empty");
                    return;
                }
                if (term2.getText().toString().trim().equals(""))
                {
                    term2.setError("Cannot Be Empty");
                    return;
                }
                String t1=term1.getText().toString().trim();
                String t2=term2.getText().toString().trim();
                term1Tv.setText(t1);
                term2Tv.setText(t2);
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v==addPointBtn)
        {
            showAddPointDialog();
        }
    }

    private void showAddPointDialog() {
        View view=LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_dialog_add_title,null,false);
        final AlertDialog dialog=new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.AppTheme)).setCancelable(false).setView(view).create();
        dialog.setTitle("New Point");
        final EditText term1=view.findViewById(R.id.term1_edittext);
        final EditText term2=view.findViewById(R.id.term2_edittext);
        Button button=view.findViewById(R.id.submit_btn);
        dialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (term1.getText().toString().trim().equals(""))
                {
                    term1.setError("Enter Correct");
                    return;
                }
                if (term2.getText().toString().trim().equals(""))
                {
                    term2.setError("Enter Correct");
                    return;
                }
                String t1=term1.getText().toString().trim();
                String t2=term2.getText().toString().trim();
                list1.add(t1);
                list1.add(t2);
                dialog.dismiss();
            }
        });
    }
}
