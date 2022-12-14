package com.example.chat.navigations

sealed class Screen(val route: String) {
    object LoginScreen: Screen("login_screen")
    object SignUpScreen: Screen("sign_up_screen")
    object ForgotPasswordScreen: Screen("forgot_password_screen")
    object MainContentScreen: Screen("main_content")
}


