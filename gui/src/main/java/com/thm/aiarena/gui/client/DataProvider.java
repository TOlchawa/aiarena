package com.thm.aiarena.gui.client;

import com.thm.aiarena.communication.DataListener;
import com.thm.aiarena.communication.DataObject;
import com.thm.aiarena.communication.ObjectStreamClient;
import com.thm.aiarena.gui.GUI;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

import static com.thm.aiarena.communication.DataObject.ADD_OBJECT;
import static com.thm.aiarena.communication.DataObject.REMOVE_OBJECT;

public class DataProvider implements DataListener {
    private ObjectStreamClient objectStreamClient;
    private GUI gui;

    public DataProvider(GUI gui) throws IOException {
        this.gui = gui;
        objectStreamClient = new ObjectStreamClient(this);
    }

    public void startListener() throws IOException, ClassNotFoundException {
        SocketAddress remote = new InetSocketAddress(InetAddress.getLocalHost(), 43555);
        SocketChannel channel = SocketChannel.open(remote);
        objectStreamClient.receiveObject(channel);
    }

    @Override
    public void process(DataObject event) {
        switch (event.type) {
            case ADD_OBJECT -> addObject(event);
            case REMOVE_OBJECT -> removeObject(event);
            default -> error();
        }
    }

    private void addObject(DataObject event) {

    }

    private void removeObject(DataObject event) {

    }

    private void error() {
    }
}
