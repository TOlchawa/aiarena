package com.thm.aiarena.model.array;

import com.thm.aiarena.model.ALocation;
import com.thm.aiarena.model.AObject;
import com.thm.aiarena.model.AResource;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class SimpleLocation implements ALocation {
    UUID id;
    List<AObject> objectSet;
    List<AResource> resourceSet;
}
