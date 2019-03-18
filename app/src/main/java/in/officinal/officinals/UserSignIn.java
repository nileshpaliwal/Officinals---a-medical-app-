package in.officinal.officinals;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserSignIn extends AppCompatActivity {

    private Button sendotp;
    private Button enterotp;
    private EditText usermobile;
    private EditText userotp;
    private int mButton = 0;
    private TextView EnterOTPText;
    private TextView RenterMobile;
    ProgressDialog progressDialog;
    private int renter=0;
    private String mVerificationId;
    private EditText name;
    private EditText aadhar;
    private TextView Name;
    private TextView Aadhar;
    private TextView Mobile;



    //Firebase
    private FirebaseAuth mAuth;


    private PhoneAuthProvider.ForceResendingToken mResendToken;


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_in);

        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user!=null){
            Intent intent = new Intent(UserSignIn.this,Home.class);
            startActivity(intent);
            finish();
        }
        RenterMobile = (TextView)findViewById(R.id.rentermobile);
        userotp=(EditText)findViewById(R.id.OTP);
        enterotp = (Button)findViewById(R.id.VerifyUserOtp);
        sendotp = (Button) findViewById(R.id.SendUserOtp);
        usermobile = (EditText)findViewById(R.id.UserMobile);
        name = (EditText)findViewById(R.id.UserNameEditText);
        Name = (TextView)findViewById(R.id.nameTextview);
        Aadhar = (TextView)findViewById(R.id.aadharTextview);
        aadhar = (EditText)findViewById(R.id.aadharEdittext);
        Mobile = (TextView)findViewById(R.id.mobiletext);

        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String namee = name.getText().toString();
                String phonenumber = usermobile.getText().toString();
                String adhhar = aadhar.getText().toString();
                if ((TextUtils.isEmpty(phonenumber)) ) {
                    Toast.makeText(getApplicationContext(), "Please enter the phone number to continue.", Toast.LENGTH_SHORT).show();
                    return;
                }else {


                    if (mButton == 0) {
                        usermobile.setVisibility(View.VISIBLE);
                        sendotp.setEnabled(false);
                        usermobile.setEnabled(false);
                        phonenumber = usermobile.getText().toString();
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + phonenumber,
                                60,
                                java.util.concurrent.TimeUnit.SECONDS,
                                UserSignIn.this,
                                mCallbacks

                        );

                        Intent intent = new Intent(UserSignIn.this, UserOTP.class);


                    }
                }
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                String OTP = phoneAuthCredential.getSmsCode();
                userotp.setText(OTP);
                //progressDialog.setMessage("Signing in");
                //progressDialog.show();

                signInWithPhoneAuthCredential(phoneAuthCredential);


            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getApplicationContext(),"Error occurred while Sending OTP",Toast.LENGTH_LONG).show();
                usermobile.setEnabled(true);
                sendotp.setVisibility(View.VISIBLE);
                sendotp.setEnabled(true);
                //progressDialog.cancel();
                enterotp.setVisibility(View.INVISIBLE);


            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.


                // Save verification ID and resending token so we can use them later

                mButton=1;
                //progressDialog.dismiss();
                Mobile.setVisibility(View.INVISIBLE);
                aadhar.setVisibility(View.INVISIBLE);
                Aadhar.setVisibility(View.INVISIBLE);
                name.setVisibility(View.INVISIBLE);
                Name.setVisibility(View.INVISIBLE);
                mVerificationId = verificationId;
                mResendToken = token;
                sendotp.setEnabled(true);
                usermobile.setVisibility(View.VISIBLE);
                usermobile.setVisibility(View.INVISIBLE);
                userotp.setVisibility(View.VISIBLE);
                usermobile.setVisibility(View.INVISIBLE);
                userotp.setVisibility(View.VISIBLE);
                RenterMobile.setVisibility(View.VISIBLE);
                sendotp.setVisibility(View.INVISIBLE);
                enterotp.setVisibility(View.VISIBLE);
                RenterMobile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendotp.setEnabled(true);
                        usermobile.setEnabled(true);
                        sendotp.setVisibility(View.VISIBLE);
                        enterotp.setVisibility(View.INVISIBLE);
                        usermobile.setVisibility(View.VISIBLE);
                        userotp.setVisibility(View.INVISIBLE);
                        EnterOTPText.setVisibility(View.INVISIBLE);
                        RenterMobile.setVisibility(View.GONE);
                        mButton=0;
                        renter=1;
                        //progressDialog.cancel();

                    }
                });

            }
        };


        enterotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String OTP = userotp.getText().toString();
                if(!TextUtils.isEmpty(OTP)){
                    sendotp.setEnabled(false);
                    String verificationCode = userotp.getText().toString();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                   // progressDialog.setMessage("Verifying OTP");
                   // progressDialog.show();
                    signInWithPhoneAuthCredential(credential);
                }
                else{
                    Toast.makeText(getApplicationContext(),"OTP cannot be empty",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser User_Mob = FirebaseAuth.getInstance().getCurrentUser();
                            assert User_Mob != null;
                            String mob = User_Mob.getPhoneNumber();

                            FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
                            assert mob != null;
                            Map<String, Object> docData = new HashMap<>();


                            mFirestore.collection("Users").document(mob).set(docData);

                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = task.getResult().getUser();
                            Intent mainintent= new Intent(UserSignIn.this, Home.class);
                            //progressDialog.dismiss();
                            startActivity(mainintent);
                            finish();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            usermobile.setEnabled(true);
                            sendotp.setEnabled(true);
                            //progressDialog.cancel();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                if(renter==0) {
                                    Toast.makeText(getApplicationContext(), "You have entered an invalid OTP", Toast.LENGTH_LONG).show();
                                    RenterMobile.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                });
    }
}
