package com.thanosfisherman.data_network.repos

import androidx.paging.PagingSource
import com.haroldadmin.cnradapter.NetworkResponse
import com.thanosfisherman.data_network.api.MarvelApi
import com.thanosfisherman.domain.common.NetworkResultState
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.model.ErrorModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel

@ExperimentalCoroutinesApi
class HeroesPageDataSource(private val marvelApi: MarvelApi) :
    PagingSource<Int, CharacterModel>() {

    val networkStateChannel: ConflatedBroadcastChannel<NetworkResultState<List<CharacterModel>>> = ConflatedBroadcastChannel()


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterModel> {
        val position = params.key ?: 0
        return try {

            when (val response = marvelApi.getCharacters(position * params.loadSize)) {
                is NetworkResponse.Success -> {
                    val character = response.body
                    LoadResult.Page<Int, CharacterModel>(
                        data = character.asDomain(),
                        prevKey = if (position == 0) null else position - 1,
                        nextKey = if (character.asDomain().isEmpty()) null else position + 1
                    )
                }
                else -> LoadResult.Error(Throwable("ERROR PLS"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        networkStateChannel.offer(NetworkResultState.Error(ErrorModel.Unknown(e.message.toString())))
    }
}