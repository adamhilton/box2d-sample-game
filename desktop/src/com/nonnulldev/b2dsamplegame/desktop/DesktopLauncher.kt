package com.nonnulldev.b2dsamplegame.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.nonnulldev.b2dsamplegame.B2DSampleGame

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.width = 720
        config.height = 480
        config.backgroundFPS = 60
        config.foregroundFPS = 60
        LwjglApplication(B2DSampleGame(), config)
    }
}
