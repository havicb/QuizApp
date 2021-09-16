package com.example.quizapp.di.module

import android.content.Context
import com.example.quizapp.data.NetworkHandler
import com.example.quizapp.data.StorageConfig
import com.example.quizapp.data.StorageConfigImpl
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
    fun provideQuestionRepository(
        networkHandler: NetworkHandler,
        questionsApi: QuestionsAPI
    ): QuestionRepository =
        QuestionRepositoryImpl(networkHandler, questionsApi)

    @Provides
    @Singleton
    fun provideLoginRepository(
        networkHandler: NetworkHandler,
        loginAPI: LoginAPI
    ): LoginRepository =
        LoginRepositoryImpl(networkHandler, loginAPI)

    @Provides
    @Singleton
    fun provideRegistrationRepository(
        networkHandler: NetworkHandler,
        registrationAPI: RegistrationAPI
    ): RegistrationRepository =
        RegistrationRepositoryImpl(networkHandler, registrationAPI)

    @Provides
    @Singleton
    fun provideRegistrationApi(@AuthRetrofit retrofit: Retrofit): RegistrationAPI =
        retrofit.create(RegistrationAPI::class.java)

    @Provides
    @Singleton
    fun provideQuestionApi(@QuestionRetrofit retrofit: Retrofit): QuestionsAPI =
        retrofit.create(QuestionsAPI::class.java)

    @Provides
    @Singleton
    fun provideLoginApi(@AuthRetrofit retrofit: Retrofit): LoginAPI =
        retrofit.create(LoginAPI::class.java)

    @Provides
    @Singleton
    fun providePrefStore(@ApplicationContext context: Context, storageConfig: StorageConfig): PrefsStore = PrefsStoreImpl(context, storageConfig)

    @Provides
    @Singleton
    fun provideStorageConfig(@ApplicationContext context: Context): StorageConfig = StorageConfigImpl(context)
}
