package com.thm.aiarena.model.aobject;

import com.thm.aiarena.model.AResource;

public interface Container {
    // returns current amount of specified resource
    long inventory(AResource resource);
    // returns maximum capacity of specified resource
    long max(AResource resource);
    // modify current resource
    // returns amount of specified resource after modification (can be negative value)
    long change(AResource resource, long delta);
}
