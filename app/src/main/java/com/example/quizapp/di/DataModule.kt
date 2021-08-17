package com.example.quizapp.di

import android.content.Context
import com.example.quizapp.data.quiz.repository.QuizRepository
import com.example.quizapp.data.quiz.repository.QuizRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DataModule {
    @Provides
    @Singleton
    fun provideQuizRepository(@ApplicationContext context: Context): QuizRepository =
        QuizRepositoryImpl(context)
}
