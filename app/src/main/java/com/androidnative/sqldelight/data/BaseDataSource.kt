package com.androidnative.sqldelight.data

import com.androidnative.sqldelight.R
import database.persondb.PersonEntity
import kotlinx.coroutines.flow.Flow


interface BaseDataSource<T,K>{

    suspend fun getSingleByKey(key:K):T?

    fun getAllValue():Flow<List<T>>

    suspend fun deleteValueByPrimaryKey(key:K)

    suspend fun insertValue(vararg valueField: Any,id:K?=null)
}