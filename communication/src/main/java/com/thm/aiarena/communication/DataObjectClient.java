package com.thm.aiarena.communication;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class DataObjectClient {

    private static final int PORT = 33333;
    private static final int BUFFER_SIZE = 4;

    List<DataListener> dataListeners = new ArrayList<>();

    public DataObjectClient(DataListener listener) {

        dataListeners.add(listener);

        try (SocketChannel socketChannel = SocketChannel.open() ) {
            socketChannel.connect(new InetSocketAddress("localhost", PORT));
            System.out.println("Connected to the server!");

            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE * Integer.BYTES);
            while (true) {
                buffer.clear();
                socketChannel.read(buffer);
                buffer.flip();

                DataObject data = new DataObject();
                data.type = buffer.getInt();
                data.x = buffer.getInt();
                data.y = buffer.getInt();
                data.z = buffer.getInt();

                if (finish(data)) {
                    break;
                } else {
                    propagateData(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            dataListeners.remove(listener);
        }

    }

    private static boolean finish(DataObject data) {
        return data.type == 0 && data.x == 0 && data.y == 0 && data.z == 0;
    }

    private static int[] readDataObject(ObjectInputStream ois, int[] buf) throws IOException {
        buf[0] = ois.read();
        buf[1] = ois.read();
        buf[2] = ois.read();
        buf[3] = ois.read();
        return buf;
    }

    private void propagateData(DataObject data) {
        dataListeners.forEach(listener -> listener.process(data));
    }

}
