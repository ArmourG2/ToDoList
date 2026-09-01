package com.note.todolistv1

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun TaskItem(
    task: Task,
    onToggleComplete: () -> Unit,
    onDelete: () -> Unit,
    onSaveEdit: (String) -> Unit
) {
    // Local UI state for this item only.
    var isExpanded by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }

    // Temporary text used while editing.
    // remember(task.text) resets the draft if the real task text changes.
    var draftText by remember(task.text) { mutableStateOf(task.text) }

    TaskCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Top row: task text or edit field + checkbox
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = !(isExpanded && isEditing)) {
                        isExpanded = !isExpanded

                        // If the item is collapsed, stop editing
                        // and discard any unfinished edit text.
                        if (!isExpanded) {
                            isEditing = false
                            draftText = task.text
                        }
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isExpanded && isEditing) {
                    OutlinedTextField(
                        value = draftText,
                        onValueChange = { draftText = it },
                        modifier = Modifier.weight(1f),
                        label = { Text("Task description") },
                        singleLine = true
                    )
                } else {
                    Text(
                        text = task.text,
                        modifier = Modifier.weight(1f),
                        textDecoration = if (task.isCompleted) {
                            TextDecoration.LineThrough
                        } else {
                            TextDecoration.None
                        },
                        color = if (task.isCompleted) {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                    )
                }

                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = { onToggleComplete() }
                )
            }

            // Expanded controls
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isEditing) {
                        ElevatedButton(
                            onClick = {
                                draftText = task.text
                                isEditing = false
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.elevatedButtonColors(
                                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        ) {
                            Text("Cancel")
                        }

                        ElevatedButton(
                            onClick = {
                                onSaveEdit(draftText.trim())
                                isEditing = false
                            },
                            enabled = draftText.isNotBlank(),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Save")
                        }
                    } else {
                        ElevatedButton(
                            onClick = { isEditing = true },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Edit")
                        }

                        ElevatedButton(
                            onClick = onDelete,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer,
                                contentColor = MaterialTheme.colorScheme.onErrorContainer
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}