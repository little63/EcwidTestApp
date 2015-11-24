package ru.panov.testapp.products;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ru.panov.testapp.R;
import ru.panov.testapp.model.ProductItem;

/**
 * Created by vitaly.panov on 19.11.15.
 */
public class DetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail,
                container, false);

        return view;
    }

    public void setItem( ProductItem item){
        EditText tittleEditText = (EditText)getView().findViewById(R.id.tittle);
        EditText priceEditText  = (EditText)getView().findViewById(R.id.price);
        EditText countEditText  = (EditText)getView().findViewById(R.id.count);

        if( tittleEditText != null && priceEditText != null && countEditText != null ){


            tittleEditText.setText(item.getName());

            Float price = item.getPrice();
            if( price != null )
                priceEditText.setText( price.toString() );

            countEditText.setText( item.getCount() );

        }
    }
}
