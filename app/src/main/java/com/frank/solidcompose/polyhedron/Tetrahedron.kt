package com.frank.solidcompose.polyhedron

import com.frank.solidcompose.Common

object Tetrahedron : Polyhedron() {
    init {
        val thisEdge = arrayOf(
            intArrayOf(0, 1),
            intArrayOf(0, 2),
            intArrayOf(0, 3),
            intArrayOf(1, 2),
            intArrayOf(1, 3),
            intArrayOf(2, 3)
        )
        initialization(4, 6, thisEdge)
        edgeLength = Common._screenWidth / 2f
        Common.ObjCenter.reset(Common._screenWidth / 2f, Common._screenHeight / 2f, -edgeLength / 2)
        pX[0] = Common._screenWidth / 2f + edgeLength / 2
        pY[0] = Common._screenHeight / 2f - edgeLength / 2
        pZ[0] = -edgeLength
        pX[1] = Common._screenWidth / 2f - edgeLength / 2
        pY[1] = pY[0]
        pZ[1] = 0f
        pX[2] = pX[1]
        pY[2] = Common._screenHeight / 2f + edgeLength / 2
        pZ[2] = pZ[0]
        pX[3] = pX[0]
        pY[3] = pY[2]
        pZ[3] = 0f
        myInit()
    }
}
