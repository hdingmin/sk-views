package com.hdingmin.skviews.views;

/**
 * Created by hdingmin on 2017/7/11.
 */

public class SimpleItemTemplateSelector implements TemplateSelector {
    private  int templateId = 0 ;
    public  SimpleItemTemplateSelector(int templateId)
    {
        this.templateId = templateId;
    }
    @Override
    public int getLayoutId(int itemType) {
        return templateId;
    }

    @Override
    public int getItemType(Object item) {
        return 0;
    }
}
