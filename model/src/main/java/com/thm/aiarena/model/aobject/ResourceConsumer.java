package com.thm.aiarena.model.aobject;

import com.thm.aiarena.model.AResource;

public interface ResourceConsumer {

    // consume specified resource in specified time
    // returns time required to consumption
    long consume(long amount, AResource resource);
}
