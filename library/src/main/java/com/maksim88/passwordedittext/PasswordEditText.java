package com.maksim88.passwordedittext;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
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
    @SuppressWarnings("FieldCanBeLocal")
    private static int EXTRA_TAPPABLE_AREA = 50;

    @DrawableRes
    private int showPwIcon = R.drawable.ic_visibility_24dp;

    @DrawableRes
    private int hidePwIcon = R.drawable.ic_visibility_off_24dp;

    private boolean showingPasswordIcon;

    private Drawable drawableRight;

    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFields(attrs, 0, 0);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFields(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initFields(attrs, defStyleAttr, defStyleRes);
    }

    public void initFields(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (attrs != null) {
            TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PasswordEditText, defStyleAttr, defStyleRes);
            try {
                showPwIcon = styledAttributes.getResourceId(R.styleable.PasswordEditText_iconShow, showPwIcon);
                hidePwIcon = styledAttributes.getResourceId(R.styleable.PasswordEditText_iconHide, hidePwIcon);
            } finally {
                styledAttributes.recycle();
            }
        }
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
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, showingPasswordIcon);
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        showingPasswordIcon = savedState.isShowingIcon();
        restorePasswordIconVisibility();
        showPasswordVisibilityIndicator(true);
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
                    ContextCompat.getDrawable(getContext(), hidePwIcon):
                    ContextCompat.getDrawable(getContext(), showPwIcon);

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
        showingPasswordIcon = !showingPasswordIcon;
        restorePasswordIconVisibility();
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
        // move cursor to the end of the input as it is being reset automatically
        setSelection(getText().length());

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
