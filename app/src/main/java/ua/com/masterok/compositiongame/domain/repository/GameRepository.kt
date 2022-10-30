package ua.com.masterok.compositiongame.domain.repository

import ua.com.masterok.compositiongame.domain.entity.GameSettings
import ua.com.masterok.compositiongame.domain.entity.Level
import ua.com.masterok.compositiongame.domain.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOgOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings

}