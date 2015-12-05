package ru.panov.testapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vitaly.panov on 18.11.15.
 */

public class ProductItem implements Parcelable {
    private Integer id;
    private String  name;
    private Float   price;
    private Integer count;

    public static final Parcelable.Creator<ProductItem> CREATOR = new Parcelable.Creator<ProductItem>() {
        public ProductItem createFromParcel(Parcel in) {
            return new ProductItem(in);
        }

        public ProductItem[] newArray(int size) {
            return new ProductItem[size];
        }
    };

    public ProductItem() {
    }

    public ProductItem(String name, Float price, Integer count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public ProductItem(Parcel in) {
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readFloat();
        count = in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeFloat(price);
        parcel.writeInt(count);
    }

    public ProductItem(int id, String name, float price, int count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}