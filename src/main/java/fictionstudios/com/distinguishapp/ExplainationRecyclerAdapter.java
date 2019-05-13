package fictionstudios.com.distinguishapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ExplainationRecyclerAdapter extends RecyclerView.Adapter<ExplainationRecyclerAdapter.MyViewHolder> {
    private ArrayList<String> list;
    private Context context;
    public ExplainationRecyclerAdapter(ArrayList<String> list,Context context){
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public ExplainationRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.single_explaination_layout,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExplainationRecyclerAdapter.MyViewHolder myViewHolder, int i) {
            myViewHolder.explainTextView.setText(list.get(i));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView explainTextView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            explainTextView=itemView.findViewById(R.id.single_term_tv);
            explainTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,explainTextView.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            });
            explainTextView.setLinksClickable(true);
        }
    }
}
