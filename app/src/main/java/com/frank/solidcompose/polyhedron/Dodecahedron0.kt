package com.frank.solidcompose.polyhedron

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.frank.solidcompose.Common
import com.frank.solidcompose.Common.PointXYZ
import com.frank.solidcompose.toDraw

object Dodecahedron0 : Polyhedron() {
    var FaceCount = 12
    private val face = arrayOf(
        intArrayOf(0, 1, 2, 3, 4),
        intArrayOf(19, 18, 17, 16, 15),
        intArrayOf(6, 13, 3, 2, 5),
        intArrayOf(8, 5, 2, 1, 7),
        intArrayOf(7, 1, 0, 9, 10),
        intArrayOf(9, 0, 4, 11, 12),
        intArrayOf(11, 4, 3, 13, 14),
        intArrayOf(16, 14, 13, 6, 15),
        intArrayOf(15, 6, 5, 8, 19),
        intArrayOf(19, 8, 7, 10, 18),
        intArrayOf(18, 10, 9, 12, 17),
        intArrayOf(17, 12, 11, 14, 16)
    )

    private val faceE = arrayOf(
        intArrayOf(0, 1, 2, 3, 4),
        intArrayOf(20, 21, 22, 23, 24),
        intArrayOf(19, 16, 2, 5, 6),
        intArrayOf(8, 5, 1, 7, 9),
        intArrayOf(7, 0, 10, 11, 12),
        intArrayOf(10, 4, 13, 14, 15),
        intArrayOf(13, 3, 16, 17, 18),
        intArrayOf(29, 17, 19, 25, 20),
        intArrayOf(25, 6, 8, 26, 24),
        intArrayOf(26, 9, 12, 27, 23),
        intArrayOf(27, 11, 15, 28, 22),
        intArrayOf(28, 14, 18, 29, 21)
    )

    private fun getNewPoint1(pOld: PointXYZ, pNew: PointXYZ, angleA: Double) {
        pNew.reset(
            pOld.x - Common.ObjCenter.x,
            pOld.y - Common.ObjCenter.y,
            pOld.z - Common.ObjCenter.z
        )
        val x = pNew.x * Math.cos(angleA) - pNew.z * Math.sin(angleA)
        val z = pNew.z * Math.cos(angleA) + pNew.x * Math.sin(angleA)
        pNew.reset(x.toFloat(), pNew.y, z.toFloat())
        pNew.reset(
            pNew.x + Common.ObjCenter.x,
            pNew.y + Common.ObjCenter.y,
            pNew.z + Common.ObjCenter.z
        )
    }

    init {
        init0()
    }

    fun init0() {
        val thisEdge = arrayOf(
            intArrayOf(0, 1),
            intArrayOf(1, 2),
            intArrayOf(2, 3),
            intArrayOf(3, 4),
            intArrayOf(4, 0),
            intArrayOf(2, 5),
            intArrayOf(5, 6),
            intArrayOf(1, 7),
            intArrayOf(5, 8),
            intArrayOf(7, 8),
            intArrayOf(0, 9),
            intArrayOf(9, 10),
            intArrayOf(7, 10),
            intArrayOf(4, 11),
            intArrayOf(11, 12),
            intArrayOf(9, 12),
            intArrayOf(13, 3),
            intArrayOf(13, 14),
            intArrayOf(14, 11),
            intArrayOf(6, 13),
            intArrayOf(15, 16),
            intArrayOf(16, 17),
            intArrayOf(17, 18),
            intArrayOf(18, 19),
            intArrayOf(15, 19),
            intArrayOf(6, 15),
            intArrayOf(8, 19),
            intArrayOf(10, 18),
            intArrayOf(12, 17),
            intArrayOf(14, 16)
        )
        initialization(20, 30, thisEdge)

        edgeLength = Common._screenWidth / 4f
        Common.ObjCenter.reset(Common._screenWidth / 2f, Common._screenHeight / 2f, -edgeLength / 2)

        for (i in 0..4) {
            pX[i] = Common._screenWidth / 2f + edgeLength * Math.cos(2 * 3.1416 / 5 * i.toDouble())
                .toFloat()
            pY[i] = Common._screenHeight / 2f
            pZ[i] = -edgeLength / 2 + edgeLength * Math.sin(2 * 3.1416 / 5 * i.toDouble()).toFloat()
        }
        var k = 5
        val px2 = pX[2]
        val newP = PointXYZ()
        val old = PointXYZ()
        for (j in 0..4) {
            var xOffset = px2 - edgeLength * Math.cos(2 * 3.1416 / 10).toFloat()
            for (i in 0..1) {
                pX[k] =
                    xOffset + edgeLength * Math.cos(6 * 3.1416 / 10 + 2 * 3.1416 / 5 * i.toDouble())
                        .toFloat()
                pY[k] = Common._screenHeight / 2f
                pZ[k] =
                    -edgeLength / 2 + edgeLength * Math.sin(6 * 3.1416 / 10 + 2 * 3.1416 / 5 * i.toDouble())
                        .toFloat()
                k++
            }
            xOffset = xOffset + edgeLength * Math.cos(2 * 3.1416 / 10).toFloat()
            var angleA = Math.toRadians(63.4349)
            for (i in k - 2 until k) {
                pY[i] = Common._screenHeight / 2f - Math.abs(pX[i] - xOffset) * Math.sin(angleA)
                    .toFloat()
                pX[i] = xOffset + (pX[i] - xOffset) * Math.cos(angleA).toFloat()
            }
            angleA = 2 * 3.1416 / 5
            for (i in 0 until k) {
                old.reset(pX[i], pY[i], pZ[i])
                getNewPoint1(old, newP, angleA)
                pX[i] = newP.x
                pY[i] = newP.y
                pZ[i] = newP.z
            }
        }
        val h = edgeLength * 2.227f * 2 * Math.sin(2 * 3.1416 / 10).toFloat()
        for (i in 0..4) {
            pX[k] =
                Common._screenWidth / 2f + edgeLength * Math.cos(3.1416 + 2 * 3.1416 / 5 * i.toDouble())
                    .toFloat()
            pY[k] = Common._screenHeight / 2f - h
            pZ[k] = -edgeLength / 2 + edgeLength * Math.sin(3.1416 + 2 * 3.1416 / 5 * i.toDouble())
                .toFloat()
            k++
        }
        for (i in 0 until PointCount) {
            pY[i] = pY[i] + h / 2
        }

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
                    for (j in 0..4) {
                        drawLine(
                            start = Offset(
                                x = p[edge[faceE[i][j]][0]].x,
                                y = p[edge[faceE[i][j]][0]].y
                            ),
                            end = Offset(
                                x = p[edge[faceE[i][j]][1]].x,
                                y = p[edge[faceE[i][j]][1]].y
                            ),
                            color = Color.LightGray
                        )
                    }
                }
            }
        }
    }
}
