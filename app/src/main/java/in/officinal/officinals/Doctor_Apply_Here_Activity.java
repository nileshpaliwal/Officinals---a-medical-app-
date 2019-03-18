package in.officinal.officinals;

import android.*;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Doctor_Apply_Here_Activity extends AppCompatActivity {


    private EditText Name;
    private EditText City;
    private EditText Qualification;
    private EditText Experience;
    private EditText Email;
    private EditText Mobile_No;
    private EditText Aadhar_No;
    private Button Submit;
    private CheckBox Agreement;
    private static final int CAMERA_REQUEST = 1888;
    private static final int LICENSE_REQUEST = 1834;

    private ImageView imageshow;
    private ImageButton UploadImage;
    private ImageButton UploadDocument;
    String mProfilePath;
    String mLicensePath;
    private FirebaseStorage storage;
    UploadTask uploadTask;
    private ProgressDialog progressDialog;




    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_apply_here);

        Name=(EditText)findViewById(R.id.nametext);
        City=(EditText)findViewById(R.id.citytext);
        Qualification=(EditText)findViewById(R.id.qualificationtext);
        Experience=(EditText)findViewById(R.id.experiencetext);
        Email=(EditText)findViewById(R.id.email);
        Mobile_No=(EditText)findViewById(R.id.mobile);
        Aadhar_No=(EditText)findViewById(R.id.aadhar);
        Submit=(Button) findViewById(R.id.submit);
        Agreement=(CheckBox)findViewById(R.id.agree);
        UploadImage = (ImageButton)findViewById(R.id.imageButton);
        UploadDocument = (ImageButton)findViewById(R.id.imageButton2);





        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((!TextUtils.isEmpty(Name.getText().toString()))&&(!TextUtils.isEmpty(City.getText().toString()))&&(!TextUtils.isEmpty(Qualification.getText().toString()))
                        &&(!TextUtils.isEmpty(Experience.getText().toString()))&&(!TextUtils.isEmpty(Email.getText().toString()))&&(!TextUtils.isEmpty(Mobile_No.getText().toString()))
                        &&(!TextUtils.isEmpty(Aadhar_No.getText().toString()))){

                    if (Aadhar_No.length()<13){

                        Toast.makeText(getApplicationContext(), "Invalid Adhar Number!!!", Toast.LENGTH_SHORT).show();
                    }else{
                        if(Agreement.isChecked()){
                            pushentries();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Please Agree to Terms & Conditions",Toast.LENGTH_SHORT).show();

                        }

                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Some field is missiong",Toast.LENGTH_SHORT).show();
                }

            }



            private void pushentries() {

                storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference(Mobile_No.getText().toString());
                StorageReference imageRef = storageRef.child("profile");
                StorageReference licenseRef = storageRef.child("license");
                Uri imageUri = Uri.parse(mProfilePath);
                File file1 = new File(imageUri.getPath());
                Uri licenseUri = Uri.parse(mLicensePath);
                File file2 = new File(licenseUri.getPath());
                uploadTask = imageRef.putFile(imageUri);
                uploadTask = licenseRef.putFile(licenseUri);

                final Map<String,Object>newDoc = new HashMap<>();
                newDoc.put("Name",Name.getText().toString());
                newDoc.put("City",City.getText().toString());
                newDoc.put("Qualifications",Qualification.getText().toString());
                newDoc.put("Experience",Experience.getText().toString());
                newDoc.put("Email",Email.getText().toString());
                newDoc.put("Mobile Number",Mobile_No.getText().toString());
                newDoc.put("Aadhar Number",Aadhar_No.getText().toString());



                mFirestore = FirebaseFirestore.getInstance();
                mFirestore.collection("Doctor Registrations").document(Mobile_No.getText().toString()).collection("Details")
                        .add(newDoc)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {



                            }
                        });



                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        Toast.makeText(getApplicationContext(),"Thanks for applying. Your account will be enabled within 24 Hours after verification",Toast.LENGTH_SHORT).show();
                        Log.d("Success","Registrations Sent");
                        Intent intent=new Intent(Doctor_Apply_Here_Activity.this,UnderVerification.class);
                        startActivity(intent);
                        finish();


                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Error while Registring",Toast.LENGTH_SHORT).show();

                                Log.d("Error","Registrations not Sent");
                            }});


            }
        });




UploadImage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        try {
            captureCameraImage();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
});

UploadDocument.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        try {
            captureCameraImage2();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



});



    }
    private void captureCameraImage() throws IOException {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getApplication().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                return;
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        createImageFile());

                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);

            }



        }
    }
    private void captureCameraImage2() throws IOException {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getApplication().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile2();
            } catch (IOException ex) {
                // Error occurred while creating the File
                return;
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        createImageFile2());

                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, LICENSE_REQUEST);

            }



        }
    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        mProfilePath = "file:" + image.getAbsolutePath();
        return image;
    }

    private File createImageFile2() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        mLicensePath = "file:" + image.getAbsolutePath();
        return image;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    Uri imageUri = Uri.parse(mProfilePath);
                    File file = new File(imageUri.getPath());

                    Bitmap mphoto = null;
                    try {
                        mphoto = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), imageUri);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    final Bitmap finalMphoto = mphoto;
                    final Bitmap scaledphoto = scaleDown(finalMphoto, (int) (finalMphoto.getWidth() * 0.1), true);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            UploadImage.setImageBitmap(scaledphoto);

                        }
                    });

                }
            });
        }
        if (requestCode == LICENSE_REQUEST && resultCode == RESULT_OK) {

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    Uri imageUri = Uri.parse(mLicensePath);
                    File file = new File(imageUri.getPath());

                    Bitmap mphoto = null;
                    try {
                        mphoto = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), imageUri);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    final Bitmap finalMphoto = mphoto;
                    final Bitmap scaledphoto = scaleDown(finalMphoto, (int) (finalMphoto.getWidth() * 0.1), true);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            UploadDocument.setImageBitmap(scaledphoto);

                        }
                    });

                }
            });
        }


    }

    public Bitmap scaleDown(final Bitmap realImage, final float maxImageSize,
                            final boolean filter) {

        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());
        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width, height, filter);
        return newBitmap;
    }
}
