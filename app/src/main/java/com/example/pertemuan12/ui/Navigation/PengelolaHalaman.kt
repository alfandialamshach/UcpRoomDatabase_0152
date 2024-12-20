package com.example.pertemuan12.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pertemuan12.ui.home.DestinasiHome
import com.example.pertemuan12.ui.home.HomeView
import com.example.pertemuan12.ui.view.Dosen.DestinasiTambahDosen
import com.example.pertemuan12.ui.view.Dosen.HomeDosenView
import com.example.pertemuan12.ui.view.Dosen.TambahDosenView

import com.example.pertemuan12.ui.view.MataKuliah.DestinasiTambah
import com.example.pertemuan12.ui.view.MataKuliah.HomeMataKuliahView
import com.example.pertemuan12.ui.view.MataKuliah.InsertMataKuliahView

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
                    navController.navigate("${DestinasiTambah.route}/$kode")
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
    }
}
