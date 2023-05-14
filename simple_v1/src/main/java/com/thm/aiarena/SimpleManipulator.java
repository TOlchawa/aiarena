package com.thm.aiarena;

import com.thm.aiarena.array.SimpleObject;
import com.thm.aiarena.array.SimpleResource;
import com.thm.aiarena.model.AResource;
import com.thm.aiarena.model.aobject.Manipulator;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import static com.thm.aiarena.SimpleConst.GAIN_COST;
import static com.thm.aiarena.SimpleConst.MAX_PORTION_OF_RESOURCE;

@Slf4j
@AllArgsConstructor
@ToString
public class SimpleManipulator implements Manipulator {


    @ToString.Exclude
    private SimpleObject subject;

    @Override
    public void gain(AResource resource) {
        if (subject.getContainer().change(SimpleResource.TYPE, -GAIN_COST) > 0) {
            SimpleResource simpleResource = (SimpleResource) resource;
            int resourceAmount = resource.getAmount();
            int maxGain = subject.getContainer().max(resource) - subject.getContainer().inventory(SimpleResource.TYPE);
            int gain = Math.min(resourceAmount, Math.min(MAX_PORTION_OF_RESOURCE, maxGain));
            // less than MAX_PORTION_OF_RESOURCE
            // less than total amount
            // less than max capacity - current capacity

            simpleResource.setAmount(resourceAmount - gain); // consume resources
            subject.getContainer().change(resource.getType(), gain); // gain resources

            log.debug("object: {} ; resource: {}", subject.getContainer().inventory(SimpleResource.TYPE), simpleResource.getAmount());
        }
    }
}
