package fictionstudios.com.distinguishapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsRecyclerAdapter extends RecyclerView.Adapter<CommentsRecyclerAdapter.MyViewHolder> {
    private List<CommentModel> mList;
    private static final String TAG = "CommentsRecyclerAdapter";
   private Context mContext;
    public CommentsRecyclerAdapter(Context mContext,List<CommentModel> mList)
    {
        this.mList=mList;
        this.mContext=mContext;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.single_comment_layout,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        CommentModel model=mList.get(i);
        Picasso.get().load(model.getImageurl().trim())
                .placeholder(R.drawable.ic_like)
                .fit()
                .error(R.drawable.ic_unlike)
                .into(myViewHolder.circleImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "onSuccess: Image Successfully Loaded");
                    }
                    @Override
                    public void onError(Exception e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }
                });
        myViewHolder.comment.setText(model.getComment());
        myViewHolder.timeTextView.setText(model.getTimestamp());
        myViewHolder.nameTextView.setText(model.getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView nameTextView,timeTextView,comment;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.comment_imageview);
            nameTextView=itemView.findViewById(R.id.comment_users_name);
            timeTextView=itemView.findViewById(R.id.comment_timesamp);
            comment=itemView.findViewById(R.id.comment_textview);

        }
    }
}
