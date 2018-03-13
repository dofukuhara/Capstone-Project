package br.com.dofukuhara.nutritionalassistant.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dofukuhara on 15/02/2018.
 */

public class Ingredient implements Parcelable {

    @SerializedName("_id")
    private int id;

    @SerializedName("descricacao")
    private String description;

    @SerializedName("classificacao")
    private String classification;

    @SerializedName("umidade")
    private String umity;

    @SerializedName("energia")
    private Energy energy;

    @SerializedName("proteina")
    private String protein;

    @SerializedName("lipideos")
    private String lipids;

    @SerializedName("colesterol")
    private String cholesterol;

    @SerializedName("carboidrato")
    private String carbohydrate;

    @SerializedName("fibra_alimentar")
    private String foodFiber;

    @SerializedName("cinzas")
    private String ashes;

    @SerializedName("calcio")
    private String calcium;

    @SerializedName("magnesio")
    private String magnesium;

    @SerializedName("manganes")
    private String manganese;

    @SerializedName("fosforo")
    private String phosphor;

    @SerializedName("ferro")
    private String iron;

    @SerializedName("sodio")
    private String sodium;

    @SerializedName("potassio")
    private String potassium;

    @SerializedName("cobre")
    private String copper;

    @SerializedName("zinco")
    private String zinc;

    @SerializedName("retinol")
    private String retinol;

    @SerializedName("re")
    private String re;

    @SerializedName("rae")
    private String rae;

    @SerializedName("tiamina")
    private String thiamine;

    @SerializedName("riboflavina")
    private String riboflavin;

    @SerializedName("piridoxina")
    private String pyridoxine;

    @SerializedName("niacina")
    private String niacin;

    @SerializedName("vitamina_c")
    private String vitaminC;

    protected Ingredient(Parcel in) {
        id = in.readInt();
        description = in.readString();
        classification = in.readString();
        umity = in.readString();

        String kcal = in.readString();
        String kj = in.readString();
        energy = new Energy(kcal, kj);

        protein = in.readString();
        lipids = in.readString();
        cholesterol = in.readString();
        carbohydrate = in.readString();
        foodFiber = in.readString();
        ashes = in.readString();
        calcium = in.readString();
        magnesium = in.readString();
        manganese = in.readString();
        phosphor = in.readString();
        iron = in.readString();
        sodium = in.readString();
        potassium = in.readString();
        copper = in.readString();
        zinc = in.readString();
        retinol = in.readString();
        re = in.readString();
        rae = in.readString();
        thiamine = in.readString();
        riboflavin = in.readString();
        pyridoxine = in.readString();
        niacin = in.readString();
        vitaminC = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description.trim();
    }

    public String getClassification() {
        return classification;
    }

    public String getUmity() {
        return handleEmptyString(umity);
    }

    public Energy getEnergy() {
        return energy;
    }

    public String getEnergyKcal() {
        return handleEmptyString(energy.kcal);
    }

    public String getEnergyKj() {
        return handleEmptyString(energy.kj);
    }

    public String getProtein() {
        return handleEmptyString(protein);
    }

    public String getLipids() {
        return handleEmptyString(lipids);
    }

    public String getCholesterol() {
        return handleEmptyString(cholesterol);
    }

    public String getCarbohydrate() {
        return handleEmptyString(carbohydrate);
    }

    public String getFoodFiber() {
        return handleEmptyString(foodFiber);
    }

    public String getAshes() {
        return handleEmptyString(ashes);
    }

    public String getCalcium() {
        return handleEmptyString(calcium);
    }

    public String getMagnesium() {
        return handleEmptyString(magnesium);
    }

    public String getManganese() {
        return handleEmptyString(manganese);
    }

    public String getPhosphor() {
        return handleEmptyString(phosphor);
    }

    public String getIron() {
        return handleEmptyString(iron);
    }

    public String getSodium() {
        return handleEmptyString(sodium);
    }

    public String getPotassium() {
        return handleEmptyString(potassium);
    }

    public String getCopper() {
        return handleEmptyString(copper);
    }

    public String getZinc() {
        return handleEmptyString(zinc);
    }

    public String getRetinol() {
        return handleEmptyString(retinol);
    }

    public String getRe() {
        return handleEmptyString(re);
    }

    public String getRae() {
        return handleEmptyString(rae);
    }

    public String getThiamine() {
        return handleEmptyString(thiamine);
    }

    public String getRiboflavin() {
        return handleEmptyString(riboflavin);
    }

    public String getPyridoxine() {
        return handleEmptyString(pyridoxine);
    }

    public String getNiacin() {
        return handleEmptyString(niacin);
    }

    public String getVitaminC() {
        return handleEmptyString(vitaminC);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(description);
        parcel.writeString(classification);
        parcel.writeString(umity);
        parcel.writeString(energy.kcal);
        parcel.writeString(energy.kj);
        parcel.writeString(protein);
        parcel.writeString(lipids);
        parcel.writeString(cholesterol);
        parcel.writeString(carbohydrate);
        parcel.writeString(foodFiber);
        parcel.writeString(ashes);
        parcel.writeString(calcium);
        parcel.writeString(magnesium);
        parcel.writeString(manganese);
        parcel.writeString(phosphor);
        parcel.writeString(iron);
        parcel.writeString(sodium);
        parcel.writeString(potassium);
        parcel.writeString(copper);
        parcel.writeString(zinc);
        parcel.writeString(retinol);
        parcel.writeString(re);
        parcel.writeString(rae);
        parcel.writeString(thiamine);
        parcel.writeString(riboflavin);
        parcel.writeString(pyridoxine);
        parcel.writeString(niacin);
        parcel.writeString(vitaminC);
    }

    class Energy {

        @SerializedName("kcal")
        String kcal;

        @SerializedName("kj")
        String kj;

        protected Energy(String kcal, String kj) {
            this.kcal = kcal;
            this.kj = kj;
        }
    }

    private String handleEmptyString(String value) {
        // If the value is EMPTY, just return a dash
        if (TextUtils.isEmpty(value) ) {
            return "-";
        } else {
            // Else, lets check if is in a float format
            Pattern pattern = Pattern.compile("^\\d*.\\d*$");
            Matcher matcher = pattern.matcher(value);

            if (!matcher.matches()) {
                // If it is not a floating styled String, just return it...
                return value;
            } else {
                // ... otherwise, we will print it rounded with 2-digits decimal
                return String.format("%.2f", Float.valueOf(value));
            }
        }
    }

}
