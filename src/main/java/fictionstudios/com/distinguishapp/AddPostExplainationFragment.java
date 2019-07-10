package fictionstudios.com.distinguishapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class AddPostExplainationFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private String t1,t2;

    static AddPostExplainationFragment fragment;
    ArrayList<String> list=new ArrayList<>();
    ArrayList<String> list1=new ArrayList<>();
    ArrayList<String> list2=new ArrayList<>();
    private RecyclerView mRecyclerView1;

    public AddPostExplainationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_post_explaination, container, false);
        TextView term1Tv = view.findViewById(R.id.term1_textview_main);
        TextView term2Tv = view.findViewById(R.id.term2_textview_main);
        term1Tv.setText(StaticData.term1);
        term2Tv.setText(StaticData.term2);
        Button button=view.findViewById(R.id.previous_btn_explain);
        button.setOnClickListener(this);
        Button button1=view.findViewById(R.id.next_btn_explain);
        button1.setOnClickListener(this);

        Button addPOintBtn = view.findViewById(R.id.add_point_btn);
        addPOintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPointDialog();
            }
        });
        mRecyclerView1=view.findViewById(R.id.recyclerview1_add_posts);
        mRecyclerView1.setLayoutManager(new GridLayoutManager(context,2));
        mRecyclerView1.setHasFixedSize(true);
        ExplainationRecyclerAdapter adapter=new ExplainationRecyclerAdapter(list,context);
        mRecyclerView1.setAdapter(adapter);
        return view;
    }
    private void addPointDialog() {
        View view=LayoutInflater.from(context).inflate(R.layout.custom_dialog_add_title,null,false);
        final AlertDialog dialog=new AlertDialog.Builder(context).setCancelable(false).setView(view).create();
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
                t1=term1.getText().toString().trim();
                t2=term2.getText().toString().trim();
                list.add(t1);
                list.add(t2);
                list1.add(t1);
                list2.add(t2);
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }


    public static AddPostExplainationFragment getInstance()
    {
        if (fragment==null)
        {
            fragment=new AddPostExplainationFragment();
        }
        return fragment;
    }

    @Override
    public void onClick(View v) {
    if (v.getId()==R.id.previous_btn_explain)
    {
        StaticData.explain1=list1;
        StaticData.explain2=list2;
        assert getActivity()!=null;
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.container_addpost,AddPostTitleDescFragment.getInstance()).commit();    }
        if (v.getId()==R.id.next_btn_explain)
        {
            StaticData.explain1=list1;
            StaticData.explain2=list2;
            assert getActivity()!=null;
            FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.container_addpost,AddPostImageFragment.getInstance()).commit();
        }
    }
}
