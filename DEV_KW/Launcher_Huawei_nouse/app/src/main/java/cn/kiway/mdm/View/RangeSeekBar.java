package cn.kiway.mdm.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import cn.kiway.mdm.R;


/**
 * @author jayce
 * @date 2015/3/9
 */
public class RangeSeekBar extends ViewGroup {
    private Drawable mThumbDrawable;
    private Drawable mThumbPlaceDrawable;

    private ThumbView mThumbLeft;   //左游标
    private ThumbView mThumbRight;  //右游标
    private int mProgressBarHeight;     //进度条的高度
    private int mThumbPlaceHeight;      //游标的高度

    private int mLeftValue;     //左游标  数值    (100分之多少)   例如：1就是 1/100

    private int mMaxValue=24;   //分成100份，每一小格占2份


    private int mRightValue=mMaxValue;  //右游标  数值    (100分之多少)

    private int mLeftLimit;     //游标左边的限制坐标
    private int mRightLimit;        //游标右边的限制坐标
    private int proPaddingLeftAndRight;     //进度条左右的padding 等于游标图标宽度的一半
    private int mProBaseline;       //进度条top  坐标

    private static final int PART_ITEM=1;//半小 占的分数
    private float mPartWidth;   //每一小份的宽度

    public static final int SHORTLINE_HEIGHT=5; //短线的高度 （画刻度时会有长短线）
    public static final int LONGLINE_HEIGHT=10; //长线的高度

    public static final int RULE_HEIGHT_DP=20;  //尺子的高度  dp
    public static int RULE_HEIGHT_PX;

    private int degs[]={0,2,4,6,8,10,12,14,16,18,20,22,24};      //尺子上标记刻度值
    private String unitStr="时";     //尺子标记单位

    private OnRangeChangeListener mOnRangeChangeListener;       //当左右任意一个游标改变时，回调接口

    public interface OnRangeChangeListener{
        public void onRangeChange(int leftValue, int rightValue);
    }

    public RangeSeekBar(Context context) {
        this(context, null);
    }

    public RangeSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RangeSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setBackgroundDrawable(new BitmapDrawable());
        //换算px
        RULE_HEIGHT_PX=DensityUtil.dip2px(context,RULE_HEIGHT_DP);
        mProgressBarHeight=DensityUtil.dip2px(context,4);

        mThumbDrawable=getResources().getDrawable(R.drawable.rod_handshank_butten);
        mThumbPlaceDrawable=getResources().getDrawable(R.drawable.rod_place_icon);

        mThumbPlaceHeight=mThumbPlaceDrawable.getIntrinsicHeight();
        mProBaseline=RULE_HEIGHT_PX+mThumbPlaceHeight;

        mThumbLeft=new ThumbView(getContext());
        mThumbLeft.setRangeSeekBar(this);
        mThumbLeft.setImageDrawable(mThumbDrawable);
        mThumbRight=new ThumbView(getContext());
        mThumbRight.setRangeSeekBar(this);
        mThumbRight.setImageDrawable(mThumbDrawable);

        //measureView(mThumbLeft);

        addView(mThumbLeft);
        addView(mThumbRight);
        mThumbLeft.setOnThumbListener(new ThumbView.OnThumbListener() {
            @Override
            public void onThumbChange(int i) {
                mLeftValue=i;
                if(mOnRangeChangeListener!=null){
                    mOnRangeChangeListener.onRangeChange(mLeftValue,mRightValue);
                }
            }
        });
        mThumbRight.setOnThumbListener(new ThumbView.OnThumbListener() {
            @Override
            public void onThumbChange(int i) {
                mRightValue=i;
                if(mOnRangeChangeListener!=null){
                    mOnRangeChangeListener.onRangeChange(mLeftValue,mRightValue);
                }
            }
        });
    }

    public void setOnRangeChangeListener(OnRangeChangeListener mOnRangeChangeListener) {
        this.mOnRangeChangeListener = mOnRangeChangeListener;
    }

