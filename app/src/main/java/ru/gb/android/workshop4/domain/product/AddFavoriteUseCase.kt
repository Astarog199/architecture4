package ru.gb.android.workshop4.domain.product

import kotlinx.coroutines.flow.map
import ru.gb.android.workshop4.data.favorites.FavoriteEntity
import ru.gb.android.workshop4.data.favorites.FavoritesRepository
import ru.gb.android.workshop4.presentation.product.ProductState
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
) {
    fun addProduct (productState: ProductState){
//         val favorite = FavoriteEntity(id = productState.id)
//        favoritesRepository.addToFavorites(favorite = favorite)
    }
}