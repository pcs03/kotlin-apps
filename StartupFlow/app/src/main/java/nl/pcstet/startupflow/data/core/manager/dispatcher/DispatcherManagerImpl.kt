package nl.pcstet.startupflow.data.core.manager.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

class DispatcherManagerImpl : DispatcherManager {
    override val default: CoroutineDispatcher = Dispatchers.Default
    override val main: MainCoroutineDispatcher = Dispatchers.Main
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
}