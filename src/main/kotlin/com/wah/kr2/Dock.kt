package com.wah.kr2

/**
 * This class represents Dock
 * @property id dock id
 */
class Dock(val id: Int) {

    var state = DockState.AVAILABLE
        private set

    /**
     * Loads cargo
     */
    fun loadCargo() {
        state = DockState.LOADING
        Thread.sleep(1000)
        state = DockState.AVAILABLE
    }

    /**
     * Unloads cargo
     */
    fun unloadCargo() {
        state = DockState.UNLOADING
        Thread.sleep(1000)
        state = DockState.BUSY
    }
}