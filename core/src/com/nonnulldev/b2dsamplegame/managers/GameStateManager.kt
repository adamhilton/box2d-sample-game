package com.nonnulldev.b2dsamplegame.managers

import com.nonnulldev.b2dsamplegame.B2DSampleGame
import com.nonnulldev.b2dsamplegame.states.GameState
import com.nonnulldev.b2dsamplegame.states.PlayState
import com.nonnulldev.b2dsamplegame.states.SplashState
import java.util.*

class GameStateManager(val app: B2DSampleGame) {

    private val states: Stack<GameState> = Stack()

    enum class State {
        SPLASH,
        PLAY
    }

    init {
        setState(State.PLAY)
    }

    fun update(delta: Float) {
        states.peek().update(delta)
    }

    fun render() {
        states.peek().render()
    }

    fun dispose() {
        states.forEach(GameState::dispose)
        states.clear()
    }

    fun resize(w: Int, h: Int) {
        states.peek().resize(w, h)
    }

    fun setState(state: State) {
        if (states.size >= 1) {
            states.pop().dispose()
        }
        states.push(getGameState(state))
    }

    fun getGameState(state: State): GameState {
        when (state) {
            State.SPLASH -> return SplashState(this)
            State.PLAY -> return PlayState(this)
            else -> return SplashState(this)
        }
    }
}
