package com.example.busschedule.data

import kotlinx.coroutines.flow.Flow

class OfflineScheduleRepository(private val scheduleDao: ScheduleDao) : ScheduleRepository {
    override fun getAllSchedulesStream(): Flow<List<Schedule>> {
        return scheduleDao.getAll()
    }
    override fun getScheduleStream(id: Int): Flow<Schedule?> {
        return scheduleDao.getOneById(id)
    }
    override fun getScheduleStreamByStopName(stopName: String): Flow<List<Schedule>> {
        return scheduleDao.getOneByStopName(stopName)
    }
    override suspend fun insertSchedule(schedule: Schedule) {
        scheduleDao.insert(schedule)
    }
    override suspend fun updateSchedule(schedule: Schedule) {
        scheduleDao.update(schedule)
    }
    override suspend fun deleteSchedule(schedule: Schedule) {
        scheduleDao.delete(schedule)
    }
}