package com.frank.solidcompose

import android.graphics.drawable.BitmapDrawable
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun showMenu() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        ShowMenuDialog()
        Row(Modifier.weight(1.0f)) {
            textButton("正四面体", Modifier.weight(1.0f)) { solidType = 0 }
            textButton("球体", Modifier.weight(1.0f)) { solidType = 10 }
        }
        Row(Modifier.weight(1.0f)) {
            textButton("正六面体", Modifier.weight(1.0f)) { solidType = 1 }
            textButton("正六面体(消隐)", Modifier.weight(1.0f)) { solidType = 11 }
        }
        Row(Modifier.weight(1.0f)) {
            textButton("正八面体", Modifier.weight(1.0f)) { solidType = 2 }
            textButton("照片球面", Modifier.weight(1.0f)) { openBMP() }
        }
        Row(Modifier.weight(1.0f)) {
            textButton("正十二面体", Modifier.weight(1.0f)) { solidType = 3 }
            textButton("正十二面体(消隐)", Modifier.weight(1.0f)) { solidType = 13 }
        }
        Row(Modifier.weight(1.0f)) {
            textButton("正二十面体", Modifier.weight(1.0f)) { solidType = 4 }
            textButton("彩色立方体", Modifier.weight(1.0f)) { solidType = 14 }
        }
        Row(Modifier.weight(1.0f)) {
            textButton("图片立方体", Modifier.weight(1.0f)) { solidType = 5 }
            textButton("图片立方体(六图)", Modifier.weight(1.0f)) {
                solidType = 15
                Toast.makeText(mainActivity, "作成中...", Toast.LENGTH_SHORT).show()
            }
        }
        Row(Modifier.weight(1.0f)) {
            textButton("圆环", Modifier.weight(1.0f)) { solidType = 6 }
            textButton("图片圆环", Modifier.weight(1.0f)) {
                solidType = 16
                Toast.makeText(mainActivity, "作成中...", Toast.LENGTH_SHORT).show()
            }
        }
        Row(Modifier.weight(1.0f)) {
            textButton("莫比乌斯环", Modifier.weight(1.0f)) {
                isRotateNumDialog = true
            }
            textButton("地球", Modifier.weight(1.0f)) {
                originalEarthBMP =
                    (mainActivity.resources.getDrawable(R.drawable.earth) as BitmapDrawable).bitmap
                Earth.init0()
                solidType = 17
            }
        }
    }
}

@Composable
fun textButton(currentShowWord: String, modifier: Modifier = Modifier, event: () -> Unit) {
    Column(
        modifier = modifier
            .padding(3.dp)
            .background(Color.DarkGray)
    ) {
        Row(Modifier.weight(1.2f)) {}
        ClickableText(
            text = AnnotatedString(currentShowWord),
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            ),
            onClick = {
                event()
                if (!isRotateNumDialog && solidType != 15 && solidType != 16) {
                    isShowMenu = false
                }
            },
            modifier = modifier
                .fillMaxSize()
        )
        Row(Modifier.weight(1.0f)) {}
    }
}