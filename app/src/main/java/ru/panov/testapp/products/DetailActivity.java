package ru.panov.testapp.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.androidannotations.annotations.EActivity;

import ru.panov.testapp.BaseActivity;
import ru.panov.testapp.R;
import ru.panov.testapp.db.Product;
import ru.panov.testapp.model.ProductModel;

/**
 * Created by vitaly.panov on 19.11.15.
 */

@EActivity(R.layout.activity_detail)
public class DetailActivity extends BaseActivity {

    //@Fragment(R.id.detailFragment)
    //protected DetailFragment fragment;

    private ProductModel selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check if dual pane mode is active
        // if yes, finish this activity
        if (getResources().getBoolean(R.bool.dual_pane)) {
            finish();
            return;
        }
        //setContentView(R.layout.activity_detail);

        //get selected item
        Bundle b = getIntent().getExtras();
        if( b != null ){
            selectedItem = b.getParcelable(Product.class.getCanonicalName());
            if( selectedItem != null ){

                //fragment set item
                //DetailFragment fragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detailFragment);
                //DetailFragment fragment = (DetailFragment)getSupportFragmentManager().findFragmentById( R.id.detailFragment );
                DetailFragment fragment = new DetailFragment_();
                fragment.setItem( selectedItem );

            }
        }

        editButton.setVisibility(View.VISIBLE);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( getApplicationContext(), AddProductItemActivity_.class);
                intent.putExtra(AddProductItemActivity_.ACTION_PARAM_NAME, AddProductItemActivity_.ACTION_UPDATE);
                //intent.putExtra(Product.class.getCanonicalName(), selectedItem);
                intent.putExtra(ProductModel.class.getCanonicalName(), selectedItem);
                startActivity(intent);

            }
        });
    }
}
