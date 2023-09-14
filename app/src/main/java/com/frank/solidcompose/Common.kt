package com.frank.solidcompose

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Point
import android.media.ExifInterface
import android.net.Uri
import android.view.WindowManager
import java.io.IOException
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

const val PI = 3.14159f

object Common {
    init {
        System.loadLibrary("DrawSolid")
    }

    external fun setEYE(eyeX: Float, eyeY: Float, eyeZ: Float)
    external fun isVisible(
        xE: Float, yE: Float, zE: Float,
        xF: Float, yF: Float, zF: Float,
        xG: Float, yG: Float, zG: Float
    ): Int

    var _screenWidth = 0
    var _screenHeight = 0
    var Eye = PointXYZ()
    var firstDistance = 0f
    var DepthZ = 0f
    var StartX = 0f
    var StartY = 0f
    var EndX = 0f
    var EndY = 0f
    var isSingle = true
    var ObjCenter = PointXYZ()
    var square = 0.0
    var distance = 0f
    var angleY = 0.0

    fun initCommon(windowManager: WindowManager) {
        val outSize = Point()
        windowManager.defaultDisplay.getRealSize(outSize)
        _screenWidth = outSize.x
        _screenHeight = outSize.y

        Eye.reset(_screenWidth / 2f,_screenHeight / 2f,_screenHeight * 2f)
        ObjCenter.reset(_screenWidth / 2f, _screenHeight / 2f, -_screenWidth / 4f)
        DepthZ = _screenWidth / 2f
    }

    fun resetPointF(thisP: PointF, P: PointXYZ) {
        thisP.x = (P.x - Eye.x) / (Eye.z - P.z) * (Eye.z - DepthZ) + Eye.x
        thisP.y = (P.y - Eye.y) / (Eye.z - P.z) * (Eye.z - DepthZ) + Eye.y
    }

    fun getDistance(x1: Float, y1: Float, x2: Float, y2: Float): Double {
        square = ((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)).toDouble()
        return sqrt(square)
    }

    fun getNewSizePoint(pOld: PointXYZ, pNew: PointXYZ) {
        getNewSizePoint(pOld, pNew, true)
    }

    fun getNewSizePoint(pOld: PointXYZ, pNew: PointXYZ, isChange: Boolean) {
        pNew.reset(pOld.x - ObjCenter.x, pOld.y - ObjCenter.y, pOld.z - ObjCenter.z)
        distance =
            sqrt(((ObjCenter.x - EndX) * (ObjCenter.x - EndX) + (ObjCenter.y - EndY) * (ObjCenter.y - EndY)).toDouble())
                .toFloat()
        distance /= firstDistance
        if (isChange) {
            pNew.reset(pNew.x * distance, pNew.y * distance, pNew.z * distance)
        }
        getDistance(StartX, StartY, EndX, EndY)
        val a2 = square
        val b = getDistance(ObjCenter.x, ObjCenter.y, StartX, StartY)
        val b2 = square
        val c = getDistance(ObjCenter.x, ObjCenter.y, EndX, EndY)
        val c2 = square
        val aCos = (b2 + c2 - a2) / (2 * b * c)
        var angleA: Double = if (abs(aCos) > 1) {
            0.0
        } else {
            acos(aCos)
        }
        val dir = getDir(ObjCenter.x, ObjCenter.y, StartX, StartY, EndX, EndY)
        angleA *= dir.toDouble()
        val x = pNew.x * cos(angleA) - pNew.y * sin(angleA)
        val y = pNew.y * cos(angleA) + pNew.x * sin(angleA)
        pNew.reset(x.toFloat(), y.toFloat(), pNew.z)
        pNew.reset(pNew.x + ObjCenter.x, pNew.y + ObjCenter.y, pNew.z + ObjCenter.z)
    }

    private fun getDir(x1: Float, y1: Float, x2: Float, y2: Float, x3: Float, y3: Float): Int {
        return if (x1 * y2 + x2 * y3 + x3 * y1 - x1 * y3 - x2 * y1 - x3 * y2 > 0) 1 else -1
    }

