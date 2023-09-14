package com.frank.solidcompose

import android.graphics.Bitmap
import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import com.frank.solidcompose.Common.PointF
import com.frank.solidcompose.Common.PointXYZ
import java.nio.ByteBuffer
import kotlin.math.sqrt

object Earth {

    private external fun initialization(
        ex: Float,
        ey: Float,
        ez: Float,
        z: Float,
        cx: Float,
        cy: Float,
        cz: Float
    )

    external fun Initialization2(
        width0: Int,
        height0: Int,
        width: Int,
        height: Int,
        RR: Float,
        r: Float
    )

    external fun transforms(
        in_: ByteArray?,
        out: ByteArray?,
        ArcticX: Float,
        ArcticY: Float,
        ArcticZ: Float,
        MeridianX: Float,
        MeridianY: Float,
        MeridianZ: Float,
        r: Float
    ): Int

    init {
        System.loadLibrary("DrawSolid")
    }

    var RR = 0f
    var r = 0f
    var Old_r = 0f

    private val Arctic: PointXYZ = PointXYZ()
    private val Meridian: PointXYZ = PointXYZ()
    private val ArcticF: PointF = PointF()
    private val MeridianF: PointF = PointF()
    private val Meridian0F: PointF = PointF()
    private val OldArctic: PointXYZ = PointXYZ()
    private val OldMeridian: PointXYZ = PointXYZ()

    lateinit var bmpByteArray: ByteArray
    lateinit var newBmpByteArray: ByteArray

    fun init0() {
        Common.Eye.reset(
            Common._screenWidth / 2f, Common._screenHeight / 2f,
            Common._screenHeight.toFloat()
        )
        RR = Common._screenHeight / 3f
        Common.ObjCenter.reset(Common._screenWidth / 2f, Common._screenHeight / 2f, -RR)

        initialization(
            Common.Eye.x,
            Common.Eye.y,
            Common.Eye.z,
            Common.DepthZ,
            Common.ObjCenter.x,
            Common.ObjCenter.y,
            Common.ObjCenter.z
        )
        Arctic.reset(Common.ObjCenter.x, Common.ObjCenter.y - RR, Common.ObjCenter.z)
        Meridian.reset(Common.ObjCenter.x - RR, Common.ObjCenter.y, Common.ObjCenter.z)
        Common.resetPointF(ArcticF, Arctic)
        Common.resetPointF(MeridianF, Meridian)
        val Meridian0 = PointXYZ()
        Meridian0.reset(Common.ObjCenter.x - RR, Common.ObjCenter.y, Common.ObjCenter.z)
        Common.resetPointF(Meridian0F, Meridian0)
        r = Common.ObjCenter.x - Meridian0F.x
        Old_r = r
        newBallBMP = Bitmap.createBitmap(
            Common._screenWidth,
            Common._screenHeight,
            Bitmap.Config.ARGB_8888
        )
        Initialization2(
            originalEarthBMP!!.width,
            originalEarthBMP!!.height,
            Common._screenWidth,
            Common._screenHeight,
            RR,
            r
        )
        bmp2byte()
        drawPicBall0()
        toDraw = 1 - toDraw
    }

    fun saveOldPoints() {
        OldArctic.reset(Arctic.x, Arctic.y, Arctic.z)
        OldMeridian.reset(Meridian.x, Meridian.y, Meridian.z)
        OldArctic.reset(Arctic.x, Arctic.y, Arctic.z)
        Old_r = r
    }

    fun convert() {
        Common.getNewPoint(OldArctic, Arctic)
        Common.getNewPoint(OldMeridian, Meridian)
        Common.resetPointF(ArcticF, Arctic)
        Common.resetPointF(MeridianF, Meridian)
    }

    fun convert2() {
        Common.getNewSizePoint(OldArctic, Arctic, false)
        r = Old_r * Common.distance
        Common.getNewSizePoint(OldMeridian, Meridian, false)
    }

    private fun bmp2byte() {
        var bytes = originalEarthBMP!!.byteCount
        var buf = ByteBuffer.allocate(bytes)
        originalEarthBMP!!.copyPixelsToBuffer(buf)
        bmpByteArray = buf.array()
        bytes = newBallBMP.byteCount
        buf = ByteBuffer.allocate(bytes)
        newBallBMP.copyPixelsToBuffer(buf)
        newBmpByteArray = buf.array()
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun DrawPicBall(modifier: Modifier = Modifier) {
        if (toDraw < -1) return

        Image(
            bitmap = newBallBMP.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black)
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            Common.StartX = it.x
                            Common.StartY = it.y
                            saveOldPoints()
                            Common.isSingle = it.pointerCount == 1
                            Common.firstDistance =
                                sqrt(((Common.ObjCenter.x - Common.StartX) * (Common.ObjCenter.x - Common.StartX) + (Common.ObjCenter.y - Common.StartY) * (Common.ObjCenter.y - Common.StartY)).toDouble())
                                    .toFloat()
                        }

                        MotionEvent.ACTION_MOVE -> {
                            Common.EndX = it.x
                            Common.EndY = it.y
                            if (it.pointerCount == 2) {
                                Common.isSingle = false
                                convert2()
                                drawPicBall0()
                                toDraw = 1 - toDraw
                            } else if (it.pointerCount == 1 && Common.isSingle) {
                                convert()
                                drawPicBall0()
                                toDraw = 1 - toDraw
                            }
                        }
                    }
                    true
                }
        )
    }


    fun drawPicBall0() {
        try {
            transforms(
                bmpByteArray,
                newBmpByteArray,
                Arctic.x,
                Arctic.y,
                Arctic.z,
                Meridian.x,
                Meridian.y,
                Meridian.z,
                r
            )
            newBallBMP.copyPixelsFromBuffer(ByteBuffer.wrap(newBmpByteArray))
        } catch (_: Exception) {
        }
    }
}
