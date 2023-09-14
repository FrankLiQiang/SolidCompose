package com.frank.solidcompose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

var isRotateNumDialog by mutableStateOf(false)
var rotateNum by mutableStateOf(1)

@Composable
private fun CustomDialogWithResultExample(
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: (Int) -> Unit,
) {
    var num by remember { mutableStateOf(rotateNum) }

    Dialog(onDismissRequest = onDismiss) {

        Card(
            shape = RoundedCornerShape(12.dp)
        ) {

            Column(modifier = Modifier.padding(8.dp)) {

                Text(
                    text = "莫比乌斯环 旋转半周数",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Color Selection
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Column {

                        Text(text = "半周数 ${num}")

                        Slider(
                            value = num.toFloat(),
                            onValueChange = { num = it.toInt() },
                            valueRange = 0f..8f,
                            onValueChangeFinished = {}
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    TextButton(onClick = onNegativeClick) {
                        Text(text = "CANCEL")
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    TextButton(onClick = {
                        onPositiveClick(num)
                    }) {
                        Text(text = "OK")
                    }
                }
            }
        }
    }
}

@Composable
fun ShowMenuDialog() {
    if (isRotateNumDialog) {
        CustomDialogWithResultExample(
            onDismiss = {
                isRotateNumDialog = false
            },
            onNegativeClick = {
                isRotateNumDialog = false
            },
            onPositiveClick = { num ->
                isRotateNumDialog = false
                isShowMenu = false
                solidType = 7
                rotateNum = num
            },
        )
    }
}
