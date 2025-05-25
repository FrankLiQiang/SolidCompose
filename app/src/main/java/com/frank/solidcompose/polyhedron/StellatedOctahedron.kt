package com.frank.solidcompose.polyhedron

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.frank.solidcompose.Common
import com.frank.solidcompose.toDraw
import kotlin.collections.indices
import kotlin.collections.mutableMapOf
import kotlin.collections.set

object StellatedOctahedron : Polyhedron() {
    // 顶点 0 对面
    private val face0 = arrayOf(
        intArrayOf(4, 8, 12),
        intArrayOf(7, 11, 8 ),
        intArrayOf(5, 12, 11),
    )
    // 顶点 1 对面
    private val face1 = arrayOf(
        intArrayOf(5, 10, 12),
        intArrayOf(6, 9, 10),
        intArrayOf(4, 12, 9),
    )
    // 顶点 2 对面
    private val face2 = arrayOf(
        intArrayOf(6, 13, 9),
        intArrayOf(7, 8, 13),
        intArrayOf(4, 9, 8),
    )
    // 顶点 3 对面
    private val face3 = arrayOf(
        intArrayOf(5, 11, 10),
        intArrayOf(7, 13, 11),
        intArrayOf(6, 10, 13),
    )

    // 顶点 4 对面
    private val face4 = arrayOf(
        intArrayOf(1, 9, 12),
        intArrayOf(2, 8, 9),
        intArrayOf(0, 12, 8),
    )
    // 顶点 5 对面
    private val face5 = arrayOf(
        intArrayOf(3, 10, 11),
        intArrayOf(1, 12, 10 ),
        intArrayOf(0, 11, 12),
    )
    // 顶点 6 对面
    private val face6 = arrayOf(
        intArrayOf(1, 10, 9),
        intArrayOf(3, 13, 10),
        intArrayOf(2, 9, 13),
    )
    // 顶点 7 对面
    private val face7 = arrayOf(
        intArrayOf(2, 13, 8),
        intArrayOf(3, 11, 13),
        intArrayOf(0, 8, 11),
    )
    private val v1 = arrayOf(
        face0,
        face1,
        face2,
        face3,
    )
    private val v2 = arrayOf(
        face4,
        face5,
        face6,
        face7,
    )
    private val S8 = arrayOf(
        v1, v2,
    )
    init {
        initialization(14, 24, face0)
        edgeLength = Common._screenWidth / 2f
        Common.ObjCenter.reset(Common._screenWidth / 2f, Common._screenHeight / 2f, -edgeLength / 2)

        pX[5] = Common._screenWidth / 4f
        pX[0] = pX[5]
        pX[3] = pX[5]
        pX[7] = pX[5]

        pX[1] = pX[5] + edgeLength
        pX[4] = pX[1]
        pX[6] = pX[1]
        pX[2] = pX[1]

        pY[5] = Common._screenHeight / 2f - edgeLength / 2
        pY[1] = pY[5]
        pY[4] = pY[5]
        pY[0] = pY[5]

        pY[3] = pY[5] + edgeLength
        pY[6] = pY[3]
        pY[2] = pY[3]
        pY[7] = pY[3]

        pZ[4] = 0f
        pZ[0] = pZ[4]
        pZ[2] = pZ[4]
        pZ[7] = pZ[4]

        pZ[5] = pZ[4] - edgeLength
        pZ[1] = pZ[5]
        pZ[3] = pZ[5]
        pZ[6] = pZ[5]

        pX[8] = (pX[4] + pX[7]) / 2
        pY[8] = (pY[4] + pY[7]) / 2
        pZ[8] = (pZ[4] + pZ[7]) / 2

        pX[9] = (pX[4] + pX[6]) / 2
        pY[9] = (pY[4] + pY[6]) / 2
        pZ[9] = (pZ[4] + pZ[6]) / 2

        pX[10] = (pX[1] + pX[3]) / 2
        pY[10] = (pY[1] + pY[3]) / 2
        pZ[10] = (pZ[1] + pZ[3]) / 2

        pX[11] = (pX[0] + pX[3]) / 2
        pY[11] = (pY[0] + pY[3]) / 2
        pZ[11] = (pZ[0] + pZ[3]) / 2

        pX[12] = (pX[0] + pX[1]) / 2
        pY[12] = (pY[0] + pY[1]) / 2
        pZ[12] = (pZ[0] + pZ[1]) / 2

        pX[13] = (pX[6] + pX[7]) / 2
        pY[13] = (pY[6] + pY[7]) / 2
        pZ[13] = (pZ[6] + pZ[7]) / 2
        myInit()
    }

