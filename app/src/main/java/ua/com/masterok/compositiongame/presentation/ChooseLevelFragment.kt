package ua.com.masterok.compositiongame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ua.com.masterok.compositiongame.databinding.FragmentChooseLevelBinding
import ua.com.masterok.compositiongame.domain.entity.Level


class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            buttonLevelTest.setOnClickListener {
                launchGameLevel(Level.TEST)
            }
            buttonLevelEasy.setOnClickListener {
                launchGameLevel(Level.EASY)
            }
            buttonLevelNormal.setOnClickListener {
                launchGameLevel(Level.NORMAL)
            }
            buttonLevelHard.setOnClickListener {
                launchGameLevel(Level.HARD)
            }
        }
    }

    private fun launchGameLevel(level: Level) {
        findNavController().navigate(
            ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}