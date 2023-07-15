package com.kennycason.war.font

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator

object TTFLoader {

    fun load(resource: String, size: Int) : BitmapFont {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("ttf/$resource"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.size = size
        val font = generator.generateFont(parameter)
        generator.dispose() // don't forget to dispose to avoid memory leaks!
        return font
    }

}