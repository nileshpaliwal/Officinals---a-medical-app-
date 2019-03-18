package in.officinal.officinals;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by student on 23/03/18.
 */

public class UnderVerification extends AppCompatActivity {

    private Button Login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.under_verification);

        Login = (Button)findViewById(R.id.login);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UnderVerification.this,DoctorSignIn.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
