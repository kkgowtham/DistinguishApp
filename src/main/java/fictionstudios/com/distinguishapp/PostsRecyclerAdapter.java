package fictionstudios.com.distinguishapp;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
public class PostsRecyclerAdapter extends RecyclerView.Adapter<PostsRecyclerAdapter.MyViewHolder> {

    FirebaseUser user;
    private static final String TAG = "PostsRecyclerAdapter";
  private   OnItemClickListerner mOnItemClickListener;
  private OnCommentBtnClickListener onCommentBtnClickListener;
   private Context context;
   private ArrayList<PostModel> list;
   private ArrayList<String> likeArray;
     PostsRecyclerAdapter(Context context, ArrayList<PostModel> list,ArrayList<String> likeArray,OnItemClickListerner mOnItemClickListener,OnCommentBtnClickListener onCommentBtnClickListener)
    {
        this.likeArray=likeArray;
        this.context=context;
        this.list=list;
        this.mOnItemClickListener=mOnItemClickListener;
        this.onCommentBtnClickListener=onCommentBtnClickListener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.single_item_post,viewGroup,false);
        return new MyViewHolder(view,mOnItemClickListener,onCommentBtnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
            final PostModel model=list.get(i);
            myViewHolder.title1.setText(model.getTerm1());
            myViewHolder.title2.setText(model.getTerm2());
            myViewHolder.description.setText(model.getDescription());
            myViewHolder.comments.setText(model.getComments());
            myViewHolder.likes.setText(model.getLikes());
            if (likeArray.contains(model.getId()))
            {
                myViewHolder.likeToggle.setChecked(true);
            }
            else {
                myViewHolder.likeToggle.setChecked(false);
            }
        if (myViewHolder.likeToggle.isChecked())
        {
            myViewHolder.likeToggle.setBackgroundResource(R.drawable.ic_like);
        }
        else {
            myViewHolder.likeToggle.setBackgroundResource(R.drawable.ic_unlike);

        }
            myViewHolder.likeToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                    {
                        myViewHolder.likeToggle.setBackgroundResource(R.drawable.ic_like);
                        likeArray.add(String.valueOf(model.getId()));
                        int n=Integer.parseInt(model.getLikes())+1;
                        model.setLikes(String.valueOf(n));
                        myViewHolder.likes.setText(String.valueOf(n));
                        if (user!=null) {
                            updateData("like", model.getId(), user.getEmail(), String.valueOf(n));
                        }
                    }else{
                        myViewHolder.likeToggle.setBackgroundResource(R.drawable.ic_unlike);
                        likeArray.remove(String.valueOf(model.getId()));
                        int n=Integer.parseInt(model.getLikes())-1;
                        myViewHolder.likes.setText(String.valueOf(n));
                        model.setLikes(String.valueOf(n));
                        if(user!=null) {
                            updateData("unlike", model.getId(), user.getEmail(), String.valueOf(n));
                        }
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

  public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

         OnItemClickListerner onItemClickListerner;
         TextView title1,title2;
         TextView description,comments,likes;
         ToggleButton likeToggle;
         Button commentsBtn;
         OnCommentBtnClickListener onCommentBtnClickListener;
         MyViewHolder(@NonNull View itemView,OnItemClickListerner onItemClickListerner,OnCommentBtnClickListener onCommentBtnClickListener) {
            super(itemView);
            this.onItemClickListerner=onItemClickListerner;
            this.onCommentBtnClickListener=onCommentBtnClickListener;
            itemView.setOnClickListener(this);
            title1=itemView.findViewById(R.id.title1);
            title2=itemView.findViewById(R.id.title2);
            likes=itemView.findViewById(R.id.like_tv);
            description=itemView.findViewById(R.id.description);
            comments=itemView.findViewById(R.id.comments_tv);
            commentsBtn=itemView.findViewById(R.id.comment_btn);
            commentsBtn.setOnClickListener(this);
            likeToggle=itemView.findViewById(R.id.button_favorite);
        }

      @Override
      public void onClick(View v) {
             if (v==commentsBtn)
             {
                 onCommentBtnClickListener.onCommentButtonClick(getAdapterPosition());
             }else {
                 onItemClickListerner.onItemClick(getAdapterPosition());
             }
      }
  }

    public interface OnItemClickListerner
    {
         void onItemClick(int pos);
    }
    public interface OnCommentBtnClickListener{
         void onCommentButtonClick(int pos);
    }
    public void updateData(String fun,String postid,String email,String num)
    {
        StringRequest request=new StringRequest(Request.Method.GET, "http://192.168.1.105/distinguish/updatelike.php?postid="+postid+"&numlikes="+num+"&email="+email+"&fun="+fun,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: "+response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error.getMessage());
            }
        });
        SingleTonVolley.getInstance(context).add(request);
    }
}
