package org.example.project.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.example.project.books.data.database.DatabaseFactory
import org.example.project.books.data.database.FavoriteBookDB
import org.example.project.books.data.network.KtorRemoteBookDataSource
import org.example.project.books.data.network.RemoteBookDataSource
import org.example.project.books.data.repository.SearchedBookRepo
import org.example.project.books.domain.BookRepository
import org.example.project.books.presentation.SelectedBookViewModel
import org.example.project.books.presentation.book_detail.BookDetailsViewModel
import org.example.project.books.presentation.book_list.BookListViewModel
import org.example.project.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    single {
        HttpClientFactory.initClient(get())
    }

    single {
        KtorRemoteBookDataSource(httpClient = get())
    }

    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::SearchedBookRepo).bind<BookRepository>()

    single {
        get<DatabaseFactory>()
            .create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    single { get<FavoriteBookDB>().favoriteBookDao }

    viewModelOf(::BookListViewModel)
    viewModelOf(::SelectedBookViewModel)
    viewModelOf(::BookDetailsViewModel)
}

expect val platformModule: Module