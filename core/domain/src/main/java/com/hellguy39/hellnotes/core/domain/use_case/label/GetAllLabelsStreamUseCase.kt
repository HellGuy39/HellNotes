package com.hellguy39.hellnotes.core.domain.use_case.label

import com.hellguy39.hellnotes.core.common.date.di.IoDispatcher
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.model.local.database.Label
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllLabelsStreamUseCase @Inject constructor(
    private val labelRepository: LabelRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    operator fun invoke(): Flow<List<Label>> {
        return labelRepository.getAllLabelsStream()
            .flowOn(ioDispatcher)
    }
}