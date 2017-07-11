package com.hdingmin.skviews.views;

/**
 * Created by hdingmin on 2017/7/11.
 */

public interface TemplateSelector<T> {
    int getLayoutId(int itemType);
    int getItemType(T item);
}
