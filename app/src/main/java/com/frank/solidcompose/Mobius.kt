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

open class Mobius {
    var RR = 0f
    var r = 0f
    var r0 = 0f
    var angle = 0f
    var x0 = 0f
    private val parallelNum = 17
    private val ellipsePointNum = 64
    private val lineGroup: Array<PointXYZ?> = arrayOfNulls(ellipsePointNum * 2)
    private val oldLineGroup: Array<PointXYZ?> = arrayOfNulls(ellipsePointNum * 2)
    private val line2Group = arrayOfNulls<PointF>(ellipsePointNum * 2)
    private val ellipseGroup = arrayOfNulls<Ellipse>(parallelNum)
    private val oldEllipseGroup = arrayOfNulls<Ellipse>(parallelNum)

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
        RR = Common._screenWidth * 0.4f
        r = RR * 0.3f

        //纬线
        var lineNum = 0
        for (i in 0 until parallelNum) {
            ellipseGroup[i] = Ellipse()
            oldEllipseGroup[i] = Ellipse()
            for (j in 0 until ellipsePointNum) {
                r0 = r - 2 * r / (parallelNum - 1) * i
                angle = (2 * PI / ellipsePointNum * j)
                x0 = RR - r0 * sin((angle / 2 * rotateNum).toDouble()).toFloat()
                ellipseGroup[i]!!.ellipse[j]!!.x =
                    Common.ObjCenter.x - x0 * sin(angle.toDouble()).toFloat()
                ellipseGroup[i]!!.ellipse[j]!!.y =
                    Common.ObjCenter.y - r0 * cos((angle / 2 * rotateNum).toDouble()).toFloat()
                ellipseGroup[i]!!.ellipse[j]!!.z =
                    Common.ObjCenter.z + x0 * cos(angle.toDouble()).toFloat()
                if (i == 0 || i == parallelNum - 1) {
                    lineGroup[lineNum] = PointXYZ()
                    oldLineGroup[lineNum] = PointXYZ()
                    lineGroup[lineNum++]!!.reset(
                        ellipseGroup[i]!!.ellipse[j]!!.x,
                        ellipseGroup[i]!!.ellipse[j]!!.y,
                        ellipseGroup[i]!!.ellipse[j]!!.z
                    )
                }
            }
        }
        for (i in 0 until parallelNum) {
            for (j in 0 until ellipsePointNum) {
                Common.resetPointF(ellipseGroup[i]!!.ellipse2[j]!!, ellipseGroup[i]!!.ellipse[j]!!)
                oldEllipseGroup[i]!!.ellipse[j]!!.x = ellipseGroup[i]!!.ellipse[j]!!.x
                oldEllipseGroup[i]!!.ellipse[j]!!.y = ellipseGroup[i]!!.ellipse[j]!!.y
                oldEllipseGroup[i]!!.ellipse[j]!!.z = ellipseGroup[i]!!.ellipse[j]!!.z
            }
        }
        for (i in 0 until ellipsePointNum * 2) {
            line2Group[i] = PointF()
            Common.resetPointF(line2Group[i]!!, lineGroup[i]!!)
            oldLineGroup[i]!!.x = lineGroup[i]!!.x
            oldLineGroup[i]!!.y = lineGroup[i]!!.y
            oldLineGroup[i]!!.z = lineGroup[i]!!.z
        }
    }

    fun saveOldPoints() {
        for (i in 0 until parallelNum) {
            for (j in 0 until ellipsePointNum) {
                oldEllipseGroup[i]!!.ellipse[j]!!.reset(
                    ellipseGroup[i]!!.ellipse[j]!!.x,
                    ellipseGroup[i]!!.ellipse[j]!!.y,
                    ellipseGroup[i]!!.ellipse[j]!!.z
                )
            }
        }
        for (i in 0 until ellipsePointNum * 2) {
            oldLineGroup[i]!!.reset(lineGroup[i]!!.x, lineGroup[i]!!.y, lineGroup[i]!!.z)
        }
    }

    fun convert() {
        for (i in 0 until parallelNum) {
            for (j in 0 until ellipsePointNum) {
                Common.getNewPoint(
                    oldEllipseGroup[i]!!.ellipse[j]!!,
                    ellipseGroup[i]!!.ellipse[j]!!
                )
                Common.resetPointF(ellipseGroup[i]!!.ellipse2[j]!!, ellipseGroup[i]!!.ellipse[j]!!)
            }
        }
        for (i in 0 until ellipsePointNum * 2) {
            Common.getNewPoint(oldLineGroup[i]!!, lineGroup[i]!!)
            Common.resetPointF(line2Group[i]!!, lineGroup[i]!!)
        }
    }

    fun convert2() {
        for (i in 0 until parallelNum) {
            for (j in 0 until ellipsePointNum) {
                Common.getNewSizePoint(
                    oldEllipseGroup[i]!!.ellipse[j]!!,
                    ellipseGroup[i]!!.ellipse[j]!!
                )
                Common.resetPointF(ellipseGroup[i]!!.ellipse2[j]!!, ellipseGroup[i]!!.ellipse[j]!!)
            }
        }
        for (i in 0 until ellipsePointNum * 2) {
            Common.getNewSizePoint(oldLineGroup[i]!!, lineGroup[i]!!)
            Common.resetPointF(line2Group[i]!!, lineGroup[i]!!)
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
                var theIndex = index
                if (rotateNum.toFloat() / 2 != (rotateNum / 2).toFloat()) {
                    theIndex = parallelNum - index - 1
                }
                drawLine(
                    start = Offset(
                        x = ellipseGroup[index]!!.ellipse2[0]!!.x,
                        y = ellipseGroup[index]!!.ellipse2[0]!!.y
                    ),
                    end = Offset(
                        x = ellipseGroup[theIndex]!!.ellipse2[ellipsePointNum - 1]!!.x,
                        y = ellipseGroup[theIndex]!!.ellipse2[ellipsePointNum - 1]!!.y
                    ),
                    color = Color.LightGray
                )
            }
            for (i in 0 until parallelNum) {
                drawEllipse(i)
            }
            for (i in 0 until ellipsePointNum) {
                drawLine(
                    start = Offset(
                        x = line2Group[i]!!.x,
                        y = line2Group[i]!!.y
                    ),
                    end = Offset(
                        x = line2Group[ellipsePointNum + i]!!.x,
                        y = line2Group[ellipsePointNum + i]!!.y
                    ),
                    color = Color.LightGray
                )
            }
        }
    }

    protected inner class Ellipse internal constructor() {
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
