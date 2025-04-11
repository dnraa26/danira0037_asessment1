package com.danira0037.asessment1.ui.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.danira0037.asessment1.R
import com.danira0037.asessment1.model.Diary
import com.danira0037.asessment1.model.DiaryList
import com.danira0037.asessment1.navigation.Screen
import com.danira0037.asessment1.ui.theme.Asessment1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.Add.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(id = R.string.diary_create),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        MainScreenContent(Modifier.padding(innerPadding).padding(16.dp))
    }
}

@Composable
fun MainScreenContent(modifier: Modifier = Modifier){
    val data = DiaryList.diaryList


    if(data.isEmpty()){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.homework),
                contentDescription = "Sweeping dust",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(140.dp).padding(vertical = 20.dp)
            )

            Text(
                text = stringResource(id = R.string.empty_diary),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }else{

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ){

            items(data){ diary ->
                DiaryBox(diary = diary)
            }
        }
    }

}

@Composable
fun DiaryBox(diary: Diary) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Text(
                text = diary.judul,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = diary.tanggal,
                    style = MaterialTheme.typography.bodyMedium
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = diary.mood,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Text(
                text = diary.isi,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        shareDiary(
                            context = context,
                            message = context.getString(
                                R.string.share_template,
                                diary.judul,
                                diary.tanggal,
                                diary.mood,
                                diary.isi
                            )
                        )
                    },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = stringResource(id = R.string.share),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(id = R.string.share))
                }
            }
        }
    }
}


private fun shareDiary(context: Context, message : String){
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }

    val pickApplication = Intent.createChooser(shareIntent, context.getString(R.string.share_title))

    if(shareIntent.resolveActivity(context.packageManager) != null){
        context.startActivity(pickApplication)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Asessment1Theme {
        MainScreen(rememberNavController())
    }
}