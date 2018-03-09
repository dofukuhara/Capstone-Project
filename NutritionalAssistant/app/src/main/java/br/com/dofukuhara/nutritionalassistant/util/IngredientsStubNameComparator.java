package br.com.dofukuhara.nutritionalassistant.util;

import java.util.Comparator;

import br.com.dofukuhara.nutritionalassistant.model.IngredientStub;

/**
 * Created by dofukuhara on 06/03/2018.
 */

public class IngredientsStubNameComparator implements Comparator<IngredientStub> {
    @Override
    public int compare(IngredientStub left, IngredientStub right) {
        return left.getDescription().compareTo(right.getDescription());
    }
}
