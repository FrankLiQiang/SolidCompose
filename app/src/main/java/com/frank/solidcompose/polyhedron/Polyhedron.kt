package com.frank.solidcompose.polyhedron

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
import com.frank.solidcompose.Common
import com.frank.solidcompose.Common.EndX
import com.frank.solidcompose.Common.EndY
import com.frank.solidcompose.Common.ObjCenter
import com.frank.solidcompose.Common.PointF
import com.frank.solidcompose.Common.StartX
import com.frank.solidcompose.Common.StartY
import com.frank.solidcompose.Common.firstDistance
import com.frank.solidcompose.toDraw
import kotlin.math.sqrt

open class Polyhedron {
    var edgeLength = 0f
    lateinit var pX: FloatArray
    lateinit var pY: FloatArray
    lateinit var pZ: FloatArray
    lateinit var edge: Array<IntArray>
    lateinit var P: Array<Common.PointXYZ?>
    private lateinit var oldP: Array<Common.PointXYZ?>
    var p: Array<PointF> = arrayOf(
        PointF(),
        PointF(),
        PointF(),
        PointF(),
        PointF(),
        PointF(),
        PointF(),
        PointF(),
        PointF(),
        PointF(),
        PointF(),
        PointF(),
        PointF(),
        PointF(),
        PointF(),
        PointF(),
        PointF(),
        PointF(),
        PointF(),
        PointF(),
        PointF(),
        PointF(),
        PointF(),
        PointF(),
        PointF()
    )

    @OptIn(ExperimentalComposeUiApi::class)
    var modifier = Modifier
        .pointerInteropFilter {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    StartX = it.x
                    StartY = it.y
                    saveOldPoints()
                    Common.isSingle = it.pointerCount == 1
                    firstDistance =
                        sqrt(((ObjCenter.x - StartX) * (ObjCenter.x - StartX) + (ObjCenter.y - StartY) * (ObjCenter.y - StartY)).toDouble())
                            .toFloat()
                }

                MotionEvent.ACTION_MOVE -> {
                    EndX = it.x
                    EndY = it.y
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

    fun myInit() {

        for (i in 0 until PointCount) {
            P[i] = Common.PointXYZ(pX[i], pY[i], pZ[i])
            oldP[i] = Common.PointXYZ(pX[i], pY[i], pZ[i])
            Common.resetPointF(p[i], P[i]!!)
        }
        StartX = 300F
        StartY = 100F
        EndX = 100F
        EndY = 300F
        convert()
    }

    fun initialization(pointCount: Int, edgeCount: Int, newEdge: Array<IntArray>) {
        PointCount = pointCount
        EdgeCount = edgeCount
        pX = FloatArray(PointCount)
        pY = FloatArray(PointCount)
        pZ = FloatArray(PointCount)
        edge = newEdge
        P = arrayOfNulls(PointCount)
        oldP = arrayOfNulls(PointCount)
    }

    private fun saveOldPoints() {
        for (i in 0 until PointCount) {
            oldP[i]!!.reset(P[i]!!.x, P[i]!!.y, P[i]!!.z)
        }
    }

    private fun convert() {
        for (i in 0 until PointCount) {
            Common.getNewPoint(oldP[i]!!, P[i]!!)
            Common.resetPointF(p[i], P[i]!!)
        }
    }

    private fun convert2() {
        for (i in 0 until PointCount) {
            Common.getNewSizePoint(oldP[i]!!, P[i]!!)
            Common.resetPointF(p[i], P[i]!!)
        }
    }

    @Composable
    open fun DrawSolid() {
        Canvas(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            if (toDraw < -1) return@Canvas

            for (i in 0 until EdgeCount) {
                drawLine(
                    start = Offset(x = p[edge[i][0]].x, y = p[edge[i][0]].y),
                    end = Offset(x = p[edge[i][1]].x, y = p[edge[i][1]].y),
                    color = Color.LightGray
                )
            }
        }
    }

    companion object {
        @JvmStatic
        protected var PointCount = 1
        @JvmStatic
        protected var EdgeCount = 1
    }
}