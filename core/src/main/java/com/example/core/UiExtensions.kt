package com.example.core

import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

fun Activity.hideKeyboard() {
    val view = currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun EditText.showKeyboard() {
    requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.toggleVisibility(isVisible: Boolean, useGone: Boolean = true) {
    visibility = if (isVisible) View.VISIBLE else if (useGone) View.GONE else View.INVISIBLE
}

fun View.enable() {
    isEnabled = true
    alpha = 1f
}

fun View.disable(alphaValue: Float = 0.5f) {
    isEnabled = false
    alpha = alphaValue
}

fun TextView.setTextColorRes(@ColorRes colorRes: Int) {
    setTextColor(ContextCompat.getColor(context, colorRes))
}

fun TextView.strikeThrough() {
    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

fun TextView.removeStrikeThrough() {
    paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
}

fun TextView.bold() {
    setTypeface(typeface, Typeface.BOLD)
}

fun TextView.italic() {
    setTypeface(typeface, Typeface.ITALIC)
}

fun ImageView.setImageDrawableRes(@DrawableRes drawableRes: Int) {
    setImageDrawable(ContextCompat.getDrawable(context, drawableRes))
}

fun ImageView.setTint(@ColorRes colorRes: Int) {
    setColorFilter(ContextCompat.getColor(context, colorRes))
}

fun View.setSafeOnClickListener(interval: Long = 800L, onSafeClick: (View) -> Unit) {
    var lastClickTime: Long = 0
    setOnClickListener {
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - lastClickTime >= interval) {
            lastClickTime = currentTime
            onSafeClick(it)
        }
    }
}

fun View.setMargins(
    left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null
) {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(
        left ?: params.leftMargin,
        top ?: params.topMargin,
        right ?: params.rightMargin,
        bottom ?: params.bottomMargin
    )
    layoutParams = params
}

fun View.setPaddingAll(padding: Int) {
    setPadding(padding, padding, padding, padding)
}

fun View.setPaddingRes(@DimenRes res: Int) {
    val padding = resources.getDimensionPixelSize(res)
    setPaddingAll(padding)
}

fun View.fadeIn(duration: Long = 300) {
    animate().alpha(1f).setDuration(duration).withStartAction { visible() }.start()
}

fun View.fadeOut(duration: Long = 300, useGone: Boolean = true) {
    animate().alpha(0f).setDuration(duration).withEndAction {
        if (useGone) gone() else invisible()
    }.start()
}

fun View.rotate(degrees: Float, duration: Long = 300) {
    animate().rotation(degrees).setDuration(duration).start()
}

fun View.scale(scale: Float, duration: Long = 300) {
    animate().scaleX(scale).scaleY(scale).setDuration(duration).start()
}

fun <T> MutableList<T>.replaceAll(newList: List<T>) {
    clear()
    addAll(newList)
}
