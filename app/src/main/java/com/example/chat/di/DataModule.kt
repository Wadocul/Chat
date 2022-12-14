package com.example.chat.di

import com.example.data.firebase.authentication.FirebaseAuthentication
import com.example.data.firebase.database.chat.FirebaseChatDB
import com.example.data.firebase.database.profile.FirebaseProfilesDB
import com.example.data.repository.RepositoryImpl
import com.example.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideRepository(firebaseAuthentication: FirebaseAuthentication,
                          firebaseChatDB: FirebaseChatDB,
                          firebaseProfilesDB: FirebaseProfilesDB
    ): Repository {
        return RepositoryImpl(firebaseAuthentication, firebaseChatDB, firebaseProfilesDB)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthentication(): FirebaseAuthentication {
        return FirebaseAuthentication()
    }

    @Provides
    @Singleton
    fun provideFirebaseChatDB(): FirebaseChatDB {
        return FirebaseChatDB()
    }

    @Provides
    @Singleton
    fun provideFirebaseProfileDB(): FirebaseProfilesDB {
        return FirebaseProfilesDB()
    }
}