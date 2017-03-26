package com.nonnulldev.b2dsamplegame

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.nonnulldev.b2dsamplegame.utils.Contants

class B2DSampleGame : ApplicationAdapter() {

    private val DEBUG = false

    private lateinit var camera: OrthographicCamera

    private lateinit var b2dr: Box2DDebugRenderer
    private lateinit var world: World
    private lateinit var player: Body
    private lateinit var platform: Body

    override fun create() {
        val w = Gdx.graphics.width
        val h = Gdx.graphics.height

        camera = OrthographicCamera()
        camera.setToOrtho(false, w / 2f, h / 2f)

        world = World(Vector2(0f, -9.8f), false)
        b2dr = Box2DDebugRenderer()

        player = createBox(8, 10, 32, 32, false)
        platform = createBox(0, 0, 64, 32, true)
    }

    private fun createBox(x: Int, y: Int, width: Int, height: Int, isStatic: Boolean): Body {
        val body: Body
        val def: BodyDef = BodyDef()

        if(isStatic)
            def.type = BodyDef.BodyType.StaticBody
        else
            def.type = BodyDef.BodyType.DynamicBody

        def.position.set(x / Contants.PPM, y / Contants.PPM)
        def.fixedRotation = true
        body = world.createBody(def)

        val shape = PolygonShape()
        shape.setAsBox(width / 2f / Contants.PPM, height / 2f / Contants.PPM)

        body.createFixture(shape, 1.0f)
        shape.dispose()
        return body
    }

    override fun render() {
        update(Gdx.graphics.deltaTime)

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        b2dr.render(world, camera.combined.scl(Contants.PPM))

    }

    private fun update(delta: Float) {
        world.step(1 / 60f, 6, 2)

        inputUpdate(delta)
        cameraUpdate(delta)
    }

    private fun inputUpdate(delta: Float) {
        var horizontalForce = 0

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            horizontalForce -= 1
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            horizontalForce += 1
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.applyForceToCenter(0f, 300f, false)
        }

        player.setLinearVelocity(horizontalForce * 5f, player.linearVelocity.y)
    }

    private fun cameraUpdate(delta: Float) {
        val position = camera.position
        position.x = player.position.x * Contants.PPM
        position.y = player.position.y * Contants.PPM
        camera.position.set(position)

        camera.update()
    }

    override fun resize(width: Int, height: Int) {
        camera.setToOrtho(false, width / 2f, height / 2f)
    }

    override fun dispose() {
        world.dispose()
        b2dr.dispose()
    }
}
