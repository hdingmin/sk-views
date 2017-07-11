package com.hdingmin.skviewsdemo;

import com.hdingmin.skviews.views.TemplateSelector;

/**
 * Created by hdingmin on 2017/7/11.
 */

public class TestItemTempleSelector implements TemplateSelector<String> {
    @Override
    public int getLayoutId(int itemType) {
        if(itemType == 0)
            return R.layout.sklinearlayout_item3;
        if(itemType == 1)
            return R.layout.sklinearlayout_item2;
        if(itemType == 2)
            return R.layout.sklinearlayout_item1;
        return R.layout.sklinearlayout_item1;
    }

    @Override
    public int getItemType(String item) {
        if(item.length() ==1)
            return 0;
        else if(item.length()>1 && item.length()<6)
            return 1;
        else if(item.length()>=6)
            return 2;
        return  -1;
    }
}
