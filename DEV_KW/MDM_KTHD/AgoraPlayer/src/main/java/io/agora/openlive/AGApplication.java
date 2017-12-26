package io.agora.openlive;

import android.content.Context;

import io.agora.openlive.model.WorkerThread;

public class AGApplication {

    private static WorkerThread mWorkerThread;

    public static synchronized void initWorkerThread(Context context) {
        if (mWorkerThread == null) {
            mWorkerThread = new WorkerThread(context);
            mWorkerThread.start();

            mWorkerThread.waitForReady();
        }
    }

    public static synchronized WorkerThread getWorkerThread(Context context) {
        if (mWorkerThread == null) {
            initWorkerThread(context);
        }
        return mWorkerThread;
    }

    public static synchronized void deInitWorkerThread() {
        mWorkerThread.exit();
        try {
            mWorkerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mWorkerThread = null;
    }
}
