package nsu.dymont.connection;


import nsu.dymont.handler.Handler;

import java.nio.ByteBuffer;

public abstract class Connection implements Handler {
    private boolean disconnect = false;

    abstract void linkBuffer(ByteBuffer clientBuffer);

    public void setDisconnect() {
        disconnect = true;
    }

    public boolean getDisconnect() {
        return disconnect;
    }
}
