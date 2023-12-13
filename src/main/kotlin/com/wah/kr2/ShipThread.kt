package com.wah.kr2

import java.time.Instant

/**
 * This class represents ship thread
 * @property id thread id
 * @property dockPool poolProvider
 *
 */
class ShipThread(private val id: Int, private val dockPool: DockPool): Thread() {
    override fun run() {
        val dock = dockPool.giveDock()
        println("Ship $id unloading cargo in dock ${dock.id}. Timestamp: ${Instant.now()}")
        dock.unloadCargo()
        println("Ship $id unloaded cargo in dock ${dock.id}. Timestamp: ${Instant.now()}")
        println("Ship $id loading cargo in dock ${dock.id}. Timestamp: ${Instant.now()}")
        dock.loadCargo()
        println("Ship $id loaded cargo in dock ${dock.id}. Timestamp: ${Instant.now()}")
        dockPool.returnDock(dock)
    }
}
