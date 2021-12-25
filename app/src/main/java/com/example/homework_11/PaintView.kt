package com.example.homework_11

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.homework_11.MainActivity.Companion.paintBrush
import com.example.homework_11.MainActivity.Companion.path

class PaintView : View {

    var params: ViewGroup.LayoutParams? = null

    companion object {
        var pathList = ArrayList<Path>()
        var colorList = ArrayList<Int>()
        var currentBrush = Color.BLACK
    }

    constructor(context: Context) : this(context, null) {
        initBrush()

    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        initBrush()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initBrush()
    }

    private fun initBrush() {
        val widthSize: Float = 8f
        paintBrush.isAntiAlias = true
        paintBrush.color = currentBrush
        paintBrush.style = Paint.Style.STROKE
        paintBrush.strokeJoin = Paint.Join.ROUND
        paintBrush.strokeWidth = widthSize
        params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var x = event.x
        var y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x, y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
                pathList.add(path)
                colorList.add(currentBrush)
            }
            else -> return false
        }
        postInvalidate()
        return false
    }

    override fun onDraw(canvas: Canvas) {
        for (i in pathList.indices) {
            paintBrush.color = colorList[i]
            canvas.drawPath(pathList[i], paintBrush)
            invalidate()
        }
    }

}