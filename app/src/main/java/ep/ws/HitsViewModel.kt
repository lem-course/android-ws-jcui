package ep.ws

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await


class HitsViewModel : ViewModel() {
    val hits: MutableState<List<Hit>> = mutableStateOf(emptyList())
    val error: MutableState<Boolean> = mutableStateOf(false)

    fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = OMDBService.instance.search(query).await()

                if (response.Response) {
                    hits.value = response.Search
                }
                error.value = response.Response
            } catch (_: Exception) {
                error.value = true
                hits.value = emptyList()
            }
        }
    }
}
