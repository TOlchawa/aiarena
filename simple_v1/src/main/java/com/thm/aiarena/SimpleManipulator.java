package com.thm.aiarena;

import com.thm.aiarena.array.SimpleObject;
import com.thm.aiarena.array.SimpleResource;
import com.thm.aiarena.model.AResource;
import com.thm.aiarena.model.aobject.Manipulator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class SimpleManipulator implements Manipulator {

    private static int MAX_PORTION_OF_RESOURCE = 1000;

    private SimpleObject subject;

    @Override
    public void gain(AResource resource) {
        SimpleResource simpleResource = (SimpleResource)resource;
        int amount = resource.getAmount();
        int gain = subject.getContainer().max(resource) - subject.getContainer().inventory(SimpleResource.TYPE);

        gain = Math.min(MAX_PORTION_OF_RESOURCE, Math.min(amount, gain));
        // less than MAX_PORTION_OF_RESOURCE
        // less than total amount
        // less than max capacity - current capacity

        simpleResource.setAmount(amount - gain); // consume resources
        subject.getContainer().change(resource.getType(), gain); // gain resources
    }
}
