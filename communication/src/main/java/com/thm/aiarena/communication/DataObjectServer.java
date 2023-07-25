package com.thm.aiarena.communication;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executors;

public class DataObjectServer {

    private static final int PORT = 33333;
    private static final int BUFFER_SIZE = 4;
    private Set<BlockingQueue<DataObject>> inputs = new ConcurrentSkipListSet<>();

    public DataObjectServer() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
        System.out.println("Server is listening on port " + PORT);

        Executors.newSingleThreadExecutor().submit(() -> {
            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                System.out.println("New client connected");
                inputs.add(startProcessing(socketChannel));
            }
        });
    }

    private BlockingQueue<DataObject> startProcessing(SocketChannel socketChannel) {
        BlockingQueue<DataObject> input = new ArrayBlockingQueue<>(100);
        Executors.newSingleThreadExecutor().submit(() -> {
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE * Integer.BYTES);
            try {
                DataObject data = input.take();
                while (true) {
                    buffer.putInt(data.type);
                    buffer.putInt(data.x);
                    buffer.putInt(data.y);
                    buffer.putInt(data.z);
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        socketChannel.write(buffer);
                    }
                    data = input.take();
                    if (data.type == 0 && data.x == 0 && data.y == 0 && data.z == 0) {
                        break;
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                inputs.remove(input);
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return input;
    }

    public void send(DataObject data) {
        inputs.forEach(input -> input.offer(data));
    }

}
