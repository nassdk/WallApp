package com.nassdk.wallapp.library.coreui.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.nassdk.wallapp.R

inline fun Activity.alert(
    title: CharSequence? = null,
    message: CharSequence? = null,
    theme: THEME = THEME.LIGHT,
    cancelable: Boolean = true,
    func: AlertDialogHelper.() -> Unit
): AlertDialog {
    return AlertDialogHelper(
        context = this,
        title = title,
        cancelable = cancelable,
        theme = theme,
        message = message
    ).apply {
        func()
    }.create()
}

inline fun Activity.alert(
    titleResource: Int = 0,
    messageResource: Int = 0,
    theme: THEME = THEME.LIGHT,
    cancelable: Boolean = true,
    func: AlertDialogHelper.() -> Unit
): AlertDialog {
    val title = if (titleResource == 0) null else getString(titleResource)
    val message = if (messageResource == 0) null else getString(messageResource)
    return AlertDialogHelper(
        context = this,
        title = title,
        cancelable = cancelable,
        theme = theme,
        message = message
    ).apply {
        func()
    }.create()
}

inline fun Fragment.alert(
    title: CharSequence? = null,
    message: CharSequence? = null,
    theme: THEME = THEME.LIGHT,
    cancelable: Boolean = true,
    func: AlertDialogHelper.() -> Unit
): AlertDialog {
    return AlertDialogHelper(
        context = view?.context!!,
        title = title,
        theme = theme,
        cancelable = cancelable,
        message = message
    ).apply {
        func()
    }.create()
}

inline fun Fragment.alert(
    titleResource: String = "",
    messageResource: String = "",
    theme: THEME = THEME.LIGHT,
    cancelable: Boolean = true,
    func: AlertDialogHelper.() -> Unit
) = AlertDialogHelper(
    context = view?.context!!,
    title = titleResource,
    message = messageResource,
    cancelable = cancelable,
    theme = theme
).apply {
    func()
}.create()

@SuppressLint("InflateParams")
class AlertDialogHelper(
    context: Context,
    title: CharSequence?,
    message: CharSequence?,
    theme: THEME = THEME.LIGHT,
    cancelable: Boolean = true
) {

    private val dialogView: View by lazyFast {
        LayoutInflater.from(context).inflate(R.layout.layout_dialog, null)
    }

    private val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        .setView(dialogView)

    private val cardContainer: CardView by lazyFast {
        dialogView.findViewById<CardView>(R.id.cardContainer)
    }

    private val title: TextView by lazyFast {
        dialogView.findViewById<TextView>(R.id.dialogInfoTitleTextView)
    }

    private val message: TextView by lazyFast {
        dialogView.findViewById<TextView>(R.id.dialogInfoMessageTextView)
    }

    private val positiveButton: Button by lazyFast {
        dialogView.findViewById<Button>(R.id.dialogInfoPositiveButton)
    }

    private val negativeButton: Button by lazyFast {
        dialogView.findViewById<Button>(R.id.dialogInfoNegativeButton)
    }

    private var dialog: AlertDialog? = null
    private var theme: THEME = THEME.LIGHT
    private var cancelable: Boolean = cancelable

    init {
        this.title.text = title
        this.message.text = message
        this.theme = theme
    }

    fun positiveButton(@StringRes textResource: Int, func: (() -> Unit)? = null) {
        with(positiveButton) {
            text = builder.context.getString(textResource)
            setClickListenerToDialogButton(func)
        }
    }

    fun positiveButton(text: CharSequence, func: (() -> Unit)? = null) {
        with(positiveButton) {
            this.text = text
            setClickListenerToDialogButton(func)
        }
    }

    fun negativeButton(@StringRes textResource: Int, func: (() -> Unit)? = null) {
        with(negativeButton) {
            text = builder.context.getString(textResource)
            setClickListenerToDialogButton(func)
        }
    }

    fun negativeButton(text: CharSequence, func: (() -> Unit)? = null) {
        with(negativeButton) {
            this.text = text
            setClickListenerToDialogButton(func)
        }
    }

    fun onCancel(func: () -> Unit) {
        builder.setOnCancelListener { func() }
    }

    fun create(): AlertDialog {

        title.isVisible(title.text.isNullOrEmpty().not())
        message.isVisible(message.text.isNullOrEmpty().not())
        positiveButton.isVisible(positiveButton.text.isNullOrEmpty().not())
        negativeButton.isVisible(negativeButton.text.isNullOrEmpty().not())


        if (theme == THEME.LIGHT) {

            cardContainer.setCardBackgroundColor(
                ContextCompat.getColor(
                    dialogView.context,
                    R.color.color_white
                )
            )

            title.setTextColor(ContextCompat.getColor(dialogView.context, R.color.color_black))
            message.setTextColor(ContextCompat.getColor(dialogView.context, R.color.color_black))
        } else {
            cardContainer.setCardBackgroundColor(
                ContextCompat.getColor(
                    dialogView.context,
                    R.color.gray_353535
                )
            )

            title.setTextColor(ContextCompat.getColor(dialogView.context, R.color.color_white))
            message.setTextColor(ContextCompat.getColor(dialogView.context, R.color.color_white))
        }

        dialog = builder
            .setCancelable(cancelable)
            .create()

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog!!
    }

    private fun Button.setClickListenerToDialogButton(func: (() -> Unit)?) {
        setOnClickListener {
            func?.invoke()
            dialog?.dismiss()
        }
    }
}

/**
 * Implementation of lazy that is not thread safe. Useful when you know what thread you will be
 * executing on and are not worried about synchronization.
 */
fun <T> lazyFast(operation: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) {
    operation()
}

enum class THEME {
    LIGHT,
    DARK
}