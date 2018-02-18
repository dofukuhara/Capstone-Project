package br.com.dofukuhara.nutritionalassistant.util;

import java.util.Comparator;

import br.com.dofukuhara.nutritionalassistant.model.Category;

/**
 * Created by dofukuhara on 18/02/2018.
 */

public class CategoriesNameComparator implements Comparator<Category> {
    @Override
    public int compare(Category left, Category right) {
        return left.getCategoryName().compareTo(right.getCategoryName());
    }
}
