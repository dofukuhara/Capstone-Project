package br.com.dofukuhara.nutritionalassistant.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dofukuhara on 18/03/2018.
 */

public class Favorite implements Parcelable {

    private int ingredientId;
    private String ingredientName;

    public Favorite(int ingredientId, String ingredientName) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
    }

    protected Favorite(Parcel in) {
        ingredientId = in.readInt();
        ingredientName = in.readString();
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ingredientId);
        parcel.writeString(ingredientName);
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }
}
