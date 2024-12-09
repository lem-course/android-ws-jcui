package ep.ws

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchUI(HitsViewModel())
        }
    }
}

@Composable
fun SearchUI(viewModel: HitsViewModel) {
    val text = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            text.value,
            onValueChange = { text.value = it },
            label = { Text("Iskalna poizvedba") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            { viewModel.search(text.value) },
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(10)
        ) { Text("Išči (${viewModel.hits.value.size})") }
        LazyColumn {
            items(viewModel.hits.value) {
                Hit(it)
            }
        }
    }
}

@Composable
fun Hit(hit: Hit) {
    Row {
        Column(modifier = Modifier.weight(1f)) {
            AsyncImage(
                model = hit.Poster,
                contentDescription = null,
                modifier = Modifier.padding(5.dp)
            )
        }
        Column(modifier = Modifier.weight(4f)) {
            Text(
                hit.Title,
                fontSize = 20.sp
            )
            Text(
                "${hit.Type} (${hit.Year})",
                fontSize = 15.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchUIPreview() {
//    Hit(Hit())
    SearchUI(HitsViewModel())
}