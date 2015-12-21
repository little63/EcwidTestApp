package ru.panov.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;

import ru.panov.testapp.model.ProductModel;
import ru.panov.testapp.products.DetailActivity_;
import ru.panov.testapp.products.DetailFragment;
import ru.panov.testapp.products.RecyclerViewFragment;
import ru.panov.testapp.products.RecyclerViewFragment_;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements RecyclerViewFragment_.OnItemSelectedListener {

    @FragmentById(R.id.detailFragment)
    public DetailFragment fragment;

    @FragmentById(R.id.listFragment)
    public RecyclerViewFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide action bar
        getSupportActionBar().hide();
    }

    @Override
    public void listItemSelected( ProductModel item ) {
        boolean dual_pane = getResources().getBoolean(R.bool.dual_pane);
        if (dual_pane) {
            //fragment.setItem(item);
        } else {
            Intent intent = new Intent(getApplicationContext(), DetailActivity_.class);
            intent.putExtra(ProductModel.class.getCanonicalName(), item);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //if (requestCode == 1) {
        //    if(resultCode == Activity.RESULT_OK){
                if( listFragment != null ){
                    listFragment.loadData();
                }
        //    }
        //}
    }

    @AfterViews
    void afterViews(){
        boolean dual_pane = getResources().getBoolean(R.bool.dual_pane);
        if (dual_pane) {

        }
    }
}
