package ru.gb.android.workshop4.domain.product

import android.util.Log
import kotlinx.coroutines.flow.map
import ru.gb.android.workshop4.data.favorites.FavoriteEntity
import ru.gb.android.workshop4.data.favorites.FavoritesRepository
import ru.gb.android.workshop4.presentation.product.ProductState
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
) {
    suspend fun addProduct (productState: Favorite){
         val favorite = FavoriteEntity(id = productState.id)
        favoritesRepository.addToFavorites(favorite = favorite)
    }
}