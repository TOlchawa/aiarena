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
import java.util.concurrent.Executors;

import static com.thm.aiarena.communication.DataObject.ADD_OBJECT;
import static com.thm.aiarena.communication.DataObject.REMOVE_OBJECT;

public class DataProvider implements DataListener {

    private GUI gui;

    public DataProvider(GUI gui) throws IOException {
        this.gui = gui;
        Executors.newSingleThreadExecutor().submit( () -> {
            new ObjectStreamClient(this);
        });
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
        System.out.println("ADD " + event.x + "," + event.y);
        gui.add(event);
    }

    private void removeObject(DataObject event) {
        System.out.println("REM " + event.x + "," + event.y);
    }

    private void error() {
        System.err.println("ERROR!");
    }
}
