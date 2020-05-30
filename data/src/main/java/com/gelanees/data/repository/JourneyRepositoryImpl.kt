package com.gelanees.data.repository

import com.gelanees.data.model.map
import com.gelanees.data.service.JourneyPlannerService
import com.gelanees.data.utils.retrofitApiCall
import com.gelanees.domain.model.GetAvailablePlannerModesResult
import com.gelanees.domain.repository.IJourneyRepository

class JourneyRepositoryImpl(
    private val journeyPlannerService: JourneyPlannerService
) : IJourneyRepository {

    override suspend fun getAvailablePlannerModes() = retrofitApiCall(
        call = { journeyPlannerService.getAvailablePlannerModes() },
        success = { GetAvailablePlannerModesResult.Success(it.map { modeJson -> modeJson.map() }) },
        failure = { body, code ->
            GetAvailablePlannerModesResult.Error(code = code, message = body ?: "")
        },
        noInternet = { GetAvailablePlannerModesResult.IOException }
    )
}