package com.thm.aiarena.model.aobject;

import com.thm.aiarena.model.ALocation;
import com.thm.aiarena.model.AObject;

public interface Weapon {
    void attack(ALocation location, AObject aObject);
}
