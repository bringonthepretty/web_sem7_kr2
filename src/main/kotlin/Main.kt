import com.wah.kr2.DockPoolLock
import com.wah.kr2.LOG_FILE_NAME
import com.wah.kr2.ShipThread
import java.io.File

fun main() {
    Thread.currentThread().contextClassLoader.loadClass("com.wah.kr2.DockPoolSynchronized")

    val dockPool = DockPoolLock

    for (i in 0 until 100) {
        ShipThread(i, dockPool).start()
    }

    File(LOG_FILE_NAME).writeBytes("".toByteArray())

    for (j in 0 .. 5) {
        dockPool.logState()
        Thread.sleep(5000)
    }
}