//    private void measureView(View view){
//        ViewGroup.LayoutParams params=view.getLayoutParams();
//
//        if(params==null){
//            params=new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        }
//
//        int widthSpec=ViewGroup.getChildMeasureSpec(0,0,params.width);
//
//        int heightSpec;
//        if(params.height>0){
//            heightSpec=MeasureSpec.makeMeasureSpec(params.height,MeasureSpec.EXACTLY);
//        }else{
//            heightSpec=MeasureSpec.makeMeasureSpec(params.height,MeasureSpec.UNSPECIFIED);
//        }
//
//        view.measure(widthSpec,heightSpec);
//    }

    /**
     * 画尺子
     *@param canvas
     */
    protected void drawProgressBar(Canvas canvas){
        //画背景
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.grey));
        Rect rect=new Rect(mLeftLimit,mProBaseline,mRightLimit,mProBaseline+mProgressBarHeight);
        canvas.drawRect(rect,paint);

        //画进度
        paint.setColor(getResources().getColor(R.color.blue));
        rect=new Rect(mThumbLeft.getCenterX(),mProBaseline,mThumbRight.getCenterX(),mProBaseline+mProgressBarHeight);
        canvas.drawRect(rect,paint);
    }

    /**
     * 画刻度尺
     * @param canvas
     */
    protected void drawRule(Canvas canvas){
        Paint paint=new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(getResources().getColor(R.color.grey));
        paint.setTextSize(20);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);

        //一次遍历两份,绘制的位置都是在奇数位置
        for(int i=0;i<=mMaxValue;i+=1){
            if(i<PART_ITEM||i>mMaxValue-PART_ITEM){
                continue;
            }

            float degX=mLeftLimit+i*mPartWidth;
            int degY;

            if((i-PART_ITEM)%(PART_ITEM*2)==0){
                degY=mProBaseline-DensityUtil.dip2px(getContext(),LONGLINE_HEIGHT);
                canvas.drawText(degs[i/2]+unitStr,degX,degY,paint);
            }else{
                degY=mProBaseline-DensityUtil.dip2px(getContext(),SHORTLINE_HEIGHT);
            }
            canvas.drawLine(degX,mProBaseline,degX,degY,paint);
        }
    }

    /**
     * 画 Thumb 位置的数值
     */
    protected void drawRodPlaceValue(Canvas canvas, ThumbView thumbView){
        int centerX=thumbView.getCenterX();
        Paint paint=new Paint();
        BitmapDrawable bd= (BitmapDrawable) mThumbPlaceDrawable;
        canvas.drawBitmap(bd.getBitmap(),centerX-mThumbPlaceDrawable.getIntrinsicWidth()/2,0,paint);

        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(30);
        canvas.drawText(geneareThumbValue(thumbView)+"",centerX,mThumbDrawable.getIntrinsicHeight()/2,paint);
    }

    //onLayout调用后执行的函数
    private void onLayoutPrepared(){
        mThumbLeft.setCenterX(mLeftLimit);
        mThumbRight.setCenterX(mRightLimit);
    }

    private int geneareThumbValue(ThumbView view){
        // 这里只是计算了100之多少的值，需要自行转换成刻度上的值
        int proValue=mMaxValue*(view.getCenterX()-mLeftLimit)/(mRightLimit-mLeftLimit);
        return proValue;
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec,heightMeasureSpec);    //测量子控件
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int mWidth= MeasureSpec.getSize(widthMeasureSpec);
        proPaddingLeftAndRight=mThumbLeft.getMeasuredWidth()/3;
        mLeftLimit=proPaddingLeftAndRight;
        mRightLimit=mWidth-proPaddingLeftAndRight;

        //位置标记的高度+尺子的刻度高度+尺子的高度+游标的高度
        setMeasuredDimension(mWidth,mThumbPlaceHeight+RULE_HEIGHT_PX+mProgressBarHeight+mThumbLeft.getMeasuredHeight());
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawProgressBar(canvas);
        drawRule(canvas);

        if(mThumbLeft.isMoving()){
            drawRodPlaceValue(canvas,mThumbLeft);
        }else if(mThumbRight.isMoving()){
            drawRodPlaceValue(canvas,mThumbRight);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int heightSum=0;

        heightSum+=mThumbPlaceHeight;

        heightSum+=RULE_HEIGHT_PX;

        heightSum+=mProgressBarHeight;

        mPartWidth=(mRightLimit-mLeftLimit)/(float)mMaxValue;   //计算一份所占的宽度  一定要用float

        mThumbLeft.setLimit(mLeftLimit,mRightLimit);    //设置可以移动的范围
        mThumbLeft.layout(0,heightSum,mThumbLeft.getMeasuredWidth(),b-10);      //设置在父布局的位置

        mThumbRight.setLimit(mLeftLimit,mRightLimit);
        mThumbRight.layout(0,heightSum,mThumbLeft.getMeasuredWidth(),b-10);

        onLayoutPrepared();     //layout调用后调用的方法，比如设置thumb limit
    }
}
