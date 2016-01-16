package com.maksim88.passwordedittext;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

/**
 * Created by maksim on 15.01.16.
 */
public class PasswordEditText extends EditText {

    /**
     * This area is added as padding to increase the clickable area of the icon
     */
    private static int EXTRA_TAPPABLE_AREA = 50;
    private static String STATE_SHOW_ICON = "iconShown";
    private static String PARCELABLE_NAME = "superState";

    @DrawableRes
    private static int SHOW_PW_ICON = R.drawable.ic_visibility_off_24dp;

    @DrawableRes
    private static int HIDE_PW_ICON = R.drawable.ic_visibility_24dp;

    private boolean showingPasswordIcon;

    private Drawable drawableRight;

    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFields(attrs);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFields(attrs);
    }

    @TargetApi(21)
    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initFields(attrs);
    }

    public void initFields(AttributeSet attrs) {
        // attrs not used yet, but may come handy when extending functionality

        setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        setTypeface(Typeface.DEFAULT);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //NOOP
            }

            @Override
            public void onTextChanged(CharSequence seq, int start, int before, int count) {
                if (seq.length() > 0) {
                    showPasswordVisibilityIndicator(true);
                } else {
                    // hides the indicator if no text inside text field
                    showingPasswordIcon = false;
                    restorePasswordIconVisibility();
                    showPasswordVisibilityIndicator(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //NOOP
            }
        });
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(PARCELABLE_NAME, super.onSaveInstanceState());
        bundle.putBoolean(STATE_SHOW_ICON, showingPasswordIcon);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            showingPasswordIcon = bundle.getBoolean(STATE_SHOW_ICON);
            restorePasswordIconVisibility();
            showPasswordVisibilityIndicator(true);
            state = bundle.getParcelable(PARCELABLE_NAME);
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (drawableRight == null) {
            return super.onTouchEvent(event);
        }
        final Rect bounds = drawableRight.getBounds();
        final int x = (int) event.getX();
        int rightCoord = getRight() - bounds.width() - EXTRA_TAPPABLE_AREA;
        if (x >= rightCoord) {
            togglePasswordIconVisibility();
            event.setAction(MotionEvent.ACTION_CANCEL);
            return false;
        }
        return super.onTouchEvent(event);
    }


    private void showPasswordVisibilityIndicator(boolean shouldShowIcon) {
        if (shouldShowIcon) {
            Drawable drawable = showingPasswordIcon ?
                    ContextCompat.getDrawable(getContext(), SHOW_PW_ICON):
                    ContextCompat.getDrawable(getContext(), HIDE_PW_ICON);

            setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            drawableRight = drawable;
        } else {
            // reset drawable
            setCompoundDrawables(null, null, null, null);
            drawableRight = null;
        }
    }

    /**
     * This method toggles the visibility of the icon and takes care of switching the input type
     * of the view to be able to see the password afterwards.
     *
     * This method may only be called if there is an icon visible
     */
    private void togglePasswordIconVisibility() {
        if (showingPasswordIcon) {
            setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        showingPasswordIcon = !showingPasswordIcon;
        showPasswordVisibilityIndicator(true);
    }

    /**
     * This method is called when restoring the state (e.g. on orientation change)
     */
    private void restorePasswordIconVisibility() {
        if (showingPasswordIcon) {
            setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }
}
