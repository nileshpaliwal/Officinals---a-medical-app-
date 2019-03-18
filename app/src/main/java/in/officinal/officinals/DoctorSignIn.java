package in.officinal.officinals;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by student on 23/03/18.
 */

public class DoctorSignIn extends AppCompatActivity {

    private EditText phonenumber;
    private EditText otp;
    private Button Login;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    private int mButton = 0;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private int renter=0;
    private String mVerificationId;
    private Button VerifyOTP;
    private TextView apply;
    private TextView Mobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_signin);

        apply = (TextView)findViewById(R.id.docApply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorSignIn.this,Doctor_Apply_Here_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        Mobile = (TextView)findViewById(R.id.mobiletext);
        Login = (Button) findViewById(R.id.loginbutton);
        phonenumber = (EditText) findViewById(R.id.mobile);
        otp = (EditText) findViewById(R.id.otp);
        VerifyOTP = (Button)findViewById(R.id.verifybutton);
        mAuth = FirebaseAuth.getInstance();

        final String DocPhone = phonenumber.getText().toString();
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mFirestore = FirebaseFirestore.getInstance();
                mFirestore.collection("Verified Doctors").document(DocPhone).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                        String regdocphone = documentSnapshot.getString("Phone");


                        if ((phonenumber.getText().toString().equals(regdocphone))) {

                            String PhoneNumber = phonenumber.getText().toString();
                            if ((TextUtils.isEmpty(PhoneNumber))) {
                                Toast.makeText(getApplicationContext(), "Please fill complete details to continue.", Toast.LENGTH_SHORT).show();
                                return;
                            } else {


                                if (mButton == 0) {
                                    phonenumber.setVisibility(View.VISIBLE);
                                    Login.setEnabled(false);
                                    phonenumber.setEnabled(false);
                                    PhoneNumber = phonenumber.getText().toString();
                                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                            "+91" + PhoneNumber,
                                            60,
                                            java.util.concurrent.TimeUnit.SECONDS,
                                            DoctorSignIn.this,
                                            mCallbacks

                                    );


                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "You are not a Regstered Doctor Yet!!!! Apply First", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                String OTP = phoneAuthCredential.getSmsCode();
                otp.setText(OTP);
                //progressDialog.setMessage("Signing in");
                //progressDialog.show();

                signInWithPhoneAuthCredential(phoneAuthCredential);


            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getApplicationContext(),"Error occurred while Sending OTP",Toast.LENGTH_LONG).show();
                phonenumber.setEnabled(true);
                Login.setVisibility(View.VISIBLE);
                Login.setEnabled(true);
                //progressDialog.cancel();
                VerifyOTP.setVisibility(View.INVISIBLE);

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
                mVerificationId = verificationId;
                mResendToken = token;
                Login.setEnabled(true);
                phonenumber.setVisibility(View.VISIBLE);
                phonenumber.setVisibility(View.INVISIBLE);
                otp.setVisibility(View.VISIBLE);
                phonenumber.setVisibility(View.INVISIBLE);
                otp.setVisibility(View.VISIBLE);
                //RenterMobile.setVisibility(View.VISIBLE);
                Login.setVisibility(View.INVISIBLE);
                VerifyOTP.setVisibility(View.VISIBLE);
                /*RenterMobile.setOnClickListener(new View.OnClickListener() {
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
                });*/

            }
        };


        VerifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String OTP = otp.getText().toString();
                if(!TextUtils.isEmpty(OTP)){
                    Login.setEnabled(false);
                    String verificationCode = otp.getText().toString();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                    signInWithPhoneAuthCredential(credential);

                }
                else{
                    Toast.makeText(getApplicationContext(),"OTP cannot be empty",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth=FirebaseAuth.getInstance();
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
                            mFirestore.collection("Verified Doctors").document(mob).set(docData);
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = task.getResult().getUser();

                            Intent mainintent= new Intent(DoctorSignIn.this, DoctorHome.class);
                            //progressDialog.dismiss();
                            startActivity(mainintent);
                            finish();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            phonenumber.setEnabled(true);
                            Login.setEnabled(true);
                            //progressDialog.cancel();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                if(renter==0) {
                                    //Toast.makeText(getApplicationContext(), "You have entered an invalid OTP", Toast.LENGTH_LONG).show();
                                    //RenterMobile.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                });

    }
}


