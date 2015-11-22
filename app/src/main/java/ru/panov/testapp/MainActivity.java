package ru.panov.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.panov.testapp.fragments.DetailFragment;
import ru.panov.testapp.model.ProductItem;

public class MainActivity extends AppCompatActivity implements ProductItemAdapter.OnItemSelectedListener {

    private List<ProductItem> productItems = new ArrayList<ProductItem>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
    }

    @Override
    public void onRssItemSelected(String link) {
        boolean dual_pane = getResources().getBoolean(R.bool.dual_pane);
        if (dual_pane) {
            DetailFragment fragment = (DetailFragment) getFragmentManager()
                    .findFragmentById(R.id.detailFragment);
            fragment.setText(link);
        } else {
            Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_URL, link);
            startActivity(intent);
        }
    }
}
