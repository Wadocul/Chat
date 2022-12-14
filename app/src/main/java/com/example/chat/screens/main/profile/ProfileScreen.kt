package com.example.chat.screens.main.profile

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chat.getBitmap
import com.example.chat.navigations.MainScreens
import com.example.chat.ui.theme.Purple40
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(uid: String, navController: NavController) {
    val scope = rememberCoroutineScope()

    val activity = (LocalContext.current as? Activity)

    Column(Modifier
        .fillMaxSize()
        .padding(5.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween) {

        Row() {
            ProfileContentScreen(uid = uid)
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = {
                scope.launch {
                    navController.navigate(MainScreens.ProfileChangeData.withUid(uid))
                }
            }, content = { Text(text = "Change info") },
            shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Purple40, contentColor = Color.White))

            ClickableText(text = AnnotatedString("Sign out"),
                onClick = {
                    Firebase.auth.signOut()
                    activity?.finish()
            },
            modifier = Modifier.align(Alignment.CenterVertically),
            style = TextStyle(color = Color.Blue,
                textDecoration = TextDecoration.Underline))

            Button(onClick = {
                scope.launch {
                    navController.navigate(MainScreens.ProfileChangeStyle.withUid(uid))
                }
            }, content = { Text(text = "Change style") },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Purple40, contentColor = Color.White))
        }
    }
}

@Composable
fun ProfileContentScreen(uid: String) {
    val profileViewModel = hiltViewModel<ProfileViewModel>()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = uid) {
        scope.launch {
            profileViewModel.loadProfile(uid)
        }
    }

    val profileData by profileViewModel.profileData

    val image = getBitmap(profileData.imageStr)


    Column(Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(50.dp))

        Row() {
            Image(bitmap = image.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .border(2.dp, Color.Gray, CircleShape)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row() {
            TextField(value = profileData.username ?: "", onValueChange = { }, readOnly = true,
                textStyle = TextStyle(fontSize = 17.sp),
                label = {Text(text = "Username", style = TextStyle(fontSize = 15.sp))},
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color(0xFFF0EDED)))
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row() {
            TextField(value = profileData.firstName ?: "", onValueChange = { }, readOnly = true,
                textStyle = TextStyle(fontSize = 17.sp),
                label = {Text(text = "First name", style = TextStyle(fontSize = 15.sp))},
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color(0xFFF0EDED))
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row() {
            TextField(value = profileData.lastName ?: "", onValueChange = { }, readOnly = true,
                textStyle = TextStyle(fontSize = 17.sp),
                label = {Text(text = "Last name", style = TextStyle(fontSize = 15.sp))},
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color(0xFFF0EDED))
            )
        }
    }
}