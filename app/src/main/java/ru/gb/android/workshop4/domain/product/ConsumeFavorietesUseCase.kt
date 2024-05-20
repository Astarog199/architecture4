package ru.gb.android.workshop4.domain.product

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.gb.android.workshop4.data.favorites.FavoritesRepository
import ru.gb.android.workshop4.data.product.ProductRepository
import ru.gb.android.workshop4.presentation.product.ProductState
import javax.inject.Inject

class ConsumeFavorietesUseCase @Inject constructor(
    private val favoritesRepository : FavoritesRepository,
    private val favoriteDomainMapper: FavoriteDomainMapper
) {
    operator fun invoke(): Flow<List<Favorite>> {
        return favoritesRepository.consumeFavorites()
            .map { products ->  products.map (favoriteDomainMapper::fromEntity)
        }
    }
}