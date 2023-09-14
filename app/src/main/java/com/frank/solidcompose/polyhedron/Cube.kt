package com.frank.solidcompose.polyhedron

import com.frank.solidcompose.Common

object Cube : Polyhedron() {
    init {
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
        myInit()
    }
}

