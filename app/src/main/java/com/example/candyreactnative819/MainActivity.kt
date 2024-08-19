package com.example.candyreactnative819

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CandyReactNative819Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GameBoard(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun GameBoard(
    modifier: Modifier = Modifier
) {
    val width = 8
    val candyColors = listOf("blue", "green", "orange", "red", "purple", "yellow")
    val currentColorArrangement by remember { mutableStateOf(createRandomColorArrangement(width * width, candyColors)) }
    val squareBeingDragged by remember { mutableStateOf<Int?>(null) }
    val squareBeingReplaced by remember { mutableStateOf<Int?>(null) }
    val scoreDisplay by remember { mutableStateOf(0) }

    Box(modifier = modifier.fillMaxSize()) {
        currentColorArrangement.forEachIndexed { index, color ->
            val isSquareBeingDragged = squareBeingDragged == index
            val isSquareBeingReplaced = squareBeingReplaced == index
            Candy(
                color = color,
                isSquareBeingDragged,
                isSquareBeingReplaced,
                modifier = Modifier
                    .size(70.dp)
                    .padding(3.dp)
                    .background(Color.White)
                    .draggable(
                        orientation = Orientation.Vertical,
                        onDragStarted = { onDragStart(index) },
                        onDragStopped = { onDragEnd() },
                        onDrag = { dragAmount ->  }
                    )
            )
        }
    }

    Text(text = "Score: $scoreDisplay", modifier = Modifier.align(Alignment.CenterHorizontally))
}

private fun Modifier.draggable(orientation: Orientation, onDragStarted: suspend CoroutineScope.(startedPosition: Offset) -> Unit, onDragStopped: suspend CoroutineScope.(velocity: Float) -> Unit, onDrag: Any): Modifier {

}

fun onDragEnd() {
    TODO("Not yet implemented")
}

fun onDragStart(index: Int) {
    TODO("Not yet implemented")
}

@Composable
fun Candy(
    color: String,
    isSquareBeingDragged: Boolean,
    isSquareBeingReplaced: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color.parseColor(color))
            .graphicsLayer {
                if (isSquareBeingDragged || isSquareBeingReplaced) {
                    elevation = 10.dp.toPx()
                    shadowElevation = 10.dp.toPx()
                }
            }
    )
}

fun createRandomColorArrangement(size: Int, candyColors: List<String>): List<String> {
    return (0 until size).map { candyColors.random() }
}

fun checkForColumnOfFour(currentColorArrangement: List<String>): Boolean {
    for (i in 0 until currentColorArrangement.size - 3) {
        if (i % 8 != 0) continue
        val columnOfFour = listOf(i, i + 8, i + 16, i + 24)
        val decidedColor = currentColorArrangement[i]
        if (columnOfFour.all { currentColorArrangement[it] == decidedColor }) {
            return true
        }
    }
    return false
}

fun checkForRowOfFour(currentColorArrangement: List<String>): Boolean {
    for (i in 0 until currentColorArrangement.size) {
        val rowOfFour = listOf(i, i + 1, i + 2, i + 3)
        val decidedColor = currentColorArrangement[i]
        if (rowOfFour.all { currentColorArrangement[it] == decidedColor }) {
            return true
        }
    }
    return false
}

fun checkForColumnOfThree(currentColorArrangement: List<String>): Boolean {
    for (i in 0 until currentColorArrangement.size - 2) {
        if (i % 8 != 0) continue
        val columnOfThree = listOf(i, i + 8, i + 16)
        val decidedColor = currentColorArrangement[i]
        if (columnOfThree.all { currentColorArrangement[it] == decidedColor }) {
            return true
        }
    }
    return false
}

fun checkForRowOfThree(currentColorArrangement: List<String>): Boolean {
    for (i in 0 until currentColorArrangement.size - 2) {
        val rowOfThree = listOf(i, i + 1, i + 2)
        val decidedColor = currentColorArrangement[i]
        if (rowOfThree.all { currentColorArrangement[it] == decidedColor }) {
            return true
        }
    }
    return false
}

@Composable
fun CandyReactNative819Theme(
    content: @Composable () -> Unit
) {
    ProvideTextStyle(CandyTextStyle) {
        content()
    }
}

@Composable
fun ProvideTextStyle(candyTextStyle: TextStyle, content: @Composable () -> Unit) {

}

private val CandyTextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp
)

@Preview(showBackground = true)
@Composable
fun GameBoardPreview() {
    CandyReactNative819Theme {
        GameBoard()
    }
}