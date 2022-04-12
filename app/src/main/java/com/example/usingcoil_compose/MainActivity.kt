package com.example.usingcoil_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.usingcoil_compose.ui.theme.UsingCoilComposeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UsingCoilComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ScrollingList()
                }
            }
        }
    }
}

@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberImagePainter(
                data = "https://w1.pngwing.com/pngs/31/647/png-transparent-sheep-pygmy-goat-boer-goat-anglonubian-goat-cattle-feral-goat-goats-live.png"
            ),
            contentDescription = "Goat",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "Item $index", style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun ScrollingList(){
    val listSize = 100
    //save scrolling position with this state

    /**
     * To avoid blocking the list rendering while we scroll,
     * the scrolling APIs are suspend functions.
     * Therefore, we'll need to call them in a coroutine.
     * To do so, we can create a CoroutineScope using the
     * rememberCoroutineScope function to create coroutines from
     * the button event handlers.
     */
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Row {
        Button(onClick = {
            coroutineScope.launch {
                //first item on the list
                scrollState.animateScrollToItem(0)
            }
        }) {
            Text(text = "Back to top" )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Button(onClick = {
            coroutineScope.launch {
                //last item on the list
                scrollState.animateScrollToItem(listSize -1)
            }
        }) {
            Text(text = "Scroll to bottom")
        }
    }

    LazyColumn(state = scrollState) {
        items(100) {
            ImageListItem(it)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    UsingCoilComposeTheme {
ScrollingList()
    }
}