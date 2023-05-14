package com.thm.aiarena.array;

import com.thm.aiarena.model.AResource;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SimpleResource implements AResource {

    public static int TYPE = 0;

    private int amount;

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public int getAmount() {
        return amount;
    }

}
