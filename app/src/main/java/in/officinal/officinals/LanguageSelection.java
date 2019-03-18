package in.officinal.officinals;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Jarvis on 3/23/2018.
 */

public class LanguageSelection extends AppCompatActivity {

   /* private RadioButton english;
    private RadioButton hindi;
    private Button Next;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_selection);

        /*english = (RadioButton)findViewById(R.id.radio_english);
        hindi=(RadioButton)findViewById(R.id.radio_hindi);
        Next = (Button)findViewById(R.id.next);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(english.isChecked()){

                    Intent intent = new Intent(LanguageSelection.this,UserSignIn.class);
                    startActivity(intent);
                    finish();
                }

                else if(hindi.isChecked()){
                    Toast.makeText(getApplicationContext(),"Under Construction",Toast.LENGTH_SHORT).show();


                }
                else{
                    Toast.makeText(getApplicationContext(),"Please select User Type",Toast.LENGTH_SHORT).show();
                }

            }
        });*/



    }

    public void buttonOnClick(View v)
    {
        RadioButton rn = (RadioButton) findViewById(R.id.radio_english);
        RadioButton rp = (RadioButton) findViewById(R.id.radio_hindi);


        if(rp.isChecked()) {

            Intent intent = new Intent(LanguageSelection.this,UserSignIn.class);
            finish();

           // Toast.makeText(getApplicationContext(),"Under Construction...",Toast.LENGTH_SHORT).show();
        }
        else if (rn.isChecked())
        {

            startActivity(new Intent(LanguageSelection.this,UserSignIn.class));
            finish();

        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please select Way to continue...",Toast.LENGTH_SHORT).show();
        }
    }

}
