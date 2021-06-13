package com.example.clothesvillage.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.example.clothesvillage.R


class BottomLineTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var underLinePaint : Paint
    private var underLineColor = Color.TRANSPARENT
    private var underLineWidth = 0
    private var width = 0f
    private var height = 0f

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BottomLineTextView, defStyleAttr, 0)
        underLineColor = typedArray.getColor(R.styleable.BottomLineTextView_underlineColor, 0)
        underLineWidth = typedArray.getDimensionPixelSize(R.styleable.BottomLineTextView_underlineWidth, 0)
        typedArray.recycle()

        underLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)

        underLinePaint.run {
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = underLineColor
            strokeWidth = underLineWidth.toFloat()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        width = w.toFloat()
        height = h.toFloat()
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawLine(0f, height, width, height, underLinePaint)
    }
}