package com.thm.aiarena.controller;

import com.thm.aiarena.event.GameUpdate;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {
    GameUpdate provideUpdate() {
        return new GameUpdate();
    }

}
