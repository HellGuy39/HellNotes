package com.hellguy39.hellnotes.core.domain.use_case

import com.hellguy39.hellnotes.core.domain.repository.local.TrashRepository
import com.hellguy39.hellnotes.core.model.repository.local.database.Trash
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DeleteExpiredNotesUseCase @Inject constructor(
    private val trashRepository: TrashRepository
) {

    suspend operator fun invoke() {

        val trashToRemove = mutableListOf<Trash>()

        trashRepository.getAllTrash().forEach { trash ->

            val expirationDate = trash.dateOfAdding + TimeUnit.DAYS.toMillis(7)

            if (System.currentTimeMillis() > expirationDate) {
                trashToRemove.add(trash)
            }
        }

        trashToRemove.forEach { trash ->
            trashRepository.deleteTrash(trash)
        }
    }

}