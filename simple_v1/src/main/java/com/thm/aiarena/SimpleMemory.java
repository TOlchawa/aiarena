package com.thm.aiarena;

import com.thm.aiarena.array.SimpleObject;
import com.thm.aiarena.model.aobject.Memory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@Slf4j
@ToString
public class SimpleMemory implements Memory {

    @ToString.Exclude
    SimpleObject subject;

    public SimpleMemory(SimpleObject subject) {
        this.subject = subject;
    }

    private AtomicInteger memory = new AtomicInteger(0);

    @Override
    public Object memory() {
        return memory;
    }

    @Override
    public void remember(Object data) {
        if (Integer.class.isInstance(data)) {
            Integer intValue = (Integer) data;
            memory.set(intValue.intValue());
        } else {
            memory.set(0);
            log.warn("Unknown data");
        }
    }
}