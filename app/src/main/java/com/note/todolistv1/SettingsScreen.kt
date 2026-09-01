package com.note.todolistv1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    innerPadding: PaddingValues,
    onClearAllTasks: () -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(innerPadding), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(26.dp),
    ) {
        // Clear Data
        item {
            Text(
                text = "Danger Zone",
                style = MaterialTheme.typography.titleMedium
            )

            TaskCard {
                ElevatedButton(
                    onClick = onClearAllTasks,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Clear All Tasks")
                }
            }
        }

        // About Me
        item {
            Text(
                text = "About",
                style = MaterialTheme.typography.titleMedium
            )

            TaskCard {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("App: Simple To-Do List")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Version: 1.0")
                    Spacer(modifier = Modifier.height(4.dp))
                    // You can change this text later!
                    Text("Made by: Akmal Irfan")
                }
            }
        }
    }
}