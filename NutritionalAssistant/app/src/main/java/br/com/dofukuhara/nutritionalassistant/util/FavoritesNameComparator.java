package br.com.dofukuhara.nutritionalassistant.util;

import java.util.Comparator;

import br.com.dofukuhara.nutritionalassistant.model.Favorite;

/**
 * Created by dofukuhara on 18/03/2018.
 */

public class FavoritesNameComparator implements Comparator<Favorite> {
    @Override
    public int compare(Favorite left, Favorite right) {
        return left.getIngredientName().compareTo(right.getIngredientName());
    }
}
