package in.officinal.officinals;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.officinal.officinals.DoctorOrUser;

public class SplashActivity extends AppCompatActivity {
    private static int splash_time_out=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = getSharedPreferences("doc_or_user", Activity.MODE_PRIVATE);
                int key_value = sp.getInt("key", -1);



                if(key_value==1) {
                    Intent homeIntent = new Intent(SplashActivity.this, Home.class);
                    startActivity(homeIntent);
                    finish();
                }
                else if(key_value==2) {
                    Intent homeIntent = new Intent(SplashActivity.this, DoctorHome.class);
                    startActivity(homeIntent);
                    finish();
                }
                else {
                    Intent homeIntent = new Intent(SplashActivity.this, DoctorOrUser.class);
                    startActivity(homeIntent);
                    finish();
                }


            }
        },splash_time_out);
    }
}
