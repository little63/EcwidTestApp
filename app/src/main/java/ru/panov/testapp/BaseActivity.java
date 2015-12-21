package ru.panov.testapp;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

/**
 * Created by vetalpanov on 23.11.15.
 */

@EActivity
public class BaseActivity extends AppCompatActivity {

    //@ViewById(R.id.editButton)
    public ImageButton editButton;
    //@ViewById(R.id.backButton)
    public ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);

        actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.header)));

        //inflate tools menu
        //TODO inflate from android annotations
        View view = LayoutInflater.from(actionBar.getThemedContext()).inflate(R.layout.tools, null);
        ImageButton backBtn = (ImageButton)view.findViewById( R.id.backButton );
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        actionBar.setCustomView(view);

        editButton = (ImageButton)view.findViewById( R.id.editButton );
    }
}
