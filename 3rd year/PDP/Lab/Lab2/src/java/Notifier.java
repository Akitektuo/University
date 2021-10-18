package java;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Notifier<T> {
    private T data = null;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final boolean withLogs;

    public Notifier(boolean withLogs) {
        this.withLogs = withLogs;
    }

    public Notifier() {
        this.withLogs = false;
    }

    public void notify(T data) {
        lock.lock();

        try {
            while (this.data != null) {
                    condition.await();
            }

            this.data = data;
            if (withLogs) {
                System.out.println("Notifying data: " + data);
            }

            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lock.unlock();
    }

    public T waitForData() {
        lock.lock();

        try {
            while (this.data == null) {
                condition.await();
            }

            var data = readData();
            if (withLogs) {
                System.out.println("Waited for data: " + data);
            }

            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lock.unlock();

        return data;
    }

    private T readData() {
        var data = this.data;

        this.data = null;

        return data;
    }
}
