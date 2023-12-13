package com.wah.kr2

import java.io.File
import java.time.Instant
import java.util.concurrent.locks.ReentrantLock
import kotlin.collections.ArrayDeque

private const val DOCKS_AMOUNT = 10

/**
 * This object represents lock implementation of [DockPool]
 */
object DockPoolLock: DockPool {

    private val availableDocs = ArrayDeque<Dock>()
    private val retrievedDocs = ArrayDeque<Dock>()
    private val lock = ReentrantLock()
    private val condition = lock.newCondition()

    init {
        for (i in 0 until DOCKS_AMOUNT) {
            availableDocs.add(Dock(i))
        }
    }

    /**
     * Retrieves dock if available, waits for available dock otherwise
     * @return dock
     */
    override fun giveDock(): Dock {
        lock.lock()
        while (availableDocs.isEmpty()) {
            condition.await()
        }
        return availableDocs.removeLast().also {
            retrievedDocs.add(it)
            lock.unlock()
        }
    }

    /**
     * Returns dock to pool
     */
    override fun returnDock(dock: Dock) {
        lock.lock()
        availableDocs.add(dock)
        retrievedDocs.remove(dock)
        condition.signal()
        lock.unlock()
    }

    /**
     * Logs port state
     */
    override fun logState() {
        lock.lock()
        val stringBuilder = StringBuilder()
        stringBuilder.append("Timestamp: ").append(Instant.now()).append("\n")
        stringBuilder.append("Port state:\n")
        availableDocs.forEach {
            stringBuilder.append("Dock ").append(it.id).append(" state ").append(it.state).append("\n")
        }
        retrievedDocs.forEach {
            stringBuilder.append("Dock ").append(it.id).append(" state ").append(it.state).append("\n")
        }
        lock.unlock()
        File(LOG_FILE_NAME).appendBytes(stringBuilder.toString().toByteArray())
    }
}