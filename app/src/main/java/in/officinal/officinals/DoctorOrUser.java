package in.officinal.officinals;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jarvis on 3/23/2018.
 */

public class DoctorOrUser extends AppCompatActivity {

   /* FirebaseAuth mAuth;
    FirebaseUser user;
    private Button button;*/

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.select_doctororuser);


        if (Build.VERSION.SDK_INT < 23) {
            //Do not need to check the permission
        } else {
            if (checkAndRequestPermissions()) {
                //If you have already permitted the permission
            }
        }



    }

    public void buttonOnClick(View v)
    {
        RadioButton rn = (RadioButton) findViewById(R.id.user_select);
        RadioButton rp = (RadioButton) findViewById(R.id.doctor_select);


        if(rp.isChecked())
        {
            startActivity(new Intent(DoctorOrUser.this,DoctorSignIn.class));
            finish();
        }
        else if (rn.isChecked())
        {

            startActivity(new Intent(DoctorOrUser.this,UserSignIn.class));
            finish();

        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please select Way to continue...",Toast.LENGTH_SHORT).show();
        }
    }

    /*public void buttonOnClick(View v)
    {
        RadioButton rn = (RadioButton) findViewById(R.id.user_select);
        RadioButton rp = (RadioButton) findViewById(R.id.doctor_select);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String phone = user.getPhoneNumber();


        if(rn.isChecked()) {
            if((user!=null)&&((!phone.equals("+918769049543"))||(!phone.equals("+919461671344")))){
                Intent intent = new Intent(DoctorOrUser.this,Home.class);
                startActivity(intent);
                finish();

            }//Toast.makeText(getApplicationContext(),"Please select language to continue...",Toast.LENGTH_SHORT).show();

        }

        else if (rp.isChecked()){

            {
                if ((user != null) && ((phone.equals("+918769049543")) || (phone.equals("+919461671344")))) {
                    Intent intent = new Intent(DoctorOrUser.this, DoctorHome.class);
                    startActivity(intent);
                    finish();
                    //Toast.makeText(getApplicationContext(),"Please select language to continue...",Toast.LENGTH_SHORT).show();

                } else {
                    startActivity(new Intent(DoctorOrUser.this, LanguageSelection.class));
                }
            }
        }

        else
        {
            Toast.makeText(getApplicationContext(),"Please select language to continue...",Toast.LENGTH_SHORT).show();
        }





    }*/


    private boolean checkAndRequestPermissions() {
        int permissionCAMERA = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA);


        int readStoragePermission = ContextCompat.checkSelfPermission(this,


                android.Manifest.permission.READ_EXTERNAL_STORAGE);

        int writeStoragePermission = ContextCompat.checkSelfPermission(this,


                Manifest.permission.WRITE_EXTERNAL_STORAGE);



        List<String> listPermissionsNeeded = new ArrayList<>();
        if (readStoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }
        if (writeStoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,


                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_CAMERA);
            return false;
        }

        return true;
    }

    }








