package com.thm.aiarena.array;

import lombok.Getter;
import lombok.ToString;
import lombok.With;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ToString
@With
public class NeuralNetworkSimpleObject extends SimpleObject {

    public NeuralNetworkSimpleObject() {
        super();
    }

    @Override
    public void operate() {
        log.info("operate");
    }

}
