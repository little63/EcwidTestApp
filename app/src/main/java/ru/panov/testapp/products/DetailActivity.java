package ru.panov.testapp.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ru.panov.testapp.BaseActivity;
import ru.panov.testapp.R;
import ru.panov.testapp.model.ProductItem;

/**
 * Created by vitaly.panov on 19.11.15.
 */
public class DetailActivity extends BaseActivity {

    private ProductItem selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check if dual pane mode is active
        // if yes, finish this activity
        if (getResources().getBoolean(R.bool.dual_pane)) {
            finish();
            return;
        }
        setContentView(R.layout.activity_detail);

        //get selected item
        Bundle b = getIntent().getExtras();
        if( b != null ){
            selectedItem = b.getParcelable(ProductItem.class.getCanonicalName());
            if( selectedItem != null ){

                //fragment set item
                //DetailFragment fragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detailFragment);
                DetailFragment fragment = (DetailFragment)getSupportFragmentManager().findFragmentById( R.id.detailFragment );
                fragment.setItem( selectedItem );

            }
        }

        editButton.setVisibility(View.VISIBLE);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( getApplicationContext(), AddProductItemActivity.class);
                intent.putExtra(AddProductItemActivity.ACTION_PARAM_NAME, AddProductItemActivity.ACTION_UPDATE);
                intent.putExtra(ProductItem.class.getCanonicalName(), selectedItem);
                startActivity(intent);

            }
        });
    }
}
