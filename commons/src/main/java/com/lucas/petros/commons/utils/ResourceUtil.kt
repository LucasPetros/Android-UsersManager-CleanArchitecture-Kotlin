
package com.lucas.petros.commons.utils

import androidx.lifecycle.MutableLiveData
import com.lucas.petros.commons.base.BaseState
import com.lucas.petros.commons.data.DataResource

object ResourceUtil {

    fun <T> mapResultToState(result: DataResource<T>, state: MutableLiveData<BaseState<T>>) {
        when (result) {
            is DataResource.Loading -> {
                state.value = BaseState(isLoading = true)
            }
            is DataResource.Success -> {
                state.value = BaseState(isLoading = false, data = result.data)
            }
            is DataResource.Error -> {
                state.value = BaseState(
                    error = result.message ?: Constants.UNEXPECTED_ERROR,
                    isLoading = false
                )
            }
        }
    }
}
