package com.nonnulldev.b2dsamplegame.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.nonnulldev.b2dsamplegame.managers.GameStateManager

class SplashState(private val gameStateManager: GameStateManager) : GameState(gameStateManager) {

    private var acc = 0f
    private val texture: Texture = Texture("images/splash.png")

    override fun update(delta: Float) {
        acc += delta
        if (acc >= 3) {
            gameStateManager.setState(GameStateManager.State.PLAY)
        }
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.projectionMatrix = camera.combined
        batch.begin()
        batch.draw(texture,
                Gdx.graphics.width / 4f - texture.width,
                Gdx.graphics.height / 4f - texture.height)
        batch.end()
    }

    override fun dispose() {
        texture.dispose()
    }
}
