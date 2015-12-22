package ru.panov.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ViewById;
import org.w3c.dom.Text;

import ru.panov.testapp.model.ProductModel;
import ru.panov.testapp.products.AddProductItemActivity_;
import ru.panov.testapp.products.DetailActivity_;
import ru.panov.testapp.products.DetailFragment;
import ru.panov.testapp.products.RecyclerViewFragment;
import ru.panov.testapp.products.RecyclerViewFragment_;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements RecyclerViewFragment_.OnItemSelectedListener {

    @FragmentById(R.id.detailFragment)
    public DetailFragment detailFragment;

    @FragmentById(R.id.listFragment)
    public RecyclerViewFragment listFragment;

    @ViewById(R.id.editButton)
    public ImageButton editButton;

    @ViewById(R.id.tittleText)
    public TextView tittleText;

    @ViewById(R.id.noSelected)
    public TextView noSelectedText;

    @ViewById(R.id.price_layout)
    public LinearLayout priceLayout;

    @ViewById(R.id.count_layout)
    public LinearLayout countLayout;

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
            detailFragment.setItem(item);

            updateDetailFragmentViews();
        } else {
            Intent intent = new Intent(getApplicationContext(), DetailActivity_.class);
            intent.putExtra(ProductModel.class.getCanonicalName(), item);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            if( listFragment != null ){
                listFragment.loadData();
            }
            ProductModel item = data.getParcelableExtra("result");
            detailFragment.setItem(item);
        }
    }

    void updateDetailFragmentViews(){
        boolean dual_pane = getResources().getBoolean(R.bool.dual_pane);
        if (dual_pane) {
            if( detailFragment.getItem() == null ){
                //show message
                noSelectedText.setVisibility( View.VISIBLE );

                //hide other elements
                editButton.setVisibility( View.GONE );
                tittleText.setVisibility( View.GONE );
                priceLayout.setVisibility( View.GONE );
                countLayout.setVisibility( View.GONE );
            } else {
                noSelectedText.setVisibility( View.GONE );

                editButton.setVisibility( View.VISIBLE );
                tittleText.setVisibility( View.VISIBLE );
                priceLayout.setVisibility( View.VISIBLE );
                countLayout.setVisibility( View.VISIBLE );
            }

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), AddProductItemActivity_.class);
                    intent.putExtra(AddProductItemActivity_.ACTION_PARAM_NAME, AddProductItemActivity_.ACTION_UPDATE);

                    ProductModel item = detailFragment.getItem();
                    if( item != null )
                        intent.putExtra(ProductModel.class.getCanonicalName(), detailFragment.getItem());

                    startActivityForResult(intent, 1);
                }
            });
        }
    }

    @AfterViews
    void afterViews(){
        updateDetailFragmentViews();
    }
}
