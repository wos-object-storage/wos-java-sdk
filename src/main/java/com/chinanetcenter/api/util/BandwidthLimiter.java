package com.chinanetcenter.api.util;

/**
 * Created by chenql on 2022/03/15.
 */
public class BandwidthLimiter {


    /* KB */
    private long KB = 1024L;
    /**
     * The smallest count chunk length in bytes,默认值时8K
     */
    private long CHUNK_LENGTH = 8 * KB;

    // How many bytes will be sent or receive
    private int bytesWillBeSentOrReceive = 0;

    // When the last piece was sent or receive
    private long lastPieceSentOrReceiveTick = System.nanoTime();

    /**
     * Default rate is 1024K/s
     * Set the max upload or download rate in KB/s. maxRate must be grater than 0. If maxRate is zero, it means there is no bandwidth limit.
     */
    private int maxRate = 1024;

    // Time cost for sending CHUNK_LENGTH bytes in nanoseconds
    private long timeCostPerChunk = (1000000000L * CHUNK_LENGTH)
            / (maxRate * KB);

    private long nextStartTime = 0L;
    public BandwidthLimiter() {
    }
    public BandwidthLimiter(int maxRate) {
        // Non-positive numbers mean no speed limit; values less than 100 are set to 100
        this.maxRate = maxRate < 0 ? 0 : (Math.max(maxRate, 100));

        if (this.maxRate == 0) {
            timeCostPerChunk = 0;
        } else {
            CHUNK_LENGTH = this.maxRate * KB / 100;
            if (CHUNK_LENGTH < 8192L) {
                CHUNK_LENGTH = 8192L;
            }
            timeCostPerChunk = (1000000000L * CHUNK_LENGTH)
                    / (this.maxRate * KB);
        }
    }

    public int getChunkSize() {
        if (CHUNK_LENGTH > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int) CHUNK_LENGTH;
    }


    /**
     * Next 1 byte should do bandwidth limit.
     */
    public synchronized void limitNextBytes() {
        limitNextBytes(1);
    }

    /**
     * Whether to allow read/write operations
     *
     * @return
     */
    public synchronized boolean isReadWriteable() {
        if (nextStartTime == 0L) {
            nextStartTime = System.nanoTime();
        }
        return nextStartTime <= System.nanoTime();
    }

    /**
     * Record the size of data read/written
     *
     * @param len
     */
    public synchronized void readWriteSize(int len) {
        long nowTick = System.nanoTime();
        // Time required to transmit 'len' bytes under bandwidth limit in nanoseconds
        long costTime = timeCostPerChunk * len / CHUNK_LENGTH;
        if ((nowTick - nextStartTime) < costTime) {
            // If actual transmission time is less than the limited time, delay next execution by costTime
            nextStartTime = nowTick + costTime;
        } else {
            // If actual transmission time is greater than or equal to the limited time, set next execution time to current time
            nextStartTime = nowTick;
        }
    }
    /**
     * Next len bytes should do bandwidth limit
     *
     * @param len
     */
    public synchronized void limitNextBytes(int len) {
        if (maxRate < 1) {
            return;
        }
        bytesWillBeSentOrReceive += len;

        /* We have sent CHUNK_LENGTH bytes */
        while (bytesWillBeSentOrReceive >= CHUNK_LENGTH) {
            long nowTick = System.nanoTime();
            long missedTime = timeCostPerChunk
                    - (nowTick - lastPieceSentOrReceiveTick);
            if (missedTime > 0) {
                try {
                    Thread.sleep(missedTime / 1000000,
                            (int) (missedTime % 1000000));
                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
                }
            }
            bytesWillBeSentOrReceive -= CHUNK_LENGTH;
            lastPieceSentOrReceiveTick = nowTick
                    + (missedTime > 0 ? missedTime : 0);
        }
    }

    public synchronized void limitNextBytes(long len) {
        if (maxRate < 1) {
            return;
        }
        while (len > Integer.MAX_VALUE) {
            limitNextBytes(Integer.MAX_VALUE);
            len -= Integer.MAX_VALUE;
        }
        limitNextBytes((int) len);
    }
}
