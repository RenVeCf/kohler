package com.mengyang.kohler.whole_category.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.mengyang.kohler.common.utils.LogUtils;

import java.util.Vector;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/23
 */

public class ComposeTextView extends View {
    // 总高度、宽度
    private int sumHeight = 0;
    private int maxWidth = 0;
    // 一些属性
    private int textColor = getResources().getColor(android.R.color.black);
    private int textSize = 11;
    private int lineSpace = 2;  //行间距
    private int typeFace = 0;
    private String text = "";
    private int maxLine = Integer.MAX_VALUE; //最大行数
    // 上下左右的距离
    private int left_Margin = 0;
    private int right_Margin = 45;
    private int top_Margin = 0;
    private int bottom_Margin = 0;

    private Paint mPaint;

    public ComposeTextView(Context context, int textWidth) {
        super(context);
        init(textWidth);
    }

    public ComposeTextView(Context context, AttributeSet attrs, int textWidth) {
        super(context, attrs, 0);
        init(textWidth);
    }

    private void init(int textWidth) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        //为属性定义单位
        textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize, dm);
        lineSpace = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineSpace, dm);
        left_Margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, left_Margin, dm);
        right_Margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, right_Margin, dm);
        top_Margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, top_Margin, dm);
        bottom_Margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, bottom_Margin, dm);

        int width = dm.widthPixels;
        maxWidth = width - left_Margin - right_Margin - textWidth;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);// 抗锯齿
        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
        switch (typeFace) {
            case 0:
                mPaint.setTypeface(Typeface.DEFAULT);
                break;
            case 1:
                mPaint.setTypeface(Typeface.SANS_SERIF);
                break;
            case 2:
                mPaint.setTypeface(Typeface.SERIF);
                break;
            case 3:
                mPaint.setTypeface(Typeface.MONOSPACE);
                break;
            default:
                mPaint.setTypeface(Typeface.DEFAULT);
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int lineWidth = 0;  //字符串所占行宽
        int lineHeight = 0; //行高
        int lineNum = 0;    //总行数
        int start = 0;      //定位：用于截取字符串
        char ch;
        int x = 0;
        int y = 30; //为了让文字可以显示出来，不至于被遮盖，特别是第一行，这个值要根据字体大小设置，可以设置一个值用来动态控制。

        Vector<String> mString = new Vector<String>();
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        lineHeight = (int) (Math.ceil(fontMetrics.descent - fontMetrics.top) + lineSpace);
        //      y += Math.ceil(fontMetrics.descent);
        for (int i = 0; i < text.length(); i++) {
            ch = text.charAt(i);
            String str = String.valueOf(ch);
            float widths[] = new float[1];
            mPaint.getTextWidths(str, widths);

            if (ch == '\n') {
                lineNum++;
                mString.addElement(text.substring(start, i));
                start = i + 1;
                lineWidth = 0;
            } else {
                lineWidth += Math.ceil(widths[0]);
                if (lineWidth > maxWidth) {
                    lineNum++;
                    mString.addElement(text.substring(start, i));
                    start = i;
                    i--;
                    lineWidth = 0;
                } else {
                    if (i == text.length() - 1) {
                        lineNum++;
                        mString.addElement(text.substring(start, text.length()));
                    }
                }
            }
            //判断行数是否大于最大行数
            if (lineNum > maxLine) {
                lineNum = lineNum < maxLine ? lineNum : maxLine;
                float dotWidths[] = new float[3];
                String dot = "...";
                mPaint.getTextWidths(dot, dotWidths);
                int dotWidth = 0;
                for (int j = 0; j < 3; j++) {
                    dotWidth += Math.ceil(dotWidths[j]);
                }

                String string = (String) mString.elementAt(lineNum - 1);
                lineWidth = 0;
                for (int j = string.length() - 1; j >= 0; j--) {
                    float stringWidths[] = new float[j + 1];
                    String stringSub = string.substring(0, j + 1);
                    mPaint.getTextWidths(stringSub, stringWidths);
                    for (int k = 0; k < stringSub.length(); k++) {
                        lineWidth += Math.ceil(stringWidths[k]);
                    }
                    if (lineWidth + dotWidth <= maxWidth) {
                        while (mString.size() > lineNum - 1) {
                            mString.remove(mString.size() - 1);
                        }
                        mString.addElement(stringSub + dot);
                        break;
                    }
                    lineWidth = 0;
                }
                break;
            }
        }
        sumHeight = lineHeight * lineNum - lineSpace;
        for (int i = 0; i < lineNum; i++) {
            //其实设置y的只就是为了迎合这个函数，要是不清楚可以自己百度一下
            canvas.drawText((String) mString.elementAt(i), x, y + lineHeight
                    * i, mPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = measuredWidth(widthMeasureSpec);
        int measuredHeight = measuredHeight(heightMeasureSpec);
        this.setMeasuredDimension(measuredWidth, measuredHeight);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                measuredWidth, measuredHeight);
        params.topMargin = top_Margin;
        params.bottomMargin = bottom_Margin;
        params.leftMargin = left_Margin;
        params.rightMargin = right_Margin;
        this.setLayoutParams(params);
    }

    private int measuredHeight(int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        initHeight();
        // Default size if no limits specified.
        int result = sumHeight;
        if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = sumHeight;
        }
        return result;
    }

    /**
     * 初始化高度
     */
    private void initHeight() {
        int lineHeight = 0;
        int lineNum = 0;
        int lineWidth = 0;

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        lineHeight = (int) (Math.ceil(fontMetrics.descent - fontMetrics.top) + lineSpace);
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            String str = String.valueOf(ch);
            float widths[] = new float[1];
            mPaint.getTextWidths(str, widths);

            if (ch == '\n') {
                lineNum++;
                lineWidth = 0;
            } else {
                lineWidth += Math.ceil(widths[0]);
                if (lineWidth > maxWidth) {
                    lineNum++;
                    i--;
                    lineWidth = 0;
                } else {
                    if (i == text.length() - 1) {
                        lineNum++;
                    }
                }
            }
            if (lineNum > maxLine) {
                lineNum = lineNum < maxLine ? lineNum : maxLine;
                break;
            }
        }
        sumHeight = lineNum * lineHeight - lineSpace;
    }

    private int measuredWidth(int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        // Default size if no limits specified.
        int result = maxWidth;
        if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = maxWidth;
        }
        return result;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getLineSpace() {
        return lineSpace;
    }

    public void setLineSpace(int lineSpace) {
        this.lineSpace = lineSpace;
    }

    public int getTypeFace() {
        return typeFace;
    }

    public void setTypeFace(int typeFace) {
        this.typeFace = typeFace;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLeft_Margin() {
        return left_Margin;
    }

    public void setLeft_Margin(int left_Margin) {
        this.left_Margin = left_Margin;
    }

    public int getRight_Margin() {
        return right_Margin;
    }

    public void setRight_Margin(int right_Margin) {
        this.right_Margin = right_Margin;
    }

    public int getTop_Margin() {
        return top_Margin;
    }

    public void setTop_Margin(int top_Margin) {
        this.top_Margin = top_Margin;
    }

    public int getBottom_Margin() {
        return bottom_Margin;
    }

    public void setBottom_Margin(int bottom_Margin) {
        this.bottom_Margin = bottom_Margin;
    }

    public int getMaxLine() {
        return maxLine;
    }

    public void setMaxLine(int maxLine) {
        this.maxLine = maxLine;
    }
}