    private fun isClockwise(p1: Int, p2: Int, p3: Int) :Boolean{
        val x1 = p[p1].x
        val y1 = p[p1].y
        val x2 = p[p2].x
        val y2 = p[p2].y
        val x3 = p[p3].x
        val y3 = p[p3].y
        return (x3 - x1) * (y2 - y1) - (y3 - y1) * (x2 - x1) < 0
    }

    @Composable
    override fun DrawSolid() {
        Canvas(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            fun drawTriangle(triangle: IntArray) {
                drawLine(
                    start = Offset(x = p[triangle[0]].x, y = p[triangle[0]].y),
                    end = Offset(x = p[triangle[1]].x, y = p[triangle[1]].y),
                    strokeWidth = 10f,
//                            color = Color.LightGray
                    color = Color.Yellow
                )
                drawLine(
                    start = Offset(x = p[triangle[1]].x, y = p[triangle[1]].y),
                    end = Offset(x = p[triangle[2]].x, y = p[triangle[2]].y),
                    strokeWidth = 10f,
                    color = Color.Yellow
                )
                drawLine(
                    start = Offset(x = p[triangle[0]].x, y = p[triangle[0]].y),
                    end = Offset(x = p[triangle[2]].x, y = p[triangle[2]].y),
                    strokeWidth = 10f,
                    color = Color.Yellow
                )
            }

            if (toDraw < -1) return@Canvas

            val map = mutableMapOf<String, String>()

            fun fuzzySearch(map: Map<String, String>, headTerm: String, searchTerm: String): Boolean {
                Log.i("AAA","headTerm = " + headTerm)
                Log.i("AAA","searchTerm = " + searchTerm)
                return map.values.any { it.startsWith(headTerm, ignoreCase = true) && it.endsWith(searchTerm, ignoreCase = true) }
            }
            for (i in S8.indices) {
                val v = S8[i]
                for (j in v.indices) {
                    val face = v[j]
                    var triangle = face[0]
                    var tIndex = String.format("%02d,%02d,%02d", triangle[0], triangle[1], triangle[2])
                    if (isClockwise(triangle[0], triangle[1], triangle[2])) {
                        var value = String.format("%02d,%02d,%02d,%02d,%02d,%02d", i, 0, 0, triangle[0], triangle[1], triangle[2])
                        map[tIndex] = value
                        triangle = face[1]
                        tIndex =
                            String.format("%02d,%02d,%02d", triangle[0], triangle[1], triangle[2])
                        value = String.format("%02d,%02d,%02d,%02d,%02d,%02d", i, 0, 1, triangle[0], triangle[1], triangle[2])
                        map[tIndex] = value
                        triangle = face[2]
                        tIndex =
                            String.format("%02d,%02d,%02d", triangle[0], triangle[1], triangle[2])
                        value = String.format("%02d,%02d,%02d,%02d,%02d,%02d", i, 0, 2, triangle[0], triangle[1], triangle[2])
                        map[tIndex] = value
                    }
                }
            }
            for (key in map.keys) {
                println("key= " + key + " and value= " + map[key])
                val arr = map[key]?.split(",")
                val i = arr?.get(0)?.toInt()
                val j = arr?.get(1)?.toInt()
                val k = arr?.get(2)?.toInt()
                val p1 = arr?.get(3)?.toInt()
                val p2 = arr?.get(4)?.toInt()
                val p3 = arr?.get(5)?.toInt()
                val found = fuzzySearch(map, String.format("%02d", 1 - i!!), arr[5] + "," + arr[4])
                if (found) {
                    drawTriangle(intArrayOf(p1!!, p2!!, p3!!))
                }
            }
        }
    }
}



