package ru.panov.testapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import ru.panov.testapp.model.ProductItem;
import ru.panov.testapp.products.DetailActivity;
import ru.panov.testapp.products.RecyclerViewFragment;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements RecyclerViewFragment.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

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

        //hide action bar
        getSupportActionBar().hide();
    }

    @Override
    public void listItemSelected( ProductItem item ) {
        boolean dual_pane = getResources().getBoolean(R.bool.dual_pane);
        if (dual_pane) {
            //DetailFragment fragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detailFragment);
            //fragment.setItem(item);
        } else {
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra(ProductItem.class.getCanonicalName(), item);
            startActivityForResult(intent, 1);
        }
    }
}
