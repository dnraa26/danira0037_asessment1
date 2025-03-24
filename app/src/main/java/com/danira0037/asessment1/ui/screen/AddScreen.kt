package com.danira0037.asessment1.ui.screen

import android.content.res.Configuration
import android.icu.util.Calendar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.danira0037.asessment1.R
import com.danira0037.asessment1.model.Diary
import com.danira0037.asessment1.model.DiaryList
import com.danira0037.asessment1.ui.theme.Asessment1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = { Text("My Diary") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { innerPadding ->
        DiaryFormScreen(Modifier.padding(innerPadding).padding(16.dp), navController)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryFormScreen(modifier: Modifier, navController: NavController){
    var diaryTitle by rememberSaveable { mutableStateOf("") }
    var diaryTitleError by rememberSaveable { mutableStateOf(false) }

    var diaryContent by rememberSaveable { mutableStateOf("") }
    var diaryContentError  by rememberSaveable { mutableStateOf(false) }

    var diaryDate by rememberSaveable { mutableStateOf("00/00/0000") }
    var diaryDateError by rememberSaveable { mutableStateOf(false) }

    var diaryMood by rememberSaveable { mutableStateOf("Mood") }
    var diaryMoodError by rememberSaveable { mutableStateOf(false) }


    val datePickerState = rememberDatePickerState()
    val focusManager = LocalFocusManager.current

    var showMoodDropDown by rememberSaveable { mutableStateOf(false) }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.intro_diary_form),
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = diaryTitle,
            onValueChange = { title -> diaryTitle = title },
            label = { Text(text = stringResource(id = R.string.diary_title)) },
            singleLine = true,
            trailingIcon = { IconPicker(diaryTitleError) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        ErrorHint(diaryTitleError, "Judul tidak boleh kosong!")


        OutlinedTextField(
            value = diaryContent,
            onValueChange = { content -> diaryContent = content },
            label = {
                Text(
                    text = stringResource(id = R.string.diary_content)
                )
            },
            trailingIcon = { IconPicker(diaryContentError) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
            ),
            modifier = Modifier.fillMaxWidth().height(150.dp).padding(top = 12.dp)
        )
        ErrorHint(diaryContentError, "Konten tidak boleh kosong!")

        OutlinedTextField(
            value = diaryDate,
            onValueChange = { },
            readOnly = true,
            label = {
                Text(
                    text = stringResource(id = R.string.diary_date)
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        focusManager.clearFocus()
                        showDatePicker = true
                    }
                ) {
                   IconPicker(diaryDateError, {
                       Icon(
                           imageVector = Icons.Outlined.DateRange,
                           contentDescription = "Date",
                           tint = MaterialTheme.colorScheme.primary
                       )
                   })
                }
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = true){
                    focusManager.clearFocus()
                    showDatePicker = true
                }
        )
        ErrorHint(diaryDateError, "Pilih tanggal!")

        if(showDatePicker){
            DatePickerDialog(
                onDismissRequest = {
                    showDatePicker = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let {
                                millis -> val calendar = Calendar.getInstance().apply{
                                    timeInMillis = millis
                                }

                                diaryDate = "${calendar.get(Calendar.DAY_OF_MONTH)}/" +
                                            "${calendar.get(Calendar.MONTH) + 1}/" +
                                            "${calendar.get(Calendar.YEAR)}"
                            }

                            showDatePicker = false
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.diary_date_set_confirm)
                        )
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState
                )
            }
        }

        Box (
            modifier = Modifier.padding(16.dp)
        ){
            Button(
                onClick = {
                    showMoodDropDown = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = diaryMood
                )

                Icon(
                    imageVector = Icons.Outlined.ArrowDropDown,
                    contentDescription = "Mood",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }

            DropdownMenu(
                expanded = showMoodDropDown,
                onDismissRequest = { showMoodDropDown = false }
            ) {
                listOf("Senang", "Sedih", "Normal").forEach { mood ->
                    DropdownMenuItem(
                        text = { Text(text = mood) },
                        onClick = {
                            diaryMood = mood
                            showMoodDropDown = false
                        }
                    )
                }
            }
        }
        ErrorHint(diaryMoodError, "Pilih mood!")

        Button(
            onClick = {

                diaryTitleError = diaryTitle.isBlank()
                diaryContentError = diaryContent.isBlank()
                diaryDateError = diaryDate == "00/00/0000"
                diaryMoodError = diaryMood == "Mood"

                if (!diaryTitleError && !diaryContentError && !diaryDateError && !diaryMoodError) {
                    DiaryList.addToDiary(Diary(diaryTitle, diaryContent, diaryDate, diaryMood))
                    navController.popBackStack()
                }
            }
        ) {
            Text(
                text = stringResource(id = R.string.diary_create)
            )
        }


    }
}

@Composable
fun ErrorHint(isError: Boolean, message : String){
    if(isError) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun IconPicker(isError : Boolean, content : @Composable () -> Unit = {}){
    if(isError) {
        Icon(
            imageVector = Icons.Outlined.Warning,
            contentDescription = "Error",
            tint = MaterialTheme.colorScheme.error
        )
    }else{
        content()
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DiaryFromScreenPreview(){
    Asessment1Theme {
        AddScreen(rememberNavController())
    }
}