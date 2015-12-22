package ru.panov.testapp.products;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ru.panov.testapp.R;
import ru.panov.testapp.model.ProductModel;

/**
 * Created by vitaly.panov on 19.11.15.
 */

@EFragment(R.layout.fragment_detail)
public class DetailFragment extends Fragment {

    @ViewById(R.id.tittleText)
    public TextView tittleText;
    @ViewById(R.id.priceText)
    public TextView priceText;
    @ViewById(R.id.countText)
    public TextView countText;
    @ViewById(R.id.editButton)
    public ImageButton editButton;

    private ProductModel item;

    @AfterViews
    void afterViews(){
        boolean dual_pane = getResources().getBoolean(R.bool.dual_pane);
        if (dual_pane) {
            editButton.setVisibility(View.VISIBLE );
        } else {
            editButton.setVisibility(View.GONE );
        }
    }

    public ProductModel getItem() {
        return item;
    }

    public void setItem( ProductModel item){

        this.item = item;

        if( tittleText != null && priceText != null && countText != null && item != null ){

            tittleText.setText(item.getTittle());

            Float price = item.getPrice();
            if( price != null )
                priceText.setText( price.toString() );

            Integer count = item.getCount();
            if( count != null )
                countText.setText( count.toString() );

        }
    }
}
