package com.thm.aiarena.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class ObjectStreamClient {

    List<DataListener> dataListeners = new ArrayList<>();

    public ObjectStreamClient(DataListener listener) throws IOException {
        SocketAddress remote = new InetSocketAddress(InetAddress.getLocalHost(), 43555);
        SocketChannel channel = SocketChannel.open(remote);
        dataListeners.add(listener);
    }

    public void sendObject(SocketChannel channel, Object obj) throws IOException {
        try (ObjectOutputStream objectStream = new ObjectOutputStream(channel.socket().getOutputStream())) {
            // Write the object to the object stream
            objectStream.writeObject(obj);
        }
    }

    public Object receiveObject(SocketChannel channel) throws IOException, ClassNotFoundException {
        DataObject data = null;
        try (ObjectInputStream objectStream = new ObjectInputStream(channel.socket().getInputStream())) {
            boolean finish = false;
            while (!finish) {
                data = (DataObject) objectStream.readObject();
                propgateData(data);
            }
        }
        return data;
    }

    public void propgateData(DataObject data) {
        dataListeners.forEach(listener -> listener.process(data));
    }

}
