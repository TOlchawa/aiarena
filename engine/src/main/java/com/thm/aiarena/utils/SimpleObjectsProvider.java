package com.thm.aiarena.utils;

import com.thm.aiarena.ai.NeuralNetworkSimpleObject;
import com.thm.aiarena.array.SimpleLocation;
import com.thm.aiarena.array.SimpleObject;
import com.thm.aiarena.array.SimpleResource;
import com.thm.aiarena.communication.DataObject;
import com.thm.aiarena.model.AArena;
import com.thm.aiarena.model.AObject;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Service
@AllArgsConstructor
public class SimpleObjectsProvider {

    private final AArena arena;
    private final Random randomObjectsIndexProvider;
    private List<AObject> aObjectsToProvide;
    private final BlockingQueue<DataObject> inputQueue = new LinkedBlockingQueue<>();
    private final DataObject buffer = new DataObject(null);
    private

    @PostConstruct
    void initialize() {
        arena.getAllAObjects();
        Executors.newSingleThreadExecutor().submit(() -> {
            initCommunication();
        });
    }

    public AObject provideAObject() {
        if (aObjectsToProvide == null || aObjectsToProvide.isEmpty()) {
            arena.getAllAObjects().removeIf(o -> o.getContainer() == null || o.getContainer().inventory(SimpleResource.TYPE) <= 0);
            aObjectsToProvide = new ArrayList<>(arena.getAllAObjects());
        } else {
            aObjectsToProvide.removeIf(o -> !o.isAlive());
        }
        int max = aObjectsToProvide.size();
        if (max < 1) {
            return null;
        }
        int nextIdx = randomObjectsIndexProvider.nextInt(max);
        log.debug("randomObjectsIndexProvider: {} ", nextIdx);
        AObject result = aObjectsToProvide.remove(nextIdx);


        updateGUI(result);


        if (aObjectsToProvide.isEmpty()) {
            arena.getAllAObjects().removeIf(o -> o.getContainer().inventory(SimpleResource.TYPE) <= 0);
            aObjectsToProvide = null;
            log.debug(".");

            int[] maxValue = new int[] { 0 };
            AObject[] maxObj = new AObject[1];
            arena.getAllAObjects().forEach( obj -> {
                int inventory = obj.getContainer().inventory(SimpleResource.TYPE);
                if (maxValue[0] < inventory) {
                    maxValue[0] = inventory;
                    maxObj[0] = obj;
                }
            });

            if (maxObj[0] != null) {
                ((NeuralNetworkSimpleObject) maxObj[0]).getAi().save("nn.bin");
                ((NeuralNetworkSimpleObject) maxObj[0]).getAi().load("nn.bin");
            }
        }
        return result;
    }

    private void updateGUI(AObject result) {
        SimpleLocation location = (SimpleLocation) result.getLocation();
        int type = 1; // ADD
        int x = location.getX();
        int y = location.getY();
        int z = 0;
        inputQueue.offer(buffer.update(new int[] {type, x, y, z}));
    }

    public void initCommunication() {
//        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
//            serverSocketChannel.bind(new InetSocketAddress(33333));
//            SocketChannel clientSocketChannel = serverSocketChannel.accept();
//            OutputStream outputStream = Channels.newOutputStream(clientSocketChannel);
//
//            DataObject data = inputQueue.take();
//            while (data != null) {
//                outputStream.write(data.type);
//                outputStream.write(data.x);
//                outputStream.write(data.y);
//                outputStream.write(0);
//                data = inputQueue.take();
//            }
//
//            clientSocketChannel.close(); // Close the connection with the client
//        } catch (IOException | InterruptedException ex) {
//            ex.printStackTrace();
//        }

        try (SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress( 33333))) {
            ObjectOutputStream outputStream = new ObjectOutputStream(socketChannel.socket().getOutputStream());

            DataObject data = inputQueue.take();
            while (data != null) {
                outputStream.write(data.type);
                outputStream.write(data.x);
                outputStream.write(data.y);
                outputStream.write(0);
                data = inputQueue.take();
            }

            // Send additional data or perform other operations as needed

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
