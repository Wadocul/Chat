package com.example.chat.navigations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Chat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chat.screens.login.LoginViewModel
import com.example.chat.screens.main.chat.ChatScreen
import com.example.chat.screens.main.profile.ChangeProfileStyleScreen
import com.example.chat.screens.main.profile.ChangeProfilesDataScreen
import com.example.chat.screens.main.profile.ProfileContentScreen
import com.example.chat.screens.main.profile.ProfileScreen
import com.example.chat.ui.theme.Purple40
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch


typealias ComposableFun = @Composable ()->Unit

class TabItem(val title:String, val icons: ImageVector, val screens:ComposableFun) {

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainContent() {

    val pagerState = rememberPagerState()
    val navController = rememberNavController()

    val tabs = listOf(
        TabItem(title = "Chat",icons= Icons.Default.Chat, screens = { ChatScreen(navController) }),
        TabItem(title = "Profile",icons= Icons.Default.AccountCircle,
            screens = { ProfileScreen(uid = LoginViewModel.uid ?: "", navController) }))

    NavHost(navController = navController, startDestination = MainScreens.Main.route) {
        composable(route = MainScreens.Main.route) {
            Column(modifier = Modifier.fillMaxSize()) {
                Tabs(tabs = tabs, pagerState = pagerState)
                TabContent(tabs = tabs, pagerState = pagerState)
            }
        }

        composable(
            route = MainScreens.ProfileContent.route + "/{uid}",
            arguments = listOf(
                navArgument("uid") {
                    type = NavType.StringType
                }
            )
            ) { entry ->
                ProfileContentScreen(uid = entry.arguments?.getString("uid") ?: "")
        }

        composable(
            route = MainScreens.ProfileChangeData.route + "/{uid}",
            arguments = listOf(
                navArgument("uid") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            ChangeProfilesDataScreen(uid = entry.arguments?.getString("uid") ?: "", navController)
        }

        composable(
            route = MainScreens.ProfileChangeStyle.route + "/{uid}",
            arguments = listOf(
                navArgument("uid") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            ChangeProfileStyleScreen(uid = entry.arguments?.getString("uid") ?: "", navController)
        }
    }

}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<TabItem>, pagerState: PagerState) {

    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Purple40,
        indicator = { tabPositions ->
            Modifier.pagerTabIndicatorOffset(pagerState = pagerState, tabPositions = tabPositions)
        }) {
        tabs.forEachIndexed { index, tabItem ->

            LeadingIconTab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = { Text(tabItem.title) },
                icon = { Icon(imageVector = tabItem.icons, contentDescription = null) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Gray,
                enabled = true
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabContent(tabs:List<TabItem>,pagerState: PagerState) {
    HorizontalPager(count = tabs.size,state = pagerState) { page ->
        tabs[page].screens()
    }
}
