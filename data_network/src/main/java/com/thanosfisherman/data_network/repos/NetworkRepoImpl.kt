package com.thanosfisherman.data_network.repos

import com.thanosfisherman.data_network.api.MarvelApi
import com.thanosfisherman.data_network.common.mapToDomain
import com.thanosfisherman.domain.common.NetworkResultState
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.repos.NetworkRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NetworkRepoImpl(private val marvelApi: MarvelApi) : NetworkRepo {
    override fun getAllCharacters(offset: Int): Flow<NetworkResultState<List<CharacterModel>>> = flow {
        emit(marvelApi.getCharacters(offset).mapToDomain())
    }
}