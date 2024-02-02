package com.example.jctest

import android.annotation.SuppressLint
import android.net.http.HttpException
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jctest.api.RetrofitInstance
import com.example.jctest.models.User
import com.example.jctest.ui.theme.JCTestTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var body by remember {
                mutableStateOf("Loading...")
            }
            var id by remember {
                mutableStateOf(0)
            }
            var title by remember {
                mutableStateOf("Loading...")
            }
            var userId by remember {
                mutableStateOf(0)
            }
            
            val scope = rememberCoroutineScope()

            LaunchedEffect(key1 = true) {
                scope.launch(Dispatchers.IO) {
                    val response = try {
                        RetrofitInstance.api.getUserById(5)

                    } catch (e: IOException) {
                        Log.e("error", "I/O Error ${e.message}")
                        return@launch

                    } catch (e: HttpException) {
                        Log.e("error", "Http Error ${e.message}")
                        return@launch
                    }
                    if (response.isSuccessful && response.body() != null) {
                        withContext(Dispatchers.IO) {

                            body = response.body()!!.body
                            title = response.body()!!.title
                            userId = response.body()!!.userId
                            id = response.body()!!.id

                        }
                    }
                }
            }

            UserUI(id = id, userId = userId, title = title, body = body)


        }
    }
}

@Composable
fun UserUI(modifier: Modifier = Modifier, id: Int, userId: Int, title: String, body: String) {


    Card(
        modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier
                .wrapContentSize()
                .padding(10.dp),
        ) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = modifier.height(10.dp))
            Text(text = body)
            Text(text = "User: $userId\nId: $id")
        }
    }

}


