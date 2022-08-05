package com.androidnative.sqldelight.domain

import android.os.SystemClock
import android.util.Log
import com.androidnative.sqldelight.common.TAG
import com.androidnative.sqldelight.data.BaseDataSource
import com.androidnative.sqldelightdatabase.PersonDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import database.persondb.PersonEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class PersonDataSourceImpl @Inject constructor(db:PersonDatabase): BaseDataSource<PersonEntity,Long>{
    private val queries = db.personEntityQueries
    override suspend fun getSingleByKey(key: Long): PersonEntity? {
        return withContext(Dispatchers.IO){
            queries.transactionWithResult {
                afterCommit { Log.d(TAG, "getPersonById") }
                try {
                    queries.getPersionById(key).executeAsOneOrNull()
                }catch (e:Exception){
                    null
                }
            }
        }
    }

    override fun getAllValue(): Flow<List<PersonEntity>> {
        return queries.transactionWithResult {
                afterCommit { Log.d(TAG, "getAllPersons") }
                queries.getALlPersion().asFlow().mapToList()
            }
    }

    override suspend fun deleteValueByPrimaryKey(key: Long) {
        return withContext(Dispatchers.IO){
            queries.transaction {
                afterCommit { Log.d(TAG, "deletePersonById") }
                try {
                    queries.delePersionById(key)
                }catch (e:Exception){
                    rollback()
                }
            }
        }
    }

    override suspend fun insertValue(vararg valueField: Any, id: Long?) {
        withContext(Dispatchers.IO){
            queries.transaction {
                afterCommit { Log.d(TAG, "insertValue: $valueField") }
                if(valueField[0] is PersonEntity) {
                    var person = valueField[0] as PersonEntity
                    if (id != null) {
                        person = person.copy(id=id)
                    } else person = person.copy(id = SystemClock.currentThreadTimeMillis())
                    queries.insertPersion(person)
                }
            }
        }
    }


}