package com.example.asf.remoteaccess;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SliderLayout sliderLayout=(SliderLayout)findViewById(R.id.slide);

        TextSliderView textSliderView=new TextSliderView(this);
        textSliderView.description("Contacts").image(R.drawable.contacs);
        sliderLayout.addSlider(textSliderView);

        TextSliderView textSliderView1=new TextSliderView(this);
        textSliderView1.description("Missed calls").image(R.drawable.missedcalls);
        sliderLayout.addSlider(textSliderView1);

        TextSliderView textSliderView2=new TextSliderView(this);
        textSliderView2.description("Un read SMS").image(R.drawable.unreadsms);
        sliderLayout.addSlider(textSliderView2);

        TextSliderView textSliderView3=new TextSliderView(this);
        textSliderView3.description("Flight Mode").image(R.drawable.flightmode);
        sliderLayout.addSlider(textSliderView3);

        TextSliderView textSliderView4=new TextSliderView(this);
        textSliderView4.description("Wifi").image(R.drawable.wifimode);
        sliderLayout.addSlider(textSliderView4);

        TextSliderView textSliderView5=new TextSliderView(this);
        textSliderView5.description("Mobile Data").image(R.drawable.mobiledata);
        sliderLayout.addSlider(textSliderView5);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.missedcall) {
            Intent i=new Intent(MainActivity.this,MissedCalls.class);
            startActivity(i);        }
        else if (id == R.id.changepass) {
            Intent i=new Intent(MainActivity.this,ChangePassWord.class);
            startActivity(i);

        } else if (id == R.id.incomecall) {

            Intent i=new Intent(MainActivity.this,IncommingCalls.class);
            startActivity(i);
        } else if (id == R.id.outgoing) {

            Intent i=new Intent(MainActivity.this,OutgoingCallls.class);
            startActivity(i);
        } else if (id == R.id.sent) {

            Intent i=new Intent(MainActivity.this,SentSms.class);
            startActivity(i);
        } else if (id == R.id.inbox) {

            Intent i=new Intent(MainActivity.this,Inbox.class);
            startActivity(i);
        }
        else if (id == R.id.contact)
        {
            Intent i=new Intent(MainActivity.this,Contacts.class);
            startActivity(i);
        }
        else if (id == R.id.phonenumber)
        {
            Intent i=new Intent(MainActivity.this,SetPhoneNumber.class);
            startActivity(i);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
