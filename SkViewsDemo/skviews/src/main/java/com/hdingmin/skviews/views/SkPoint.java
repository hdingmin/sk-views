package com.hdingmin.skviews.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.hdingmin.skviews.R;
import com.hdingmin.skviews.utils.ScreenUtils;
import com.hdingmin.skviews.utils.StringUtils;

/**
 * Created by hdingmin on 2017/7/12.
 */

public class SkPoint  extends View {
    private int textSize;
    private int textColor;
    private int backGroundColor;
    private int paddingTopBottom;
    private int paddingLeftRight;
    private int padding;
    private int height;
    private int width;
    private int textHeight;
    private int textWidth;
    private Paint paintBackground;
    private Paint paintText;
    private Paint borderPaint;
    private int borderWidth;
    private int borderColor;
    private boolean isPoint;

    private String _text;


    public SkPoint(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitAttr(context, attrs);
        InitDefault();
        InitView();
        InitPaint();
    }

    public SkPoint(Context context) {
        super(context);
    }
    public void setText(String text)
    {
        _text = text;
        if (!canDraw()) return;
        initViewSize();
    }
    private void InitAttr(Context context, AttributeSet attrs)
    {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SkPoint);
        borderWidth = a.getDimensionPixelOffset(R.styleable.SkPoint_borderWidth,0);
        borderColor = a.getColor(R.styleable.SkPoint_borderColor, Color.WHITE);
        textColor = a.getColor(R.styleable.SkPoint_textColor, Color.WHITE);
        backGroundColor = a.getColor(R.styleable.SkPoint_backgroundColor, Color.RED);
        isPoint = a.getBoolean(R.styleable.SkPoint_isPoint, false);
        _text = a.getString(R.styleable.SkPoint_text);
        textSize = a.getDimensionPixelOffset(R.styleable.SkPoint_textSize, ScreenUtils.sp2px(getContext(),11));
        a.recycle();
    }

    private void InitView()
    {
        //setVisibility(INVISIBLE);
    }
    private void InitDefault()
    {
        paddingTopBottom = ScreenUtils.sp2px(getContext(),2);
        paddingLeftRight = ScreenUtils.sp2px(getContext(),4);
        padding = ScreenUtils.sp2px(getContext(),4);
        setMinimumHeight(ScreenUtils.sp2px(getContext(),10));
        setMinimumWidth(ScreenUtils.sp2px(getContext(),10));
    }

    private void InitPaint()
    {
        paintBackground = new Paint();
        paintBackground.setStyle(Paint.Style.FILL);
        paintBackground.setColor(backGroundColor);
        paintBackground.setAntiAlias(true);

        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.FILL);
        borderPaint.setColor(borderColor);
        borderPaint.setAntiAlias(true);

        paintText = new Paint();
        paintText.setStyle(Paint.Style.FILL);
        paintText.setColor(Color.WHITE);
        paintText.setAntiAlias(true);
        paintText.setTextSize(textSize);
        paintText.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int modelWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modelHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        if(modelWidth == MeasureSpec.EXACTLY)
        {
            width =  sizeWidth;
        }
        if(modelHeight == MeasureSpec.EXACTLY)
        {
            height= sizeHeight;
        }
        initViewSize();
    }

    @Override
    public void draw(Canvas canvas) {
        //
        if (!isPoint && !canDraw()) return;

        if (isPoint)
        {
            canvas.drawCircle(width / 2, height / 2, Math.min(width, height) / 2, borderPaint);
            int radius = (Math.min(width, height) - 2 * borderWidth) / 2;
            canvas.drawCircle(width / 2, height / 2, radius, paintBackground);
        }
        else
        {
            if (_text.length() <= 1)
            {
                canvas.drawCircle(width / 2, height / 2, Math.min(width, height) / 2, borderPaint);
                int radius = Math.min(width, height) / 2 - borderWidth;
                canvas.drawCircle(width / 2, height / 2, radius, paintBackground);
            }
            else
            {
                Rect rect = new Rect(0, 0, width, height);
                RectF rectF = new RectF(rect);
                canvas.drawRoundRect(rectF, height / 2, height / 2, borderPaint);
                Rect rect1 = new Rect(borderWidth, borderWidth, width - borderWidth, height - borderWidth);
                RectF rectF1 = new RectF(rect1);
                float radius = height / 2f;
                canvas.drawRoundRect(rectF1, radius, radius, paintBackground);
            }
            Paint.FontMetricsInt fontMetrics = paintText.getFontMetricsInt();
            //计算文字高度
            float fontHeight = fontMetrics.bottom - fontMetrics.top;
            //计算文字baseline
            float textBaseY = height - (height - fontHeight) / 2 - fontMetrics.bottom;
            canvas.drawText(_text, width / 2, textBaseY - 2, paintText);
        }
        super.draw(canvas);
    }
    private boolean canDraw()
    {
//        int resutl = Integer.parseInt(_text);
//        if (resutl > 0)
//        {
//            setVisibility(VISIBLE);
//            return true;
//        }
//        else
//        {
//            setVisibility(INVISIBLE);
//            return false;
//        }
        return true;
    }
    private  void initViewSize()
    {
        if (!StringUtils.isNullOrEmpty(_text)&&width==0&&height==0)
        {
            Rect rect = new Rect();
            if (_text.length() > 1)
                paintText.getTextBounds(_text, 0, _text.length(), rect);
            else
                paintText.getTextBounds("6", 0, _text.length(), rect);//修复数字为1时计算的长宽比 其他数字长宽小的问题
            if ( _text.length() > 1)
            {
                textHeight = rect.height() + paddingTopBottom * 2;
                textWidth = rect.width() + paddingLeftRight * 2;
            }
            else
            {
                textHeight =Math.max(rect.height(),rect.width())  + padding * 2;
                textWidth =  textHeight;
            }
            height = textHeight + 2 * borderWidth;
            width = textWidth + 2 * borderWidth;
        }
        if (isPoint)
        {
            borderWidth = ScreenUtils.dp2px(getContext(),2);
            width = ScreenUtils.dp2px(getContext(),13);
            height = ScreenUtils.dp2px(getContext(),13);
        }
        setMeasuredDimension(width, height);
    }
}
