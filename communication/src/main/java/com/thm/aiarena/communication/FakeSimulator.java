package com.thm.aiarena.communication;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.sleep;

public class FakeSimulator {

    private final BlockingQueue<DataObject> inputQueue = new LinkedBlockingQueue<>();

    public FakeSimulator() {
        Executors.newSingleThreadExecutor().submit(() -> startServer());
        Executors.newSingleThreadExecutor().submit(() -> startClient());
    }

    private void startServer() {
        DataObjectServer server = null;
        try {
            server = new DataObjectServer();

            while (true) {

                DataObject data = new DataObject();
                data.type = 1;
                data.x = 2;
                data.y = 3;
                data.z = 4;

                sleep(1000);

                server.send(data);

            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String...args) throws InterruptedException {
        FakeSimulator sim = new FakeSimulator();
        while (true) {
            sleep(1000);
            System.out.println("iq: "+sim.inputQueue.size());
        }
    }

    private void startClient() {
        DataListener listener = new DataListener() {
            @Override
            public void process(DataObject data) {
                System.out.println("data: " + data);
            }
        };
        DataObjectClient client = new DataObjectClient(listener);

    }

}
