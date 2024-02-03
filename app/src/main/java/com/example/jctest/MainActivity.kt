package com.example.jctest

 import android.net.http.HttpException
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jctest.api.RetrofitInstance
import com.example.jctest.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var usersList by remember {
                mutableStateOf(listOf<User>())
            }
            val scope = rememberCoroutineScope()

            LaunchedEffect(key1 = true) {
                scope.launch(Dispatchers.IO) {
                    val response = try {
                        RetrofitInstance.api.getDataByUserId(userId = 8, sort = "id", order = "desc")

                    } catch (e: IOException) {
                        Log.e("error", "I/O Error ${e.message}")
                        return@launch

                    } catch (e: HttpException) {
                        Log.e("error", "Http Error ${e.message}")
                        return@launch
                    }
                    if (response.isSuccessful && response.body() != null) {
                        withContext(Dispatchers.Main) {
                            usersList = response.body()!!
                        }
                    }
                }
            }

            LazyColumnUser(userList = usersList)


        }
    }
}

@Composable
fun LazyColumnUser(userList: List<User>){

    Text(
        modifier = Modifier.fillMaxWidth()
            .background(Color.Yellow)
            .padding(bottom = 10.dp),
        text = "Retrofit",
        textAlign = TextAlign.Center,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold)

    LazyColumn(
        modifier = Modifier.padding(top = 40.dp)
    ){
        items(userList.size){
            UserUI(userList = userList, itemIndex = it)
        }
    }

}

@Composable
fun UserUI(modifier: Modifier = Modifier, userList: List<User>, itemIndex: Int) {
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
            val currentUser = userList[itemIndex]

            Text(text = "User: ${currentUser.userId} -> Id: ${currentUser.id}", fontWeight = FontWeight.Bold,)
            Spacer(modifier = modifier.height(10.dp))
            Text(text = "Title:", fontWeight = FontWeight.Bold)
            Text(text = currentUser.title)
            Text(text = "Body:", fontWeight = FontWeight.Bold)
            Text(text = currentUser.body, maxLines = 3, overflow = TextOverflow.Ellipsis)

        }
    }

}


