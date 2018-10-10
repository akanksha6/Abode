package com.example.srushti.abode.AccountActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.srushti.abode.AccountActivity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class NavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseAuth auth;
    ProgressBar progressBar;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ImageButton SLand,Stenants,want;
    TextView abc,xyz,abc1,xyz1,abc2,xyz11;
    Boolean Status,Status1,Status2;
    WebView videoWeb;
    ImageView img1,img2,img3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(NavActivity.this,LoginActivity.class));
                    finish();
                } else {


                }
            }


        };
        LayoutInflater layoutInflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ScrollView home= (ScrollView) layoutInflater.inflate(R.layout.content_nav,null);
        SLand=findViewById(R.id.SearchLand);
        Stenants=findViewById(R.id.SearchTenants);
        abc=findViewById(R.id.abc);
        xyz=findViewById(R.id.xyz);
        abc1=findViewById(R.id.abc1);
        xyz1=findViewById(R.id.xyz1);
        xyz11=findViewById(R.id.xyz11);
        abc2=findViewById(R.id.acb2);
        want=findViewById(R.id.want);
        videoWeb=findViewById(R.id.web1);
        img1=findViewById(R.id.img1);
        img2=findViewById(R.id.img2);
        img3=findViewById(R.id.img3);


        SLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NavActivity.this,AreaActivity.class));
            }
        });
        Stenants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NavActivity.this,TentantsActivity.class));
            }
        });
        Status=false;
        abc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Status==false) {
                    xyz.setVisibility(View.VISIBLE);
                    Status=true;
                }
                else
                {
                    xyz.setVisibility(View.GONE);
                    Status=false;
                }
            }
        });
        Status1=false;
        abc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Status1==false) {
                    xyz1.setVisibility(View.VISIBLE);
                    videoWeb.setVisibility(View.VISIBLE);
                    videoWeb.setWebViewClient(new MyBrouser());
                    videoWeb.getSettings().setLoadsImagesAutomatically(true);
                    videoWeb.getSettings().setJavaScriptEnabled(true);
                    videoWeb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                    videoWeb.loadUrl("https://www.youtube.com/watch?v=LV0429VXJoM") ;
                    Status1=true;
                }
                else
                {
                    xyz1.setVisibility(View.GONE);
                    videoWeb.setVisibility(View.GONE);
                    Status1=false;
                }
            }
        });
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "odeab18@gmail.com"));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
                    intent.putExtra(Intent.EXTRA_TEXT, "your_text");
                    startActivity(intent);
                }catch(ActivityNotFoundException e){
                    //TODO smth
                }
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String facebookScheme = "fb://facewebmodal/f?href=https://www.facebook.com/ABODE-INC-2107087026210070/";
                    Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookScheme));
                    startActivity(facebookIntent);
                }catch(ActivityNotFoundException e){
                    //TODO smth
                }
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String twitterScheme = "https://twitter.com/" +
                            "IncAbode";
                    Intent twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterScheme));
                    startActivity(twitterIntent);
                }catch(ActivityNotFoundException e){
                    //TODO smth
                }
            }
        });
        Status2=false;
        abc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Status2==false) {
                    xyz11.setVisibility(View.VISIBLE);
                    Status2=true;
                }
                else
                {
                    xyz11.setVisibility(View.GONE);
                    Status2=false;
                }
            }
        });
        want.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NavActivity.this,VerifiersActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       /* int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.My_Profile)
        {
            String user1=user.getUid();
            Intent intent=new Intent(NavActivity.this,UserProfile.class);
            intent.putExtra("UserInfo",user1);
            startActivity(intent);
        }
        if (id == R.id.Search_Tenants)
        {
            startActivity(new Intent(NavActivity.this,TentantsActivity.class));
        }
        else if(id == R.id.Search_Landlords)
        {
            startActivity(new Intent(NavActivity.this,AreaActivity.class));
        }
        else if(id == R.id.Enter_Requirements)
        {
            startActivity(new Intent(NavActivity.this,Requirements.class));
        }
        else if (id == R.id.nav_gallery) {
            startActivity(new Intent(NavActivity.this,DisplayActivity.class));
        }
        else if (id == R.id.Favorites) {
            startActivity(new Intent(NavActivity.this,FavouritesActivity.class));
        }
        else if (id == R.id.MyChat) {
            startActivity(new Intent(NavActivity.this,RecentActivity.class));
          /*  Intent emailIntent=new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[] {"ankita@gmail.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
            startActivity(emailIntent);*/
        }
        else if(id==R.id.Remove_User)
        {
            startActivity(new Intent(NavActivity.this,RemoveActivity.class));
            finish();
        }else if(id==R.id.Logout)
        {
            startActivity(new Intent(NavActivity.this,SignOutActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class MyBrouser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view,String url)
        {
            view.loadUrl(url);
            return true;
        }
    }

}
