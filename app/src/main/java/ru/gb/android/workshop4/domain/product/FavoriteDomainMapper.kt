package ru.gb.android.workshop4.domain.product

import ru.gb.android.workshop4.data.favorites.FavoriteEntity
import ru.gb.android.workshop4.data.product.ProductEntity
import javax.inject.Inject

class FavoriteDomainMapper @Inject constructor() {
    fun fromEntity(productEntity: FavoriteEntity): Favorite {
        return Favorite(
            id = productEntity.id,
        )
    }
}