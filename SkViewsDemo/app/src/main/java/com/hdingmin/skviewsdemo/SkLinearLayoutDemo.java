package com.hdingmin.skviewsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hdingmin.skviews.views.SkLinearLayout;
import com.hdingmin.skviews.views.TemplateSelector;

public class SkLinearLayoutDemo extends AppCompatActivity {
    private SkLinearLayout singleTempLinearLayout;
    private SkLinearLayout multiTempLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear_layout_demo);

        singleTempLinearLayout =(SkLinearLayout)findViewById(R.id.single_temp_linearlayout);
        multiTempLinearLayout = (SkLinearLayout)findViewById(R.id.multi_temp_linearlayout);

//=================================================单模板 ====================================================
        //先设置数据绑定 跟点击事件
        singleTempLinearLayout.setOnViewBindListener(new SkLinearLayout.IOnViewBindListener() {
            @Override
            public void onViewBind(View view, Object object) {
                ((TextView)view).setText((String)object);
            }
        });
        singleTempLinearLayout.setOnItemClickListener(new SkLinearLayout.IOnItemClickListener() {
            @Override
            public void onItemClick(View view, Object object) {
                Toast.makeText(SkLinearLayoutDemo.this,((TextView)view).getText(),Toast.LENGTH_SHORT).show();
            }
        });
        //最后设置数据源
        singleTempLinearLayout.setItemSource(DataFactory.getSkLinearLayoutData("单个Item模板，自动换行"));


//=================================================多模板选择 ====================================================

        //先设置数据绑定 跟点击事件
        multiTempLinearLayout.setOnViewBindListener(new SkLinearLayout.IOnViewBindListener() {
            @Override
            public void onViewBind(View view, Object object) {
                String item = (String) object;
                if(item.length()>1 && item.length()<6)
                    ((Button)view).setText(item);
                else if(item.length()>=6)
                    ((TextView)view).setText(item);
            }
        });
        multiTempLinearLayout.setOnItemClickListener(new SkLinearLayout.IOnItemClickListener() {
            @Override
            public void onItemClick(View view, Object object) {
                Toast.makeText(SkLinearLayoutDemo.this,(String)object,Toast.LENGTH_SHORT).show();
            }
        });
        //最后设置数据源
        multiTempLinearLayout.setItemSource(DataFactory.getSkLinearLayoutData("多模板，自动换行"));
    }
}