    fun getNewPoint(pOld: PointXYZ, pNew: PointXYZ) {
        pNew.reset(pOld.x - ObjCenter.x, pOld.y - ObjCenter.y, pOld.z - ObjCenter.z)
        var a2 = ((StartX - EndX) * (StartX - EndX)).toDouble()
        var b = getDistance(ObjCenter.x, ObjCenter.z, StartX, DepthZ)
        var b2 = square
        var c = getDistance(ObjCenter.x, ObjCenter.z, EndX, DepthZ)
        var c2 = square
        var aCos = (b2 + c2 - a2) / (2 * b * c)
        var angleA: Double
        angleA = if (Math.abs(aCos) > 1) {
            0.0
        } else {
            acos(aCos) * 2
        }
        if (EndX > StartX) angleA *= -1.0
        angleY = angleA
        val x = pNew.x * cos(angleA) - pNew.z * sin(angleA)
        var z = pNew.z * cos(angleA) + pNew.x * sin(angleA)
        pNew.reset(x.toFloat(), pNew.y, z.toFloat())

        //---------
        a2 = ((StartY - EndY) * (StartY - EndY)).toDouble()
        b = getDistance(ObjCenter.y, ObjCenter.z, StartY, DepthZ)
        b2 = square
        c = getDistance(ObjCenter.y, ObjCenter.z, EndY, DepthZ)
        c2 = square
        aCos = (b2 + c2 - a2) / (2 * b * c)
        angleA = if (abs(aCos) > 1) {
            0.0
        } else {
            acos(aCos) * 2
        }
        if (EndY > StartY) angleA *= -1.0
        val y = pNew.y * cos(angleA) - pNew.z * sin(angleA)
        z = pNew.z * cos(angleA) + pNew.y * sin(angleA)
        pNew.reset(pNew.x, y.toFloat(), z.toFloat())
        pNew.reset(pNew.x + ObjCenter.x, pNew.y + ObjCenter.y, pNew.z + ObjCenter.z)
    }

    @Throws(IOException::class)
    fun getExifOrientation(uri: Uri?, resolver: ContentResolver): Short {
        val parcelFileDescriptor = resolver.openFileDescriptor(uri!!, "r")
        val fileDescriptor = parcelFileDescriptor!!.fileDescriptor
        var exif: ExifInterface? = null
        try {
            exif = ExifInterface(fileDescriptor)
        } catch (var4: IOException) {
            var a = var4.toString()
            a += ""
        }
        var degree: Short = 0
        if (exif != null) {
            val orientation = exif.getAttributeInt("Orientation", -1)
            if (orientation != -1) {
                when (orientation) {
                    3 -> degree = 180
                    4, 5, 7 -> {}
                    6 -> degree = 90
                    8 -> degree = 270
                    else -> {}
                }
            }
        }
        parcelFileDescriptor.close()
        return degree
    }

    @Throws(IOException::class)
    fun getBitmapFromUri(uri: Uri?, resolver: ContentResolver): Bitmap {
        val parcelFileDescriptor = resolver.openFileDescriptor(uri!!, "r")
        val fileDescriptor = parcelFileDescriptor!!.fileDescriptor
        var image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        val degrees = getExifOrientation(uri, resolver).toInt()
        if (degrees != 0) {
            val m = Matrix()
            m.setRotate(degrees.toFloat())
            try {
                val b2 = Bitmap.createBitmap(image, 0, 0, image.width, image.height, m, true)
                if (image != b2) {
                    image.recycle()
                    image = b2
                }
            } catch (_: OutOfMemoryError) {
            }
        }
        return image
    }

    class PointXYZ {
        var x = 0f
        var y = 0f
        var z = 0f

        constructor()
        constructor(X: Float, Y: Float, Z: Float) {
            x = X
            y = Y
            z = Z
        }

        fun reset(X: Float, Y: Float, Z: Float) {
            x = X
            y = Y
            z = Z
        }
    }

    class PointF internal constructor() {
        var x = 0f
        var y = 0f
    }
}