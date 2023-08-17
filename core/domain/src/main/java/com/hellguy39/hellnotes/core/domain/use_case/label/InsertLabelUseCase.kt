package com.hellguy39.hellnotes.core.domain.use_case.label

import com.hellguy39.hellnotes.core.common.date.di.IoDispatcher
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.model.local.database.Label
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InsertLabelUseCase @Inject constructor(
    private val labelRepository: LabelRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(label: Label): Long {
        return withContext(ioDispatcher) {
            labelRepository.insertLabel(label)
        }
    }
}