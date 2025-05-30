package com.frank.solidcompose.polyhedron

//import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.frank.solidcompose.Common
import com.frank.solidcompose.Common.PointF
import com.frank.solidcompose.toDraw
import kotlin.collections.indices
import kotlin.collections.mutableMapOf
import kotlin.collections.set
import kotlin.math.abs

object StellatedOctahedron : Polyhedron() {
    // 顶点 0 对面
    private val face0 = arrayOf(
        intArrayOf(4, 8, 12, 0, 11, 12, 0, 8, 11),
        intArrayOf(7, 11, 8, 0, 12, 8, 0, 11, 12),
        intArrayOf(5, 12, 11, 0, 8, 11, 0, 12, 8),
    )
    // 顶点 1 对面
    private val face1 = arrayOf(
        intArrayOf(5, 10, 12, 1, 9, 12, 1, 10, 9),
        intArrayOf(6, 9, 10, 1, 12, 10, 1, 9, 12),
        intArrayOf(4, 12, 9, 1, 10, 9, 1, 12, 10),
    )
    // 顶点 2 对面
    private val face2 = arrayOf(
        intArrayOf(6, 13, 9, 2, 8, 9, 2, 13, 8),
        intArrayOf(7, 8, 13, 2, 9, 13, 2, 8, 9),
        intArrayOf(4, 9, 8, 2, 13, 8, 2, 9, 13),
    )
    // 顶点 3 对面
    private val face3 = arrayOf(
        intArrayOf(5, 11, 10, 3, 13, 10, 3, 11, 13),
        intArrayOf(7, 13, 11, 3, 10, 11, 3, 13, 10),
        intArrayOf(6, 10, 13, 3, 11, 13, 3, 10, 11),
    )
    // 顶点 4 对面
    private val face4 = arrayOf(
        intArrayOf(1, 9, 12, 4, 8, 12, 4, 9, 8),
        intArrayOf(2, 8, 9, 4, 12, 9, 4, 8, 12),
        intArrayOf(0, 12, 8, 4, 9, 8, 4, 12, 9),
    )
    // 顶点 5 对面
    private val face5 = arrayOf(
        intArrayOf(3, 10, 11, 5, 12, 11, 5, 10, 12),
        intArrayOf(1, 12, 10, 5, 11, 10, 5, 12, 11),
        intArrayOf(0, 11, 12, 5, 10, 12, 5, 11, 10),
    )
    // 顶点 6 对面
    private val face6 = arrayOf(
        intArrayOf(1, 10, 9, 6, 13, 9, 6, 10, 13),
        intArrayOf(3, 13, 10, 6, 9, 10, 6, 13, 9),
        intArrayOf(2, 9, 13,  6, 10, 13, 6, 9, 10),
    )
    // 顶点 7 对面
    private val face7 = arrayOf(
        intArrayOf(2, 13, 8, 7, 11, 8, 7, 13, 11),
        intArrayOf(3, 11, 13, 7, 8, 13, 7, 11, 8),
        intArrayOf(0, 8, 11, 7, 13, 11, 7, 8, 13),
    )
    private val v1 = arrayOf(
        face0,
        face1,
        face2,
        face3,
    )
    private val v2 = arrayOf(
        face4,
        face5,
        face6,
        face7,
    )
    private val S8 = arrayOf(
        v1, v2,
    )
    init {
        init0()
    }

    fun init0() {
        initialization(14, 24, face0)
        edgeLength = Common._screenWidth / 1.3f
        Common.ObjCenter.reset(Common._screenWidth / 2f, Common._screenHeight / 2f, -edgeLength / 2)

        pX[5] = Common.ObjCenter.x - edgeLength / 2
        pX[0] = pX[5]
        pX[3] = pX[5]
        pX[7] = pX[5]

        pX[1] = pX[5] + edgeLength
        pX[4] = pX[1]
        pX[6] = pX[1]
        pX[2] = pX[1]

        pY[5] = Common.ObjCenter.y - edgeLength / 2
        pY[1] = pY[5]
        pY[4] = pY[5]
        pY[0] = pY[5]

        pY[3] = pY[5] + edgeLength
        pY[6] = pY[3]
        pY[2] = pY[3]
        pY[7] = pY[3]

        pZ[4] = 0f
        pZ[0] = pZ[4]
        pZ[2] = pZ[4]
        pZ[7] = pZ[4]

        pZ[5] = pZ[4] - edgeLength
        pZ[1] = pZ[5]
        pZ[3] = pZ[5]
        pZ[6] = pZ[5]

        pX[8] = (pX[4] + pX[7]) / 2
        pY[8] = (pY[4] + pY[7]) / 2
        pZ[8] = (pZ[4] + pZ[7]) / 2

        pX[9] = (pX[4] + pX[6]) / 2
        pY[9] = (pY[4] + pY[6]) / 2
        pZ[9] = (pZ[4] + pZ[6]) / 2

        pX[10] = (pX[1] + pX[3]) / 2
        pY[10] = (pY[1] + pY[3]) / 2
        pZ[10] = (pZ[1] + pZ[3]) / 2

        pX[11] = (pX[0] + pX[3]) / 2
        pY[11] = (pY[0] + pY[3]) / 2
        pZ[11] = (pZ[0] + pZ[3]) / 2

        pX[12] = (pX[0] + pX[1]) / 2
        pY[12] = (pY[0] + pY[1]) / 2
        pZ[12] = (pZ[0] + pZ[1]) / 2

        pX[13] = (pX[6] + pX[7]) / 2
        pY[13] = (pY[6] + pY[7]) / 2
        pZ[13] = (pZ[6] + pZ[7]) / 2
        myInit()
    }

