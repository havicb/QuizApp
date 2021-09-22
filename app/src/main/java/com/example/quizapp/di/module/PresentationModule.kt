package com.example.quizapp.di.module

import com.example.quizapp.presentation.main.home.QuizHomeAdapter
import com.example.quizapp.presentation.main.home.Quizzes
import com.example.quizapp.presentation.main.home.QuizzesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PresentationModule {
    @Provides
    @Singleton
    fun provideQuizzes(): Quizzes = QuizzesImpl()

    @Provides
    @Singleton
    fun provideAdapter(quizzes: Quizzes): QuizHomeAdapter = QuizHomeAdapter(quizzes)
}
