package com.hellguy39.hellnotes.core.domain.use_case.trash

import com.hellguy39.hellnotes.core.domain.repository.local.TrashRepository
import javax.inject.Inject

class EmptyTrashUseCase @Inject constructor(
    private val trashRepository: TrashRepository
) {

    suspend operator fun invoke() {
        trashRepository.deleteAll()
    }

}