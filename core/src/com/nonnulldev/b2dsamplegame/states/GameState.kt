package com.nonnulldev.b2dsamplegame.states

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.nonnulldev.b2dsamplegame.B2DSampleGame
import com.nonnulldev.b2dsamplegame.managers.GameStateManager

abstract class GameState(gameStateManager: GameStateManager) {

    protected val app: B2DSampleGame = gameStateManager.app
    protected val batch: SpriteBatch = app.batch
    protected val camera: OrthographicCamera = app.camera

    fun resize(w: Int, h: Int) {
        camera.setToOrtho(false, w.toFloat(), h.toFloat())
    }

    abstract fun update(delta: Float)
    abstract fun render()
    abstract fun dispose()
}
