package com.frank.solidcompose.polyhedron

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.frank.solidcompose.Common
import com.frank.solidcompose.newCubeBMP
import com.frank.solidcompose.originalCubeBMP
import com.frank.solidcompose.toDraw
import java.nio.ByteBuffer

object PictCube : Polyhedron() {
    var FaceCount = 6
    lateinit var bmpByteArray: ByteArray
    lateinit var newCubeBMPByteArray: ByteArray

    external fun Initialization2(width: Int, height: Int, width00: Int, height00: Int)
    external fun transforms(
        count: Int,
        index1: Int,
        index2: Int,
        index3: Int,
        _in_: ByteArray?,
        out: ByteArray?,
        A1x: Float,
        A1y: Float,
        B1x: Float,
        B1y: Float,
        C1x: Float,
        C1y: Float,
        D1x: Float,
        D1y: Float,
        A2x: Float,
        A2y: Float,
        B2x: Float,
        B2y: Float,
        C2x: Float,
        C2y: Float,
        D2x: Float,
        D2y: Float,
        A3x: Float,
        A3y: Float,
        B3x: Float,
        B3y: Float,
        C3x: Float,
        C3y: Float,
        D3x: Float,
        D3y: Float
    )

    init {
        System.loadLibrary("DrawSolid")

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

        init2()
        myInit()
        Common.setEYE(Common.Eye.x, Common.Eye.y, Common.Eye.z)
    }

    private val face = arrayOf(
        intArrayOf(0, 3, 2, 1),
        intArrayOf(7, 4, 5, 6),
        intArrayOf(0, 4, 7, 3),
        intArrayOf(2, 6, 5, 1),
        intArrayOf(3, 7, 6, 2),
        intArrayOf(1, 5, 4, 0)
    )

    private fun bmp2byte() {
        var bytes: Int
        var buf: ByteBuffer
        bytes = originalCubeBMP!!.byteCount
        buf = ByteBuffer.allocate(bytes)
        originalCubeBMP!!.copyPixelsToBuffer(buf)
        bmpByteArray = buf.array()
        bytes = newCubeBMP.byteCount
        buf = ByteBuffer.allocate(bytes)
        newCubeBMP.copyPixelsToBuffer(buf)
        newCubeBMPByteArray = buf.array()
    }

    fun init2() {
        Common.setEYE(Common.Eye.x, Common.Eye.y, Common.Eye.z)
        newCubeBMP = Bitmap.createBitmap(
            Common._screenWidth,
            Common._screenHeight,
            Bitmap.Config.ARGB_8888
        )
        bmp2byte()
        Initialization2(
            Common._screenWidth, Common._screenHeight,
            originalCubeBMP!!.width, originalCubeBMP!!.height
        )
    }

    @Composable
    override fun DrawSolid() {
        if (toDraw < -1) return

        val index = intArrayOf(0, 0, 0)
        val bmpIndex = intArrayOf(0, 0, 0)
        var count = 0
        for (i in 0 until FaceCount) {
            if (Common.isVisible(
                    P[face[i][1]]!!.x, P[face[i][1]]!!.y, P[face[i][1]]!!.z,
                    P[face[i][2]]!!.x, P[face[i][2]]!!.y, P[face[i][2]]!!.z,
                    P[face[i][3]]!!.x, P[face[i][3]]!!.y, P[face[i][3]]!!.z
                ) > 0
            ) {
                index[count] = i
                bmpIndex[count++] = i
            }
        }
        try {
            transforms(
                count,
                index[0],
                index[1],
                index[2],
                bmpByteArray,
                newCubeBMPByteArray,
                p[face[index[0]][0]].x,
                p[face[index[0]][0]].y,
                p[face[index[0]][1]].x,
                p[face[index[0]][1]].y,
                p[face[index[0]][2]].x,
                p[face[index[0]][2]].y,
                p[face[index[0]][3]].x,
                p[face[index[0]][3]].y,
                p[face[index[1]][0]].x,
                p[face[index[1]][0]].y,
                p[face[index[1]][1]].x,
                p[face[index[1]][1]].y,
                p[face[index[1]][2]].x,
                p[face[index[1]][2]].y,
                p[face[index[1]][3]].x,
                p[face[index[1]][3]].y,
                p[face[index[2]][0]].x,
                p[face[index[2]][0]].y,
                p[face[index[2]][1]].x,
                p[face[index[2]][1]].y,
                p[face[index[2]][2]].x,
                p[face[index[2]][2]].y,
                p[face[index[2]][3]].x,
                p[face[index[2]][3]].y
            )
            newCubeBMP.copyPixelsFromBuffer(ByteBuffer.wrap(newCubeBMPByteArray))
            toDraw = 1 - toDraw
        } catch (e: Exception) {
            var a = e.toString()
            a += ""
        }
        Image(
            bitmap = newCubeBMP.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = modifier
                .background(Color.Black)
                .fillMaxSize()
        )
    }
}
