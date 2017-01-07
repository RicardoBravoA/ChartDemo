package com.rba.chartdemo.util.control.spinner;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.rba.chartdemo.R;

import java.util.List;

/**
 * Created by Ricardo Bravo on 6/01/17.
 */

public class CustomSpinner extends TextView {

    private static final int MAX_LEVEL = 10000;
    private static final int DEFAULT_ELEVATION = 16;
    private static final String INSTANCE_STATE = "instance_state";
    private static final String SELECTED_INDEX = "selected_index";
    private static final String IS_POPUP_SHOWING = "is_popup_showing";

    private int selectedIndex;
    private Drawable drawable;
    private PopupWindow popupWindow;
    private ListView listView;
    private CustomSpinnerBaseAdapter adapter;
    private AdapterView.OnItemClickListener onItemClickListener;
    private AdapterView.OnItemSelectedListener onItemSelectedListener;
    private boolean isArrowHide;
    private int textColor;
    private int backgroundSelector;

    public CustomSpinner(Context context) {
        super(context);
        init(context, null);
    }

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putInt(SELECTED_INDEX, selectedIndex);
        if (popupWindow != null) {
            bundle.putBoolean(IS_POPUP_SHOWING, popupWindow.isShowing());
            dismissDropDown();
        }
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable savedState) {
        if (savedState instanceof Bundle) {
            Bundle bundle = (Bundle) savedState;
            selectedIndex = bundle.getInt(SELECTED_INDEX);

            if (adapter != null) {
                setText(adapter.getItemInDataset(selectedIndex).toString());
                adapter.notifyItemSelected(selectedIndex);
            }

            if (bundle.getBoolean(IS_POPUP_SHOWING)) {
                if (popupWindow != null) {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            showDropDown();
                        }
                    });
                }
            }
            savedState = bundle.getParcelable(INSTANCE_STATE);
        }
        super.onRestoreInstanceState(savedState);
    }

    private void init(Context context, AttributeSet attrs) {
        Resources resources = getResources();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSpinner);

        setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        setPadding(resources.getDimensionPixelSize(R.dimen.padding_grid_unit),
                resources.getDimensionPixelSize(R.dimen.padding_spinner_5),
                resources.getDimensionPixelSize(R.dimen.padding_spinner_0),
                resources.getDimensionPixelSize(R.dimen.padding_spinner_5));
        setClickable(true);

        backgroundSelector = typedArray.getResourceId(R.styleable.CustomSpinner_backgroundSelector, R.drawable.selector_spinner);
        setBackgroundResource(backgroundSelector);
        textColor = typedArray.getColor(R.styleable.CustomSpinner_textColor, -1);
        setTextColor(textColor);


        listView = new ListView(context);
        listView.setId(getId());
        listView.setDivider(null);
        listView.setItemsCanFocus(true);
        listView.setVerticalScrollBarEnabled(true);
        listView.setHorizontalScrollBarEnabled(false);
        listView.setScrollBarStyle(SCROLLBARS_INSIDE_OVERLAY);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= selectedIndex && position < adapter.getCount()) {
                    position++;
                }

                Log.i("x- position", ""+position);

                selectedIndex = position;

                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(parent, view, position, id);
                }

                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(parent, view, position, id);
                }

                adapter.notifyItemSelected(position);
                setText(adapter.getItemInDataset(position).toString());
                dismissDropDown();
            }
        });

        popupWindow = new PopupWindow(context);
        popupWindow.setContentView(listView);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(DEFAULT_ELEVATION);
            popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.spinner_drawable));
        } else {
            popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.spinner_drawable));
        }

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                if (!isArrowHide) {
                    animateArrow(false);
                }
            }
        });

        isArrowHide = typedArray.getBoolean(R.styleable.CustomSpinner_hideArrow, false);
        if (!isArrowHide) {
            Drawable basicDrawable = ContextCompat.getDrawable(context, R.drawable.ic_arrow_spinner);
            int resId = typedArray.getColor(R.styleable.CustomSpinner_arrowTint, -1);
            if (basicDrawable != null) {
                drawable = DrawableCompat.wrap(basicDrawable);
                if (resId != -1) {
                    DrawableCompat.setTint(drawable, resId);
                }
            }
            setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        }

        typedArray.recycle();
    }

// --Commented out by Inspection START (4/08/16 3:55 PM):
//    public void setSelectItemAtPosition(int position){
//        adapter.notifyItemSelected(position);
//        setText(adapter.getItemInDataset(position).toString());
//        dismissDropDown();
//    }
// --Commented out by Inspection STOP (4/08/16 3:55 PM)

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int position) {
        if (adapter != null) {
            if (position >= 0 && position <= adapter.getCount()) {
                adapter.notifyItemSelected(position);
                selectedIndex = position;
                setText(adapter.getItemInDataset(position).toString());
            } else {
                throw new IllegalArgumentException("Position must be lower than adapter count!");
            }
        }
    }

// --Commented out by Inspection START (4/08/16 3:55 PM):
//    public void addOnItemClickListener(@NonNull AdapterView.OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }
// --Commented out by Inspection STOP (4/08/16 3:55 PM)

    public void setOnItemSelectedListener(@NonNull AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public <T> void setDataSource(@NonNull List<T> dataset) {
        adapter = new CustomSpinnerAdapter<>(getContext(), dataset, textColor, backgroundSelector);
        setAdapterInternal(adapter);
    }

// --Commented out by Inspection START (4/08/16 3:55 PM):
//    public void setAdapter(@NonNull ListAdapter adapter) {
//        this.adapter = new CustomSpinnerAdapterWrapper(getContext(), adapter, textColor, backgroundSelector);
//        setAdapterInternal(this.adapter);
//    }
// --Commented out by Inspection STOP (4/08/16 3:55 PM)

    private void setAdapterInternal(@NonNull CustomSpinnerBaseAdapter adapter) {
        selectedIndex = 0;
        listView.setAdapter(adapter);

        if(adapter.getItemInDataset(selectedIndex)!=null){
            setText(adapter.getItemInDataset(selectedIndex).toString());
        }else{
            setText("");
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        popupWindow.setWidth(MeasureSpec.getSize(widthMeasureSpec));
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (!popupWindow.isShowing()) {
                showDropDown();
            } else {
                dismissDropDown();
            }
        }
        return super.onTouchEvent(event);
    }

    private void animateArrow(boolean shouldRotateUp) {
        int start = shouldRotateUp ? 0 : MAX_LEVEL;
        int end = shouldRotateUp ? MAX_LEVEL : 0;
        ObjectAnimator animator = ObjectAnimator.ofInt(drawable, "level", start, end);
        animator.setInterpolator(new LinearOutSlowInInterpolator());
        animator.start();
    }

    private void dismissDropDown() {
        if (!isArrowHide) {
            animateArrow(false);
        }
        popupWindow.dismiss();
    }

    private void showDropDown() {
        if (!isArrowHide) {
            animateArrow(true);
        }
        popupWindow.showAsDropDown(this);
    }

}