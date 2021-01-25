package com.joker.lpgo.data.mock

import com.joker.lpgo.data.model.Category

data class CategoryListMock(
    val data: MutableList<Category> = mutableListOf<Category>(
        Category(0, "Kategori 1", 23, ""),
        Category(0, "Kategori 2", 12, ""),
        Category(0, "Kategori 3", 43, ""),
        Category(0, "Kategori 4", 2, ""),
        Category(0, "Kategori 5", 15, ""),
        Category(0, "Kategori 6", 21, ""),
        Category(0, "Kategori 7", 13, ""),
        Category(0, "Kategori 8", 1, ""),
        Category(0, "Kategori 9", 11, ""),
        Category(0, "Kategori 10", 5, ""),
    )
)
