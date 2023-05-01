package com.thm.aiarena.ai;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Random;

@Slf4j
class NeuralNetworkTest {

    Random rnd = new Random(1234567890L);

    @Test
    public void test() {
        NeuralNetwork n1 = new NeuralNetwork(rnd);
        NeuralNetwork n2 = new NeuralNetwork(rnd);
        int out1 = n1.think(1);
        int out2 = n2.think(1);
        log.info("outs: {} {} dest: {}", out1, out2);



//        for(int i=0; i<16; i++) {
//            System.out.print(n1.extractBit(out, i, 1));
//        }
//        System.out.println();
    }

}