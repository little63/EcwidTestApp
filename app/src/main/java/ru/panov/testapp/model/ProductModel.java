package ru.panov.testapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import ru.panov.testapp.db.Product;

/**
 * Created by vitaly.panov on 21.12.15.
 */

public class ProductModel extends Product implements Parcelable {


    protected ProductModel(Parcel in) {
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        setId(in.readLong());
        setTittle(in.readString());
        setPrice(in.readFloat());
        setCount( in.readInt() );
    }

    public ProductModel() {
        super();
    }

    public ProductModel( Product product ) throws NullPointerException {
        super(product.getId(), product.getTittle(), product.getPrice(), product.getCount());
    }

    public ProductModel(String tittle, Float price, Integer count) {
        super(null, tittle, price, count);
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong( getId() );
        parcel.writeString( getTittle() );
        parcel.writeFloat( getPrice());
        parcel.writeInt( getCount() );
    }
}
