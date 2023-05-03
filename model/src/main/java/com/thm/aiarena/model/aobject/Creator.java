package com.thm.aiarena.model.aobject;

import com.thm.aiarena.model.AObject;

public interface Creator {
    AObject newChild(AObject parent);
}
