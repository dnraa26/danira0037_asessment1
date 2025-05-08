package com.danira0037.asessment1.ui.screen

import android.content.Context
import android.content.res.Configuration
import android.icu.util.Calendar
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.danira0037.asessment1.R
import com.danira0037.asessment1.model.DetailViewModel
import com.danira0037.asessment1.ui.theme.Asessment1Theme
import com.danira0037.asessment1.util.ViewModelFactory

const val KEY_ID_DIARY = "idDiary"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navController: NavController, id : Long? = null) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                title = { Text(text = stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    if(id != null){
                        DeleteAction {
                            showDialog = true
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        DiaryFormScreen(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            navController = navController,
            id = id,
            context = context,
            viewModel = viewModel
        )

        if(id != null && showDialog){
            DisplayAlertDialog(
                onDismissRequest = { showDialog = false }
            ){
                showDialog = false
                viewModel.delete(id)
                navController.popBackStack()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryFormScreen(modifier: Modifier, navController: NavController, id : Long? = null, context: Context, viewModel: DetailViewModel) {
    val moodText = stringResource(R.string.diary_mood)

    var diaryTitle by rememberSaveable { mutableStateOf("") }
    var diaryContent by rememberSaveable { mutableStateOf("") }
    var diaryDate by rememberSaveable { mutableStateOf("00/00/0000") }
    var diaryMood by rememberSaveable { mutableStateOf(moodText) }

    var diaryTitleError by rememberSaveable { mutableStateOf(false) }
    var diaryContentError by rememberSaveable { mutableStateOf(false) }
    var diaryDateError by rememberSaveable { mutableStateOf(false) }
    var diaryMoodError by rememberSaveable { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()
    val focusManager = LocalFocusManager.current

    var showMoodDropDown by rememberSaveable { mutableStateOf(false) }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if(id == null) return@LaunchedEffect
        val data = viewModel.getDiary(id) ?: return@LaunchedEffect
        diaryTitle = data.judul
        diaryContent = data.isi
        diaryDate = data.tanggal
        diaryMood = data.mood
    }

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.magic),
            contentDescription = "Sweeping dust",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(125.dp).padding(top = 20.dp, bottom = 10.dp)
        )

        Text(
            text =  if(id == null ) stringResource(id = R.string.diary_create) else stringResource(id = R.string.diary_edit),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(id = R.string.intro_diary_form),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        OutlinedTextField(
            value = diaryTitle,
            onValueChange = {
                diaryTitle = it
            },
            label = { Text(stringResource(id = R.string.diary_title)) },
            isError = diaryTitleError,
            trailingIcon = { IconPicker(diaryTitleError) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            ),
            modifier = Modifier.fillMaxWidth()
        )
        ErrorHint(diaryTitleError, stringResource(id = R.string.diary_title_error))

        OutlinedTextField(
            value = diaryContent,
            onValueChange = {
                diaryContent = it
            },
            label = { Text(stringResource(id = R.string.diary_content)) },
            isError = diaryContentError,
            trailingIcon = { IconPicker(diaryContentError) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        ErrorHint(diaryContentError, stringResource(id = R.string.diary_content_error))

        OutlinedTextField(
            value = diaryDate,
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(id = R.string.diary_date)) },
            isError = diaryDateError,
            trailingIcon = {
                IconButton(
                    onClick = {
                        focusManager.clearFocus()
                        showDatePicker = true
                    }
                ) {
                    IconPicker(diaryDateError) {
                        Icon(
                            imageVector = Icons.Outlined.DateRange,
                            contentDescription = stringResource(id = R.string.diary_date),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    focusManager.clearFocus()
                    showDatePicker = true
                }
        )
        ErrorHint(diaryDateError, stringResource(id = R.string.diary_date_error))

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val calendar = Calendar.getInstance().apply {
                                timeInMillis = millis
                            }
                            diaryDate = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
                        }
                        showDatePicker = false
                    }) {
                        Text(stringResource(id = R.string.diary_date_set_confirm))
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { showMoodDropDown = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(diaryMood)
                Icon(
                    imageVector = Icons.Outlined.ArrowDropDown,
                    contentDescription = stringResource(id = R.string.diary_mood),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            DropdownMenu(
                expanded = showMoodDropDown,
                onDismissRequest = { showMoodDropDown = false }
            ) {
                listOf(
                    stringResource(id = R.string.happy_mood),
                    stringResource(id = R.string.sad_mood),
                    stringResource(id = R.string.normal_mood)
                ).forEach { mood ->
                    DropdownMenuItem(
                        text = { Text(mood) },
                        onClick = {
                            diaryMood = mood
                            showMoodDropDown = false
                        }
                    )
                }
            }
        }
        ErrorHint(diaryMoodError, stringResource(id = R.string.diary_mood_error))

        Button(
            onClick = {
                diaryTitleError = diaryTitle.isBlank()
                diaryContentError = diaryContent.isBlank()
                diaryDateError = diaryDate == "00/00/0000"
                diaryMoodError = diaryMood == moodText

                if (diaryTitleError || diaryContentError || diaryDateError || diaryMoodError) {
                    Toast.makeText(context, R.string.invalid, Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if(id == null){
                    viewModel.insert(diaryTitle, diaryContent, diaryDate, diaryMood)
                }else{
                    viewModel.update(id, diaryTitle, diaryContent, diaryDate, diaryMood)
                }

                navController.popBackStack()

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(stringResource(id = R.string.diary))
        }
    }
}

@Composable
fun DeleteAction(delete : () -> Unit){
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = {
        expanded = true
    }) {
        Icon(
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = stringResource(id = R.string.other),
            tint = MaterialTheme.colorScheme.primary
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.diary_delete)) },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}


@Composable
fun ErrorHint(isError: Boolean, message: String) {
    if (isError) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun IconPicker(isError: Boolean, content: @Composable () -> Unit = {}) {
    if (isError) {
        Icon(
            imageVector = Icons.Outlined.Warning,
            contentDescription = "Error",
            tint = MaterialTheme.colorScheme.error
        )
    } else {
        content()
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DiaryFromScreenPreview() {
    Asessment1Theme (
        theme = "LightTheme",
        content = {
            AddScreen(rememberNavController())
        }
    )
}
