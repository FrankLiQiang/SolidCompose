package com.frank.solidcompose

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.frank.solidcompose.Common.initCommon
import com.frank.solidcompose.polyhedron.ColorCube
import com.frank.solidcompose.polyhedron.Cube
import com.frank.solidcompose.polyhedron.Cube0
import com.frank.solidcompose.polyhedron.Dodecahedron
import com.frank.solidcompose.polyhedron.Dodecahedron0
import com.frank.solidcompose.polyhedron.Icosahedron
import com.frank.solidcompose.polyhedron.Octahedron
import com.frank.solidcompose.polyhedron.PictCube
import com.frank.solidcompose.polyhedron.StellatedOctahedron
import com.frank.solidcompose.polyhedron.Tetrahedron
import com.frank.solidcompose.ui.theme.SolidComposeTheme
import kotlin.system.exitProcess

var isShowMenu by mutableStateOf(true)
var solidType by mutableStateOf(0)
var toDraw by mutableStateOf(0)
var originalEarthBMP: Bitmap? = null
var originalCubeBMP: Bitmap? = null
lateinit var mainActivity: MainActivity
lateinit var newBallBMP: Bitmap
lateinit var newCubeBMP: Bitmap
lateinit var openBMP: ()-> Unit

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        originalEarthBMP = (resources.getDrawable(R.drawable.earth) as BitmapDrawable).bitmap
        originalCubeBMP = (resources.getDrawable(R.drawable.cube) as BitmapDrawable).bitmap
        initCommon(windowManager)
        mainActivity = this
        // 设置页面全屏 刘海屏 显示
        val window = window
        val lp = window.attributes
        lp.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        window.attributes = lp
        val decorView = window.decorView
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        // 设置页面全屏 刘海屏 显示
        setContent {
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) { result ->
                originalEarthBMP = MediaStore.Images.Media.getBitmap(getContentResolver(), result)
                Earth.init0()
                solidType = 12
            }
            openBMP = {launcher.launch("image/*")}
            SolidComposeTheme {
                if (isShowMenu) {
                    showMenu()
                } else {
                    when (solidType) {
                        0 -> {
                            Tetrahedron.DrawSolid()
                        }

                        1 -> {
                            Cube.DrawSolid()
                        }

                        2 -> {
                            Octahedron.DrawSolid()
                        }

                        3 -> {
                            Dodecahedron.DrawSolid()
                        }

                        4 -> {
                            Icosahedron.DrawSolid()
                        }

                        5 -> {
                            PictCube.DrawSolid()
                        }

                        6 -> {
                            Ring.DrawRing()
                        }

                        7 -> {
                            Mobius().DrawRing()
                        }

                        10 -> {
                            Ball().DrawBall()
                        }

                        11 -> {
                            Cube0.DrawSolid()
                        }

                        12 -> {
                            Earth.DrawPicBall()
                        }

                        13 -> {
                            Dodecahedron0.DrawSolid()
                        }

                        14 -> {
                            ColorCube.DrawSolid()
                        }
                        15 -> {
                            StellatedOctahedron.DrawSolid()
                        }
                        17 -> {
                            Earth.DrawPicBall()
                        }
                    }
                }
            }
        }
    }

    companion object {
        init {
            System.loadLibrary("DrawSolid")
        }
    }

    override fun onBackPressed() {
        if (isShowMenu) {
            exitProcess(0)
        } else {
            isShowMenu = true
        }
    }
}
