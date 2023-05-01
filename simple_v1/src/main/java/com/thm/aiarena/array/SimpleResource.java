package com.thm.aiarena.array;

import com.thm.aiarena.model.AResource;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class SimpleResource implements AResource {

    private long amount;

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public long getAmount() {
        return 0;
    }

}
