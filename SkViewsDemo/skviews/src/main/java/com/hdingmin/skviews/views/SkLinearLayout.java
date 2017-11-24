package com.hdingmin.skviews.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.hdingmin.skviews.R;
import com.hdingmin.skviews.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hdingmin on 2017/7/11.
 */

public class SkLinearLayout extends LinearLayout {
    private int itemTemplate;
    private boolean autoNextLine;//是否自动换行
    private List _itemSource;
    private IOnViewBindListener onViewBindListener;
    private IOnItemClickListener onItemClickListener;
    private LayoutInflater inflater;
    private int mLeft,mRight,mBottom,mTop;
    private int dividerSize;
    private TemplateSelector templateSelector;
    private Map<View,Position> map = new HashMap<View,Position>();
    public SkLinearLayout(Context context) {
        super(context);init();
    }
    public SkLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttr(context,attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //
        if(autoNextLine)
        {
            int count = getChildCount();
            for (int i = 0; i < count; i++)
            {
                View child = getChildAt(i);
                Position pos = map.get(child);
                if (pos != null)
                {
                    child.layout(pos.left, pos.top, pos.right, pos.bottom);
                }
                else
                {
                    Log.i("", "");
                }
            }
        }
        else
        {
            super.onLayout(changed, l, t, r, b);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode =  MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if(autoNextLine)
        {
            int mWidth = width -   getPaddingLeft() - getPaddingRight();
            int mCount = getChildCount();
            int mX =0;
            int mY = 0;
            int wrapHeight = 0;
            mLeft = 0;
            mRight = 0;
            mTop =getPaddingTop() ;
            mBottom = 0;

            int j = 0;
            for(int i = 0;i<mCount;i++)
            {
                View child = getChildAt(i);
                int cellWidthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                int cellHeightSpec = MeasureSpec.makeMeasureSpec(20, MeasureSpec.UNSPECIFIED);
                child.measure(cellWidthSpec, cellHeightSpec);
                int childW = child.getMeasuredWidth();
                int childH = child.getMeasuredHeight();
                mX+=childW;
                if(i==0) { //第一次赋初始值
                    wrapHeight = childH;
                }
                Position position = new Position();
                mLeft =getPositionLeft(i-j,i);
                mRight = mLeft +child.getMeasuredWidth();
                if(mX>mWidth)
                {
                    mX = childW;
                    mY+=childH+dividerSize;
                    wrapHeight+=childH+dividerSize;
                    j=i;
                    mLeft = getPaddingLeft();
                    mRight = mLeft+child.getMeasuredWidth();
                    mTop = mY;
                }
                mX+=dividerSize;
                mBottom = mTop+child.getMeasuredHeight();
                mY = mTop;
                position.left = mLeft;
                position.right = mRight;
                position.top = mTop;
                position.bottom = mBottom;
                if(!map.containsKey(child))
                {
                    map.put(child,position);
                }
            }
            if(heightMode == MeasureSpec.AT_MOST) {//如果是Wrap_Content 需要计算高度
                height = wrapHeight + getPaddingTop() + getPaddingBottom();
            }
            setMeasuredDimension(width,height);
        }else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
    private int getPositionLeft(int indexInRow,int childIndex)
    {
        if (indexInRow > 0)
        {
            return getPositionLeft(indexInRow - 1, childIndex - 1) + getChildAt(childIndex - 1).getMeasuredWidth() + dividerSize;
        }
        return getPaddingLeft();
    }
    private void init()
    {
        inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    private  void initAttr(Context context, AttributeSet attrs)
    {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SkLinearLayout);
        itemTemplate = a.getResourceId(R.styleable.SkLinearLayout_itemTemplate,-1);
        dividerSize = (int)a.getDimension(R.styleable.SkLinearLayout_dividerSize,0);
        autoNextLine = a.getBoolean(R.styleable.SkLinearLayout_autoNextLine,false);
        String itemTemplateSelector = a.getString(R.styleable.SkLinearLayout_itemTemplateSelector);
        a.recycle();
        if (StringUtils.isNullOrEmpty(itemTemplateSelector)) {
            templateSelector = new SimpleItemTemplateSelector(itemTemplate);
        } else {
            try {
                templateSelector = (TemplateSelector) Class.forName(itemTemplateSelector).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("templateSelector 实例化不成功，请检查itemTemplateSelector类型名称是否正确 Path:"+itemTemplateSelector);
            }
        }
    }
    public void setItemSource(List itemsource)
    {
        try {
            _itemSource = itemsource;
            notifyDataChanged();
        }catch (Exception ex)
        {
            Log.e("SkLinearLayout",ex.getMessage());
        }
    }
    public void setOnViewBindListener(IOnViewBindListener listener)
    {
        onViewBindListener = listener;
    }
    public  void  setOnItemClickListener(IOnItemClickListener listener){
        onItemClickListener=listener;
    }
    //private  List;
    private  void notifyDataChanged()throws NullPointerException
    {
        removeAllViews();
        if(_itemSource == null||_itemSource.size() ==  0) return;
        for (int i = 0;i<_itemSource.size();i++)
        {
            final Object item = _itemSource.get(i);
            int itemType =templateSelector.getItemType(item);
            int layoutId = templateSelector.getLayoutId(itemType);
            View view = inflater.inflate(layoutId,this,false);

            if (onViewBindListener!=null){
                onViewBindListener.onViewBind(view,item);
            }
            if (onItemClickListener!=null){
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(view,item);
                    }
                });
            }
            addView(view);
        }
    }
    private class Position
    {
        public int left,top,right,bottom;
    }
    public interface IOnViewBindListener
    {
        void onViewBind(View view,Object object);
    }

    public interface IOnItemClickListener{
        void onItemClick(View view,Object object);
    }
}