package com.frank.solidcompose.polyhedron

import com.frank.solidcompose.Common

object Icosahedron : Polyhedron() {
    init {
        val m = 0.5257f
        val n = 0.85065f
        val thisEdge = arrayOf(
            intArrayOf(2, 9),
            intArrayOf(2, 10),
            intArrayOf(9, 10),
            intArrayOf(5, 0),
            intArrayOf(5, 11),
            intArrayOf(0, 11),
            intArrayOf(8, 11),
            intArrayOf(8, 0),
            intArrayOf(3, 8),
            intArrayOf(3, 11),
            intArrayOf(7, 8),
            intArrayOf(7, 3),
            intArrayOf(2, 3),
            intArrayOf(2, 7),
            intArrayOf(1, 5),
            intArrayOf(
                1, 0
            ),
            intArrayOf(4, 0),
            intArrayOf(4, 8),
            intArrayOf(4, 9),
            intArrayOf(4, 7),
            intArrayOf(9, 7),
            intArrayOf(1, 9),
            intArrayOf(1, 4),
            intArrayOf(6, 5),
            intArrayOf(6, 11),
            intArrayOf(1, 10),
            intArrayOf(5, 10),
            intArrayOf(6, 10),
            intArrayOf(2, 6),
            intArrayOf(6, 3)
        )
        initialization(12, 30, thisEdge)

        edgeLength = Common._screenWidth / 2f
        Common.ObjCenter.reset(Common._screenWidth / 2f, Common._screenHeight / 2f, -edgeLength / 2)

        //{(±m,0,±n), (0,±n,±m), (±n,±m,0)}

        //{(±m,0,±n), (0,±n,±m), (±n,±m,0)}
        pX[0] = m
        pY[0] = 0f
        pZ[0] = n

        pX[1] = -m
        pY[1] = 0f
        pZ[1] = n

        pX[2] = -m
        pY[2] = 0f
        pZ[2] = -n

        pX[3] = m
        pY[3] = 0f
        pZ[3] = -n

        pX[4] = 0f
        pY[4] = n
        pZ[4] = m

        pX[5] = 0f
        pY[5] = -n
        pZ[5] = m

        pX[6] = 0f
        pY[6] = -n
        pZ[6] = -m

        pX[7] = 0f
        pY[7] = n
        pZ[7] = -m

        pX[8] = n
        pY[8] = m
        pZ[8] = 0f

        pX[9] = -n
        pY[9] = m
        pZ[9] = 0f

        pX[10] = -n
        pY[10] = -m
        pZ[10] = 0f

        pX[11] = n
        pY[11] = -m
        pZ[11] = 0f

        for (i in 0 until PointCount) {
            pX[i] = pX[i] * edgeLength + Common.ObjCenter.x
            pY[i] = pY[i] * edgeLength + Common.ObjCenter.y
            pZ[i] = pZ[i] * edgeLength + Common.ObjCenter.z
        }
        myInit()
    }
}

