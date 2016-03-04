package com.maksim88.passwordedittext;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

/**
 * Created by maksim on 15.01.16. 
 */
public class PasswordEditText extends AppCompatEditText {

    /**
     * This area is added as padding to increase the clickable area of the icon
     */
    private final static int EXTRA_TAPPABLE_AREA = 50;

    @DrawableRes
    private int showPwIcon = R.drawable.ic_visibility_24dp;

    @DrawableRes
    private int hidePwIcon = R.drawable.ic_visibility_off_24dp;

    private Drawable showPwDrawable;

    private Drawable hidePwDrawable;

    private boolean passwordVisible;

    private boolean isNumericInputType;

    private boolean isRTL;

    private boolean showingIcon;

    private boolean setErrorCalled;

    private boolean hoverShowsPw;

    private boolean useNonMonospaceFont;

    private boolean handlingHoverEvent;

    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFields(attrs, 0);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFields(attrs, defStyleAttr);
    }

    public void initFields(AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PasswordEditText, defStyleAttr, 0);
            try {
                showPwIcon = styledAttributes.getResourceId(R.styleable.PasswordEditText_pet_iconShow, showPwIcon);
                hidePwIcon = styledAttributes.getResourceId(R.styleable.PasswordEditText_pet_iconHide, hidePwIcon);
                hoverShowsPw = styledAttributes.getBoolean(R.styleable.PasswordEditText_pet_hoverShowsPw, false);
                useNonMonospaceFont = styledAttributes.getBoolean(R.styleable.PasswordEditText_pet_nonMonospaceFont, false);
            } finally {
                styledAttributes.recycle();
            }
        }

        hidePwDrawable = ContextCompat.getDrawable(getContext(), hidePwIcon);

        showPwDrawable = ContextCompat.getDrawable(getContext(), showPwIcon);

        if (useNonMonospaceFont) {
            setTypeface(Typeface.DEFAULT);
        }

        isRTL = isRTLLanguage();

        isNumericInputType = matchNumericalInputType();

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //NOOP
            }

            @Override
            public void onTextChanged(CharSequence seq, int start, int before, int count) {
                //NOOP
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    if (setErrorCalled) {
                        // resets drawables after setError was called as this leads to icons
                        // not changing anymore afterwards
                        setCompoundDrawables(null, null, null, null);
                        setErrorCalled = false;
                        showPasswordVisibilityIndicator(true);
                    }
                    if (!showingIcon) {
                        showPasswordVisibilityIndicator(true);
                    }
                } else {
                    // hides the indicator if no text inside text field
                    passwordVisible = false;
                    handlePasswordInputVisibility();
                    showPasswordVisibilityIndicator(false);
                }

            }
        });
    }

    private boolean isRTLLanguage() {
        //TODO investigate why ViewUtils.isLayoutRtl(this) not working as intended
        // as getLayoutDirection was introduced in API 17, under 17 we default to LTR
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1){
            return false;
        }
        Configuration config = getResources().getConfiguration();
        return config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }

    private boolean matchNumericalInputType() {
        int type = getInputType();
        boolean classNumber = (type & InputType.TYPE_CLASS_NUMBER) == InputType.TYPE_CLASS_NUMBER;
        boolean numberVariation = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            numberVariation = (type & InputType.TYPE_NUMBER_VARIATION_PASSWORD) == InputType.TYPE_NUMBER_VARIATION_PASSWORD;
        }
        return classNumber || numberVariation;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, passwordVisible);
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        passwordVisible = savedState.isShowingIcon();
        handlePasswordInputVisibility();
        showPasswordVisibilityIndicator(true);
    }

    @Override
    public void setError(CharSequence error) {
        super.setError(error);
        setErrorCalled = true;

    }

    @Override
    public void setError(CharSequence error, Drawable icon) {
        super.setError(error, icon);
        setErrorCalled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!showingIcon) {
            return super.onTouchEvent(event);
        }
        final Rect bounds = showPwDrawable.getBounds();
        final int x = (int) event.getX();
        int iconXRect = isRTL? getLeft() + bounds.width() + EXTRA_TAPPABLE_AREA :
                getRight() - bounds.width() - EXTRA_TAPPABLE_AREA;

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (hoverShowsPw) {
                    if (isRTL? x<= iconXRect : x >= iconXRect) {
                        togglePasswordIconVisibility();
                        // prevent keyboard from coming up
                        event.setAction(MotionEvent.ACTION_CANCEL);
                        handlingHoverEvent = true;
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (handlingHoverEvent || (isRTL? x<= iconXRect : x >= iconXRect)) {
                    togglePasswordIconVisibility();
                    // prevent keyboard from coming up
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    handlingHoverEvent = false;
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }


    private void showPasswordVisibilityIndicator(boolean shouldShowIcon) {
        if (shouldShowIcon) {
            Drawable drawable = passwordVisible ? hidePwDrawable : showPwDrawable;
            showingIcon = true;
            setCompoundDrawablesWithIntrinsicBounds(isRTL ? drawable : null, null, isRTL ? null : drawable, null);
        } else {
            // reset drawable
            setCompoundDrawables(null, null, null, null);
            showingIcon = false;
        }
    }

    /**
     * This method toggles the visibility of the icon and takes care of switching the input type
     * of the view to be able to see the password afterwards.
     *
     * This method may only be called if there is an icon visible
     */
    private void togglePasswordIconVisibility() {
        passwordVisible = !passwordVisible;
        handlePasswordInputVisibility();
        showPasswordVisibilityIndicator(true);
    }

    /**
     * This method is called when restoring the state (e.g. on orientation change)
     */
    private void handlePasswordInputVisibility() {
        if (passwordVisible) {
            if (isNumericInputType) {
                //TODO there is no EditorInfo.TYPE_NUMBER_VARIATION_VISIBLE_PASSWORD
                setInputType(EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
        } else {
            if (isNumericInputType && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                setInputType(EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_VARIATION_PASSWORD);

            } else {
                setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
            }

        }
        // move cursor to the end of the input as it is being reset automatically
        setSelection(getText().length());
        if (useNonMonospaceFont) {
            setTypeface(Typeface.DEFAULT);
        }

    }

    /**
     * Convenience class to save / restore the state of icon.
     */
    protected static class SavedState extends BaseSavedState {

        private final boolean mShowingIcon;

        private SavedState(Parcelable superState, boolean showingIcon) {
            super(superState);
            mShowingIcon = showingIcon;
        }

        private SavedState(Parcel in) {
            super(in);
            mShowingIcon = in.readByte() != 0;
        }

        public boolean isShowingIcon() {
            return mShowingIcon;
        }

        @Override
        public void writeToParcel(Parcel destination, int flags) {
            super.writeToParcel(destination, flags);
            destination.writeByte((byte) (mShowingIcon ? 1 : 0));
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Creator<SavedState>() {

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }

        };
    }
}
