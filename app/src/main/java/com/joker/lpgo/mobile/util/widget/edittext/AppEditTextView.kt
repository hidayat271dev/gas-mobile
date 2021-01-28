package com.joker.lpgo.mobile.util.widget.edittext

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.*
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.transition.Fade
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.google.android.material.textfield.TextInputEditText
import com.joker.lpgo.mobile.R
import com.joker.lpgo.mobile.util.AppDimensionsUtils
import com.joker.lpgo.mobile.util.extension.show
import kotlinx.android.synthetic.main.widget_edittext.view.*
import java.lang.reflect.Field
import java.util.*

interface AppEditTextViewListener {
    fun onTextCloseClicked()
    fun onTextChangeClicked(string: String?)
    fun onTextIsEmpty()
}

class AppEditTextView : FrameLayout {

    private var mContext: Context? = null
    private var listener: AppEditTextViewListener? = null
    private var defaultLabelSize = 0f
    private var inputType = 0
    private var DEFAULT_BORDER_COLOR: Int = 0
    private var indicatorBackgroundResId = 0
    private var indicatorBackgroundTextResId = 0
    private var indicatorBackgroundActiveResId = 0
    private var drawableId = 0
    private var cursorDrawable = 0
    private var hintText: String? = null
    private var textColor = 0
    private var toggleTextColor = 0
    private var textHintColor = 0
    private var bgTintColor: Int = 0
    private var imageColor = 0
    private var maxLines = 0
    private var maxLength: Int = 0
    private var lines: Int = 0
    private var gravity: Int = 0
    private var mTextSize = 0f
    private var isEditable = true
    private var imeOptions = 0
    private var padding = 0
    private var paddingStartView = 0
    private var paddingEndView: Int = 0
    private var paddingTopView: Int = 0
    private var paddingBottomView: Int = 0
    private var isClicked = true
    private var isShowClear = false
    private var isShowCloseOnlyTextInput = true

    constructor(context: Context) : super(context) {
        setup(context, null, 0)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        setup(context, attrs, 0)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        setup(context, attrs, defStyleAttr)
    }

