package com.example.listofnames

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.listofnames.data.DataNames
import com.example.listofnames.model.NamesViewModel
import com.example.listofnames.ui.theme.ListOfNamesTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }

    @Composable
    fun NameRow(dataNames: DataNames, onDeleteClick: (DataNames) -> Unit) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = dataNames.name,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = dataNames.id.toString(),
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(16.dp)
            )
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = Color.Red, // Cor do ícone de exclusão
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onDeleteClick(dataNames) }
            )
        }
        Divider()
    }

    @Composable
    fun App(){
        val viewModel = NamesViewModel()
        val dataState = viewModel.names.collectAsState()

        var count = 0
        var name by remember { mutableStateOf("") }
        var listOfNames by remember { mutableStateOf(listOf<DataNames?>()) }


        LaunchedEffect(Unit){
            listOfNames = viewModel.getAllNames()
        }

        if(listOfNames.isNotEmpty()){
            count = listOfNames.size
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { text ->
                        name = text
                    },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = {
                    if(name.isNotBlank()){
                        val d = DataNames(count, name)
                        listOfNames = listOfNames + d
                        count++
                        name = ""
                    }

                }) {
                    Text(text = "Add String")
                }
            }

            LazyColumn {
                items(listOfNames){ currentName ->
                    if (currentName != null) {
                        NameRow(
                            dataNames = currentName,
                            onDeleteClick = { deletedName ->
                                listOfNames = listOfNames - deletedName
                                count--
                            }
                        )
                    }
                }
            }
        }

    }
}


