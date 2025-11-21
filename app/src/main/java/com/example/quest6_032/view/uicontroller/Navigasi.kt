package com.example.quest6_032.view.uicontroller

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quest6_032.model.DataJK
import com.example.quest6_032.view.FormIsian
import com.example.quest6_032.view.TampilData
import com.example.quest6_032.viewmodel.SiswaViewModel

enum class Navigasi {
    Formulir,
    Detail
}

@Composable
fun DataApp(
    modifier: Modifier = Modifier,
    viewModel: SiswaViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
){
    Scaffold(
        modifier = modifier
    ) { isiRuang ->

        val uiState by viewModel.statusUI.collectAsState()

        NavHost(
            navController = navController,
            startDestination = Navigasi.Formulir.name,
            modifier = Modifier.padding(isiRuang)
        ) {

            composable(route = Navigasi.Formulir.name) {
                val konteks = LocalContext.current
                FormIsian(
                    pilihanJK = DataJK.JenisK.map { id ->
                        konteks.resources.getString(id)
                    },
                    onSubmitButtonClicked = { data ->
                        viewModel.setDataSiswa(data)
                        navController.navigate(Navigasi.Detail.name)
                    }
                )
            }

            composable(route = Navigasi.Detail.name) {
                TampilData(
                    siswaUiState = uiState,
                    onBackBtnClick = {
                        cancelAndBackToFormulir(navController)
                    }
                )
            }
        }
    }
}

private fun cancelAndBackToFormulir(
    navController: NavHostController
) {
    navController.popBackStack(
        route = Navigasi.Formulir.name,
        inclusive = false
    )
}
