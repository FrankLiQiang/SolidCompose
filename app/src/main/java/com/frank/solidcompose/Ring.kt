package com.frank.solidcompose

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import com.frank.solidcompose.Common.PointF
import com.frank.solidcompose.Common.PointXYZ
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object Ring {
    var RR = 0f
    var r = 0f
    var R0 = 0f
    private val meridianNum = 37
    private val parallelNum = 17
    private val ellipsePointNum = 64
    private val ellipseGroup = arrayOfNulls<Ellipse>(meridianNum + parallelNum)
    private val oldEllipseGroup = arrayOfNulls<Ellipse>(meridianNum + parallelNum)

    @OptIn(ExperimentalComposeUiApi::class)
    var modifier = Modifier
        .pointerInteropFilter {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    Common.StartX = it.x
                    Common.StartY = it.y
                    saveOldPoints()
                    Common.isSingle = it.pointerCount == 1
                    Common.firstDistance =
                        sqrt(((Common.ObjCenter.x - Common.StartX) * (Common.ObjCenter.x - Common.StartX) + (Common.ObjCenter.y - Common.StartY) * (Common.ObjCenter.y - Common.StartY)).toDouble())
                            .toFloat()
                }

                MotionEvent.ACTION_MOVE -> {
                    Common.EndX = it.x
                    Common.EndY = it.y
                    if (it.pointerCount == 2) {
                        Common.isSingle = false
                        convert2()
                        toDraw = 1 - toDraw
                    } else if (it.pointerCount == 1 && Common.isSingle) {
                        convert()
                        toDraw = 1 - toDraw
                    }
                }
            }
            true
        }

    init {
        createEllipse()
        Common.StartX = 700f
        Common.StartY = 100f
        Common.EndX = 100f
        Common.EndY = 700f
        convert()
    }

    private fun createEllipse() {
        RR = Common._screenWidth * 0.4f
        r = RR * 0.3f

        //纬线
        for (i in 0 until parallelNum) {
            ellipseGroup[i] = Ellipse()
            oldEllipseGroup[i] = Ellipse()
            R0 = RR - r * cos(2 * PI / parallelNum * i).toFloat()
            for (j in 0 until ellipsePointNum) {
                ellipseGroup[i]!!.ellipse[j]!!.x =
                    Common.ObjCenter.x - R0 * cos(2 * PI / ellipsePointNum * j)
                        .toFloat()
                ellipseGroup[i]!!.ellipse[j]!!.y =
                    Common.ObjCenter.y - r * sin(2 * PI / parallelNum * i).toFloat()
                ellipseGroup[i]!!.ellipse[j]!!.z =
                    Common.ObjCenter.z + R0 * sin(2 * PI / ellipsePointNum * j)
                        .toFloat()
            }
        }

        //经线
        for (i in parallelNum until parallelNum + meridianNum) {
            ellipseGroup[i] = Ellipse()
            oldEllipseGroup[i] = Ellipse()
            for (j in 0 until ellipsePointNum) {
                R0 = RR - r * cos(2 * PI / ellipsePointNum * j).toFloat()
                ellipseGroup[i]!!.ellipse[j]!!.x =
                    Common.ObjCenter.x - R0 * cos(2 * PI / meridianNum * i).toFloat()
                ellipseGroup[i]!!.ellipse[j]!!.y =
                    Common.ObjCenter.y - r * sin(2 * PI / ellipsePointNum * j).toFloat()
                ellipseGroup[i]!!.ellipse[j]!!.z =
                    Common.ObjCenter.z + R0 * sin(2 * PI / meridianNum * i).toFloat()
            }
        }
        for (i in 0 until meridianNum + parallelNum) {
            for (j in 0 until ellipsePointNum) {
                Common.resetPointF(ellipseGroup[i]!!.ellipse2[j]!!, ellipseGroup[i]!!.ellipse[j]!!)
                oldEllipseGroup[i]!!.ellipse[j]!!.x = ellipseGroup[i]!!.ellipse[j]!!.x
                oldEllipseGroup[i]!!.ellipse[j]!!.y = ellipseGroup[i]!!.ellipse[j]!!.y
                oldEllipseGroup[i]!!.ellipse[j]!!.z = ellipseGroup[i]!!.ellipse[j]!!.z
                oldEllipseGroup[i]!!.ellipse2[j]!!.x = ellipseGroup[i]!!.ellipse2[j]!!.x
                oldEllipseGroup[i]!!.ellipse2[j]!!.y = ellipseGroup[i]!!.ellipse2[j]!!.y
            }
        }
    }

    fun saveOldPoints() {
        for (i in 0 until meridianNum + parallelNum) {
            for (j in 0 until ellipsePointNum) {
                oldEllipseGroup[i]!!.ellipse[j]!!.reset(
                    ellipseGroup[i]!!.ellipse[j]!!.x,
                    ellipseGroup[i]!!.ellipse[j]!!.y,
                    ellipseGroup[i]!!.ellipse[j]!!.z
                )
            }
        }
    }

    fun convert() {
        for (i in 0 until meridianNum + parallelNum) {
            for (j in 0 until ellipsePointNum) {
                Common.getNewPoint(oldEllipseGroup[i]!!.ellipse[j]!!, ellipseGroup[i]!!.ellipse[j]!!)
                Common.resetPointF(ellipseGroup[i]!!.ellipse2[j]!!, ellipseGroup[i]!!.ellipse[j]!!)
            }
        }
    }

    fun convert2() {
        for (i in 0 until meridianNum + parallelNum) {
            for (j in 0 until ellipsePointNum) {
                Common.getNewSizePoint(
                    oldEllipseGroup[i]!!.ellipse[j]!!,
                    ellipseGroup[i]!!.ellipse[j]!!
                )
                Common.resetPointF(ellipseGroup[i]!!.ellipse2[j]!!, ellipseGroup[i]!!.ellipse[j]!!)
            }
        }
    }

    @Composable
    fun DrawRing() {
        Canvas(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            if (toDraw < -1) return@Canvas

            for (index in 0 until meridianNum + parallelNum) {
                for (i in 0 until ellipsePointNum - 1) {
                    drawLine(
                        start = Offset(x = ellipseGroup[index]!!.ellipse2[i]!!.x,
                            y = ellipseGroup[index]!!.ellipse2[i]!!.y),
                        end = Offset(x = ellipseGroup[index]!!.ellipse2[i + 1]!!.x,
                            y = ellipseGroup[index]!!.ellipse2[i + 1]!!.y),
                        color = Color.LightGray
                    )
                }
                drawLine(
                    start = Offset(x = ellipseGroup[index]!!.ellipse2[0]!!.x,
                        y = ellipseGroup[index]!!.ellipse2[0]!!.y),
                    end = Offset(x = ellipseGroup[index]!!.ellipse2[ellipsePointNum - 1]!!.x,
                        y = ellipseGroup[index]!!.ellipse2[ellipsePointNum - 1]!!.y),
                    color = Color.LightGray
                )
            }
        }
    }
    class Ellipse internal constructor() {
        var ellipse: Array<PointXYZ?> = arrayOfNulls(ellipsePointNum)
        var ellipse2 = arrayOfNulls<PointF>(ellipsePointNum)

        init {
            for (i in 0 until ellipsePointNum) {
                ellipse[i] = PointXYZ()
                ellipse2[i] = PointF()
            }
        }
    }
}
