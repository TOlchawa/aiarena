package com.thm.aiarena;

import com.thm.aiarena.array.SimpleObject;
import com.thm.aiarena.model.ALocation;
import com.thm.aiarena.model.AObject;
import com.thm.aiarena.model.aobject.Weapon;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class SimpleWeapon implements Weapon {

    private SimpleObject subject;
    @Override
    public void attack(ALocation location, AObject aObject) {
        log.info("Weapon::attack");
    }
}
