package com.example.complan.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.complan.R

class EditTextPasswordCustom : AppCompatEditText, View.OnTouchListener {

    private lateinit var showButtonImage: Drawable
    private lateinit var closeButtonImage: Drawable
    private var showStatus: Int = 0
    private var textType : Int = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    private fun init() {
        //txtColor = InputType.TYPE_CLASS_TEXT
        //showCloseButton()
        showButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_see_password) as Drawable
        closeButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_nosee_password) as Drawable
        setOnTouchListener(this)
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //if (showStatus == 0) showClearButton() else hideClearButton()
            }
            override fun afterTextChanged(s: Editable) {
                if (s.length > 0 && s.length < 6){
                    error = "Ga Boleh Kurang dari 6 karakter"
                }else{
                    //inputType = txtColor
                }
            }
        })
    }

    override fun onTouch(p0: View, event: MotionEvent): Boolean {
        return false
    }

    private fun show(){
        if (showStatus == 0){
            showClearButton()
        }else{
            showCloseButton()
        }
    }

    private fun showCloseButton() {
        setButtonDrawables(endOfTheText = closeButtonImage)
    }

    private fun showClearButton() {
        setButtonDrawables(endOfTheText = showButtonImage)
    }
    private fun hideClearButton() {
        setButtonDrawables()
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText:Drawable? = null,
        endOfTheText:Drawable? = null,
        bottomOfTheText: Drawable? = null
    ){
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

}