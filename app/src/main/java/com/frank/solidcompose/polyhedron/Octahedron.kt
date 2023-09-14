package com.frank.solidcompose.polyhedron

import com.frank.solidcompose.Common

object Octahedron : Polyhedron() {
    init {
        val thisEdge = arrayOf(
            intArrayOf(0, 1),
            intArrayOf(0, 2),
            intArrayOf(0, 3),
            intArrayOf(0, 4),
            intArrayOf(1, 2),
            intArrayOf(2, 4),
            intArrayOf(3, 4),
            intArrayOf(1, 3),
            intArrayOf(5, 1),
            intArrayOf(5, 2),
            intArrayOf(5, 3),
            intArrayOf(5, 4)
        )
        initialization(6, 12, thisEdge)
        edgeLength = Common._screenWidth * 0.75f
        Common.ObjCenter.reset(Common._screenWidth / 2f, Common._screenHeight / 2f, -edgeLength / 2)

        pX[0] = Common._screenWidth / 2f
        pY[0] = Common._screenHeight / 2f - edgeLength * 0.7071f
        pZ[0] = -edgeLength / 2

        pX[1] = Common._screenWidth / 2f - edgeLength / 2
        pY[1] = Common._screenHeight / 2f
        pZ[1] = -edgeLength / 2 - edgeLength / 2

        pX[2] = Common._screenWidth / 2f + edgeLength / 2
        pY[2] = Common._screenHeight / 2f
        pZ[2] = -edgeLength / 2 - edgeLength / 2

        pX[3] = Common._screenWidth / 2f - edgeLength / 2
        pY[3] = Common._screenHeight / 2f
        pZ[3] = -edgeLength / 2 + edgeLength / 2

        pX[4] = Common._screenWidth / 2f + edgeLength / 2
        pY[4] = Common._screenHeight / 2f
        pZ[4] = -edgeLength / 2 + edgeLength / 2

        pX[5] = Common._screenWidth / 2f
        pY[5] = Common._screenHeight / 2f + edgeLength * 0.7071f
        pZ[5] = -edgeLength / 2
        myInit()
    }
}

