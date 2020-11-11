package ru.webant.domain

data class PaginationEntity<I>(
    val totalItems: Int,
    val itemsPerPage: Int,
    val countOfPages: Int,
    val items: List<I>
)