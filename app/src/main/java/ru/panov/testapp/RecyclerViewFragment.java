package ru.panov.testapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.panov.testapp.fab.FloatingActionButton;
import ru.panov.testapp.model.ProductItem;

/**
 * Created by vitaly.panov on 19.11.15.
 */
public class RecyclerViewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        List<ProductItem> items = new ArrayList<ProductItem>();
        String[] arr = getResources().getStringArray(R.array.countries);
        for( String str : arr ){
            ProductItem item = new ProductItem();
            item.setName( str );
            items.add(item);
        }
        ProductItemAdapter adapter = new ProductItemAdapter( items );
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.attachToRecyclerView(recyclerView);

        return root;
    }
}
