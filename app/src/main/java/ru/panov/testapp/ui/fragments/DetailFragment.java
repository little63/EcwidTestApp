package ru.panov.testapp.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import ru.panov.testapp.R;
import ru.panov.testapp.model.ProductItem;

/**
 * Created by vitaly.panov on 19.11.15.
 */
public class DetailFragment extends Fragment {
    private EditText tittleEditText;
    private EditText priceEditText;
    private EditText countEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail,
                container, false);

        tittleEditText = (EditText)view.findViewById( R.id.tittle );
        priceEditText  = (EditText)view.findViewById( R.id.price );
        countEditText  = (EditText)view.findViewById( R.id.count );

        return view;
    }

    public void setItem( ProductItem item){
        tittleEditText.setText( item.getName() );
        priceEditText.setText( item.getPrice().toString() );
        countEditText.setText( item.getCount() );
    }
}
