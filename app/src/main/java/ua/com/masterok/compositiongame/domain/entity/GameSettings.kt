package ua.com.masterok.compositiongame.domain.entity

data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightAnswer: Int,
    val minPercentOfRightAnswer: Int,
    val gameTimeInSeconds: Int
)