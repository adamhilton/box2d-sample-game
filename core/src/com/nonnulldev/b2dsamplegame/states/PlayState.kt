package com.nonnulldev.b2dsamplegame.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef
import com.nonnulldev.b2dsamplegame.managers.GameStateManager
import com.nonnulldev.b2dsamplegame.utils.Contants.PPM
import com.nonnulldev.b2dsamplegame.utils.TiledObjectUtil

class PlayState(gameStateManager: GameStateManager) : GameState(gameStateManager) {

    private var map: TiledMap = TmxMapLoader().load("maps/test_map.tmx")
    private var b2dr: Box2DDebugRenderer = Box2DDebugRenderer()
    private var world: World = World(Vector2(0f, -9.8f), false)

    private lateinit var body1: Body
    private lateinit var body2: Body
    private lateinit var body3: Body

    init {

        initBodies()

        TiledObjectUtil.parseTiledObjectLayer(world, map.layers.get("collision-layer").objects)
    }

    private fun initBodies() {
        body1 = createBox(140, 180, 32, 32, false, true)
        body2 = createBox(140, 70, 64, 20, true, true)
        body3 = createCircle(140, 300, 12f, false)

        createBox(168, 120, 16, 64, true, true)
        createBox(112, 120, 16, 64, true, true)


        val dDef = DistanceJointDef()
        dDef.bodyA = body1
        dDef.bodyB = body2
        dDef.length = 64 / PPM
        dDef.collideConnected = false
        dDef.frequencyHz = 3f
        dDef.dampingRatio = 0.5f
        world.createJoint(dDef)

    }


    override fun update(delta: Float) {
        world.step(1 / 60f, 6, 2)

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            body3.applyForceToCenter(0f, -200f, true)
        }

        cameraUpdate()
        batch.projectionMatrix = camera.combined
    }

    override fun render() {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        b2dr.render(world, camera.combined.scl(PPM))
    }

    override fun dispose() {
        b2dr.dispose()
        world.dispose()
        map.dispose()

    }

    private fun cameraUpdate() {
        val position = camera.position
        position.x = camera.position.x + (body1.position.x * PPM - camera.position.x) * 0.1f
        position.y = camera.position.y + (body1.position.y * PPM - camera.position.y) * 0.1f
        camera.position.set(position)

        camera.update()
    }


    private fun createBox(x: Int, y: Int, width: Int, height: Int, isStatic: Boolean, fixedRotation: Boolean): Body {
        val body: Body
        val def = BodyDef()

        if(isStatic)
            def.type = BodyDef.BodyType.StaticBody
        else
            def.type = BodyDef.BodyType.DynamicBody

        def.position.set(x / PPM, y / PPM)
        def.fixedRotation = fixedRotation
        body = world.createBody(def)

        val shape = PolygonShape()
        shape.setAsBox(width / 2f / PPM, height / 2f / PPM)

        body.createFixture(shape, 1.0f)
        shape.dispose()
        return body
    }

    private fun createCircle(x: Int, y: Int, radius: Float, isStatic: Boolean): Body {
        val body: Body
        val def = BodyDef()

        if (isStatic)
            def.type = BodyDef.BodyType.StaticBody
        else
            def.type = BodyDef.BodyType.DynamicBody

        def.position.set(x / PPM, y / PPM)
        def.fixedRotation = false
        body = world.createBody(def)

        val shape = CircleShape()
        shape.radius = radius / PPM

        body.createFixture(shape, 1.0f)
        shape.dispose()
        return body
    }
}
