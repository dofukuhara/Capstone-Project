package br.com.dofukuhara.nutritionalassistant.util;

import java.util.Comparator;

import br.com.dofukuhara.nutritionalassistant.model.Recipe;

/**
 * Created by dofukuhara on 19/03/2018.
 */

public class RecipesNameComparator implements Comparator<Recipe> {
    @Override
    public int compare(Recipe left, Recipe right) {
        return left.getRecipeName().compareTo(right.getRecipeName());
    }
}
