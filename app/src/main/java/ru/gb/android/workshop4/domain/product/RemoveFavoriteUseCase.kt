package ru.gb.android.workshop4.domain.product

import ru.gb.android.workshop4.data.favorites.FavoriteEntity
import ru.gb.android.workshop4.data.favorites.FavoritesRepository
import ru.gb.android.workshop4.presentation.product.ProductState
import javax.inject.Inject

class RemoveFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    suspend fun removeProduct(product: Favorite){
        val favorite = FavoriteEntity(id = product.id)
        favoritesRepository.removeFromFavorites(favorite)
    }
}