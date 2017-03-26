package com.nonnulldev.b2dsamplegame.utils

import com.badlogic.gdx.maps.MapObjects
import com.badlogic.gdx.maps.objects.PolylineMapObject
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*

object TiledObjectUtil {
    fun parseTiledObjectLayer(world: World, objects: MapObjects) {
        objects.forEach {
            val shape: Shape
            if (it is PolylineMapObject) {
                shape = createPolyline(it)

                val body: Body
                val bdef = BodyDef()
                bdef.type = BodyDef.BodyType.StaticBody
                body = world.createBody(bdef)
                body.createFixture(shape, 1.0f)
                shape.dispose()
            } else {

            }

        }
    }

    private fun createPolyline(polylineMapObject: PolylineMapObject): Shape {
        val vertices = polylineMapObject.polyline.transformedVertices
        val worldVertices = Array(vertices.size / 2, { Vector2() })

        for (i in 0..worldVertices.lastIndex) {
            worldVertices[i] = Vector2(
                    vertices[i * 2] / Contants.PPM,
                    vertices[i * 2 + 1] / Contants.PPM)
        }

        val cs = ChainShape()
        cs.createChain(worldVertices)
        return cs
    }
}