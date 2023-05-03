package com.thm.aiarena;

import com.thm.aiarena.array.SimpleObject;
import com.thm.aiarena.array.SimpleResource;
import com.thm.aiarena.model.ALocation;
import com.thm.aiarena.model.AObject;
import com.thm.aiarena.model.aobject.Weapon;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@ToString
public class SimpleWeapon implements Weapon {

    public static int ATTACK_COST = 500;

    @ToString.Exclude
    private SimpleObject subject;

    @Override
    public void attack(ALocation location, AObject aObject) {
        if (subject.getContainer().change(SimpleResource.TYPE, -ATTACK_COST) > 0) {
            int enemyResourceAmount = aObject.getContainer().inventory(SimpleResource.TYPE);
            int benefit = enemyResourceAmount / 2;
            log.debug("Weapon::attack - benefit: {}", benefit);
            aObject.getContainer().change(SimpleResource.TYPE, -benefit);
            subject.getContainer().change(SimpleResource.TYPE, benefit);
        }
    }
}
