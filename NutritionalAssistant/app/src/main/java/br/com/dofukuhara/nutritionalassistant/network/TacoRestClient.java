package br.com.dofukuhara.nutritionalassistant.network;

import java.util.ArrayList;

import br.com.dofukuhara.nutritionalassistant.model.Category;
import br.com.dofukuhara.nutritionalassistant.model.Ingredient;
import br.com.dofukuhara.nutritionalassistant.model.IngredientStub;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by dofukuhara on 15/02/2018.
 */

public interface TacoRestClient {

    @GET("/categorias")
    Call<ArrayList<Category>> getListOfCategories();

    @GET("categorias/{id}")
    Call<ArrayList<Category>> getCategoryById(@Path("id") int id);

    @GET("/alimentos")
    Call<ArrayList<IngredientStub>> getListOfIngredients();

    @GET("/alimentos/{id}")
    Call<ArrayList<Ingredient>> getIngredientById(@Path("id") int id);

}
