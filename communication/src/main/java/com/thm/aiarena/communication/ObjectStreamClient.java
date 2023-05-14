package com.thm.aiarena.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class ObjectStreamClient {

    List<DataListener> dataListeners = new ArrayList<>();

    public ObjectStreamClient(DataListener listener) {

        dataListeners.add(listener);

        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.bind(new InetSocketAddress(33333));
            SocketChannel clientSocketChannel = serverSocketChannel.accept();
            try (InputStream inputStream = Channels.newInputStream(clientSocketChannel)) {
                ObjectInputStream ois = new ObjectInputStream(inputStream);
                int[] buf = new int[4];
                buf = readDataObject(ois, buf);
                DataObject dataobject = new DataObject(readDataObject(ois, buf));
                while (!finish(buf)) {
                    propagateData(dataobject);
                    dataobject.update(readDataObject(ois, buf));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("DONE");

    }

    private static boolean finish(int[] buf) {
        return buf[0] == 0 && buf[1] == 0 && buf[2] == 0 && buf[3] == 0;
    }

    private static int[] readDataObject(ObjectInputStream ois, int[] buf) throws IOException {
        buf[0] = ois.read();
        buf[1] = ois.read();
        buf[2] = ois.read();
        buf[3] = ois.read();
        return buf;
    }

    public void propagateData(DataObject data) {
        dataListeners.forEach(listener -> listener.process(data));
    }

}
