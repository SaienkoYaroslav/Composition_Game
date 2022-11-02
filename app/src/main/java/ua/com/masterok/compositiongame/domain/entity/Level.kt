package ua.com.masterok.compositiongame.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// енам класи не явно реалізують інтерфейс Серіалайзбл
@Parcelize
enum class Level: Parcelable {

    TEST, EASY, NORMAL, HARD

}