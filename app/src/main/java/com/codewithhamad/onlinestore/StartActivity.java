package com.codewithhamad.onlinestore;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class StartActivity extends AppCompatActivity {
    private static final String TAG = "StartActivity";

    private ImageView logo,trolley,slogan,start,
            contactUs, exit;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Animation lefttoright = AnimationUtils.loadAnimation(this,R.anim.lefttoright);
        Animation bottom = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        Animation bounce = AnimationUtils.loadAnimation(this,R.anim.bounce);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initViews();

        // setting animations
        slogan.startAnimation(bottom);
        logo.startAnimation(lefttoright);
        trolley.startAnimation(bounce);

        //comments

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Navigating user to the MainActivity
                Intent intent= new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // showing an alert dialog when user clilck on Contact Us button

                AlertDialog.Builder builder= new AlertDialog.Builder(StartActivity.this)
                        .setTitle("About")
                        .setMessage("Created with love by Hamad.\nFor more, Visit our Facebook Profile.")
                        .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setPositiveButton("Visit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Navigating user to the WebsiteActivity
                                Intent intent= new Intent(StartActivity.this, WebsiteActivity.class);
                                startActivity(intent);
                            }
                        });
                builder.create().show();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });

    }
    private void initViews() {
        start= findViewById(R.id.start);
        slogan=findViewById(R.id.slogan);
        logo=findViewById(R.id.logo);
        trolley=findViewById(R.id.trolley);

        contactUs=findViewById(R.id.imageView7);
        exit=findViewById(R.id.imageView9);
    }

    @Override
    public void onBackPressed() {
        if(backPressedTime+2000>System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }
        else{
            backToast= Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime= System.currentTimeMillis();
    }
}