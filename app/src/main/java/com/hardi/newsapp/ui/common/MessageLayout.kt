package com.hardi.newsapp.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hardi.newsapp.R

@Composable
fun ShowLoading(){
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ){
        val contentDesc = stringResource(R.string.loading)
        CircularProgressIndicator(modifier = Modifier
            .align(Alignment.Center)
            .semantics {
                contentDescription = contentDesc
            })
    }
}

@Composable
fun ShowError(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Red,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(5.dp)
        )
    }
}

@Composable
fun ShowErrorView(
    text: String,
    onRetryClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = { onRetryClick() },
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Composable
fun ShowDefaultMessage(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(5.dp)
        )
    }
}