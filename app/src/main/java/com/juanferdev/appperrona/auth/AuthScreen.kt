package com.juanferdev.appperrona.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.auth.AuthNavDestinations.LOGIN_SCREEN_DESTINATION
import com.juanferdev.appperrona.auth.AuthNavDestinations.SIGN_UP_SCREEN_DESTINATION
import com.juanferdev.appperrona.composables.ErrorDialog
import com.juanferdev.appperrona.composables.LoadingWheel
import com.juanferdev.appperrona.models.User

@Composable
fun AuthScreen(
    onLoginButtonClick: ((String, String) -> Unit),
    onSignUpButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
    viewModel: AuthViewModel,
    onResetApiResponse: (() -> Unit),
    onUserLogin: (User) -> Unit
) {
    val navController = rememberNavController()
    AuthNavHost(
        navController,
        onLoginButtonClick,
        onSignUpButtonClick,
        viewModel = viewModel
    )

    when (val status = viewModel.status.value) {
        is ApiResponseStatus.Success -> {
            onUserLogin(status.data)
        }

        is ApiResponseStatus.Loading -> {
            LoadingWheel()
        }

        null -> {
            //Nothing happens
        }

        is ApiResponseStatus.Error -> {
            ErrorDialog(
                errorMessageId = status.messageId,
                onResetApiResponse
            )
        }
    }
}

@Composable
private fun AuthNavHost(
    navController: NavHostController,
    onLoginButtonClick: (String, String) -> Unit,
    onSignUpButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
    viewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = LOGIN_SCREEN_DESTINATION
    ) {
        composable(
            route = LOGIN_SCREEN_DESTINATION
        ) {
            LoginScreen(
                onClickRegister = { navController.navigate(route = SIGN_UP_SCREEN_DESTINATION) },
                onLoginButtonClick = onLoginButtonClick,
                viewModel = viewModel
            )
        }
        composable(
            route = SIGN_UP_SCREEN_DESTINATION
        ) {
            SignUpScreen(
                onBackClick = { navController.navigateUp() },
                onSignUpButtonClick = onSignUpButtonClick,
                viewModel = viewModel
            )
        }
    }
}