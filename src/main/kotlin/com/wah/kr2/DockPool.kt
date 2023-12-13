package com.wah.kr2

const val LOG_FILE_NAME = "C:/logs.txt"

/**
 * This interface represents dock pool
 */
interface DockPool {

    /**
     * Retrieves dock from pool
     * @return dock
     */
    fun giveDock(): Dock

    /**
     * Returns dock to pool
     */
    fun returnDock(dock: Dock)

    /**
     * Logs port state
     */
    fun logState()
}