    private fun isClockwise(p1: Int, p2: Int, p3: Int) :Boolean{
        val x1 = p[p1].x
        val y1 = p[p1].y
        val x2 = p[p2].x
        val y2 = p[p2].y
        val x3 = p[p3].x
        val y3 = p[p3].y
        return (x3 - x1) * (y2 - y1) - (y3 - y1) * (x2 - x1) < 0
    }

    private fun intersection_point(x1: Float, y1: Float, x2: Float, y2: Float, x3: Float, y3: Float, x4: Float, y4: Float): PointF? {
        val A1 = y2 - y1
        val B1 = x1 - x2
        val C1 = A1 * x1 + B1 * y1

        val A2 = y4 - y3
        val B2 = x3 - x4
        val C2 = A2 * x3 + B2 * y3

        val denominator = A1 * B2 - A2 * B1
        if (abs(denominator) < 0.000001) {
            return null
        }

        val x = (B2 * C1 - B1 * C2) / denominator
        val y = (A1 * C2 - A2 * C1) / denominator
        val ret = PointF()
        ret.x = x
        ret.y = y

        return ret
    }

    @Composable
    override fun DrawSolid() {
        Canvas(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            fun drawTriangle(triangle: IntArray) {
                drawLine(
                    start = Offset(x = p[triangle[0]].x, y = p[triangle[0]].y),
                    end = Offset(x = p[triangle[1]].x, y = p[triangle[1]].y),
                    strokeWidth = 3f,
//                            color = Color.LightGray
                    color = Color.Yellow
                )
                drawLine(
                    start = Offset(x = p[triangle[1]].x, y = p[triangle[1]].y),
                    end = Offset(x = p[triangle[2]].x, y = p[triangle[2]].y),
                    strokeWidth = 3f,
                    color = Color.Yellow
                )
                drawLine(
                    start = Offset(x = p[triangle[0]].x, y = p[triangle[0]].y),
                    end = Offset(x = p[triangle[2]].x, y = p[triangle[2]].y),
                    strokeWidth = 3f,
                    color = Color.Yellow
                )
            }
            fun isPointInTriangle(P: Int, A: Int, B: Int, C: Int): Int
            {
                val PA = PointF()
                PA.x = p[A].x - p[P].x
                PA.y = p[A].y - p[P].y

                val PB = PointF()
                PB.x = p[B].x - p[P].x
                PB.y = p[B].y - p[P].y

                val PC = PointF()
                PC.x = p[C].x - p[P].x
                PC.y = p[C].y - p[P].y

                val t1 = PA.x * PB.y - PA.y * PB.x
                val t2 = PB.x * PC.y - PB.y * PC.x
                val t3 = PC.x * PA.y - PC.y * PA.x
                return if ( t1 * t2 >= 0) {
                    if (t1 * t3 >= 0) {
                        0
                    } else {
                        1
                    }
                } else {
                    if (t1 * t3 >= 0) {
                        2
                    } else {
                        3
                    }
                }
            }
            val map = mutableMapOf<String, String>()
            fun drawTriangle2(key: String) {
                val arr0 = key.split(",")
                val arr = map[key]?.split(",")
                if (arr != null) {
                    val ret = isPointInTriangle(arr[3].toInt(), arr0[0].toInt(), arr0[1].toInt(), arr0[2].toInt())
                    if (ret == 0) {
                        drawLine(
                            start = Offset(x = p[arr0[0].toInt()].x, y = p[arr0[0].toInt()].y),
                            end = Offset(x = p[arr0[1].toInt()].x, y = p[arr0[1].toInt()].y),
                            strokeWidth = 3f,
                            color = Color.Yellow
                        )
                        drawLine(
                            start = Offset(x = p[arr0[0].toInt()].x, y = p[arr0[0].toInt()].y),
                            end = Offset(x = p[arr0[2].toInt()].x, y = p[arr0[2].toInt()].y),
                            strokeWidth = 3f,
                            color = Color.Yellow
                        )
                    }else if (ret == 1) {
                        drawLine(
                            start = Offset(x = p[arr0[0].toInt()].x, y = p[arr0[0].toInt()].y),
                            end = Offset(x = p[arr0[1].toInt()].x, y = p[arr0[1].toInt()].y),
                            strokeWidth = 3f,
                            color = Color.Yellow
                        )
                        val intersectionPoint = intersection_point(
                            p[arr0[0].toInt()].x,
                            p[arr0[0].toInt()].y,
                            p[arr0[2].toInt()].x,
                            p[arr0[2].toInt()].y,
                            p[arr[6].toInt()].x,
                            p[arr[6].toInt()].y,
                            p[arr[7].toInt()].x,
                            p[arr[7].toInt()].y,
                        )
                        if (intersectionPoint != null) {
                            drawLine(
                                start = Offset(x = p[arr0[0].toInt()].x, y = p[arr0[0].toInt()].y),
                                end = Offset(x = intersectionPoint.x, y = intersectionPoint.y),
                                strokeWidth = 3f,
                                color = Color.Yellow
                            )
                        }
                    }else if (ret == 3) {
                        drawLine(
                            start = Offset(x = p[arr0[0].toInt()].x, y = p[arr0[0].toInt()].y),
                            end = Offset(x = p[arr0[2].toInt()].x, y = p[arr0[2].toInt()].y),
                            strokeWidth = 3f,
                            color = Color.Yellow
                        )
                        val intersectionPoint = intersection_point(
                            p[arr0[0].toInt()].x,
                            p[arr0[0].toInt()].y,
                            p[arr0[1].toInt()].x,
                            p[arr0[1].toInt()].y,
                            p[arr[3].toInt()].x,
                            p[arr[3].toInt()].y,
                            p[arr[5].toInt()].x,
                            p[arr[5].toInt()].y,
                        )
                        if (intersectionPoint != null) {
                            drawLine(
                                start = Offset(x = p[arr0[0].toInt()].x, y = p[arr0[0].toInt()].y),
                                end = Offset(x = intersectionPoint.x, y = intersectionPoint.y),
                                strokeWidth = 3f,
                                color = Color.Yellow
                            )
                        }
                    }
                }
            }

            if (toDraw < -1) return@Canvas

            fun fuzzySearch(map: Map<String, String>, headTerm: String, searchTerm: String): Pair<String, String>? {
                return map.entries.find { it.value.startsWith(headTerm, ignoreCase = true) &&
                        it.key.endsWith(searchTerm, ignoreCase = true) }?.toPair()
            }

            // 扩展函数，将 Map.Entry 转换为 Pair
            fun <K, V> Map.Entry<K, V>.toPair(): Pair<K, V> {
                return Pair(this.key, this.value)
            }
            for (i in S8.indices) {
                val v = S8[i]
                for (j in v.indices) {
                    val face = v[j]
                    var triangle = face[0]
                    var tIndex = String.format("%02d,%02d,%02d", triangle[0], triangle[1], triangle[2])
                    if (isClockwise(triangle[0], triangle[1], triangle[2])) {
                        var value = String.format("%02d,%02d,%02d,%02d,%02d,%02d,%02d,%02d,%02d", i, j, 0, triangle[3], triangle[4], triangle[5], triangle[6], triangle[7], triangle[8])
                        map[tIndex] = value
                        triangle = face[1]
                        tIndex =
                            String.format("%02d,%02d,%02d", triangle[0], triangle[1], triangle[2])
                        value = String.format("%02d,%02d,%02d,%02d,%02d,%02d,%02d,%02d,%02d", i, j, 1, triangle[3], triangle[4], triangle[5], triangle[6], triangle[7], triangle[8])
                        map[tIndex] = value
                        triangle = face[2]
                        tIndex =
                            String.format("%02d,%02d,%02d", triangle[0], triangle[1], triangle[2])
                        value = String.format("%02d,%02d,%02d,%02d,%02d,%02d,%02d,%02d,%02d", i, j, 2, triangle[3], triangle[4], triangle[5], triangle[6], triangle[7], triangle[8])
                        map[tIndex] = value
                    }
                }
            }
            for (key in map.keys) {
                val arr0 = key.split(",")
                val arr = map[key]?.split(",")
                val i = arr?.get(0)?.toInt()
                val result = fuzzySearch(map, String.format("%02d", 1 - i!!), arr0[2] + "," + arr0[1])
                if (result != null) {
                    map[key] = map[key] + ",B"
                    map[result.first] = result.second + ",B"
                }
            }
            for (key in map.keys) {
                if (map[key]?.endsWith(",B")!!) {
                    val arr = key.split(",")
                    val p1 = arr[0].toInt()
                    val p2 = arr[1].toInt()
                    val p3 = arr[2].toInt()
                    drawTriangle(intArrayOf(p1, p2, p3))
                } else {
                    drawTriangle2(key)
                }
            }
        }
    }
}
