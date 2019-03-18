package in.officinal.officinals;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by mypc on 3/25/2018.
 */

public class FilterActivity extends AppCompatActivity{

    private Spinner Disease;
    private Spinner Symptoms;
    private Spinner Lang;
    private TextView touch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);

        Disease = (Spinner)findViewById(R.id.diseasespinner);
        Symptoms = (Spinner)findViewById(R.id.symptomsspinner);
        Lang = (Spinner)findViewById(R.id.languagespinner);
        touch = (TextView)findViewById(R.id.touch_and_select);

        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterActivity.this,MaleTouch.class);
                startActivity(intent);
            }
        });


        ArrayAdapter<CharSequence> d = ArrayAdapter.createFromResource(this,
                R.array.disease,android.R.layout.simple_list_item_1);
        d.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Disease.setAdapter(d);

        ArrayAdapter<CharSequence> sm = ArrayAdapter.createFromResource(this,
                R.array.symptoms,android.R.layout.simple_list_item_1);
        sm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Symptoms.setAdapter(sm);

        ArrayAdapter<CharSequence> ln = ArrayAdapter.createFromResource(this,
                R.array.Language,android.R.layout.simple_list_item_1);
        d.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Lang.setAdapter(ln);


    }
}
