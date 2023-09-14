package com.frank.solidcompose.polyhedron

import com.frank.solidcompose.Common

object Dodecahedron : Polyhedron() {
    init {
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
            intArrayOf(
                9, 12
            ),
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
        val newP = Common.PointXYZ()
        val old = Common.PointXYZ()
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
    }

    private fun getNewPoint1(pOld: Common.PointXYZ, pNew: Common.PointXYZ, angleA: Double) {
        pNew.reset(
            pOld.x - Common.ObjCenter.x, pOld.y - Common.ObjCenter.y, pOld.z - Common.ObjCenter.z
        )
        val x: Double = pNew.x * Math.cos(angleA) - pNew.z * Math.sin(angleA)
        val z: Double = pNew.z * Math.cos(angleA) + pNew.x * Math.sin(angleA)
        pNew.reset(x.toFloat(), pNew.y, z.toFloat())
        pNew.reset(
            pNew.x + Common.ObjCenter.x, pNew.y + Common.ObjCenter.y, pNew.z + Common.ObjCenter.z
        )
    }
}

