package com.example.jctest

import android.net.http.HttpException
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.jctest.api.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var id by remember {
                mutableStateOf(0)
            }

            var userId by remember {
                mutableStateOf(0)
            }

            var title by remember {
                mutableStateOf("")
            }

            var body by remember {
                mutableStateOf("")
            }

            var responseCode by remember {
                mutableStateOf(0)
            }


            val scope = rememberCoroutineScope()

            LaunchedEffect(key1 = true) {
                scope.launch(Dispatchers.IO) {
                    val response = try {

                        // 1)
//                        val user1 = User("new body", null, "new Tile", 33)
//                        RetrofitInstance.api.createUrlUser(user1)

                        // 2)
                        RetrofitInstance.api.createUrlUser(11, "url title", "url body")

                    } catch (e: IOException) {
                        Log.e("error", "I/O Error ${e.message}")
                        return@launch

                    } catch (e: HttpException) {
                        Log.e("error", "Http Error ${e.message}")
                        return@launch
                    }
                    if (response.isSuccessful && response.body() != null) {
                        withContext(Dispatchers.Main) {

                            id = response.body()!!.id!!
                            body = response.body()!!.body
                            userId = response.body()!!.userId
                            title = response.body()!!.title
                            responseCode = response.code()

                        }
                    }
                }
            }
            UserUI(
                id = id,
                userId = userId,
                title = title,
                body = body,
                responseCode = responseCode
            )

        }
    }
}


@Composable
fun UserUI(
    modifier: Modifier = Modifier,
    id: Int,
    userId: Int,
    title: String,
    body: String,
    responseCode: Int
) {
    Card(
        modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier
                .wrapContentSize()
                .padding(10.dp),
        ) {

            Text(text = "User: $userId -> Id: $id", fontWeight = FontWeight.Bold)
            Spacer(modifier = modifier.height(10.dp))
            Text(text = "Title:", fontWeight = FontWeight.Bold)
            Text(text = title)
            Text(text = "Body:", fontWeight = FontWeight.Bold)
            Text(text = body, maxLines = 3, overflow = TextOverflow.Ellipsis)
            Text(text = "Response Code: $responseCode", fontWeight = FontWeight.Bold)

        }
    }

}


