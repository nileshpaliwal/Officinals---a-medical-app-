package in.officinal.officinals;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by mypc on 3/24/2018.
 */

public class Settings extends AppCompatActivity {

    private Spinner spinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        spinner = (Spinner)findViewById(R.id.languagespinner);

        ArrayAdapter<CharSequence> lg = ArrayAdapter.createFromResource(this,
                R.array.Language,android.R.layout.simple_list_item_1);
        lg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(lg);


    }
}
