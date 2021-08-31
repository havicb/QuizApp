package com.example.quizapp.di.module

import android.content.Context
import com.example.quizapp.data.auth.login.api.LoginAPI
import com.example.quizapp.data.auth.login.repository.LoginRepository
import com.example.quizapp.data.auth.login.repository.LoginRepositoryImpl
import com.example.quizapp.data.auth.register.api.RegistrationAPI
import com.example.quizapp.data.auth.register.repository.RegistrationRepository
import com.example.quizapp.data.auth.register.repository.RegistrationRepositoryImpl
import com.example.quizapp.data.prefstore.PrefsStore
import com.example.quizapp.data.prefstore.PrefsStoreImpl
import com.example.quizapp.data.questions.api.QuestionsAPI
import com.example.quizapp.data.questions.repository.QuestionRepository
import com.example.quizapp.data.questions.repository.QuestionRepositoryImpl
import com.example.quizapp.data.quiz.repository.QuizRepository
import com.example.quizapp.data.quiz.repository.QuizRepositoryImpl
import com.example.quizapp.di.qualifiers.AuthRetrofit
import com.example.quizapp.di.qualifiers.QuestionRetrofit
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
    fun provideQuizLocalRepository(@ApplicationContext context: Context): QuizRepository =
        QuizRepositoryImpl(context)

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
    fun provideRegistrationRepository(registrationAPI: RegistrationAPI): RegistrationRepository =
        RegistrationRepositoryImpl(registrationAPI)

    @Provides
    @Singleton
    fun provideRegistrationApi(@AuthRetrofit retrofit: Retrofit) = retrofit.create(RegistrationAPI::class.java)

    @Provides
    @Singleton
    fun provideQuestionApi(@QuestionRetrofit retrofit: Retrofit) = retrofit.create(QuestionsAPI::class.java)

    @Provides
    @Singleton
    fun provideLoginApi(@AuthRetrofit retrofit: Retrofit) = retrofit.create(LoginAPI::class.java)

    @Provides
    @Singleton
    fun providePrefStore(@ApplicationContext context: Context): PrefsStore = PrefsStoreImpl(context)
}
