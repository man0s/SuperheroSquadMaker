package com.thanosfisherman.domain.repos

import com.thanosfisherman.domain.common.NetworkResultState
import com.thanosfisherman.domain.model.CharacterModel
import kotlinx.coroutines.flow.Flow

interface NetworkRepo {

    fun getAllCharacters(offset: Int): Flow<NetworkResultState<List<CharacterModel>>>
}