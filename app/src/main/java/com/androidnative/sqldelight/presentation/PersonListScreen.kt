package com.androidnative.sqldelight.presentation

import android.widget.Space
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.androidnative.sqldelight.ui.PersonListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.androidnative.sqldelight.common.WindowInfo
import com.androidnative.sqldelight.common.rememberWindowInfo
import com.androidnative.sqldelight.common.spacing

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PersonListScreen(viewModel: PersonListViewModel = viewModel()) {
    val persons = viewModel.persons.collectAsState(initial = null)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium)
    ) {
        val windowInfo = rememberWindowInfo()
        if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    persons.value?.let {
                        items(items=it,key={it.firstName+it.lastName}) { person ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colors.primary)
                                    .clickable { viewModel.getPersonById(person.id) }
                                    .shadow(3.dp, CircleShape)
                                    .padding(MaterialTheme.spacing.medium)
                                    .animateItemPlacement(animationSpec = tween(durationMillis = 1000)),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = person.firstName, style = MaterialTheme.typography.h6,color = Color.White, overflow = TextOverflow.Ellipsis, maxLines = 1)
                                IconButton(
                                    onClick = {
                                        viewModel.onActionDeletePerson(person.id)
                                    },
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Delete,
                                        contentDescription = "Delete",
                                        tint = Color.Red
                                    )
                                }
                            }
                            Divider(color = MaterialTheme.colors.primary, thickness = 1.dp,modifier = Modifier.padding(vertical = 4.dp))
                        }
                    }
                }
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter), verticalAlignment = Alignment.CenterVertically) {
                TextField(value = viewModel.firstNameText, onValueChange = viewModel::onActionFirstNameChange, placeholder = {Text(text = "First Name")},modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                TextField(value = viewModel.lastNameText, onValueChange = viewModel::onActionLastNameChange, placeholder = {Text(text = "Last Name")},modifier = Modifier.weight(1f))
                IconButton(
                    onClick = viewModel::onActionInsertPerson,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CheckCircle,
                        contentDescription = "Insert person",
                        tint = Color.Green
                    )
                }
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    persons.value?.let {
                        items(it) { person ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.getPersonById(person.id) },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = person.firstName,
                                    style = MaterialTheme.typography.h6,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                                IconButton(
                                    onClick = {
                                        viewModel.onActionDeletePerson(person.id)
                                    },
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Delete,
                                        contentDescription = "Delete",
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter), verticalAlignment = Alignment.CenterVertically) {
                TextField(value = viewModel.firstNameText, onValueChange = viewModel::onActionFirstNameChange, placeholder = {Text(text = "First Name")},modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                TextField(value = viewModel.lastNameText, onValueChange = viewModel::onActionLastNameChange, placeholder = {Text(text = "Last Name")},modifier = Modifier.weight(1f))
                IconButton(
                    onClick = viewModel::onActionInsertPerson,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CheckCircle,
                        contentDescription = "Insert person",
                        tint = Color.Green
                    )
                }
            }
        }
        if (persons.value == null) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .align(Alignment.Center),
                MaterialTheme.colors.onBackground, 3.dp
            )
        }
    }
    viewModel.personDetail?.let {
        Dialog(onDismissRequest = viewModel::onPersonDetailDialogDismiss) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(3.dp, CircleShape)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.primary)
                    .padding(MaterialTheme.spacing.medium),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "${it.firstName} ${it.lastName}",color = Color.White)
            }
        }
    }
}