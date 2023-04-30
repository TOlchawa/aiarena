package com.thm.aiarena.model.array;

import com.thm.aiarena.model.AArena;
import com.thm.aiarena.model.ALocation;
import com.thm.aiarena.model.AObject;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class SimpleObject implements AObject {
    @ToString.Exclude
    AArena arena;
    ALocation location;

    @Override
    public void process() {

    }
}
