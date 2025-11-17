package nl.pcstet.startupflow.data.core.manager.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher

interface DispatcherManager {
    val default: CoroutineDispatcher
    val main: MainCoroutineDispatcher
    val io: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}