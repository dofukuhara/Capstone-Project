package br.com.dofukuhara.nutritionalassistant.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dofukuhara on 06/03/2018.
 */

public class IngredientStub implements Parcelable {

    @SerializedName("_id")
    private int id;

    @SerializedName("descricacao")
    private String description;

    protected IngredientStub(Parcel in) {
        id = in.readInt();
        description = in.readString();
    }

    public static final Creator<IngredientStub> CREATOR = new Creator<IngredientStub>() {
        @Override
        public IngredientStub createFromParcel(Parcel in) {
            return new IngredientStub(in);
        }

        @Override
        public IngredientStub[] newArray(int size) {
            return new IngredientStub[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(description);
    }

    public int getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description.trim();
    }
}
