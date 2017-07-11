package com.hdingmin.skviewsdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by hdingmin on 2017/7/11.
 */

public class DataFactory {
    public  static  List<String> getSkLinearLayoutData(String templateData)
    {
        List<String> list = new ArrayList<>();
        for(int i = 0 ;i<10;i++){
            String source = templateData.substring(0,new Random().nextInt(templateData.length()));
            list.add(source);
        }
        return  list;
    }
}
