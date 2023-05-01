package com.thm.aiarena.model.aobject;

import com.thm.aiarena.model.AResource;

public interface Container {
    // returns current amount of specified resource
    int inventory(int resourceType);
    // returns maximum capacity of specified resource
    int max(AResource resource);
    // modify current resource type amount
    // returns amount of specified resource type after modification (can be negative value)
    int change(int resourceType, int delta);
}
