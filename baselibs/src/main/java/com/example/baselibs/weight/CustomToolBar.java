package com.example.baselibs.weight;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.baselibs.R;
import com.example.baselibs.utils.Contents;
import com.example.baselibs.utils.DensityUtil;


public class CustomToolBar extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private ImageView mIvBack;
    private TextView mTvTitle;
    private ImageView mIvRight;
    private TextView mTvRight;
    private View mLine;
    private RelativeLayout mRlRight;
    private RelativeLayout mRlBack;
    private LinearLayout tootbar;
    private ImageView iv_titlesrc;
    private TextView mTvLefttext;
    public CustomToolBar(Context context) {
        super(context);
        this.mContext = context;
        initView(context, null, 0);
    }

    public CustomToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView(context, attrs, 0);
    }

    public CustomToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.custom_toolbar, this);

        mIvBack = findViewById(R.id.iv_back);
        mTvTitle = findViewById(R.id.tv_title);
        mTvRight = findViewById(R.id.tv_right);
        mIvRight = findViewById(R.id.iv_right);
        mLine = findViewById(R.id.line);
        mRlBack = findViewById(R.id.rl_back);
        mRlRight = findViewById(R.id.rl_right);
        tootbar = findViewById(R.id.root);
        iv_titlesrc = findViewById(R.id.iv_titlesrc);
        mTvLefttext = findViewById(R.id.tv_lefttext);
        mRlBack.setOnClickListener(this);
        mRlRight.setOnClickListener(this);
        mTvLefttext.setOnClickListener(this);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomToolBar);
            int title_text_color = typedArray.getColor(R.styleable.CustomToolBar_title_text_color, 0xff000000);
            String title_text = typedArray.getString(R.styleable.CustomToolBar_title_text);
            String left_text = typedArray.getString(R.styleable.CustomToolBar_left_text);
            int left_textcolor = typedArray.getColor(R.styleable.CustomToolBar_left_textcolor,0Xff000000);
            float title_size = typedArray.getDimension(R.styleable.CustomToolBar_title_text_size, DensityUtil.sp2px(context, 18));
            int back_src = typedArray.getResourceId(R.styleable.CustomToolBar_back_src,0);
            int right_src = typedArray.getResourceId(R.styleable.CustomToolBar_right_src, 0);
            int right_title_v = typedArray.getInteger(R.styleable.CustomToolBar_right_title_visibility, View.VISIBLE);
            String right_title = typedArray.getString(R.styleable.CustomToolBar_right_title);
            int right_title_color = typedArray.getColor(R.styleable.CustomToolBar_right_title_color, 0xff000000);
            float right_title_size = typedArray.getDimension(R.styleable.CustomToolBar_title_text_size, DensityUtil.sp2px(context, 16));
            float line_height = typedArray.getDimension(R.styleable.CustomToolBar_line_height, 1);
            int line_color = typedArray.getColor(R.styleable.CustomToolBar_line_color, 0x00000000);
            boolean isbackclick = typedArray.getBoolean(R.styleable.CustomToolBar_backclick,true);
            int title_bg = typedArray.getResourceId(R.styleable.CustomToolBar_title_backage,0);
            int title_bgisVi = typedArray.getInteger(R.styleable.CustomToolBar_title_image,View.GONE);
            if (!isbackclick) {
                mRlBack.setOnClickListener(null);
            }
            if (back_src == 0) {
                mRlBack.setVisibility(GONE);

            }else{
                mRlBack.setVisibility(VISIBLE);
            }
            if (!TextUtils.isEmpty(left_text)) {
                mTvLefttext.setVisibility(VISIBLE);
            }else {
                mTvLefttext.setVisibility(GONE);

            }
            mIvBack.setImageResource(back_src);
            mTvTitle.setText(title_text);
            mTvLefttext.setText(left_text);
            mTvLefttext.setTextColor(left_textcolor);
            mTvTitle.setTextColor(title_text_color);
            mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, title_size);
            mTvRight.setText(right_title);
            mTvRight.setTextColor(right_title_color);
            mTvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, right_title_size);
            mIvRight.setImageResource(right_src);
            mTvRight.setVisibility(right_title_v);
            mLine.setBackgroundColor(line_color);
            iv_titlesrc.setImageResource(title_bg);
            iv_titlesrc.setVisibility(title_bgisVi);
            ViewGroup.LayoutParams layoutParams = mLine.getLayoutParams();
            layoutParams.height = (int) line_height;
            mLine.setLayoutParams(layoutParams);
            typedArray.recycle();

        }

        // 判断api版本号是否大于等于21
        if (Build.VERSION.SDK_INT > 23) {
            LayoutParams params = (LayoutParams) tootbar.getLayoutParams();
            params.topMargin = DensityUtil.dip2px(mContext, Contents.TopHeight);
            tootbar.setLayoutParams(params);
        }

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rl_back) {
            if (onLeftClickListener == null) {

                ((Activity) mContext).finish();
            } else {

                onLeftClickListener.onTitleLeft();
            }

        } else if (i == R.id.rl_right) {
            if (onRightClickListener != null) {
                onRightClickListener.onRight();
            }

        } else if (i == R.id.tv_lefttext) {
            if (mOnLeftTextClickListener != null) {
                mOnLeftTextClickListener.onTitleLeftText();
            }

        }
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public void  setRightText(String text){
        mTvRight.setText(text);
    }
    public String getRightText(){

        return mTvRight.getText().toString().trim();
    }
    public interface OnRightClickListener {
        void onRight();
    }
    public void setRightSrc(int icon){

        mIvRight.setImageResource(icon);
    }
    private OnRightClickListener onRightClickListener;
    public void setOnRightClickListener(OnRightClickListener onRightClickListener) {
        this.onRightClickListener = onRightClickListener;
    }

    public interface OnLeftClickListener {
        void onTitleLeft();
    }

    private OnLeftClickListener onLeftClickListener;

    public void setOnLeftClickListener(OnLeftClickListener onLeftClickListener) {
        this.onLeftClickListener = onLeftClickListener;
    }

    public interface OnLeftTextClickListener {
        void onTitleLeftText();
    }

    private OnLeftTextClickListener mOnLeftTextClickListener;

    public void setOnLeftTextClickListener(OnLeftTextClickListener onLeftTextClickListener) {
        mOnLeftTextClickListener = onLeftTextClickListener;
    }
}

