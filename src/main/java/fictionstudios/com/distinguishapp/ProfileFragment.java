package fictionstudios.com.distinguishapp;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    Context mContext;
    Button signOutBtn;
    FirebaseAuth auth=FirebaseAuth.getInstance();

    private static final String TAG = "ProfileFragment";
    @Override
    public void onStart() {
        super.onStart();

        if (auth.getCurrentUser()==null)
        {
            try {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,new SignInFragment()).commit();
            }catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile,container,false);
         FirebaseUser user=auth.getCurrentUser();
        signOutBtn=view.findViewById(R.id.sign_out_btn);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                GoogleSignInClient client=GoogleSignIn.getClient(mContext,gso);
                client.revokeAccess().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: "+"Account removed");
                    }
                });
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,new SignInFragment()).commit();
            }
        });
        if (user!=null)
        {
            String imageUrl = String.valueOf(user.getPhotoUrl());
            String emails = user.getEmail();
            String names = user.getDisplayName();
            CircleImageView imageView = view.findViewById(R.id.image_iv);
            TextView email = view.findViewById(R.id.email_tv);
            TextView name = view.findViewById(R.id.name_tv);
            Picasso.get().load(imageUrl).into(imageView);
            email.setText(emails);
            name.setText(names);
        }
        return view;
    }
}
