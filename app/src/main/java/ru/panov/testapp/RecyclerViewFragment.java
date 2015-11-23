package ru.panov.testapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.dift.ui.SwipeToAction;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import ru.panov.testapp.ui.floactionbar.FloatingActionButton;
import ru.panov.testapp.model.ProductItem;
import ru.panov.testapp.utils.DbOpenHelper;

import android.support.design.widget.Snackbar;

/**
 * Created by vitaly.panov on 19.11.15.
 */

public class RecyclerViewFragment extends Fragment {

    private ProgressDialog      dialog;
    private TextView            noItemsTextView;
    private List<ProductItem>   items;
    private ProductItemAdapter  adapter;
    private SwipeToAction       swipeToAction;

    public interface OnItemSelectedListener {
        public void listItemSelected(ProductItem item);
    }

    private OnItemSelectedListener listener;

    private WaveSwipeRefreshLayout waveSwipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        noItemsTextView = (TextView)root.findViewById(R.id.no_items_textview);

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
                Intent intent = new Intent( getContext(), AddProductItemActivity.class);
                startActivity(intent);
            }
        });
        fab.attachToRecyclerView(recyclerView);

        return root;
    }

    private int removeItem(ProductItem item) {
        int pos = items.indexOf( item );
        items.remove(item);
        adapter.notifyItemRemoved(pos);
        new DeleteTask().execute( item );
        return pos;
    }

    public void refreshList(List<ProductItem> result) {
        if(result.size() > 0){
            noItemsTextView.setVisibility(View.GONE);
        } else {
            noItemsTextView.setVisibility(View.VISIBLE);
        }
        items.clear();
        items.addAll(result);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        new GetProductItemsTask().execute();
        /*if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }*/
    }

    public class DeleteTask extends AsyncTask<ProductItem, Void, Void> {
        DbOpenHelper dbOpenHelper;

        @Override
        protected Void doInBackground(ProductItem... deletedProductItems) {
            for(int i = 0; i < deletedProductItems.length; i++){
                dbOpenHelper.deleteProductItem(deletedProductItems[i]);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dbOpenHelper = new DbOpenHelper( getContext() );
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new GetProductItemsTask().execute();
        }
    }

    public class GetProductItemsTask extends AsyncTask<Void, Void, List<ProductItem>> {
        DbOpenHelper dbOpenHelper;

        @Override
        protected List<ProductItem> doInBackground(Void... voids) {
            return dbOpenHelper.getProductItems();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dbOpenHelper = new DbOpenHelper( getContext() );
        }

        @Override
        protected void onPostExecute(List<ProductItem> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            waveSwipeRefreshLayout.setRefreshing(false);
            refreshList(result);
        }
    }
}
