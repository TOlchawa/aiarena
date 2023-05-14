package com.thm.aiarena.model;

import java.util.List;

public interface AArena extends AListener {

    int getWidth();
    int getHeight();
    int getLevel();

    // description of location
    // x - width; y - height ; z - deep
    ALocation getLocation(int x, int y, int z);

    // list of all objects connected to specified location
    // x - width; y - height ; z - deep
    List<AObject> getObjects(int x, int y, int z);

    // list of all resources connected to specified location
    // x - width; y - height ; z - deep
    List<AResource> getResources(int x, int y, int z);

    // Returns all AObjects
    List<AObject> getAllAObjects();

    // move specified aObject to specified targetLocation
    void moveAObject(AObject aObject, ALocation targetLocation);

    void removeAObject(AObject aObject);
}
