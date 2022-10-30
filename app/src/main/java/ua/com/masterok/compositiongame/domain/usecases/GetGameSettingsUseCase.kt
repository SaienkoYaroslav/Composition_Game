package ua.com.masterok.compositiongame.domain.usecases

import ua.com.masterok.compositiongame.domain.entity.GameSettings
import ua.com.masterok.compositiongame.domain.entity.Level
import ua.com.masterok.compositiongame.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }

}