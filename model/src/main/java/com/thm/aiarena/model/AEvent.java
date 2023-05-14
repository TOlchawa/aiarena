package com.thm.aiarena.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AEvent {
    public static final int NEW_BORN = 1;
    private int type;
    private AObject source;
    private ALocation location;
}