    @SuppressLint("CustomViewStyleable")
    private fun setup(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        mContext = context
        LayoutInflater.from(context).inflate(R.layout.widget_edittext, this, true)

        tv_error.show(false)

        iv_toggle_password.show(false)
        iv_toggle_password.setOnClickListener {
            togglePassword()
        }

        img_left.show(false)

        val styleable = context.obtainStyledAttributes(
            attrs,
            R.styleable.AppEditTextView,
            defStyleAttr,
            0
        )

        defaultLabelSize = resources.getDimension(R.dimen._15ssp)

        mTextSize = styleable.getDimensionPixelSize(
            R.styleable.AppEditTextView_edt_text_size,
            AppDimensionsUtils.getDimensionPixelSize(context, R.dimen._13ssp)
        ).toFloat()

        indicatorBackgroundResId = styleable.getResourceId(
            R.styleable.AppEditTextView_edt_background,
            0
        )

        indicatorBackgroundTextResId = styleable.getResourceId(
            R.styleable.AppEditTextView_edt_background_text,
            0
        )

        indicatorBackgroundActiveResId = styleable.getResourceId(
            R.styleable.AppEditTextView_edt_background_focus,
            0
        )

        hintText = styleable.getString(R.styleable.AppEditTextView_edt_hint)

        textColor = styleable.getColor(R.styleable.AppEditTextView_edt_text_color, 0)
        toggleTextColor = styleable.getColor(R.styleable.AppEditTextView_edt_toggle_text_color, 0)
        textHintColor = styleable.getColor(R.styleable.AppEditTextView_edt_text_hint_color, 0)
        bgTintColor = styleable.getColor(R.styleable.AppEditTextView_edt_background_tint, 0)

        isEditable = styleable.getBoolean(R.styleable.AppEditTextView_edt_editable, true)
        maxLength = styleable.getInteger(R.styleable.AppEditTextView_android_maxLength, 10000)
        maxLines = styleable.getInteger(R.styleable.AppEditTextView_android_maxLines, 100)
        imeOptions = styleable.getInt(R.styleable.AppEditTextView_android_imeOptions, 0)
        inputType = styleable.getInt(
            R.styleable.AppEditTextView_android_inputType,
            EditorInfo.TYPE_TEXT_VARIATION_NORMAL
        )
        lines = styleable.getInt(R.styleable.AppEditTextView_android_lines, 1)
        gravity = styleable.getInt(R.styleable.AppEditTextView_android_gravity, Gravity.START)

        val togglePassword = styleable.getBoolean(
            R.styleable.AppEditTextView_edt_password_toggle,
            false
        )
        padding = styleable.getDimensionPixelOffset(R.styleable.AppEditTextView_edt_padding, 0)

        paddingStartView = styleable.getDimensionPixelOffset(
            R.styleable.AppEditTextView_edt_padding_start,
            0
        )
        paddingEndView = styleable.getDimensionPixelOffset(
            R.styleable.AppEditTextView_edt_padding_end,
            0
        )
        paddingTopView = styleable.getDimensionPixelOffset(
            R.styleable.AppEditTextView_edt_padding_top,
            0
        )
        paddingBottomView = styleable.getDimensionPixelOffset(
            R.styleable.AppEditTextView_edt_padding_bottom,
            0
        )

        cursorDrawable = styleable.getResourceId(R.styleable.AppEditTextView_edt_cursor, 0)

        imageColor = styleable.getColor(
            R.styleable.AppEditTextView_edt_image_tint,
            DEFAULT_BORDER_COLOR
        )

        if (togglePassword) {
            showPasswordToggle()
        }

        drawableId = styleable.getResourceId(
            R.styleable.AppEditTextView_edt_drawable_start,
            0
        )

        val displayStartDrawable = styleable.getBoolean(
            R.styleable.AppEditTextView_edt_show_drawable,
            false
        )

        isShowClear = styleable.getBoolean(
            R.styleable.AppEditTextView_edt_show_clear,
            true
        )

        if (displayStartDrawable) {
            showDrawable()
        }

        styleable.recycle()

        initView()

    }

    @Suppress("DEPRECATION")
    private fun initView() {
        if (indicatorBackgroundResId != 0) {
            container_et.setBackgroundResource(indicatorBackgroundResId)
        }
        if (indicatorBackgroundTextResId != 0) {
            edt_phone.setBackgroundResource(indicatorBackgroundTextResId)
        } else {
            edt_phone.setBackgroundResource(R.color.white)
        }

        edt_phone.hint = hintText
        edt_phone.setTextColor(textColor)
        if (textHintColor != 0) {
            edt_phone.setHintTextColor(textHintColor)
        }
        if (bgTintColor != 0) {
            edt_phone.background.setColorFilter(bgTintColor, PorterDuff.Mode.SRC_ATOP)
            container_et.background.setColorFilter(bgTintColor, PorterDuff.Mode.SRC_ATOP)
        }
        edt_phone.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize)
        edt_phone.setLines(lines)
        edt_phone.gravity = gravity
        if (padding != 0) {
            setPadding(padding)
        } else {
            setPadding(paddingStartView, paddingTopView, paddingEndView, paddingBottomView)
        }
        if (imageColor != 0) {
            img_left.setColorFilter(imageColor)
        }
        if (cursorDrawable != 0) {
            try {
                val f: Field = TextView::class.java.getDeclaredField("mCursorDrawableRes")
                f.isAccessible = true
                f.set(edt_phone, cursorDrawable)
            } catch (ignored: Exception) {
            }
        }
        iv_toggle_password.setColorFilter(imageColor)
        setInputType(inputType)
        setImeOptions(imeOptions)
        setEditable(isEditable)
        setMaxLines(maxLines)
        setMaxLength(maxLength)
        setTypeface()

