package com.nonnulldev.b2dsamplegame

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.nonnulldev.b2dsamplegame.managers.GameStateManager

class B2DSampleGame : ApplicationAdapter() {

    private val DEBUG = false

    private val TITLE = "Tutorial"
    private val V_WIDTH = 720
    private val V_HEIGHT = 480
    private val SCALE = 2.0f

    private lateinit var gameStateManager: GameStateManager

    lateinit var camera: OrthographicCamera
    lateinit var batch: SpriteBatch

    override fun create() {
        val w = Gdx.graphics.width
        val h = Gdx.graphics.height

        batch = SpriteBatch()

        camera = OrthographicCamera()
        camera.setToOrtho(false, w / SCALE, h / SCALE)

        gameStateManager = GameStateManager(this)
    }

    override fun render() {
        gameStateManager.update(Gdx.graphics.deltaTime)
        gameStateManager.render()

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit()
    }

    override fun resize(width: Int, height: Int) {
        gameStateManager.resize(((width / SCALE).toInt()), ((height / SCALE).toInt()))
    }

    override fun dispose() {
        gameStateManager.dispose()
        batch.dispose()
    }
}
