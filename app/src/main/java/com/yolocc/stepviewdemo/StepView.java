package com.yolocc.stepviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 */

public class StepView extends View {

    private int width;

    private int paddingLeft;

    @ColorInt
    private static final int COMPLETE_COLOR = 0xFFff5572;
    @ColorInt
    private static final int UNDONE_COLOR = 0xffe5e5e5;
    @ColorInt
    private static final int TEXT_COLOR = 0xff8f8e94;

    private static final int DEFAULT_STROKE_WIDTH = 2;

    private static final int HEIGHT = 30;

    private int startAndStopWidth = dp2px(25);

    private int radius = dp2px(4);
    //点与点之间的间隔
    private int interval;

    private int nowStep = 0;
    //sp
    private int textSize = 12;
    //dp
    private int textMarginTop = 4;

    private int startX;

    private String[] args = {};
    //用来画完成步骤路径
    private Paint mCompletePaint;
    //用来画未完成步骤路径
    private Paint mUndonePaint;
    //步骤名称画笔
    private Paint stepNamePaint;

    public StepView(Context context) {
        super(context);
        initPaint();
    }

    public StepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public StepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    /**
     * 初始化笔画
     */
    private void initPaint() {
        mCompletePaint = new Paint();
        mCompletePaint.setAntiAlias(true);
        mCompletePaint.setStrokeWidth(dp2px(DEFAULT_STROKE_WIDTH));
        mCompletePaint.setColor(COMPLETE_COLOR);

        mUndonePaint = new Paint();
        mUndonePaint.setAntiAlias(true);
        mUndonePaint.setStrokeWidth(dp2px(DEFAULT_STROKE_WIDTH));
        mUndonePaint.setColor(UNDONE_COLOR);

        stepNamePaint = new Paint();
        stepNamePaint.setAntiAlias(true);
        stepNamePaint.setColor(TEXT_COLOR);
        stepNamePaint.setTextSize(sp2px(textSize));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = startMeasure(widthMeasureSpec);
        paddingLeft = getPaddingLeft();
        setMeasuredDimension(width, dp2px(HEIGHT));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (args.length == 0)
            return;
        interval = (width - startAndStopWidth * 2 - paddingLeft * 2) / (args.length - 1);
        startX = startAndStopWidth + paddingLeft;
        canvas.drawLine(paddingLeft, radius, startX, radius, mCompletePaint);
        canvas.drawCircle(startX, radius, radius, mCompletePaint);
        float scaleWidth = stepNamePaint.measureText(args[0]);
        canvas.drawText(args[0], startX - scaleWidth / 2, radius * 2 + dp2px(DEFAULT_STROKE_WIDTH) + sp2px(textSize) + dp2px(textMarginTop), stepNamePaint);
        nowStep = nowStep <= args.length ? nowStep : args.length;
        for (int i = 1; i <= nowStep; i++) {
            canvas.drawLine(startX, radius, startX + interval, radius, mCompletePaint);
            canvas.drawCircle(startX + interval, radius, radius, mCompletePaint);
            float scaleWidthF = stepNamePaint.measureText(args[i]);
            canvas.drawText(args[i], startX + interval - scaleWidthF / 2, radius * 2 + dp2px(DEFAULT_STROKE_WIDTH) + sp2px(textSize) + dp2px(textMarginTop), stepNamePaint);
            startX = startX + interval;
        }
        for (int i = nowStep + 1; i < args.length; i++) {
            if (i == nowStep + 1) {
                canvas.drawLine(startX + radius, radius, startX + interval, radius, mUndonePaint);
            } else {
                canvas.drawLine(startX, radius, startX + interval, radius, mUndonePaint);
            }
            canvas.drawCircle(startX + interval, radius, radius, mUndonePaint);
            float scaleWidthF = stepNamePaint.measureText(args[i]);
            canvas.drawText(args[i], startX + interval - scaleWidthF / 2, radius * 2 + dp2px(DEFAULT_STROKE_WIDTH) + sp2px(textSize) + dp2px(textMarginTop), stepNamePaint);
            startX = startX + interval;
        }
        canvas.drawLine(startX + radius, radius, width - paddingLeft, radius, mUndonePaint);
    }

    /**
     * 设置每个步骤的文本内容
     *
     * @param stepTexts
     */
    public void setStepText(String[] stepTexts, int nowStep) {
        System.out.println("+++size:" + args.length);
        System.out.println("+++nowStep:" + nowStep);
        this.nowStep = nowStep;
        this.args = stepTexts;
        invalidate();
    }

    /**
     * 设置现在步骤所在的位置
     *
     * @param nowStep
     */
    public void setNowStep(int nowStep) {
        this.nowStep = nowStep;
        invalidate();
    }

    /**
     * 根据不同的模式,设置控件的大小;
     *
     * @param whSpec
     * @return 最后控件的大小
     */
    private int startMeasure(int whSpec) {
        int result;
        int size = MeasureSpec.getSize(whSpec);
        int mode = MeasureSpec.getMode(whSpec);
        if (mode == MeasureSpec.EXACTLY || mode == MeasureSpec.AT_MOST) {
            if (size < dp2px(200)) {
                result = dp2px(200);
            } else {
                result = size;
            }
        } else {
            result = dp2px(200);
        }
        return result;
    }

    /**
     * 将 dp 转换为 px
     *
     * @param dp 需转换数
     * @return 返回转换结果
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }


}
