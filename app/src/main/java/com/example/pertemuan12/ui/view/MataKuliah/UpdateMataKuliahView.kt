package com.example.pertemuan12.ui.view.MataKuliah

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan12.entity.Dosen
import com.example.pertemuan12.ui.costumwidget.CustomTopAppBar
import com.example.pertemuan12.ui.viewmodel.PenyediaViewModelProdiTI
import com.example.pertemuan12.ui.viewmodel.UpdateMataKuliahViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UpdateMataKuliahView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,

    viewModel: UpdateMataKuliahViewModel = viewModel(factory = PenyediaViewModelProdiTI.Factory) // Initialize ViewModel
) {
    val uiStateMataKuliah = viewModel.updateUiStateMataKuliah // Get UI state from ViewModel
    val snackbarHostState = remember { SnackbarHostState() } // Snackbar state
    val coroutineScope = rememberCoroutineScope()

    // Observe changes in snackbarMessage
    LaunchedEffect(uiStateMataKuliah.snackBarMessageMataKuliah) {
        uiStateMataKuliah.snackBarMessageMataKuliah?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
                viewModel.resetSnackBarMessage() // Reset the snackbar message in ViewModel after it's shown
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, // Place snackbar in scaffold
        topBar = {
            CustomTopAppBar(
                judul = "Edit Mata Kuliah", // Title for Update page
                showBackButton = true,
                onBack = onBack // Back button functionality
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Form for Mata Kuliah update
            InsertBodyMataKuliah(
                uiState = uiStateMataKuliah,
                onValueChange = { updateEvent ->
                    viewModel.updateStateMataKuliah(updateEvent) // Update state in ViewModel
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateField()) { // Validate before updating
                            viewModel.updateDataMataKuliah() // Perform the update operation
                            delay(600) // Optional delay for UI effect
                            withContext(Dispatchers.Main) {
                                onNavigate() // Navigate after data is updated (main thread)
                            }
                        }
                    }
                },
                dosenList = uiStateMataKuliah.dosenList// Pass the list of dosen to the form
            )
        }
    }
}
