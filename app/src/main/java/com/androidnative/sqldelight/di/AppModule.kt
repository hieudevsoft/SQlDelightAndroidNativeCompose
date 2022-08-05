package com.androidnative.sqldelight.di

import android.app.Application
import com.androidnative.sqldelight.data.BaseDataSource
import com.androidnative.sqldelight.domain.PersonDataSourceImpl
import com.androidnative.sqldelightdatabase.PersonDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import database.persondb.PersonEntity
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideSqlDriver(app:Application):SqlDriver = AndroidSqliteDriver(PersonDatabase.Schema,app,"person.db")

    @Singleton
    @Provides
    fun providePersonDataSource(driver:SqlDriver):BaseDataSource<PersonEntity,Long> = PersonDataSourceImpl(PersonDatabase(driver))

}