        iv_close.setOnClickListener {
            if (edt_phone.text.isNullOrEmpty()) {
                listener?.onTextCloseClicked()
            } else {
                edt_phone.setText("")
                listener?.onTextIsEmpty()
            }
        }

        if (isShowClear) {
            edt_phone.addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(
                        string: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        string: CharSequence?,
                        start: Int,
                        after: Int,
                        count: Int
                    ) {
                        if (!string?.toString().isNullOrEmpty() || !string?.toString()
                                .isNullOrBlank()
                        ) {
                            if (isShowCloseOnlyTextInput) {
                                iv_close.visibility = View.VISIBLE
                            }
                            listener?.onTextChangeClicked(edt_phone.text?.toString())
                        } else {
                            if (isShowCloseOnlyTextInput) {
                                iv_close.visibility = View.GONE
                            }
                            listener?.onTextIsEmpty()
                        }
                    }

                    override fun afterTextChanged(string: Editable?) {

                    }

                }
            )
        }

        if (indicatorBackgroundActiveResId != 0) {
            edt_phone.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    img_left.setColorFilter(toggleTextColor);
                    container_et.setBackgroundResource(indicatorBackgroundActiveResId);
                } else {
                    img_left.setColorFilter(imageColor);
                    container_et.setBackgroundResource(indicatorBackgroundResId)
                }
            }
        }
    }

    private val cache: Hashtable<String, Typeface> = Hashtable()

    operator fun get(c: Context, assetPath: String): Typeface? {
        synchronized(cache) {
            if (!cache.containsKey(assetPath)) {
                try {
                    val t = Typeface.createFromAsset(
                        c.assets,
                        assetPath
                    )
                    cache[assetPath] = t
                    Log.e("AppEditTextViewView", "Loaded '$assetPath")
                } catch (e: java.lang.Exception) {
                    Log.e(
                        "AppEditTextViewView", "Could not get typeface '" + assetPath
                                + "' because " + e.message
                    )
                    return null
                }
            }
            return cache.get(assetPath)
        }
    }

    private fun setTypeface() {
        val font = get(context, "fonts/montserrat_regular.otf")
        edt_phone.typeface = font
        tv_error.typeface = font
    }

    fun setTypeface(font: Typeface?) {
        edt_phone.typeface = font
        tv_error.typeface = font
    }

    fun setMaxLength(maxLength: Int) {
        edt_phone.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
    }

    override fun setBackground(drawable: Drawable?) {
        edt_phone.background = drawable
    }

    fun setTextColor(color: Int) {
        edt_phone.setTextColor(color)
    }

    fun setSelection() {
        if (edt_phone.text.toString().isNotEmpty()) edt_phone.text?.length?.let {
            Selection.setSelection(
                edt_phone.text,
                it
            )
        }
    }

    fun setCloseOperation(isShowCloseOnlyTextInput: Boolean) {
        this.isShowCloseOnlyTextInput = isShowCloseOnlyTextInput
        if (!isShowCloseOnlyTextInput) {
            iv_close.visibility = View.VISIBLE
        }
    }

    fun setTextSize(textSize: Float) {
        edt_phone.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
    }

    fun setFilters(filters: Array<InputFilter?>?) {
        edt_phone.filters = filters
    }

    fun setInputType(inputType: Int) {
        setInputType(inputType, false)
    }

    fun setInputType(inputType: Int, isPassword: Boolean) {
        edt_phone.inputType = inputType
        if (isPassword) edt_phone.transformationMethod = PasswordTransformationMethod.getInstance()
    }

    fun showDrawable() {
        img_left.visibility = VISIBLE
        img_left.setImageResource(drawableId)
    }

    fun showPasswordToggle() {
        iv_toggle_password.visibility = VISIBLE
    }

    fun setEditable(isEditable: Boolean) {
        edt_phone.isFocusable = isEditable
        edt_phone.isFocusableInTouchMode = isEditable
    }

    fun togglePassword() {
        if (isClicked) {
            isClicked = false
            iv_toggle_password.setImageResource(R.drawable.ic_eye)
            edt_phone.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            isClicked = true
            iv_toggle_password.setImageResource(R.drawable.ic_eye_hide)
            edt_phone.transformationMethod = PasswordTransformationMethod.getInstance()
        }
        setSelection()
    }


    fun setPadding(padding: Int) {
        setPadding(padding, padding, padding, padding)
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        edt_phone.setPadding(left, top, right, bottom)
        img_left.setPadding(left, top, 0, bottom)
    }

    fun getHintTextView(): TextView? {
        return tv_error
    }

    fun getEditText(): TextInputEditText? {
        return edt_phone
    }

    fun setText(charSequence: CharSequence?) {
        edt_phone.setText(charSequence)
    }

    fun getText(): String? {
        return edt_phone.text.toString().trim()
    }

    fun setErrorEnabled(isErrorEnable: Boolean) {
        tv_error.visibility = if (isErrorEnable) VISIBLE else GONE
    }

    fun setError(@Nullable errorText: String?) {
        containerEditText
        TransitionManager.beginDelayedTransition(
            containerEditText,
            TransitionSet()
                .addTransition(Fade())
                .setInterpolator(LinearOutSlowInInterpolator())
        )
        if (isNullOrEmpty(errorText)) {
            tv_error.visibility = GONE
        } else {
            tv_error.visibility = VISIBLE
            tv_error.text = errorText
        }
    }

    fun setCompoundDrawablesWithIntrinsicBounds(i: Int, i1: Int, i2: Int, i3: Int) {
        edt_phone.setCompoundDrawablesWithIntrinsicBounds(i, i1, i2, i3)
    }

    fun clearText() {
        edt_phone.text?.clear()
    }

    fun setErrorPadding(left: Int, top: Int, right: Int, bottom: Int) {
        tv_error.setPadding(left, top, right, bottom)
    }

    /**
     * Change the editor type integer associated with the text view, which
     * is reported to an Input Method Editor (IME) with when it has focus.
     */
    fun setImeOptions(imeOptions: Int) {
        edt_phone.imeOptions = imeOptions
    }

    fun setImeActionLabel(label: String?, imeOptions: Int) {
        edt_phone.setImeActionLabel(label, imeOptions)
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        edt_phone.setOnClickListener(OnClickListener { view -> listener?.onClick(view) })
    }

    /**
     * Sets the text to be displayed when the text of the TextView is empty.
     * Null means to use the normal empty text. The hint does not currently
     * participate in determining the size of the view.
     */
    fun setHint(hint: String?) {
        edt_phone.hint = hint
    }

    fun setHint(hint: SpannableStringBuilder?) {
        edt_phone.hint = hint
    }

    fun setHint(resid: Int) {
        setHint(context.resources.getString(resid))
    }

    fun setOnEditorActionListener(listener: TextView.OnEditorActionListener) {
        edt_phone.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            listener.onEditorAction(v, actionId, event)
            false
        })
    }

    fun setLines(lines: Int) {
        edt_phone.isSingleLine = false
        edt_phone.setLines(lines)
    }

    fun setMaxLines(maxLines: Int) {
        edt_phone.maxLines = maxLines
    }

    fun setGravity(gravity: Int) {
        edt_phone.gravity = gravity
    }

    fun isNullOrEmpty(s: String?): Boolean {
        if (s == null) return true
        return if (s.isEmpty()) true else s.equals("null", ignoreCase = true)
    }

    fun setTextListener(listener: AppEditTextViewListener) {
        this.listener = listener
    }

    @Suppress("DEPRECATION")
    fun setDisableText(isDisable: Boolean) {
        if (isDisable) {
            edt_phone.isEnabled = false
            edt_phone.background.setColorFilter(ContextCompat.getColor(context, R.color.placeholder), PorterDuff.Mode.SRC_ATOP)
        } else {
            edt_phone.isEnabled = true
            edt_phone.background.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_ATOP)
        }

    }
}
