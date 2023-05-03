package com.thm.aiarena.ai;

import com.thm.aiarena.array.SimpleLocation;
import com.thm.aiarena.array.SimpleObject;
import com.thm.aiarena.model.AArena;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NeuralNetworkSimpleObjectTest {

    @ParameterizedTest
    @CsvSource({
            "0b0000000000000000,0b0000000000000000,0b0000000000000000,0b0000000000000000,0b0000000000000000",
            "0b0000000000000001,0b0000000000000000,0b0000000000000000,0b0000000000000000,0b0001000000000000",
            "0b0000000000000000,0b0000000000000001,0b0000000000000000,0b0000000000000000,0b0000000100000000",
            "0b0000000000000000,0b0000000000000000,0b0000000000000001,0b0000000000000000,0b0000000000010000",
            "0b0000000000000000,0b0000000000000000,0b0000000000000000,0b0000000000000001,0b0000000000000001",
            "0b0000000000000001,0b0000000000000001,0b0000000000000001,0b0000000000000001,0b0001000100010001",
            "0b0000000000001111,0b0000000000001111,0b0000000000001111,0b0000000000001111,0b1111111111111111",
            "0b0000000000001001,0b0000000000001001,0b0000000000001001,0b0000000000001001,0b1001100110011001",
    })
    public void testPrepareInput(String sMemory, String sInventory, String sFriends, String sEnemies, String sInput) {
        NeuralNetworkSimpleObject testedObject = new NeuralNetworkSimpleObject();
        int memory = Integer.parseInt(sMemory.substring(2), 2);
        int inventory = Integer.parseInt(sInventory.substring(2), 2);
        int friends = Integer.parseInt(sFriends.substring(2), 2);
        int enemies = Integer.parseInt(sEnemies.substring(2), 2);
        int input = Integer.parseInt(sInput.substring(2), 2);

        int data = testedObject.prepareInput(memory, inventory, friends, enemies);

        assertThat(data).isEqualTo(input);
    }

    @Test
    public void testFindFreeLocation() {
        NeuralNetworkSimpleObject testedObject = new NeuralNetworkSimpleObject();
        testedObject.setRandom(new Random());

        AArena arena = mock(AArena.class);
        testedObject.setArena(arena);

        SimpleLocation freeLocation = mock(SimpleLocation.class);
        when(freeLocation.getAObjects()).thenReturn(Collections.emptyList());

        SimpleLocation notFreeLocation = mock(SimpleLocation.class);
        when(notFreeLocation.getAObjects()).thenReturn(List.of(new SimpleObject()));

        when(arena.getLocation(99, 99, 0)).thenReturn(freeLocation);
        when(arena.getLocation( 99, 100, 0)).thenReturn(notFreeLocation);
        when(arena.getLocation( 99, 101, 0)).thenReturn(notFreeLocation);
        when(arena.getLocation( 100, 99, 0)).thenReturn(notFreeLocation);
        when(arena.getLocation(100, 101, 0)).thenReturn(notFreeLocation);
        when(arena.getLocation(101, 99, 0)).thenReturn(notFreeLocation);
        when(arena.getLocation(101, 100, 0)).thenReturn(notFreeLocation);
        when(arena.getLocation(101, 101, 0)).thenReturn(notFreeLocation);

        SimpleLocation result = testedObject.findFreeLocation(testedObject.getArena(), 100, 100);

        assertThat(result).isEqualTo(freeLocation);
    }



    @ParameterizedTest
    @CsvSource({
            "-1,0,0b0001",
            "0,-1,0b0010",
           "-1,-1,0b0011",
             "1,0,0b0100",
            "1,-1,0b0110",
             "0,1,0b1000",
            "-1,1,0b1001",
             "1,1,0b1100",
    })
    public void testMarkPosition(int dx, int dy, String sExpectedMarkPosition) {
        NeuralNetworkSimpleObject testedObject = new NeuralNetworkSimpleObject();
        int expectedMarkPosition = Integer.parseInt(sExpectedMarkPosition.substring(2), 2);

        int markPosition = testedObject.markPosition(dx, dy);

        AssertionsForClassTypes.assertThat(markPosition).isEqualTo(expectedMarkPosition);
    }
}