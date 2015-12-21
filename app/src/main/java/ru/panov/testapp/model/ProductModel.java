package ru.panov.testapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import ru.panov.testapp.db.Product;

/**
 * Created by vetalpanov on 21.12.15.
 */
public class ProductModel extends Product implements Parcelable {


    protected ProductModel(Parcel in) {
    }

    public ProductModel() {
        super();
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
    public void writeToParcel(Parcel dest, int flags) {
    }
}
