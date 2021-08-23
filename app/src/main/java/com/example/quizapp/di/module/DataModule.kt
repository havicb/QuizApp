package com.example.quizapp.di.module

import android.content.Context
import com.example.quizapp.data.auth.login.remote.api.LoginAPI
import com.example.quizapp.data.auth.login.remote.repository.LoginRepository
import com.example.quizapp.data.auth.login.remote.repository.LoginRepositoryImpl
import com.example.quizapp.data.questions.remote.api.QuestionsAPI
import com.example.quizapp.data.questions.remote.repository.QuestionRepository
import com.example.quizapp.data.questions.remote.repository.QuestionRepositoryImpl
import com.example.quizapp.data.quiz.local.QuizLocalRepository
import com.example.quizapp.data.quiz.local.QuizLocalRepositoryImpl
import com.example.quizapp.di.qualifiers.AuthRetrofit
import com.example.quizapp.di.qualifiers.QuestionRetrofit
import com.example.quizapp.prefstore.PrefsStore
import com.example.quizapp.prefstore.PrefsStoreImpl
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
    fun provideQuestionRepository(questionsApi: QuestionsAPI): QuestionRepository =
        QuestionRepositoryImpl(questionsApi)

    @Provides
    @Singleton
    fun provideLoginRepository(loginAPI: LoginAPI): LoginRepository =
        LoginRepositoryImpl(loginAPI)

    @Provides
    @Singleton
    fun provideQuestionApi(@QuestionRetrofit retrofit: Retrofit) =
        retrofit.create(QuestionsAPI::class.java)

    @Provides
    @Singleton
    fun provideLoginApi(@AuthRetrofit retrofit: Retrofit) = retrofit.create(LoginAPI::class.java)

    @Provides
    @Singleton
    fun providePrefStore(@ApplicationContext context: Context): PrefsStore = PrefsStoreImpl(context)
}
