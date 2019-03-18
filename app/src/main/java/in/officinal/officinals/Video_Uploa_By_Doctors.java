package in.officinal.officinals;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

/**
 * Created by mypc on 3/25/2018.
 */

public class Video_Uploa_By_Doctors extends AppCompatActivity {
    private ImageView SelectImageButton;
    private FirebaseStorage storage;
    private Button UploadButton;
    private FirebaseAuth mAuth;
    UploadTask uploadTask;
    private Spinner Disease;
    private Spinner Symptoms;


    private static final int SELECT_VIDEO = 1;
    private String selectedVideoPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_video);

        Disease = (Spinner)findViewById(R.id.diseasespinner);
        Symptoms = (Spinner)findViewById(R.id.symptomsspinner);


        ArrayAdapter<CharSequence> d = ArrayAdapter.createFromResource(this,
                R.array.disease,android.R.layout.simple_list_item_1);
        d.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Disease.setAdapter(d);

        ArrayAdapter<CharSequence> sm = ArrayAdapter.createFromResource(this,
                R.array.symptoms,android.R.layout.simple_list_item_1);
        sm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Symptoms.setAdapter(sm);




        SelectImageButton = (ImageView)findViewById(R.id.imagebutton);

        mAuth = FirebaseAuth.getInstance();
        UploadButton = (Button)findViewById(R.id.uploadbutton);



        SelectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, SELECT_VIDEO);
            }
        });


        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                String mobile = user.getPhoneNumber();
                storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference(mobile);
                StorageReference VideoRef = storageRef.child("Video");
                Uri videoUri = Uri.parse(selectedVideoPath);
                uploadTask = VideoRef.putFile(videoUri);
                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        Toast.makeText(getApplicationContext(),"Video has been uploaded successfully",Toast.LENGTH_LONG).show();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(getApplicationContext(),"Video cannot be uploaded",Toast.LENGTH_LONG).show();

                            }
                        });

            }
        });

    }




    @ Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_VIDEO) {
                selectedVideoPath = getPath(data.getData());
                if(selectedVideoPath == null) {
                    Toast.makeText(getApplicationContext(),"gkyfdfsjdgf",Toast.LENGTH_SHORT).show();
                } else {
                    /**
                     * try to do something there
                     * selectedVideoPath is path to the selected video
                     */



                }
            }
        }

    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if(cursor!=null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        else return null;
    }


}
