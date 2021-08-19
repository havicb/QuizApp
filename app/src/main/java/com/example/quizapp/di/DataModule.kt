package com.example.quizapp.di

import android.content.Context
import com.example.quizapp.data.quiz.local.QuizLocalRepository
import com.example.quizapp.data.quiz.local.QuizLocalRepositoryImpl
import com.example.quizapp.data.questions.remote.api.QuestionsAPI
import com.example.quizapp.data.questions.remote.repository.QuestionRepository
import com.example.quizapp.data.questions.remote.repository.QuestionRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [NetworkModule::class])
class DataModule {
    @Provides
    @Singleton
    fun provideQuizLocalRepository(@ApplicationContext context: Context): QuizLocalRepository =
        QuizLocalRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideQuestionRepository(questionsApi: QuestionsAPI): QuestionRepository = QuestionRepositoryImpl(questionsApi)

    @Provides
    @Singleton
    fun provideQuestionApi(retrofit: Retrofit) = retrofit.create(QuestionsAPI::class.java)
}
