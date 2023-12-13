package com.wah.kr2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Objects;

/**
 * This class represents synchronized implementation of {@link DockPool}
 */
public class DockPoolSynchronized implements DockPool {
    private final Integer DOCKS_AMOUNT = 10;
    private final String LOG_FILE_NAME = "C:/logs.txt";
    private final ArrayDeque<Dock> availableDocs = new ArrayDeque<>();
    private final ArrayDeque<Dock> retrievedDocs = new ArrayDeque<>();

    private static DockPoolSynchronized instance;

    private DockPoolSynchronized() {
        for (int i = 0; i < DOCKS_AMOUNT; i++) {
            availableDocs.add(new Dock(i));
        }
    }

    /**
     * Returns instance of {@link DockPoolSynchronized}
     * @return instance of {@link DockPoolSynchronized}
     */
    public synchronized static DockPoolSynchronized getInstance() {
        if (Objects.isNull(instance)) {
            instance = new DockPoolSynchronized();
        }
        return instance;
    }

    /**
     * Retrieves dock if available, waits for available dock otherwise
     * @return dock
     */
    public synchronized Dock giveDock() {
        while (availableDocs.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException ignored) { }
        }
        Dock dock = availableDocs.removeLast();
        retrievedDocs.add(dock);
        return dock;
    }

    /**
     * Returns dock to pool
     */
    public synchronized void returnDock(Dock dock) {
        availableDocs.add(dock);
        retrievedDocs.remove(dock);
        notify();
    }

    /**
     * Logs port state
     */
    public synchronized void logState() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Timestamp: ").append(Instant.now()).append("\n");
        stringBuilder.append("Port state:\n");
        availableDocs.forEach ( it ->
                stringBuilder.append("Dock ").append(it.getId()).append(" state ").append(it.getState()).append("\n")
        );
        retrievedDocs.forEach ( it ->
                stringBuilder.append("Dock ").append(it.getId()).append(" state ").append(it.getState()).append("\n")
        );
        try (FileOutputStream outputStream = new FileOutputStream(LOG_FILE_NAME, true)) {
            outputStream.write(stringBuilder.toString().getBytes());
        } catch (IOException ignored) { }
    }
}
