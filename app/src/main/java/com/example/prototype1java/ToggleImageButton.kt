package com.example.prototype1java

import android.content.Context
import android.util.AttributeSet
import android.widget.Checkable
import android.widget.ImageButton

class ToggleImageButton : androidx.appcompat.widget.AppCompatImageButton, Checkable {
    var onCheckedChangeListener: OnCheckedChangeListener? = null

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet) : super(context!!, attrs) {
        setChecked(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet, defStyle: Int) : super(context!!, attrs, defStyle) {
        setChecked(attrs)
    }

    private fun setChecked(attrs: AttributeSet) {
        val attributeSet = context.obtainStyledAttributes(attrs, R.styleable.ToggleImageButton)
        isChecked = attributeSet.getBoolean(R.styleable.ToggleImageButton_android_checked, false)
        attributeSet.recycle()
    }

    override fun isChecked(): Boolean {
        return isSelected
    }

    override fun setChecked(checked: Boolean) {
        isSelected = checked
        onCheckedChangeListener?.onCheckedChanged(this, checked)
    }

    override fun toggle() {
        isChecked = !isChecked
    }

    override fun performClick(): Boolean {
        toggle()
        return super.performClick()
    }

    interface OnCheckedChangeListener {
        fun onCheckedChanged(buttonView: ToggleImageButton?, isChecked: Boolean)
    }
}