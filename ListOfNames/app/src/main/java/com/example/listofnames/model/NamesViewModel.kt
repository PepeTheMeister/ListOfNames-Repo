package com.example.listofnames.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listofnames.client.NamesClient
import com.example.listofnames.data.DataNames
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NamesViewModel : ViewModel(){

    private val dataNames = MutableStateFlow<List<DataNames?>>(emptyList())
    val names: StateFlow<List<DataNames?>> = dataNames

    var listNames = emptyList<DataNames?>()

    fun getAllNames() : List<DataNames?>{
        try {
            val receivedData = NamesClient.apiService.getAll()


            println("antes enqueue")
            receivedData.enqueue(object : Callback<List<DataNames>?> {
                override fun onResponse(call: Call<List<DataNames>?>, response: Response<List<DataNames>?>
                ) {
                    listNames = response.body()!!

                }

                override fun onFailure(call: Call<List<DataNames>?>, t: Throwable) {
                    println("erro")
                }
            })



        } catch (e: Exception) {
            e.printStackTrace()
            // Trate erros de rede aqui
        }

        return listNames
    }


}