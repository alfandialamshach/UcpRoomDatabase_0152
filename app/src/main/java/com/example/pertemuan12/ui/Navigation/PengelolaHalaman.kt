package com.example.pertemuan12.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pertemuan12.ui.home.DestinasiHome
import com.example.pertemuan12.ui.home.HomeView
import com.example.pertemuan12.ui.view.Dosen.DestinasiTambahDosen
import com.example.pertemuan12.ui.view.Dosen.HomeDosenView
import com.example.pertemuan12.ui.view.Dosen.TambahDosenView

import com.example.pertemuan12.ui.view.MataKuliah.DestinasiTambah
import com.example.pertemuan12.ui.view.MataKuliah.DetailMataKuliahView
import com.example.pertemuan12.ui.view.MataKuliah.HomeMataKuliahView
import com.example.pertemuan12.ui.view.MataKuliah.InsertMataKuliahView
import com.example.pertemuan12.ui.view.MataKuliah.UpdateMataKuliahView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = DestinasiHome.route) {
        // Halaman Home
        composable(route = DestinasiHome.route) {
            HomeView(
                onClickDosen = { navController.navigate(DestinasiHomeDosen.route) },
                onClickMataKuliah = { navController.navigate(DestinasiHomeMataKuliah.route) }
            )
        }

        // Halaman Mata Kuliah
        composable(route = DestinasiHomeMataKuliah.route) {
            HomeMataKuliahView(
                onDetailClick = { kode ->
                    navController.navigate("${DestinasiDetailMatakuliah.route}/$kode")
                },
                onAddMhs = {
                    navController.navigate(DestinasiTambah.route)
                },
                onBack = { navController.popBackStack() },
                modifier = modifier
            )
        }

        composable(route = DestinasiTambah.route) {
            InsertMataKuliahView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
                modifier = modifier
            )
        }

        composable(
            DestinasiDetailMatakuliah.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailMatakuliah.KODE){
                    type = NavType.StringType
                }
            )
        ) {
            val kode = it.arguments?.getString(DestinasiDetailMatakuliah.KODE)
            kode.let {kode ->
                DetailMataKuliahView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateMataKuliah.route}/$it")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        // Halaman Dosen
        composable(route = DestinasiHomeDosen.route) {
            HomeDosenView(
                onAddMhs = {
                    navController.navigate(DestinasiTambahDosen.route)
                },
                onBack = { navController.popBackStack()},
                    modifier = modifier
            )
        }

        composable(route = DestinasiTambahDosen.route) {
            TambahDosenView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
                modifier = modifier
            )
        }

        composable(
            DestinasiUpdateMataKuliah.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateMataKuliah.KODE) {
                    type = NavType.StringType })
                )
        {

            UpdateMataKuliahView(
                onBack = {
                    navController.popBackStack() },
                onNavigate = {
                    navController.popBackStack() },
                modifier = modifier
            )
        }


    }
}
