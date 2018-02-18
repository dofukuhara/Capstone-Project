package br.com.dofukuhara.nutritionalassistant.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dofukuhara on 15/02/2018.
 */

public class Category implements Parcelable{

    @SerializedName("_id")
    private int id;

    @SerializedName("categoria")
    private String category;

    private Category(Parcel in) {
        id = in.readInt();
        category = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(category);
    }

    public int getId() {
        return this.id;
    }

    public String getCategoryName() {
        return this.category.trim();
    }
}
