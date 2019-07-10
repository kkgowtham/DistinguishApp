package fictionstudios.com.distinguishapp;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class AddPostImageFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "AddPostImageFragment";
    private Bitmap bitmap1;
   static AddPostImageFragment fragment;
    private String downloadUrl="";

    boolean a=false;
    public AddPostImageFragment() {
        // Required empty public constructor
    }
    private ImageButton imageButton1,imageButton2;

    private  Context context;
    private CheckBox checkBox;
    private static final int REQUEST_CODE1=101;
    private static final int  REQUEST_CODE2=102;
    private static final int REQUEST_CODE3=103;
    private Button thumbNailBtn;
    Button previousBtn,saveBtn;
    private LinearLayout layoutThumbnail;
    private ImageView imageView1,imageView2;
    private Uri uri1,uri2,uri3;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add_post_image, container, false);
        imageButton1=view.findViewById(R.id.image1);
        imageButton2=view.findViewById(R.id.image2);
        thumbNailBtn=view.findViewById(R.id.attach_thumbnail_btn);
        layoutThumbnail=view.findViewById(R.id.linear_layout_thumbnail);
        imageView1=view.findViewById(R.id.image1_thumbnail);
        imageView2=view.findViewById(R.id.image2_thumbnail);
        previousBtn=view.findViewById(R.id.previous_btn1);
        previousBtn.setOnClickListener(this);
        saveBtn=view.findViewById(R.id.save_button);
        saveBtn.setOnClickListener(this);
        thumbNailBtn.setOnClickListener(this);
        imageButton1.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        checkBox = view.findViewById(R.id.checkbox_creteThumbnail);
        checkBox.setOnCheckedChangeListener(this);
        return  view;
    }


    public static AddPostImageFragment getInstance()
    {
        if (fragment==null)
        {
            fragment = new AddPostImageFragment();
        }
        return fragment;
    }



    @Override
    public void onClick(View v) {
        if (v==imageButton1)
        {
            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("image/*");

            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            pickIntent.setType("image/*");

            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

            startActivityForResult(chooserIntent, REQUEST_CODE1);
        }
        if (v==imageButton2)
        {
            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("image/*");
            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//          pickIntent.setType("image/*");
            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

            startActivityForResult(chooserIntent, REQUEST_CODE2);
        }

        if (v==thumbNailBtn)
        {
            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("image/*");
            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//          pickIntent.setType("image/*");
            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

            startActivityForResult(chooserIntent, REQUEST_CODE3);
        }

        if (v.getId()==R.id.save_button)
        {
            Toast.makeText(context,"Saving",Toast.LENGTH_LONG).show();
            savePost();
        }
        if (v.getId()==R.id.previous_btn1)
        {
            assert getActivity()!=null;
            FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.container_addpost,AddPostExplainationFragment.getInstance()).commit();        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE1 && resultCode == RESULT_OK && data != null) {
            uri1= data.getData();
            Picasso.get().load(uri1).into(imageButton1);
        }
        if (requestCode == REQUEST_CODE2 && resultCode == RESULT_OK && data != null) {
             uri2 = data.getData();
            Picasso.get().load(uri2).into(imageButton2);
        }
        if (requestCode == REQUEST_CODE3 && resultCode == RESULT_OK && data != null) {
            uri3 = data.getData();
            assert getContext()!=null;
            try {
                assert uri3 != null;
                InputStream inputStream=getContext().getContentResolver().openInputStream(uri3);
                Drawable drawable=BitmapDrawable.createFromStream(inputStream,uri3.toString());
                thumbNailBtn.setBackground(drawable);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked)
            {
                a=true;
            }else {
                a=false;
            }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    private String saveImage(Uri uri1) {
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageReference= storage.getReference().child("Storage Images");
        if (uri1!=null && uri2!=null) {
            Picasso.get().load(uri1).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    bitmap1=bitmap;
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    Log.d(TAG, "onBitmapFailed: "+e.getMessage());
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
            ByteArrayOutputStream outputStream1=new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.JPEG,100,outputStream1);
            byte[] arr1=outputStream1.toByteArray();
            final StorageReference ref1=storageReference.child(UUID.randomUUID().toString()+".jpj");
            UploadTask uploadTask1=ref1.putBytes(arr1);
            uploadTask1.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "onSuccess: "+"Image Uploaded");

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: "+e.getMessage());
                }
            });
            Task<Uri> task1=uploadTask1.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful())
                    {
                        if (task.getException()!=null)
                        {
                            throw task.getException();
                        }
                    }
                    return ref1.getDownloadUrl();

                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful())
                    {
                        Uri uri=task.getResult();
                        downloadUrl=String.valueOf(uri);
                    }else {
                        Log.d(TAG, "onComplete: "+task.getException());
                    }
                }
            });
        }
        return downloadUrl;
    }


    public void savePost() {
        String email;
        String downloadurl1=saveImage(uri1);
        String downloadUrl2=saveImage(uri2);
        String thumbnail=null;
            thumbnail =saveImageFromBitmap();
        FirebaseFirestore ff=FirebaseFirestore.getInstance();
        String term1=StaticData.term1;
        String term2=StaticData.term2;
        JSONArray array1=new JSONArray(StaticData.explain1);
        JSONArray array2=new JSONArray(StaticData.explain2);
        String explain1=array1.toString();
        String explain2=array2.toString();
        String category="Sports";
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null) {
            email=user.getEmail();
        }else
        {
            email="";
        }
        PostModel model=new PostModel();
        model.setAddedby(email);
        model.setCategory(category);
        model.setComments("");
        model.setLikes("");
        model.setId(UUID.randomUUID().toString());
        model.setExplain1(explain1);
        model.setExplain2(explain2);
        model.setImageurl1(downloadurl1);
        model.setImageurl2(downloadUrl2);
        model.setThumbnail(thumbnail);

        model.setTerm2(term2);
        model.setDate("");
        model.setTimestamp("");
        model.setDescription(StaticData.description);
        Log.d(TAG, "savePost: "+model.toString());
        FirebaseFirestore db=FirebaseFirestore.getInstance();model.setTerm1(term1);
        db.collection("posts").add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "onSuccess: "+documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
            }
        });
    }
    private String saveImageFromBitmap() {
            Picasso.get().load(uri1).into(imageView1);
            Picasso.get().load(uri2).into(imageView2);
            layoutThumbnail.setVisibility(View.VISIBLE);
            Bitmap bitmapThumnail = Bitmap.createBitmap(layoutThumbnail.getWidth(), layoutThumbnail.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapThumnail);
            layoutThumbnail.draw(canvas);
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference().child("Storage Images");
            ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 0, outputStream1);
            byte[] arr1 = outputStream1.toByteArray();
            final StorageReference ref1 = storageReference.child(UUID.randomUUID().toString() + ".jpj");
            UploadTask uploadTask1 = ref1.putBytes(arr1);
            uploadTask1.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "onSuccess: " + "Image Uploaded");

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: " + e.getMessage());
                }
            });
            Task<Uri> task1 = uploadTask1.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        if (task.getException() != null) {
                            throw task.getException();
                        }
                    }
                    return ref1.getDownloadUrl();

                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri uri = task.getResult();
                        downloadUrl = String.valueOf(uri);
                    } else {
                        Log.d(TAG, "onComplete: " + task.getException());
                    }
                }
            });
            return downloadUrl;

    }
}
