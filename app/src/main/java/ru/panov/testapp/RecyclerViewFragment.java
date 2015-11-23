package ru.panov.testapp;

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

import android.support.design.widget.Snackbar;

/**
 * Created by vitaly.panov on 19.11.15.
 */

public class RecyclerViewFragment extends Fragment {

    private List<ProductItem>   items;
    private ProductItemAdapter  adapter;
    private SwipeToAction       swipeToAction;

    public interface OnItemSelectedListener {
        public void onRssItemSelected(String link);
    }

    private OnItemSelectedListener listener;

    private WaveSwipeRefreshLayout waveSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        waveSwipeRefreshLayout = (WaveSwipeRefreshLayout) root.findViewById(R.id.main_swipe);
        waveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                // Do work to refresh the list here.
                new ReloadItemsTask().execute();
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
                //TODO remove
                return true;
            }

            @Override
            public boolean swipeRight(ProductItem itemData) {
                return true;
            }

            @Override
            public void onClick(ProductItem itemData) {
                if (listener != null) {
                    listener.onRssItemSelected("asdasd");
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
        return pos;
    }

    private class ReloadItemsTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(Void... params) {
            return new String[0];
        }

        @Override protected void onPostExecute(String[] result) {
            // Call setRefreshing(false) when the list has been refreshed.
            waveSwipeRefreshLayout.setRefreshing(false);
            super.onPostExecute(result);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }*/
}
