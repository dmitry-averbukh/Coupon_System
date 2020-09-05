package com.jb.Coupon_System_20.rest;

public class ClientSession {
    private final long clientId;
    private long lastAccessMillis;
    private int identifier;

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    private ClientSession(long clientId, long lastAccessMillis) {
        this.clientId = clientId;
        this.lastAccessMillis = lastAccessMillis;
        identifier = 0;
    }

    public static ClientSession create(long clientId) {
        return new ClientSession(clientId, System.currentTimeMillis());
    }

    public long getLastAccessMillis() {
        return lastAccessMillis;
    }

    public long getClientId() {
        return clientId;
    }

    public void access() {
        lastAccessMillis = System.currentTimeMillis();
    }
}
