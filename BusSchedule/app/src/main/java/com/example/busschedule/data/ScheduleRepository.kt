package com.example.busschedule.data

import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getAllSchedulesStream(): Flow<List<Schedule>>
    fun getScheduleStream(id: Int): Flow<Schedule?>
    fun getScheduleStreamByStopName(stopName: String): Flow<List<Schedule>>
    suspend fun insertSchedule(schedule: Schedule)
    suspend fun updateSchedule(schedule: Schedule)
    suspend fun deleteSchedule(schedule: Schedule)
}