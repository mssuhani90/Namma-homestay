package com.example.nammahomestay

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.rememberUpdatedState
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.nammahomestay.ui.HomeUiState
import com.example.nammahomestay.ui.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigate: (String) -> Unit = {}) {
    val vm: HomeViewModel = viewModel()
    val state by vm.state.collectAsStateWithLifecycle()

    var images by remember { mutableStateOf<List<Uri>>(emptyList()) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                images = images + it
            }
        }
    )

    var menuTextDraft by remember { mutableStateOf("") }

    LaunchedEffect(state) {
        if (state is HomeUiState.Loaded) {
            menuTextDraft = (state as HomeUiState.Loaded).menuText
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Namma HomeStay") })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Welcome to Your Homestay Dashboard",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { onNavigate("profile") }) {
                            Text("Edit Profile")
                        }
                    }
                }
            }

            item {
                Card {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Upload Room Images", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                imagePickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }
                        ) {
                            Text("Pick Images")
                        }

                        if (images.isNotEmpty()) {
                            LazyColumn {
                                items(images) { uri ->
                                    AsyncImage(
                                        model = uri,
                                        contentDescription = "Room image",
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Card {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Daily Menu", style = MaterialTheme.typography.titleMedium)

                        if (state is HomeUiState.Loading) {
                            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        if (state is HomeUiState.Error) {
                            val msg = (state as HomeUiState.Error).message
                            Text(msg, color = MaterialTheme.colorScheme.error)
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        OutlinedTextField(
                            value = menuTextDraft,
                            onValueChange = { menuTextDraft = it },
                            label = { Text("Today's Menu") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Button(
                            onClick = { vm.saveMenu(menuTextDraft) },
                            enabled = state !is HomeUiState.Loading
                        ) {
                            Text("Update Menu")
                        }
                    }
                }
            }

            item {
                Card {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Inquiries & Calls", style = MaterialTheme.typography.titleMedium)
                        Button(onClick = { onNavigate("inquiries") }) {
                            Text("View Inquiries")
                        }
                    }
                }
            }

            item {
                Card {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Local Guide", style = MaterialTheme.typography.titleMedium)
                        Button(onClick = { onNavigate("guide") }) {
                            Text("Add Attractions")
                        }
                    }
                }
            }
        }
    }
}





