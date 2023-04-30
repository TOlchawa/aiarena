package com.thm.aiarena.model.impl;

import com.thm.aiarena.model.ALocation;
import com.thm.aiarena.model.AObject;
import com.thm.aiarena.model.aobject.Weapon;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleWeapon implements Weapon {
    @Override
    public void attack(ALocation location, AObject aObject) {
        log.info("Weapon::attack");
    }
}
