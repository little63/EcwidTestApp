package ru.panov.testapp.products;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.dift.ui.SwipeToAction;
import ru.panov.testapp.R;
import ru.panov.testapp.db.Product;
import ru.panov.testapp.model.ProductModel;

/**
 * Created by vitaly.panov on 18.11.15.
 */

public class ProductItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /** References to the views for each data item **/
    public class ProductItemViewHolder extends SwipeToAction.ViewHolder<Product> {
        public TextView titleView;
        public TextView priceView;
        public TextView countView;

        public ProductItemViewHolder(View v) {
            super(v);

            titleView = (TextView) v.findViewById(R.id.title);
            priceView = (TextView) v.findViewById(R.id.price);
            countView = (TextView) v.findViewById(R.id.count);
        }
    }

    private Context context;
    private List<ProductModel> dataset;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;

        public ViewHolder(View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
        }
    }

    public void add(int position, ProductModel item) {
        dataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Product item) {
        int position = dataset.indexOf(item);
        dataset.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProductItemAdapter( Context context, List<ProductModel> dataset) {
        this.context = context;
        this.dataset = dataset;

        /*if( context instanceof OnItemSelectedListener){
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }*/
    }

    // Create new views (invoked by the layout manager)

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);

        return new ProductItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Product item = dataset.get(position);
        ProductItemViewHolder vh = (ProductItemViewHolder) holder;
        vh.titleView.setText( item.getTittle() );
        //vh.priceView.setText( item.getPrice().toString() );
        //vh.countView.setText( item.getCount() );
        vh.data = item;
    }
    // Replace the contents of a view (invoked by the layout manager)
    /*@Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ProductItem item = dataset.get(position);
        final String name = item.getName();
        holder.txtHeader.setText( name );
        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //remove( item );
            }
        });

        holder.txtFooter.setText("Footer: " + dataset.get(position));

    }*/

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
