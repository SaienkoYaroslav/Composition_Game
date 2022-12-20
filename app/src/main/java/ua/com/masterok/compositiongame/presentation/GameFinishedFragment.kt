package ua.com.masterok.compositiongame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ua.com.masterok.compositiongame.R
import ua.com.masterok.compositiongame.databinding.FragmentGameFinishedBinding
import ua.com.masterok.compositiongame.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    private lateinit var gameResult: GameResult


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // перевизначити онБекПресед можна тільки в активіті, робиться це ось так
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, callback
        )

        binding.buttonRetry.setOnClickListener {
            retryGame()
        }

        bindingView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            gameResult = it
        }
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(
            GameFragment.NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    private fun bindingView() {
        binding.emojiResult.setImageResource(getEmoticon())

        binding.tvRequiredAnswers.text = String.format(
            getString(R.string.count_right_answer),
            gameResult.gameSettings.minCountOfRightAnswer.toString()
        )

        binding.tvScoreAnswers.text = String.format(
                getString(R.string.result),
                gameResult.countOfRightAnswers.toString()
            )

        binding.tvRequiredPercentage.text = String.format(
            getString(R.string.percent_right_answer),
            gameResult.gameSettings.minPercentOfRightAnswer.toString()
        )

        binding.tvScorePercentage.text = String.format(
            getString(R.string.score_percentage),
            getPercentOfRightAnswers().toString()
        )

    }

    private fun getPercentOfRightAnswers(): Int {
        return if (gameResult.countOfQuestions == 0) {
            0
        } else {
            (gameResult.countOfRightAnswers * 100) / gameResult.countOfQuestions
        }
    }

    private fun getEmoticon(): Int {
        return if (gameResult.winner) {
            R.drawable.ic_smile
        } else {
            R.drawable.ic_sad
        }
    }

    companion object {

        private const val KEY_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }

    }

}