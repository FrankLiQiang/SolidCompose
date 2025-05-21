package com.frank.solidcompose.polyhedron

import com.frank.solidcompose.Common

object StellatedOctahedron : Polyhedron() {
    init {
        val thisEdge = arrayOf(
            intArrayOf(0, 2),
            intArrayOf(1, 3),
            intArrayOf(4, 6),
            intArrayOf(5, 7),
            intArrayOf(0, 7),
            intArrayOf(3, 4),
            intArrayOf(6, 1),
            intArrayOf(2, 5),
            intArrayOf(0, 5),
            intArrayOf(1, 4),
            intArrayOf(3, 6),
            intArrayOf(2, 7),
            intArrayOf(8, 9),
            intArrayOf(10, 9),
            intArrayOf(10, 11),
            intArrayOf(8, 11),
            intArrayOf(8, 12),
            intArrayOf(8, 13),
            intArrayOf(10, 12),
            intArrayOf(10, 13),
            intArrayOf(14, 12),
            intArrayOf(15, 12),
            intArrayOf(14, 13),
            intArrayOf(15, 13),
        )
        initialization(16, 24, thisEdge)
        edgeLength = Common._screenWidth / 2f
        Common.ObjCenter.reset(Common._screenWidth / 2f, Common._screenHeight / 2f, -edgeLength / 2)

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

        pX[8] = (pX[2] + pX[7]) / 2
        pY[8] = (pY[2] + pY[7]) / 2
        pZ[8] = (pZ[2] + pZ[7]) / 2

        pX[9] = (pX[2] + pX[5]) / 2
        pY[9] = (pY[2] + pY[5]) / 2
        pZ[9] = (pZ[2] + pZ[5]) / 2

        pX[10] = (pX[1] + pX[4]) / 2
        pY[10] = (pY[1] + pY[4]) / 2
        pZ[10] = (pZ[1] + pZ[4]) / 2

        pX[11] = (pX[3] + pX[4]) / 2
        pY[11] = (pY[3] + pY[4]) / 2
        pZ[11] = (pZ[3] + pZ[4]) / 2

        pX[12] = (pX[3] + pX[1]) / 2
        pY[12] = (pY[3] + pY[1]) / 2
        pZ[12] = (pZ[3] + pZ[1]) / 2

        pX[13] = (pX[5] + pX[7]) / 2
        pY[13] = (pY[5] + pY[7]) / 2
        pZ[13] = (pZ[5] + pZ[7]) / 2

        pX[14] = (pX[0] + pX[7]) / 2
        pY[14] = (pY[0] + pY[7]) / 2
        pZ[14] = (pZ[0] + pZ[7]) / 2

        pX[15] = (pX[5] + pX[2]) / 2
        pY[15] = (pY[5] + pY[2]) / 2
        pZ[15] = (pZ[5] + pZ[2]) / 2
        myInit()
    }
}

