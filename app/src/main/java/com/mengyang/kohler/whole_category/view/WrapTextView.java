package com.mengyang.kohler.whole_category.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.mengyang.kohler.App;
import com.mengyang.kohler.R;

import java.util.ArrayList;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/12
 */

public class WrapTextView extends View {

    /**
     * 需要绘制的文字
     */
    private String mText;
    private ArrayList<String> mTextList;

    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    private Paint mPaint;

    public WrapTextView(Context context) {
        super(context);
        setText("");
    }

    public WrapTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setText("");
    }

    public WrapTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setText("");
    }

    public void setText(String text) {
        mTextList = new ArrayList<String>();
        //获取自定义属性的值
        mText = "115111313515131531513514415315315315531351351448438418341413153135135";
        mPaint = new Paint();
        mPaint.setTextSize(35);
        mPaint.setColor(App.getContext().getResources().getColor(R.color.black));
        //获得绘制文本的宽和高
        mBound = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), mBound);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制文字
        for (int i = 0; i < mTextList.size(); i++) {
            mPaint.getTextBounds(mTextList.get(i), 0, mTextList.get(i).length(), mBound);
            canvas.drawText(mTextList.get(i), (getWidth() / 2 - mBound.width() / 2), (getPaddingTop() + (mBound.height() * i)), mPaint);
        }
    }

    boolean isOneLines = true;
    float lineNum;
    float spLineNum;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);   //获取宽的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec); //获取高的模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);   //获取宽的尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec); //获取高的尺寸

        float textWidth = mBound.width();   //文本的宽度
        if (mTextList.size() == 0) {
            //将文本分段
            int padding = getPaddingLeft() + getPaddingRight();
            int specWidth = widthSize - padding; //能够显示文本的最大宽度
            if (textWidth < specWidth) {
                //说明一行足矣显示
                lineNum = 1;
                mTextList.add(mText);
            } else {
                //超过一行
                isOneLines = false;
                spLineNum = textWidth / specWidth;
                if ((spLineNum + "").contains(".")) {
                    lineNum = Integer.parseInt((spLineNum + "").substring(0, (spLineNum + "").indexOf("."))) + 1;
                } else {
                    lineNum = spLineNum;
                }
                int lineLength = (int) (mText.length() / spLineNum);
                for (int i = 0; i < lineNum; i++) {
                    String lineStr;
                    if (mText.length() < lineLength) {
                        lineStr = mText.substring(0, mText.length());
                    } else {
                        lineStr = mText.substring(0, lineLength);
                    }
                    mTextList.add(lineStr);
                    if (!TextUtils.isEmpty(mText)) {
                        if (mText.length() < lineLength) {
                            mText = mText.substring(0, mText.length());
                        } else {
                            mText = mText.substring(lineLength, mText.length());
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            //如果match_parent或者具体的值，直接赋值
            width = widthSize;
        } else {
            //如果是wrap_content，我们要得到控件需要多大的尺寸
            if (isOneLines) {
                //控件的宽度就是文本的宽度加上两边的内边距。内边距就是padding值，在构造方法执行完就被赋值
                width = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            } else {
                //如果是多行，说明控件宽度应该填充父窗体
                width = widthSize;
            }
        }
        //高度跟宽度处理方式一样
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            float textHeight = mBound.height();
            if (isOneLines) {
                height = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            } else {
                //如果是多行
                height = (int) (getPaddingTop() + textHeight * lineNum + getPaddingBottom());
                ;
            }
        }
        //保存测量宽度和测量高度
        setMeasuredDimension(width, height);
    }
}