package com.thm.aiarena.ai.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {

    public int extractBit(int input, int startPosition, int numBits) {
        int mask = (1 << numBits) - 1;
        return (input >> startPosition) & mask;
    }

}
