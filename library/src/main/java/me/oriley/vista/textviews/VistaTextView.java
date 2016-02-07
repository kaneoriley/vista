package me.oriley.vista.textviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashMap;

import lombok.experimental.Accessors;
import me.oriley.vista.R;
import me.oriley.vista.utils.StringUtils;

@SuppressWarnings("unused")
@Accessors(prefix = "m")
public class VistaTextView extends TextView {

    private static final String TAG = VistaTextView.class.getSimpleName();

    @NonNull
    private static HashMap<String, Typeface> sTypefaces = new HashMap<>();

    @Getter
    @Nullable
    private String mFontName;

    public VistaTextView(Context context) {
        this(context, null);
    }

    public VistaTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VistaTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VistaTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VistaTextView);
            if (a.hasValue(R.styleable.VistaTextView_vistaFont)) {
                String fontName = a.getString(R.styleable.VistaTextView_vistaFont);
                setFontName(fontName);
            }
            a.recycle();
        }
    }

    public final void setFontName(@NonNull String fontName) {
        if (!StringUtils.equals(fontName, mFontName)) {
            mFontName = fontName;
            Typeface typeface = null;
            try {
                if (sTypefaces.containsKey(mFontName)) {
                    typeface = sTypefaces.get(mFontName);
                }
                if (typeface == null) {
                    typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + mFontName + ".ttf");
                }
            } catch (RuntimeException e) {
                Log.e(TAG, "exception loading typeface: " + mFontName);
                e.printStackTrace();
            } finally {
                if (typeface != null) {
                    sTypefaces.put(mFontName, typeface);
                    setTypeface(typeface);
                }
            }
        }
    }
}
