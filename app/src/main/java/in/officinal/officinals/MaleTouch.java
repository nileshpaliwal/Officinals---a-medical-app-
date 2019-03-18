package in.officinal.officinals;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by mypc on 3/31/2018.
 */

public class MaleTouch extends AppCompatActivity {

    private Button Head;
    private Button Stomach;
    private Button Back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.male_touch);

        Head = (Button)findViewById(R.id.head);
        Head.setVisibility(View.VISIBLE);
        Head.setBackgroundColor(Color.TRANSPARENT);

        Stomach=(Button)findViewById(R.id.stomach);
        Stomach.setVisibility(View.VISIBLE);
        Stomach.setBackgroundColor(Color.TRANSPARENT);

        Back=(Button)findViewById(R.id.back);
        Back.setVisibility(View.VISIBLE);
        Back.setBackgroundColor(Color.TRANSPARENT);

        Head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"You Have Seleted Head",Toast.LENGTH_SHORT).show();

            }
        });

        Stomach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"You have Selected Stomach",Toast.LENGTH_SHORT).show();

            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"You have Selected Back",Toast.LENGTH_SHORT).show();

            }
        });



    }
}
