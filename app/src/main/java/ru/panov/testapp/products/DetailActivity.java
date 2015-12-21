package ru.panov.testapp.products;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentById;

import ru.panov.testapp.BaseActivity;
import ru.panov.testapp.R;
import ru.panov.testapp.db.Product;
import ru.panov.testapp.model.ProductModel;

/**
 * Created by vitaly.panov on 19.11.15.
 */

@EActivity(R.layout.activity_detail)
public class DetailActivity extends BaseActivity {

    @FragmentById(R.id.detailFragment)
    public DetailFragment fragment;

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
    }

    @AfterViews
    void afterViews(){
        //get selected item
        Bundle b = getIntent().getExtras();
        if( b != null ){
            selectedItem = b.getParcelable(ProductModel.class.getCanonicalName());
            if( selectedItem != null ){
                //fragment set item
                fragment.setItem( selectedItem );
            }
        }

        editButton.setVisibility(View.VISIBLE);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( getApplicationContext(), AddProductItemActivity_.class);
                intent.putExtra(AddProductItemActivity_.ACTION_PARAM_NAME, AddProductItemActivity_.ACTION_UPDATE);
                intent.putExtra(ProductModel.class.getCanonicalName(), selectedItem);
                //startActivity(intent);
                startActivityForResult(intent, 1);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                selectedItem = data.getParcelableExtra("result");
                fragment.setItem( selectedItem );
            }
        }
    }
}
