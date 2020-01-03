package com.xiayu.log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

public class SnowflakeSequenceGenerator{

    public static final int NODE_SHIFT = 9;
    public static final int SEQ_SHIFT = 10;

    public static final short MAX_NODE = 2 << NODE_SHIFT;
    public static final short MAX_SEQUENCE = 2 << SEQ_SHIFT;

    public static final long BASE_EPOCH = 1000 * LocalDateTime.of(2018, 01,01,00,00,00).toEpochSecond(ZoneOffset.UTC);

    private short sequence;
    private long lastTime;

    private int node;

    /**
     * A snowflake is designed to operate as a singleton instance within the context of a node.
     * If you deploy different nodes, supplying a unique node id will guarantee the uniqueness
     * of ids generated concurrently on different nodes.
     *
     * @param node This is an id you use to differentiate different nodes.
     */
    public SnowflakeSequenceGenerator(int node) {
        if (node < 0 || node > MAX_NODE) {
            throw new IllegalArgumentException(String.format("node must be between %s and %s", 0, MAX_NODE));
        }
        this.node = node;
    }

    public SnowflakeSequenceGenerator() {
        this(getNodeId());
    }

    static int getNodeId() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            int id;
            if (network == null) {
                return new Random().nextInt(1000) + 1;
            } else {
                byte[] mac = network.getHardwareAddress();
                id = ((0x000000FF & (int) mac[mac.length - 1]) | (0x0000FF00 & (((int) mac[mac.length - 2]) << 8))) >> 6;
            }
            return id;
        } catch (Exception e) {
            return new Random().nextInt(1000) + 1;
        }
    }

    /**
     * Generates a k-ordered unique 64-bit integer. Subsequent invocations of this method will produce
     * increasing integer values.
     *
     * @return The next 64-bit integer.
     */
    public long next() {

        long timestamp = System.currentTimeMillis();
        short counter;

        synchronized (this) {
            if (timestamp < lastTime) {
                throw new IllegalStateException("Clock moved backwards.  Refusing to generate id for " + (
                        lastTime - timestamp) + " milliseconds.");
            }
            if (lastTime == timestamp) {
                sequence = (short) ((sequence + 1) % MAX_SEQUENCE);
                if (sequence == 0) {
                    timestamp = tilNextMillis(lastTime);
                }
            } else {
                sequence = 0;
            }
            lastTime = timestamp;
            counter = sequence;
        }

        return ((timestamp - BASE_EPOCH) << NODE_SHIFT << SEQ_SHIFT) | (node << SEQ_SHIFT) | counter;
    }

    static long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
