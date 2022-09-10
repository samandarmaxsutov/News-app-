package uz.gita.mylibrary

object Repository {
    val newsRepository : NewsRepository by lazy { OrderRepositoryImpl() }

}