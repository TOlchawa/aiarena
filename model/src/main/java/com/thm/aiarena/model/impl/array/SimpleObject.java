package com.thm.aiarena.model.impl.array;

import com.thm.aiarena.model.AArena;
import com.thm.aiarena.model.ALocation;
import com.thm.aiarena.model.AObject;
import com.thm.aiarena.model.aobject.*;
import com.thm.aiarena.model.aobject.Container;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class SimpleObject implements AObject {
    @ToString.Exclude
    AArena arena;

    ALocation location;

    Sensor sensor;
    Motorics motorics;
    Weapon weapon;
    Container container;
    Manipulator manipulator;

    @Override
    public void operate() {
    }
}
