package ru.panov.testapp;

import android.content.Intent;
import android.os.Bundle;
import org.androidannotations.annotations.EActivity;

import ru.panov.testapp.model.ProductItem;

import ru.panov.testapp.products.DetailActivity_;
import ru.panov.testapp.products.RecyclerViewFragment_;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements RecyclerViewFragment_.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            Intent intent = new Intent(getApplicationContext(), DetailActivity_.class);
            intent.putExtra(ProductItem.class.getCanonicalName(), item);
            startActivityForResult(intent, 1);
        }
    }
}
