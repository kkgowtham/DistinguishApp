package fictionstudios.com.distinguishapp;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.UUID;


public class AddPostTitleDescFragment extends Fragment {


    private static final String TAG = "AddPostTitleDescFragmen";
    static AddPostTitleDescFragment fragment;
    public AddPostTitleDescFragment() {
    }

    private Bitmap bitmap1=null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_post_title_desc, container, false);
        final EditText term1EditText=view.findViewById(R.id.term1_edittext);
        final EditText term2EditText=view.findViewById(R.id.term2_edittext);
        final EditText descEditText = view.findViewById(R.id.description_edittext);
        Button button=view.findViewById(R.id.next_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String term1=term1EditText.getText().toString().trim();
                String term2= term2EditText.getText().toString().trim();
                String description = descEditText.getText().toString().trim();
                if (term1.equals(""))
                {
                    term1EditText.setError("Field Cannot Be Empty");
                    return;
                }
                if (term2.equals(""))
                {
                    term2EditText.setError("Field Cannot Be Empty");
                    return;
                }
                if (description.equals(""))
                {
                    descEditText.setError("Please Enter Description");
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putString("term1",term1);
                bundle.putString("term2",term2);
                bundle.putString("description",description);
                StaticData.term1=term1;
                StaticData.term2=term2;
                StaticData.description=description;
                assert getActivity()!=null;
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.container_addpost,AddPostExplainationFragment.getInstance()).commit();            }
        });
        return view;
    }

    public static AddPostTitleDescFragment getInstance()
    {
        if (fragment==null)
        {
            fragment=new AddPostTitleDescFragment();
        }
        return fragment;
    }


}
