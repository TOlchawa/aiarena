package com.thm.aiarena.model;

import com.thm.aiarena.model.aobject.*;

public interface AObject {
    void operate();
    Sensor getSensor();
    Motorics getMotorics();
    Weapon getWeapon();
    Container getContainer();
    Manipulator getManipulator();
}
