package com.example.dell.rhythmmusic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dell.rhythmmusic.Fragments.Favourites;
import com.example.dell.rhythmmusic.Fragments.MyPlaylist;
import com.example.dell.rhythmmusic.Fragments.RequestSong;

public class MusicActivity extends AppCompatActivity
        implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener,Favourites.OnFragmentInteractionListener, RequestSong.OnFragmentInteractionListener,
        MyPlaylist.OnFragmentInteractionListener
{
    private CardView trendingbtn, tophindibtn, topenglishbtn, artistsbtn, regionalbtn, genrebtn;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);



        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        trendingbtn = findViewById(R.id.trendingbtn);
        tophindibtn = findViewById(R.id.tophindibtn);
        topenglishbtn = findViewById(R.id.topenglishbtn);
        artistsbtn = findViewById(R.id.artistsbtn);
        regionalbtn = findViewById(R.id.regionalbtn);
        genrebtn = findViewById(R.id.genrebtn);

        trendingbtn.setOnClickListener(this);
        tophindibtn.setOnClickListener(this);
        topenglishbtn.setOnClickListener(this);
        artistsbtn.setOnClickListener(this);
        regionalbtn.setOnClickListener(this);
        genrebtn.setOnClickListener(this);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        MenuItem nav_login_signup = menu.findItem(R.id.nav_login_signup);

        SharedPreferences pref = getSharedPreferences("mydb",MODE_PRIVATE);
        boolean Islogin = pref.getBoolean("Islogin", false);
        if(Islogin){
            nav_login_signup.setTitle("Logout");
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            int fragments = getSupportFragmentManager().getBackStackEntryCount();
            if (fragments >= 1) {
               Intent it = new Intent(getBaseContext(),MusicActivity.class);
               it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(it);
            }
            else {
               finish();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.music, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch(id){

            case R.id.admin_login:

                startActivity(new Intent(getBaseContext(),AdminLogin.class));
                break;


        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
//                Toast.makeText(MusicActivity.this, "home", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(getBaseContext(),MusicActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(it);
                break;
            case R.id.nav_my_playlist:
//                Toast.makeText(MusicActivity.this, "my playlist", Toast.LENGTH_SHORT).show();
                showSong("MyPlaylist");

                break;
            case R.id.nav_favourite:

                displayFragment(new Favourites());
                break;

            case R.id.nav_request_song:
//                Toast.makeText(MusicActivity.this, "request", Toast.LENGTH_SHORT).show();
                displayFragment(new RequestSong());
                break;
            case R.id.action_about_us:
                startActivity(new Intent(getBaseContext(),aboutus.class));
                break;

            case R.id.nav_login_signup:
//                Toast.makeText(MusicActivity.this, "Login/signup", Toast.LENGTH_SHORT).show();
                SharedPreferences prefs = getSharedPreferences("mydb",MODE_PRIVATE);
                boolean Islogin = prefs.getBoolean("Islogin", false);
                NavigationView nv = (NavigationView) findViewById(R.id.nav_view);
                Menu menu = nv.getMenu();
                MenuItem nav_log_sign = menu.findItem(R.id.nav_login_signup);
                if(nav_log_sign.getTitle().equals("Logout")){
                    SharedPreferences sp = getSharedPreferences("mydb", MODE_PRIVATE);
                    sp.edit().clear().commit();
                    nav_log_sign.setTitle("Login/SignUp");
                }
                else {


                    /*if (Islogin) {
                        prefs.getString("keyemail", "");
                        prefs.getString("keypass", "");
                        prefs.getBoolean("Islogin", false);
                        Toast.makeText(this, "You are already logged in", Toast.LENGTH_SHORT).show();

                    } else {*/
                        // condition false take it user on login form
                        startActivity(new Intent(getBaseContext(), Login.class));
                    }
               // }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void displayFragment(Fragment fragment)
    {

        getSupportFragmentManager().beginTransaction().replace(R.id.frame,fragment).addToBackStack(null).commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    public void onClick(View v) {
        if(v==trendingbtn){
            showSong("Trending");
        }
        else if (v==tophindibtn){
            showSong("TopHindi");
        }
        else if (v==topenglishbtn){
            showSong("TopEnglish");
        }
        else if (v==artistsbtn){
            showSong("Artists");

        }
        else if (v==regionalbtn){
            showSong("Regional");

        }
        else if (v==genrebtn){
            showSong("Genre");

        }

    }
    public void showSong(String category){
        Intent it = new Intent(getBaseContext(), SongView.class);
        it.putExtra("key", category);
        startActivity(it);
    }
}
