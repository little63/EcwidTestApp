package ru.panov.testapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import ru.panov.testapp.model.ProductItem;
import ru.panov.testapp.ui.floactionbar.FloatingActionButton;
import ru.panov.testapp.utils.DbOpenHelper;

/**
 * Created by vitaly.panov on 19.11.15.
 */

public class AddProductItemActivity extends BaseActivity {

    public static final String ACTION_PARAM_NAME = "ACTION";
    public static final int ACTION_CREATE = 0;
    public static final int ACTION_UPDATE = 1;
    private int action = ACTION_CREATE;

    private Integer editedId;
    private EditText tittleEditText;
    private EditText priceEditText;
    private EditText countEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);

        tittleEditText = (EditText)findViewById(R.id.tittle);
        priceEditText  = (EditText)findViewById(R.id.price);
        countEditText  = (EditText)findViewById(R.id.count);

        Bundle b = getIntent().getExtras();
        if( b != null ){
            action = b.getInt(ACTION_PARAM_NAME);
            switch (action){
                case ACTION_UPDATE:
                    ProductItem editedProductItem = b.getParcelable(ProductItem.class.getCanonicalName());
                    editedId = editedProductItem.getId();
                    if(editedProductItem != null){
                        tittleEditText.setText(editedProductItem.getName());
                        priceEditText.setText(editedProductItem.getPrice().toString());
                        countEditText.setText(editedProductItem.getCount().toString());
                    }

                    break;
                default:
                    break;
            }
        }

        countEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        FloatingActionButton fabEdit = (FloatingActionButton) findViewById(R.id.btn_edit_product);
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EditAddProductItemTask().execute();
            }
        });

        FloatingActionButton fabCacel = (FloatingActionButton) findViewById(R.id.btn_cancel_product);
        fabCacel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public class EditAddProductItemTask extends AsyncTask<Void, Void, Void> {
        private DbOpenHelper dbOpenHelper;
        private ProductItem  productItem;

        @Override
        protected Void doInBackground(Void... voids) {
            switch (action){
                case ACTION_UPDATE:
                    productItem.setId(editedId);
                    dbOpenHelper.editProductItem(productItem);

                    break;
                default:
                    dbOpenHelper.addProductItem(productItem);

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dbOpenHelper = new DbOpenHelper(getApplicationContext());

            productItem = new ProductItem();
            productItem.setName(tittleEditText.getText().toString());
            Float price = null;
            try{
                price = new Float(priceEditText.getText().toString());
            }catch(NumberFormatException e){
                e.printStackTrace();
            }
            productItem.setPrice(price);
            Integer count = null;
            try{
                count = new Integer(countEditText.getText().toString());
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
            productItem.setCount(count);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setResult(RESULT_OK);
            finish();
        }
    }
}
