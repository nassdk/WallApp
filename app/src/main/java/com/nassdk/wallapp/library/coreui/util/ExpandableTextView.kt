package com.nassdk.wallapp.library.coreui.util

import android.content.Context
import android.content.res.TypedArray
import android.text.Html
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.nassdk.wallapp.R


class ExpandableTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr), View.OnClickListener {

    private var originalText: CharSequence? = null
    private var trimmedText: CharSequence? = null
    private var bufferType: BufferType? = null
    private var trim = true
    private var trimLength: Int

    init {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView)
        trimLength =
            typedArray.getInt(R.styleable.ExpandableTextView_trimLength, DEFAULT_TRIM_LENGTH)
        typedArray.recycle()
        setOnClickListener(this)
    }

    private fun setText() {
        super.setText(Html.fromHtml(displayableText.toString()), bufferType)
    }

    private val displayableText: CharSequence?
        get() = if (trim) trimmedText else originalText

    override fun setText(text: CharSequence?, type: BufferType?) {
        originalText = text
        trimmedText = getTrimmedText()
        bufferType = type
        setText()
    }

    private fun getTrimmedText(): CharSequence? {
        return if (originalText != null && originalText!!.length > trimLength) {
            SpannableStringBuilder(originalText, 0, trimLength + 1).append(ELLIPSIS)
        } else {
            originalText
        }
    }

    override fun onClick(view: View?) {
        trim = !trim
        setText()
        requestFocusFromTouch()
    }

    fun setTrimLength(trimLength: Int) {
        this.trimLength = getHeight(trimLength)
        trimmedText = getTrimmedText()
        setText()
    }

    private fun getHeight(lineCount: Int): Int {
        return ((lineCount * getLineCount().plus(if (lineCount > 0) (lineCount - 1) * paint.fontSpacing.toInt() else 0) / 1.5f)).toInt()
    }

    companion object {
        private const val DEFAULT_TRIM_LENGTH = 100
        private const val ELLIPSIS =
            "<html><body><font color='#5E7697'> Читать далее...</font></body></html>"
    }
}