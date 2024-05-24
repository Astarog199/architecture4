package ru.gb.android.workshop4.presentation.product

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.gb.android.workshop4.domain.product.AddFavoriteUseCase
import ru.gb.android.workshop4.domain.product.ConsumeFavorietesUseCase
import ru.gb.android.workshop4.domain.product.ConsumeProductsUseCase
import ru.gb.android.workshop4.domain.product.Favorite
import ru.gb.android.workshop4.domain.product.RemoveFavoriteUseCase
import ru.gb.android.workshop4.marketsample.R
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val consumeProductsUseCase: ConsumeProductsUseCase,
    private val productStateFactory: ProductStateFactory,
    private val consumeFavorietesUseCase: ConsumeFavorietesUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
) : ViewModel() {

    private var _state = MutableStateFlow(ProductsScreenState())
    var state: StateFlow<ProductsScreenState> = _state.asStateFlow()

    fun requestProducts() {
        consumeProductsUseCase()
            .map { products ->
                products.map { product -> productStateFactory.create(product) }
            }
            .onStart {
                _state.update { screenState -> screenState.copy(isLoading = true) }
            }
            .onEach { productListState ->
                _state.update { screenState ->
                    screenState.copy(
                        isLoading = false,
                        productListState = productListState,
                    )
                }
            }
            .catch {
                _state.update { screenState ->
                    screenState.copy(
                        hasError = true,
                        errorRes = R.string.error_wile_loading_data,
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun refresh() {
        requestProducts()
    }

    fun errorHasShown() {
        _state.update { screenState -> screenState.copy(hasError = false) }
    }

    fun addToFavorites(favoriteId: String) {
        consumeFavorietesUseCase()
            .onStart {
                addFavoriteUseCase.addProduct(Favorite(favoriteId))
            }

            .onEach { result ->
                _state.update {
                    it.copy(productListState = it.productListState.map { product ->
                        if (product.id == favoriteId) {
                            product.copy(isFavorite = true)
                        } else {
                            product
                        }
                    })
                }
            }
            .launchIn(viewModelScope)
    }

    fun removeFromFavorites(favoriteId: String) {
        consumeFavorietesUseCase()
            .onStart {
                removeFavoriteUseCase.removeProduct(Favorite(favoriteId))
            }

            .onEach { result ->
                _state.update {
                    it.copy(productListState = it.productListState.map { product ->
                        if (product.id == favoriteId) {
                            product.copy(isFavorite = false)
                        } else {
                            product
                        }
                    })
                }
            }
            .launchIn(viewModelScope)
    }
}
