package com.kennycason.war.sound

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound

class SoundManager(
    var musicVolume: Int = 100,
    var effectsVolume: Int = 100
) {

    private var music: Music = Sounds.TECHNO_TURDLE

    fun playSound(sound: Sound) = sound.play(effectsVolume / 100f)

    fun loopSound(sound: Sound) = sound.loop(effectsVolume / 100f)

    fun stopSound(sound: Sound, id: Long) = sound.stop(id)

    fun playMusic() {
        if (isPlaying(music)) {
            return
        }

        music.volume = musicVolume / 100f
        music.isLooping = true
        music.play()
    }


    fun stopMusic() {
        if (!isPlaying(music)) {
            return
        }
        music.stop()
    }

    private fun isPlaying(music: Music?) = music != null && music.isPlaying

}