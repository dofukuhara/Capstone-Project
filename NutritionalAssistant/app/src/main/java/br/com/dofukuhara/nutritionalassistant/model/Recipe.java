package br.com.dofukuhara.nutritionalassistant.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dofukuhara on 19/03/2018.
 */

public class Recipe implements Parcelable {

    private int recipeId;
    private String recipeName;
    private String recipeIngredients;

    public Recipe(int recipeId, String recipeName, String recipeIngredients) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipeIngredients = recipeIngredients;
    }

    protected Recipe(Parcel in) {
        recipeId = in.readInt();
        recipeName = in.readString();
        recipeIngredients = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(recipeId);
        parcel.writeString(recipeName);
        parcel.writeString(recipeIngredients);
    }

    public int getRecipeId() {
        return this.recipeId;
    }

    public String getRecipeName() {
        return this.recipeName;
    }

    public String getRecipeIngredients() {
        return this.recipeIngredients;
    }
}
