package in.officinal.officinals;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by mypc on 3/24/2018.
 */

public class ProfileDoctor extends AppCompatActivity {

    private TextView Name;
    private TextView Qualification;
    private TextView Contact;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_doctor);

        Name = (TextView)findViewById(R.id.name);
        Qualification = (TextView)findViewById(R.id.qualification);
        Contact = (TextView)findViewById(R.id.contact);



        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String phone = user.getPhoneNumber();
        if(phone.equals("+918769049543")){
                Name.setText("Jai Pillai");
                Qualification.setText("MBBS");
                Contact.setText(phone);



        }

        if(phone.equals("+919461671344")){
            Name.setText("Nilesh Paliwal");
            Qualification.setText("MBBS");
            Contact.setText(phone);


        }
    }
}
