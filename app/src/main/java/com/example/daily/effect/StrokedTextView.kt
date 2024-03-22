package com.example.daily.effect

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.example.daily.R

class StrokedTextView : AppCompatTextView {

    constructor(ctx: Context) : super(ctx, null)
    constructor(ctx: Context, attr: AttributeSet?) : super(ctx, attr, 0) {
        getStyledAttributes(attr)
    }

    constructor(ctx: Context, attr: AttributeSet?, defStyleAttr: Int) : super(ctx, attr, defStyleAttr) {
        getStyledAttributes(attr)
    }

    private var calcWidth = 0

    var strokeWidth = 4f
    var strokeColor = Color.RED

    private lateinit var staticLayout: StaticLayout

    private val staticLayoutPaint by lazy {
        TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = this@StrokedTextView.textSize
            typeface = this@StrokedTextView.typeface
        }
    }
    private fun getStyledAttributes(attr: AttributeSet?) {
        context.obtainStyledAttributes(attr, R.styleable.StrokedTextView).apply {
            strokeWidth = getDimensionPixelSize(R.styleable.StrokedTextView_strokeThickness, 4).toFloat()
            strokeColor = getColor(R.styleable.StrokedTextView_strokeColor, Color.RED)
            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setPadding(paddingStart + strokeWidth.toInt() / 2, paddingTop, paddingRight + strokeWidth.toInt() / 2, paddingBottom)
        calcWidth = (MeasureSpec.getSize(widthMeasureSpec) - paddingStart)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        val compoundPaddingLeft = compoundPaddingLeft
        val compoundPaddingTop = compoundPaddingTop
        val compoundPaddingRight = compoundPaddingRight
        val compoundPaddingBottom = compoundPaddingBottom

        if (canvas == null) return


        reinitialzieStaticLayout()

        with(canvas) {
            save()
            translate(compoundPaddingLeft.toFloat(),compoundPaddingTop.toFloat())

            staticLayoutPaint.configureForStroke()
            staticLayout.draw(this)

            staticLayoutPaint.configureForFill()
            staticLayout.draw(this)

            restore()
        }
    }

    private fun reinitialzieStaticLayout() {
        staticLayout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder
                .obtain(text, 0, text.length, staticLayoutPaint, calcWidth)
                .setLineSpacing(lineSpacingExtra, lineSpacingMultiplier)
                .build()
        } else {
            StaticLayout(
                text,
                staticLayoutPaint,
                calcWidth,
                Layout.Alignment.ALIGN_NORMAL,
                lineSpacingMultiplier,
                lineSpacingExtra,
                true
            )
        }
    }

    private fun Paint.configureForFill() {
        style = Paint.Style.FILL
        color = textColors.defaultColor
    }

    private fun Paint.configureForStroke() {
        style = Paint.Style.STROKE
        color = strokeColor
        strokeWidth = this@StrokedTextView.strokeWidth
    }
}