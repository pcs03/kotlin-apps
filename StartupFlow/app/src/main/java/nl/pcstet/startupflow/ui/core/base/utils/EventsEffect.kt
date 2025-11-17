package nl.pcstet.startupflow.ui.core.base.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import nl.pcstet.startupflow.ui.core.base.BackgroundEvent
import nl.pcstet.startupflow.ui.core.base.BaseViewModel

/**
 * Convenience method for observing event flow from [nl.pcstet.startupflow.ui.core.base.BaseViewModel].
 *
 * By default, events will only be consumed when the associated screen is
 * resumed, to avoid bugs like duplicate navigation calls. To override
 * this behavior, a given event type can implement [BackgroundEvent].
 */
@Composable
fun <E> EventsEffect(
    viewModel: BaseViewModel<*, E, *>,
    lifecycleOwner: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    handler: (E) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow
            .filter {
                it is BackgroundEvent ||
                        lifecycleOwner.currentState.isAtLeast(Lifecycle.State.RESUMED)
            }
            .onEach { handler.invoke(it) }
            .launchIn(this)
    }
}