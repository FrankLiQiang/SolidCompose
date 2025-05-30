package com.frank.solidcompose.polyhedron

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.frank.solidcompose.Common
import com.frank.solidcompose.toDraw

object Cube0 : Polyhedron() {
    var FaceCount = 6
    private val face = arrayOf(
        intArrayOf(0, 3, 2, 1),
        intArrayOf(7, 4, 5, 6),
        intArrayOf(0, 4, 7, 3),
        intArrayOf(2, 6, 5, 1),
        intArrayOf(3, 7, 6, 2),
        intArrayOf(1, 5, 4, 0)
    )

    private val faceE = arrayOf(
        intArrayOf(0, 1, 2, 3),
        intArrayOf(4, 5, 6, 7),
        intArrayOf(8, 11, 7, 3),
        intArrayOf(9, 10, 5, 1),
        intArrayOf(10, 11, 6, 2),
        intArrayOf(8, 9, 4, 0)
    )

    init {
        init0()
    }

    fun init0() {
        val thisEdge = arrayOf(
            intArrayOf(0, 1),
            intArrayOf(1, 2),
            intArrayOf(2, 3),
            intArrayOf(3, 0),
            intArrayOf(4, 5),
            intArrayOf(5, 6),
            intArrayOf(6, 7),
            intArrayOf(7, 4),
            intArrayOf(0, 4),
            intArrayOf(1, 5),
            intArrayOf(2, 6),
            intArrayOf(3, 7)
        )
        initialization(8, 12, thisEdge)
        edgeLength = Common._screenWidth / 2f

        pX[0] = Common._screenWidth / 4f
        pX[3] = pX[0]
        pX[4] = pX[0]
        pX[7] = pX[0]

        pX[1] = pX[0] + edgeLength
        pX[2] = pX[1]
        pX[5] = pX[1]
        pX[6] = pX[1]

        pY[0] = Common._screenHeight / 2f - edgeLength / 2
        pY[1] = pY[0]
        pY[2] = pY[0]
        pY[3] = pY[0]

        pY[4] = pY[0] + edgeLength
        pY[5] = pY[4]
        pY[6] = pY[4]
        pY[7] = pY[4]

        pZ[2] = 0f
        pZ[3] = pZ[2]
        pZ[6] = pZ[2]
        pZ[7] = pZ[2]

        pZ[0] = pZ[2] - edgeLength
        pZ[1] = pZ[0]
        pZ[4] = pZ[0]
        pZ[5] = pZ[0]

        myInit()
        Common.setEYE(Common.Eye.x, Common.Eye.y, Common.Eye.z)
    }

    @Composable
    override fun DrawSolid() {
        Canvas(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            if (toDraw < -1) return@Canvas

            for (i in 0 until FaceCount) {
                if (Common.isVisible(
                        P[face[i][1]]!!.x, P[face[i][1]]!!.y, P[face[i][1]]!!.z,
                        P[face[i][2]]!!.x, P[face[i][2]]!!.y, P[face[i][2]]!!.z,
                        P[face[i][3]]!!.x, P[face[i][3]]!!.y, P[face[i][3]]!!.z
                    ) > 0
                ) {
                    for (j in 0..3) {
                        drawLine(
                            start = Offset(x = p[edge[faceE[i][j]][0]].x, y = p[edge[faceE[i][j]][0]].y),
                            end = Offset(x = p[edge[faceE[i][j]][1]].x, y = p[edge[faceE[i][j]][1]].y),
                            color = Color.LightGray
                        )
                    }
                }
            }
        }
    }
}

