package com.note.todolistv1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.note.todolistv1.ui.theme.ToDoListTheme


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoListTheme {

                var tasks by remember { mutableStateOf(listOf<Task>()) }
                var nextId by remember { mutableIntStateOf(1) }
                var showDialog by remember { mutableStateOf(false) }
                var showSettings by remember { mutableStateOf(false) }

                Scaffold(
                    topBar = {
                        if (showSettings) {
                            // --- Settings Screen Top Bar ---
                            TopAppBar(
                                title = { Text("Settings") },
                                navigationIcon = {
                                    IconButton(onClick = { showSettings = false }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Back"
                                        )
                                    }
                                }
                            )
                        } else {
                            // --- Main Screen Top Bar ---
                            TopAppBar(
                                title = { Text("My To-Do List") },
                                actions = {
                                    IconButton(onClick = { showSettings = true }) {
                                        Icon(
                                            imageVector = Icons.Default.Settings,
                                            contentDescription = "Settings"
                                        )
                                    }
                                }
                            )
                        }
                    },
                    floatingActionButton = {

                        if (!showSettings) {
                            FloatingActionButton(
                                onClick = { showDialog = true }
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Add Task")
                            }
                        }
                    }) { innerPadding ->
                    if (showSettings) {
                        // Show the Settings Screen
                        SettingsScreen(
                            innerPadding = innerPadding,
                            onClearAllTasks = { tasks = emptyList() }
                        )
                    } else {
                        if (showDialog) {
                            AddTaskDialog(
                                onDismiss = { showDialog = false },
                                onConfirm = { newTaskText ->
                                    val newTask = Task(
                                        id = nextId,
                                        text = newTaskText,
                                        isCompleted = false
                                    )
                                    tasks = tasks + newTask
                                    nextId++
                                    showDialog = false
                                }
                            )
                        }
                        if (tasks.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No tasks yet.\nTap the + button to add your first task.",
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                                    .padding(horizontal = 16.dp)
                            ) {
                                items(
                                    items = tasks,
                                    key = { task -> task.id }
                                ) { task ->
                                    TaskItem(
                                        task = task,
                                        onToggleComplete = {
                                            tasks = tasks.map { currentTask ->
                                                if (currentTask.id == task.id) {
                                                    currentTask.copy(isCompleted = !currentTask.isCompleted)
                                                } else {
                                                    currentTask
                                                }
                                            }
                                        },
                                        onDelete = {
                                            tasks = tasks.filter { currentTask ->
                                                currentTask.id != task.id
                                            }
                                        },
                                        onSaveEdit = { newText ->
                                            if (newText.isNotBlank()) {
                                                tasks = tasks.map { currentTask ->
                                                    if (currentTask.id == task.id) {
                                                        currentTask.copy(text = newText)
                                                    } else {
                                                        currentTask
                                                    }
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}