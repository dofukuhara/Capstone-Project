package br.com.dofukuhara.nutritionalassistant.util;

import java.util.Comparator;

import br.com.dofukuhara.nutritionalassistant.model.Ingredient;

/**
 * Created by dofukuhara on 18/02/2018.
 */

public class IngredientsNameComparator implements Comparator<Ingredient> {
    @Override
    public int compare(Ingredient left, Ingredient right) {
        return left.getDescription().compareTo(right.getDescription());
    }
}
