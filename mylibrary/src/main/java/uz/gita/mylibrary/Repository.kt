package uz.gita.mylibrary

object Repository {
    val orderRepository : NewsRepository by lazy { OrderRepositoryImpl() }

}