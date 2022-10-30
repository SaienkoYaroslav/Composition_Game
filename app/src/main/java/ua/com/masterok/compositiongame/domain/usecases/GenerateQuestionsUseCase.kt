package ua.com.masterok.compositiongame.domain.usecases

import ua.com.masterok.compositiongame.domain.entity.Question
import ua.com.masterok.compositiongame.domain.repository.GameRepository

class GenerateQuestionsUseCase(
    private val repository: GameRepository
) {

    // перевизначення методу invoke дозволяє викликати об‘єкт GenerateQuestionsUseCase, як метод
    // (generateQuestionsUseCase(), або явно вказуючи generateQuestionsUseCase.invoke()). Робиться,
    // коли у класа є тільки один метод, який буде називаєтись так само як клас. Ідеально підходить
    // для UseCases
    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {
        private const val COUNT_OF_OPTIONS = 6
    }

}