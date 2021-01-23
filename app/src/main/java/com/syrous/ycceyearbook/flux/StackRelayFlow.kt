package com.syrous.ycceyearbook.flux

import androidx.annotation.NonNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

/**
 * A StackRelayFlow that emits the top most item on flow.
 *
 * It also contains methods that manipulate the stack, but these do not affect any flow.
 *
 */
@ExperimentalCoroutinesApi
@FlowPreview
class StackRelayFlow<T>
internal constructor(default: T){

    private val _stackStateFlow = MutableStateFlow(default)

    val stackStateFlow: StateFlow<T> = _stackStateFlow

    /** The error, write before terminating and read after checking subscribers.  */
    internal var error: Throwable? = null

    private val history = Stack<T>()

    fun addToStack(item: T) {
        synchronized(history) {
            history.push(item)
            relay()
        }
    }

    /**
     * Relay the item on the top of the stack currently.
     */
    private fun relay() {
        val item: T?
        synchronized(history) {
            item = if (history.isNotEmpty()) {
                history.peek()
            } else {
                null
            }
        }
        if(item != null) {
            _stackStateFlow.value = item
        }
    }

    // Some utility methods to manipulate the stack.

    fun getValue() = synchronized(history) { if (history.isNotEmpty()) { history.peek() } else { null } }

    fun pop() = synchronized(history) {
        if (history.isNotEmpty()) {
            history.pop()
            relay()
        } else {
            null
        }
    }

    /**
     * Only pop if the history will not empty after the pop.
     */
    fun safePop() {
        synchronized(history) {
            if (history.size > 1) {
                history.pop()
                assert(history.isNotEmpty())
                relay()
            }
        }
    }

    fun trim(vararg values: T) {
        trim { values.contains(it) }
    }

    fun trim(test: (T) -> Boolean) {
        synchronized(history) {
            while (history.isNotEmpty() && test(history.peek())) {
                history.pop()
            }
        }
    }

    fun clear() = synchronized(history) { history.clear() }

    /**
     * Clear the history, except for the last thing added.
     */
    fun trimTail() {
        synchronized(history) {
            if (history.size <= 1) {
                return
            }
            val head = history.peek()
            history.clear()
            history.push(head)
        }
    }

    fun getSize() = synchronized(history) { history.size }

    companion object {
        @NonNull
        fun <T> create(default: T): StackRelayFlow<T> {
            return StackRelayFlow(default)
        }
    }

}