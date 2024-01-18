package com.example.starwarsapp.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Internally Runs a `runCatching` with in the given [CoroutineDispatcher].
 *
 * @param dispatcher [CoroutineDispatcher] dispatcher where we want the given `block` to get executed.
 *
 * @param block suspend function to run inside the Coroutine Scope.
 *
 * @return [Result] of generic type [T] or [Throwable] if some error have occurred in given block.
 */
suspend fun <T> runCatchingWithDispatcher(dispatcher: CoroutineDispatcher, block: suspend () -> T): Result<T> {
    return withContext(dispatcher) {
        runCatching{ block() }
    }
}
