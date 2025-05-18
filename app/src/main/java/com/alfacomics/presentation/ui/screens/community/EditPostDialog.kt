package com.alfacomics.presentation.ui.screens.community

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.alfacomics.data.repository.CommunityPost
import com.alfacomics.data.repository.DummyData
import com.alfacomics.data.repository.Poll
import com.alfacomics.data.repository.PollOption

@SuppressLint("MutableCollectionMutableState")
@Composable
fun EditPostDialog(
    post: CommunityPost,
    onDismiss: () -> Unit,
    onSave: (CommunityPost) -> Unit
) {
    var editedContent by remember { mutableStateOf(TextFieldValue(post.content)) }
    var editedPollQuestion by remember { mutableStateOf(post.poll?.question ?: "") }
    var editedPollOptions by remember { mutableStateOf(post.poll?.options?.map { option -> option.text }?.toMutableList() ?: mutableListOf<String>()) }

    AlertDialog(
        onDismissRequest = {
            onDismiss()
            editedContent = TextFieldValue(post.content)
            editedPollQuestion = post.poll?.question ?: ""
            editedPollOptions = post.poll?.options?.map { option -> option.text }?.toMutableList() ?: mutableListOf()
        },
        title = { Text("Edit Post", color = Color.White) },
        text = {
            Column {
                OutlinedTextField(
                    value = editedContent,
                    onValueChange = { editedContent = it },
                    label = { Text("Post Content", color = Color.White) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFFBB86FC),
                        unfocusedBorderColor = Color.Gray,
                        focusedContainerColor = Color(0xFF1E1E1E),
                        unfocusedContainerColor = Color(0xFF1E1E1E)
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Edit Poll if exists
                if (post.poll != null) {
                    OutlinedTextField(
                        value = editedPollQuestion,
                        onValueChange = { editedPollQuestion = it },
                        label = { Text("Poll Question", color = Color.White) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFBB86FC),
                            unfocusedBorderColor = Color.Gray,
                            focusedContainerColor = Color(0xFF1E1E1E),
                            unfocusedContainerColor = Color(0xFF1E1E1E)
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    editedPollOptions.forEachIndexed { index, option ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = option,
                                onValueChange = { newValue ->
                                    val updatedOptions = editedPollOptions.toMutableList()
                                    updatedOptions[index] = newValue
                                    editedPollOptions = updatedOptions
                                },
                                label = { Text("Option ${index + 1}", color = Color.White) },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(vertical = 4.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = Color(0xFFBB86FC),
                                    unfocusedBorderColor = Color.Gray,
                                    focusedContainerColor = Color(0xFF1E1E1E),
                                    unfocusedContainerColor = Color(0xFF1E1E1E)
                                )
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Update the post in DummyData
                    val updatedPost = post.copy(
                        content = editedContent.text,
                        poll = if (post.poll != null && editedPollOptions.isNotEmpty()) {
                            Poll(
                                question = editedPollQuestion,
                                options = editedPollOptions.map { PollOption(it, 0) } // Reset votes on edit
                            )
                        } else {
                            post.poll
                        }
                    )
                    onSave(updatedPost)
                    // Reset votes for the edited poll
                    if (post.poll != null) {
                        DummyData.setPollVotes(post.id, MutableList(editedPollOptions.size) { 0 })
                        DummyData.setVotedOptionIndex(post.id, -1)
                    }
                    onDismiss()
                },
                enabled = editedContent.text.isNotBlank() && (post.poll == null || (editedPollQuestion.isNotBlank() && editedPollOptions.all { option -> option.isNotBlank() })),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFBB86FC),
                    contentColor = Color.White
                )
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                    editedContent = TextFieldValue(post.content)
                    editedPollQuestion = post.poll?.question ?: ""
                    editedPollOptions = post.poll?.options?.map { option -> option.text }?.toMutableList() ?: mutableListOf()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White
                )
            ) {
                Text("Cancel")
            }
        },
        containerColor = Color(0xFF1E1E1E)
    )
}