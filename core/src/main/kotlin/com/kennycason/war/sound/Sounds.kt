package com.kennycason.war.sound

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound

object Sounds {
    const val SOUND_PATH = "sound/"

    val TECHNO_TURDLE: Music by lazy { Gdx.audio.newMusic(Gdx.files.internal(SOUND_PATH + "techno_turdle.ogg")) }

    object Menu {
        val MENU_CHANGE: Sound = Gdx.audio.newSound(Gdx.files.internal(SOUND_PATH + "fx/menu_change.ogg"))
    }

    fun dispose() {
    }

}
