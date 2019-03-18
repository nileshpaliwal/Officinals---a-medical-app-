package in.officinal.officinals;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentActivity;
import android.widget.VideoView;
import android.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    private FirebaseAuth mAuth;
    private GridView gridViewTrending;
    private GridViewAdapter gridAdapterTrending;
    private DrawerLayout drawer;
    private GridView gridViewRecent;
    private GridViewAdapter gridAdapterRecent;
    private TextView MobileNumber;
    private int videoId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MobileNumber = (TextView)findViewById(R.id.mobilenumber);

        mAuth = FirebaseAuth.getInstance();

        SharedPreferences sp = getSharedPreferences("doc_or_user", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("key", 1);
        editor.apply();





        mAuth = FirebaseAuth.getInstance();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            String mobile = user.getPhoneNumber();
            View header = navigationView.getHeaderView(0);
            MobileNumber = (TextView) header.findViewById(R.id.mobilenumber);
            MobileNumber.setText(mobile);
        }
        if(user==null){
            Intent intent = new Intent(Home.this,DoctorOrUser.class);
            startActivity(intent);
            finish();
        }



        gridViewTrending = (GridView) findViewById(R.id.trendingVideoGrid);
        gridAdapterTrending = new GridViewAdapter(this, R.layout.grid_item_layout, getTrendingVideos());
        gridViewTrending.setAdapter(gridAdapterTrending);


        gridViewRecent = (GridView) findViewById(R.id.recentVideoGrid);
        gridAdapterRecent = new GridViewAdapter(this, R.layout.grid_item_layout, getRecentVideos());
        gridViewRecent.setAdapter(gridAdapterRecent);


       /* gridViewTrending.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

            }
        }); */
        gridViewTrending.setOnItemClickListener(myOnItemClickListener);
        gridViewRecent.setOnItemClickListener(myOnItemClickListener);
    }


    AdapterView.OnItemClickListener myOnItemClickListener  = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
          /*  String prompt = (String)parent.getItemAtPosition(position);*/
            //  Toast.makeText(getApplicationContext(),"Clicked" +position,Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),"Loading Video...",Toast.LENGTH_SHORT).show();
            setContentView(R.layout.video_content);

            String videoStr = "video"+ (position+1);
            videoId = getResources().getIdentifier("video1",  "id", getPackageName());
            String path = "android.resource://" + getPackageName() + "/raw/" + videoId;
            VideoView video=(VideoView) findViewById(videoId) ;
            if(video!=null)
            {
                MediaController mediaController = mediaController = new MediaController(Home.this);// new FullScreenMediaController(getApplicationContext());
                mediaController.setAnchorView(video);

                video.setMediaController(mediaController);

                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                lp.gravity = Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;
                mediaController.setLayoutParams(lp);

                ((ViewGroup) mediaController.getParent()).removeView(mediaController);

                ((FrameLayout) findViewById(R.id.controllerAnchor)).addView(mediaController);

                TextView txt=(TextView)findViewById( R.id.videText);
                Resources res = getResources();
                String[] vidText = res.getStringArray(R.array.content);

                txt.setText(vidText[position]);

                video.setVideoURI( Uri.parse(String.format("android.resource://%s/%s/%s",getPackageName(),"raw",videoStr)));

                video.requestFocus();
                video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    // Close the progress bar and play the video
                    VideoView video=(VideoView) findViewById(videoId) ;
                    public void onPrepared(MediaPlayer mp) {
                        //    video.start();
                        //  mp.start();
                    }
                });


            }
        }};

    private ArrayList<ImageItem> getTrendingVideos() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs =  getResources().obtainTypedArray(R.array.trendingVideos);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            Resources res = getResources(); //assuming in an activity for example, otherwise you can provide a context.
            String[] vidTitles = res.getStringArray(R.array.titles);
            imageItems.add(new ImageItem(bitmap, vidTitles[i]));
        }

    /*  for (TypedArray item : getMultiTypedArray(this, "categories")) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),item.getString(0));
            imageItems.add(new ImageItem(bitmap, item.getString(1)));
            category.title = item.getString(1);
            mCategories.add(category);
        }*/


        return imageItems;
    }

    private ArrayList<ImageItem> getRecentVideos() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs =  getResources().obtainTypedArray(R.array.recentVideos);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            Resources res = getResources(); //assuming in an activity for example, otherwise you can provide a context.
            String[] vidTitles = res.getStringArray(R.array.titles);
            imageItems.add(new ImageItem(bitmap, vidTitles[i]));
        }

        return imageItems;
    }

    public List<TypedArray> getMultiTypedArray(Context context, String key) {
        List<TypedArray> array = new ArrayList<>();

        try {
            Class<R.array> res = R.array.class;
            Field field;
            int counter = 0;

            do {
                field = res.getField(key + "_" + counter);
                array.add(context.getResources().obtainTypedArray(field.getInt(null)));
                counter++;
            } while (field != null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return array;
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.filter) {
            Intent intent = new Intent(Home.this,FilterActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logind) {
            Intent intent = new Intent(Home.this,DoctorSignIn.class);
            startActivity(intent);
            FirebaseAuth.getInstance().signOut();
            SharedPreferences sp = getSharedPreferences("doc_or_user", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("key", 0);
            editor.apply();
            finish();


            // Handle the camera action
        } else if (id == R.id.nav_search) {
            Uri gmmIntentUri = Uri.parse("google.streetview:cbll=46.414382,10.013988");
            // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            // Make the Intent explicit by setting the Google Maps package
            mapIntent.setPackage("com.google.android.apps.maps");
// Attempt to start an activity that can handle the Intent
            startActivity(mapIntent);

        }else if (id == R.id.gen){
            Intent intent = new Intent(Home.this,Generic_Activity.class);
            startActivity(intent);

        }


        else if (id == R.id.nav_settings) {
            Intent intent = new Intent(Home.this,Settings.class);
            startActivity(intent);


        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(Home.this,About.class);
            startActivity(intent);

        } else if (id == R.id.nav_contact) {
            Intent intent = new Intent(Home.this,Contact.class);
            startActivity(intent);

        } else if (id == R.id.nav_signout) {

            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(Home.this,UserSignIn.class);
            startActivity(intent);
            SharedPreferences sp = getSharedPreferences("doc_or_user", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            editor.putInt("key", 0);
            editor.apply();
            finish();



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            //preventing default implementation previous to
            //android.os.Build.VERSION_CODES.ECLAIR
            //   return false;
            // setContentView(R.layout.activity_home);
            /*Intent homeIntent = new Intent(Home.this, Home.class);
            startActivity(homeIntent);*/
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


}
