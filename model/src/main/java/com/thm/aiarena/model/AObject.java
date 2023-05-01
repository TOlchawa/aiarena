package com.thm.aiarena.model;

import com.thm.aiarena.model.aobject.*;

import java.util.UUID;

public interface AObject {
    void operate();
    UUID getId();
    Sensor getSensor();
    Motorics getMotorics();
    Weapon getWeapon();
    Container getContainer();
    Manipulator getManipulator();
    Transmitter getTransmitter();
}
