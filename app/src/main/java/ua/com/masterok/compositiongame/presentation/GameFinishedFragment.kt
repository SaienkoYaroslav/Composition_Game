package ua.com.masterok.compositiongame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ua.com.masterok.compositiongame.R
import ua.com.masterok.compositiongame.databinding.FragmentGameFinishedBinding
import ua.com.masterok.compositiongame.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    private val argsGameResult by navArgs<GameFinishedFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRetry.setOnClickListener {
            retryGame()
        }

        bindingView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun retryGame() {
        findNavController().popBackStack()
    }

    private fun bindingView() {
        binding.emojiResult.setImageResource(getEmoticon())

        binding.tvRequiredAnswers.text = String.format(
            getString(R.string.count_right_answer),
            argsGameResult.gameResult.gameSettings.minCountOfRightAnswer.toString()
        )

        binding.tvScoreAnswers.text = String.format(
                getString(R.string.result),
                argsGameResult.gameResult.countOfRightAnswers.toString()
            )

        binding.tvRequiredPercentage.text = String.format(
            getString(R.string.percent_right_answer),
            argsGameResult.gameResult.gameSettings.minPercentOfRightAnswer.toString()
        )

        binding.tvScorePercentage.text = String.format(
            getString(R.string.score_percentage),
            getPercentOfRightAnswers().toString()
        )

    }

    private fun getPercentOfRightAnswers(): Int {
        return if (argsGameResult.gameResult.countOfQuestions == 0) {
            0
        } else {
            (argsGameResult.gameResult.countOfRightAnswers * 100) / argsGameResult.gameResult.countOfQuestions
        }
    }

    private fun getEmoticon(): Int {
        return if (argsGameResult.gameResult.winner) {
            R.drawable.ic_smile
        } else {
            R.drawable.ic_sad
        }
    }


}