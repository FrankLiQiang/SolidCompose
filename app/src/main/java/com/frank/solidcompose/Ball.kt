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
import com.frank.solidcompose.Common.ObjCenter
import com.frank.solidcompose.Common._screenWidth
import com.frank.solidcompose.Common.getNewPoint
import com.frank.solidcompose.Common.getNewSizePoint
import com.frank.solidcompose.Common.resetPointF
import com.frank.solidcompose.polyhedron.StellatedOctahedron
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class Ball {
    var RR = 0f
    private val meridianNum = 7
    private val parallelNum = 7
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
                        sqrt(((ObjCenter.x - Common.StartX) * (ObjCenter.x - Common.StartX) + (ObjCenter.y - Common.StartY) * (ObjCenter.y - Common.StartY)).toDouble())
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

    inner class Ellipse internal constructor() {
        var ellipse: Array<Common.PointXYZ?> = arrayOfNulls(ellipsePointNum)
        var ellipse2 = arrayOfNulls<Common.PointF>(ellipsePointNum)

        init {
            for (i in 0 until ellipsePointNum) {
                ellipse[i] = Common.PointXYZ()
                ellipse2[i] = Common.PointF()
            }
        }
    }

    init {
        init0()
    }

    fun init0() {
        RR = _screenWidth * 0.4f

        //纬线
        for (i in 0 until parallelNum) {
            ellipseGroup[i] = Ellipse()
            oldEllipseGroup[i] = Ellipse()
            for (j in 0 until ellipsePointNum) {
                ellipseGroup[i]!!.ellipse[j]!!.x =
                    ObjCenter.x - RR * sin(PI / (parallelNum + 1) * (i + 1)) * cos(2 * PI / ellipsePointNum * j)
                ellipseGroup[i]!!.ellipse[j]!!.y =
                    ObjCenter.y - RR * cos(PI / (parallelNum + 1) * (i + 1))
                ellipseGroup[i]!!.ellipse[j]!!.z =
                    ObjCenter.z + RR * sin(PI / (parallelNum + 1) * (i + 1)) * sin(2 * PI / ellipsePointNum * j)
            }
        }

        //经线
        for (i in parallelNum until parallelNum + meridianNum) {
            ellipseGroup[i] = Ellipse()
            oldEllipseGroup[i] = Ellipse()
            for (j in 0 until ellipsePointNum) {
                ellipseGroup[i]!!.ellipse[j]!!.x =
                    ObjCenter.x - RR * cos(2 * PI / meridianNum * i) * sin(2 * PI / ellipsePointNum * j)
                ellipseGroup[i]!!.ellipse[j]!!.y =
                    ObjCenter.y - RR * cos(2 * PI / ellipsePointNum * j)
                ellipseGroup[i]!!.ellipse[j]!!.z =
                    ObjCenter.z + RR * sin(2 * PI / meridianNum * i) * sin(2 * PI / ellipsePointNum * j)
            }
        }
        for (i in 0 until meridianNum + parallelNum) {
            for (j in 0 until ellipsePointNum) {
                resetPointF(ellipseGroup[i]!!.ellipse2[j]!!, ellipseGroup[i]!!.ellipse[j]!!)
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
                getNewPoint(oldEllipseGroup[i]!!.ellipse[j]!!, ellipseGroup[i]!!.ellipse[j]!!)
                resetPointF(ellipseGroup[i]!!.ellipse2[j]!!, ellipseGroup[i]!!.ellipse[j]!!)
            }
        }
    }

    fun convert2() {
        for (i in 0 until meridianNum + parallelNum) {
            for (j in 0 until ellipsePointNum) {
                getNewSizePoint(
                    oldEllipseGroup[i]!!.ellipse[j]!!,
                    ellipseGroup[i]!!.ellipse[j]!!
                )
                resetPointF(ellipseGroup[i]!!.ellipse2[j]!!, ellipseGroup[i]!!.ellipse[j]!!)
            }
        }
    }

    @Composable
    fun DrawBall() {
        Canvas(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            if (toDraw < -1) return@Canvas

            fun drawEllipse(index: Int) {
                for (i in 0 until ellipsePointNum - 1) {
                    drawLine(
                        start = Offset(
                            x = ellipseGroup[index]!!.ellipse2[i]!!.x,
                            y = ellipseGroup[index]!!.ellipse2[i]!!.y
                        ),
                        end = Offset(
                            x = ellipseGroup[index]!!.ellipse2[i + 1]!!.x,
                            y = ellipseGroup[index]!!.ellipse2[i + 1]!!.y
                        ),
                        color = Color.LightGray
                    )
                }
                drawLine(
                    start = Offset(
                        x = ellipseGroup[index]!!.ellipse2[0]!!.x,
                        y = ellipseGroup[index]!!.ellipse2[0]!!.y
                    ),
                    end = Offset(
                        x = ellipseGroup[index]!!.ellipse2[ellipsePointNum - 1]!!.x,
                        y = ellipseGroup[index]!!.ellipse2[ellipsePointNum - 1]!!.y
                    ),
                    color = Color.LightGray
                )
            }

            for (i in 0 until meridianNum + parallelNum) {
                drawEllipse(i)
            }
        }
    }
}
