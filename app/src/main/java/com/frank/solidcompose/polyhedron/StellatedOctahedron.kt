package com.frank.solidcompose.polyhedron

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.frank.solidcompose.Common
import com.frank.solidcompose.toDraw

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
    @Composable
    override fun DrawSolid() {
        Canvas(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            if (toDraw < -1) return@Canvas

            for (i in S8.indices) {
                val v = S8[i]
                for (j in v.indices) {
                    val face = v[j]
                    for (k in face.indices) {
                        val triangle = face[k]
                        drawLine(
                            start = Offset(x = p[triangle[0]].x, y = p[triangle[0]].y),
                            end = Offset(x = p[triangle[1]].x, y = p[triangle[1]].y),
                            color = Color.LightGray
                        )
                        drawLine(
                            start = Offset(x = p[triangle[1]].x, y = p[triangle[1]].y),
                            end = Offset(x = p[triangle[2]].x, y = p[triangle[2]].y),
                            color = Color.LightGray
                        )
                        drawLine(
                            start = Offset(x = p[triangle[0]].x, y = p[triangle[0]].y),
                            end = Offset(x = p[triangle[2]].x, y = p[triangle[2]].y),
                            color = Color.LightGray
                        )
                    }
                }
            }
        }
    }
}


