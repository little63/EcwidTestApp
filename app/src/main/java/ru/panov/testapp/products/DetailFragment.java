package ru.panov.testapp.products;

import android.support.v4.app.Fragment;
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

    //private ProductModel item;

    public void setItem( ProductModel item){
        //this.item = item;

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
