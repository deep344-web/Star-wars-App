package com.example.starwarsapp.common

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/**
 * An Extension work similar to `launchWhenStarted` to launch a coroutine if lifecycle is stated.
 *
 * @param coroutineScope [CoroutineScope] on which the `block` will run
 * @param block code to run after lifecycle is started
 */
fun LifecycleOwner.launchWhenStarted(coroutineScope: CoroutineScope, block: suspend () -> Unit) {
    coroutineScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            block()
        }
    }
}