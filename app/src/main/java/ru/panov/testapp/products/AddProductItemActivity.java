package ru.panov.testapp.products;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import ru.panov.testapp.BaseActivity;
import ru.panov.testapp.R;
import ru.panov.testapp.db.DBManager;
import ru.panov.testapp.db.Product;
import ru.panov.testapp.model.ProductModel;
import ru.panov.testapp.ui.floactionbar.FloatingActionButton;

/**
 * Created by vitaly.panov on 19.11.15.
 */

@EActivity(R.layout.activity_add)
public class AddProductItemActivity extends BaseActivity {

    public static final String ACTION_PARAM_NAME = "ACTION";
    public static final int ACTION_CREATE = 0;
    public static final int ACTION_UPDATE = 1;
    private int action = ACTION_CREATE;

    private Long editedId;

    @ViewById
    public EditText tittleEditText;
    @ViewById
    public EditText priceEditText;
    @ViewById
    public EditText countEditText;

    @ViewById(R.id.btn_edit_product)
    FloatingActionButton fabEdit;

    @ViewById(R.id.btn_cancel_product)
    FloatingActionButton fabCacel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editButton.setVisibility( View.GONE );

        Bundle b = getIntent().getExtras();
        if( b != null ){
            action = b.getInt(ACTION_PARAM_NAME);
            switch (action){
                case ACTION_UPDATE:
                    Product editedProductItem = b.getParcelable(Product.class.getCanonicalName());
                    editedId = editedProductItem.getId();
                    if(editedProductItem != null){
                        tittleEditText.setText(editedProductItem.getTittle());
                        priceEditText.setText(editedProductItem.getPrice().toString());
                        countEditText.setText(editedProductItem.getCount().toString());
                    }

                    break;
                default:
                    break;
            }
        }
    }

    @AfterViews
    void viewsInitialized() {
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

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = tittleEditText.getText().toString();
                Float price = new Float(0.0);
                try {
                    price = new Float(priceEditText.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Integer count = 0;
                try {
                    count = new Integer(countEditText.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ProductModel item = new ProductModel(name, price, count);

                new EditAddProductItemTask(item).execute();
            }
        });

        fabCacel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public class EditAddProductItemTask extends AsyncTask<Void, Void, Void> {
        //private DbOpenHelper dbOpenHelper;
        private DBManager dbManager;
        private Product  productItem;

        public EditAddProductItemTask(Product productItem) {
            this.productItem = productItem;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            switch (action){
                case ACTION_UPDATE:
                    productItem.setId(editedId);
                    //dbOpenHelper.editProductItem(productItem);
                    dbManager.editProduct(productItem);

                    break;
                default:
                    //dbOpenHelper.addProductItem(productItem);
                    dbManager.addProduct(productItem);

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //dbOpenHelper = new DbOpenHelper(getApplicationContext());
            dbManager = DBManager.getInstance(getApplicationContext());
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setResult(RESULT_OK);
            finish();
        }
    }
}
