package ru.panov.testapp.products;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import co.dift.ui.SwipeToAction;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import ru.panov.testapp.DividerItemDecoration;
import ru.panov.testapp.R;
import ru.panov.testapp.db.DBManager;
import ru.panov.testapp.db.Product;
import ru.panov.testapp.model.ProductModel;
import ru.panov.testapp.ui.floactionbar.FloatingActionButton;

/**
 * Created by vitaly.panov on 19.11.15.
 */

@EFragment(R.layout.fragment_recyclerview)
public class RecyclerViewFragment extends Fragment {

    @ViewById(R.id.no_items_textview)
    public TextView noItemsTextView;

    @ViewById(R.id.fab)
    public FloatingActionButton fab;

    @ViewById(R.id.recycler_view)
    public RecyclerView recyclerView;

    private ProgressDialog      dialog;
    private List<ProductModel>  items;
    private ProductItemAdapter  adapter;
    private SwipeToAction       swipeToAction;

    public interface OnItemSelectedListener {
        public void listItemSelected(ProductModel item);
    }

    private OnItemSelectedListener listener;

    @ViewById(R.id.main_swipe)
    public WaveSwipeRefreshLayout waveSwipeRefreshLayout;

   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }*/

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        //noItemsTextView = (TextView)root.findViewById(R.id.no_items_textview);

        dialog = ProgressDialog.show(getContext(), "", getString(R.string.whait));
        dialog.setCancelable(true);

        waveSwipeRefreshLayout = (WaveSwipeRefreshLayout) root.findViewById(R.id.main_swipe);
        waveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                // Do work to refresh the list here.
                new GetProductItemsTask().execute();
            }
        });

        if( getContext() instanceof OnItemSelectedListener){
            listener = (OnItemSelectedListener) getContext();
        } else {
            throw new ClassCastException(getContext().toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        swipeToAction = new SwipeToAction(recyclerView, new SwipeToAction.SwipeListener<ProductItem>() {
            @Override
            public boolean swipeLeft(final ProductItem itemData) {
                final int pos = removeItem(itemData);
                return true;
            }

            @Override
            public boolean swipeRight(ProductItem itemData) {
                return true;
            }

            @Override
            public void onClick(ProductItem itemData) {
                if (listener != null) {
                    listener.listItemSelected( itemData );
                }
            }

            @Override
            public void onLongClick(ProductItem itemData) {

            }
        });

        items = new ArrayList<ProductItem>();
        String[] arr = getResources().getStringArray(R.array.countries);
        for (String str : arr) {
            ProductItem item = new ProductItem();
            item.setName(str);
            items.add(item);
        }
        adapter = new ProductItemAdapter(getActivity(), items);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getContext(), AddProductItemActivity_.class);
                startActivity(intent);
            }
        });
        fab.attachToRecyclerView(recyclerView);

        return root;
    }*/

    @AfterViews
    void viewsInitialized(){

        dialog = ProgressDialog.show( getActivity(), "", getString(R.string.whait));
        dialog.setCancelable(true);

        //waveSwipeRefreshLayout = (WaveSwipeRefreshLayout) root.findViewById(R.id.main_swipe);
        waveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                // Do work to refresh the list here.
                new GetProductItemsTask().execute();
            }
        });

        if( getActivity() instanceof OnItemSelectedListener){
            listener = (OnItemSelectedListener) getActivity();
        } else {
            throw new ClassCastException( getActivity().toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }

        //RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        swipeToAction = new SwipeToAction(recyclerView, new SwipeToAction.SwipeListener<Product>() {
            @Override
            public boolean swipeLeft(final Product itemData) {
                final int pos = removeItem(itemData);
                return true;
            }

            @Override
            public boolean swipeRight(Product itemData) {
                return true;
            }

            @Override
            public void onClick(Product itemData) {
                if (listener != null) {
                    listener.listItemSelected( new ProductModel(itemData) );
                }
            }

            @Override
            public void onLongClick(Product itemData) {

            }
        });

        items = new ArrayList<ProductModel>();
        String[] arr = getResources().getStringArray(R.array.countries);
        for (String str : arr) {
            ProductModel item = new ProductModel();
            item.setTittle(str);
            items.add(item);
        }
        adapter = new ProductItemAdapter( getActivity(), items);
        recyclerView.setAdapter(adapter);

        //FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getActivity(), AddProductItemActivity_.class);
                startActivity(intent);
            }
        });
        fab.attachToRecyclerView(recyclerView);

        new GetProductItemsTask().execute();
    }

    private int removeItem(Product item) {
        int pos = items.indexOf( item );
        items.remove(item);
        adapter.notifyItemRemoved(pos);
        new DeleteTask().execute(item);
        return pos;
    }

    public void refreshList(List<ProductModel> result) {
        if(result.size() > 0){
            noItemsTextView.setVisibility(View.GONE);
        } else {
            noItemsTextView.setVisibility(View.VISIBLE);
        }
        items.clear();
        items.addAll(result);
        adapter.notifyDataSetChanged();
    }

    //@Override
    //public void onAttach(Context context) {
    //    super.onAttach(context);
        //new GetProductItemsTask().execute();
        /*if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }*/
    //}

    public class DeleteTask extends AsyncTask<Product, Void, Void> {
        //DbOpenHelper dbOpenHelper;
        DBManager dbManager;

        @Override
        protected Void doInBackground(Product... deletedProductItems) {
            for(int i = 0; i < deletedProductItems.length; i++){
                //dbOpenHelper.deleteProductItem(deletedProductItems[i]);
                dbManager.removeProduct( deletedProductItems[i] );
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //dbOpenHelper = new DbOpenHelper( getActivity() );
            dbManager = DBManager.getInstance( getActivity() );
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new GetProductItemsTask().execute();
        }
    }

    public class GetProductItemsTask extends AsyncTask<Void, Void, List<ProductModel>> {
        //DbOpenHelper dbOpenHelper;
        DBManager dbManager;

        @Override
        protected List<ProductModel> doInBackground(Void... voids) {
            //return dbOpenHelper.getProductItems();
            return dbManager.getProducts();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //dbOpenHelper = new DbOpenHelper(getActivity() );
            dbManager = DBManager.getInstance( getActivity() );

        }

        @Override
        protected void onPostExecute(List<ProductModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            waveSwipeRefreshLayout.setRefreshing(false);
            refreshList(result);
        }
    }
}
