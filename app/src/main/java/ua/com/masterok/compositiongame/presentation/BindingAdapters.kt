package ua.com.masterok.compositiongame.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import ua.com.masterok.compositiongame.R
import ua.com.masterok.compositiongame.domain.entity.GameResult

interface OnOptionClickListener {

    fun onOptionClick(option: Int)
}

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.count_right_answer),
        count
    )
}

@BindingAdapter("scoreAnswers")
fun bindScoreAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.result),
        count
    )
}

@BindingAdapter("requiredPercentage")
fun bindRequiredPercentage(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.percent_right_answer),
        count
    )
}

@BindingAdapter("scorePercentage")
fun bindScorePercentage(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.score_percentage),
        getPercentOfRightAnswers(gameResult).toString()
    )
}

private fun getPercentOfRightAnswers(gameResult: GameResult): Int {
    return if (gameResult.countOfQuestions == 0) {
        0
    } else {
        (gameResult.countOfRightAnswers * 100) / gameResult.countOfQuestions
    }
}

@BindingAdapter("emojiResult")
fun bindEmojiResult(imageView: ImageView, winner: Boolean) {
    imageView.setImageResource(getEmoticon(winner))
}

private fun getEmoticon(winner: Boolean): Int {
    return if (winner) {
        R.drawable.ic_smile
    } else {
        R.drawable.ic_sad
    }
}

@BindingAdapter("progressBar")
fun bindProgressBar(progressBar: ProgressBar, count: Int) {
    progressBar.setProgress(count, true)
}

@BindingAdapter("colorCountOfRightAnswers")
fun bindColorCountOfRightAnswers(textView: TextView, goodState: Boolean) {
    textView.setTextColor(getColorByState(textView.context, goodState))
}

private fun getColorByState(context: Context, goodState: Boolean): Int {
    val colorResId = if (goodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResId)
}

@BindingAdapter("colorProgressBar")
fun bindColorProgressBar(progressBar: ProgressBar, goodState: Boolean) {
    val color = getColorByState(progressBar.context, goodState)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

@BindingAdapter("numberAsText")
fun bindNumberAsText(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}