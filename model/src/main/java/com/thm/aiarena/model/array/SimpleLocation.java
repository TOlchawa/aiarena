package com.thm.aiarena.model.array;

import com.thm.aiarena.model.ALocation;
import com.thm.aiarena.model.AObject;
import com.thm.aiarena.model.AResource;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
public class SimpleLocation implements ALocation {
    UUID id;
    int x;
    int y;
    @ToString.Exclude
    List<AObject> aObjects;
    @ToString.Exclude
    List<AResource> aResources;
}
