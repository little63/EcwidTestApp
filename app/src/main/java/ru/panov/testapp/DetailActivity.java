package ru.panov.testapp;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import ru.panov.testapp.model.ProductItem;
import ru.panov.testapp.ui.fragments.DetailFragment;

/**
 * Created by vitaly.panov on 19.11.15.
 */
public class DetailActivity extends BaseActivity {

    private EditText tittleEditText;
    private EditText priceEditText;
    private EditText countEditText;

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

        tittleEditText = (EditText)findViewById( R.id.tittle );
        priceEditText  = (EditText)findViewById( R.id.price );
        countEditText  = (EditText)findViewById( R.id.count );
    }

    public void setItem( ProductItem item){
        tittleEditText.setText( item.getName() );
        priceEditText.setText( item.getPrice().toString() );
        countEditText.setText( item.getCount() );
    }
}
