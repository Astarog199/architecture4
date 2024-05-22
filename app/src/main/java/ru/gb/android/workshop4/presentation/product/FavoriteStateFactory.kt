package ru.gb.android.workshop4.presentation.product

import dagger.hilt.android.scopes.ViewModelScoped
import ru.gb.android.workshop4.domain.product.Favorite
import javax.inject.Inject

@ViewModelScoped
class FavoriteStateFactory @Inject constructor() {
    fun create(favorite: Favorite) : FavoriteState{
        return FavoriteState(
            id = favorite.id
        )
    }
}