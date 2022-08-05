package com.androidnative.sqldelight.ui

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidnative.sqldelight.data.BaseDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import database.persondb.PersonEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonListViewModel @Inject constructor(
    app: Application,
    savedStateHandle: SavedStateHandle,
    private val personDataSource:BaseDataSource<PersonEntity,Long>,
):AndroidViewModel(app) {

    init {
        savedStateHandle.get<Long>("idPerson")?.let { getPersonById(it) }
    }


    val persons = personDataSource.getAllValue()

    var personDetail by mutableStateOf<PersonEntity?>(null)
        private set

    var firstNameText by mutableStateOf("")
        private set
    var lastNameText by mutableStateOf("")
        private set

    fun onActionInsertPerson(){
        if(firstNameText.isNotBlank()&&lastNameText.isNotBlank()){
            viewModelScope.launch(Dispatchers.IO){
                personDataSource.insertValue(PersonEntity(1L,firstNameText,lastNameText))
                firstNameText=""
                lastNameText=""
            }
        }
    }

    fun onActionDeletePerson(id:Long){
        viewModelScope.launch(Dispatchers.IO){
            personDataSource.deleteValueByPrimaryKey(id)
        }
    }

    fun getPersonById(idPerson:Long){
        viewModelScope.launch(Dispatchers.IO){
            personDetail = personDataSource.getSingleByKey(idPerson)
        }
    }

    fun onActionFirstNameChange(value:String) {
        firstNameText = value
    }

    fun onActionLastNameChange(value:String){
        lastNameText = value
    }

    fun onPersonDetailDialogDismiss(){
        personDetail = null
    }
}