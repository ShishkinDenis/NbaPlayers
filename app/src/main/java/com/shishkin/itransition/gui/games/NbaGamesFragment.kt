package com.shishkin.itransition.gui.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.shishkin.itransition.R
import com.shishkin.itransition.databinding.FragmentGamesBinding
import com.shishkin.itransition.gui.utils.CustomViewTypeItemDecoration
import com.shishkin.itransition.network.entities.NbaGame
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class NbaGamesFragment : DaggerFragment(), NbaGameItemListener {

    @Inject
    lateinit var viewModelFactory: NbaGamesViewModelFactory
    private lateinit var viewModel: NbaGamesViewModel
    private lateinit var nbaGamesAdapter: NbaGamesAdapter
    private lateinit var _binding: FragmentGamesBinding
    private val binding get() = _binding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
                ViewModelProviders.of(this, viewModelFactory).get(NbaGamesViewModel::class.java)
        initNbaGamesRecyclerView()

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.fetchGamesPagination().collectLatest { pagingData ->
                pagingData?.let { data -> nbaGamesAdapter.submitData(data) }
            }
        }
    }

    private fun initNbaGamesRecyclerView() {
        context?.let { context ->
            ContextCompat.getDrawable(context, R.drawable.divider_drawable)?.let { drawable ->
                binding.rvNbaGames.context?.let { context ->
                    CustomViewTypeItemDecoration(context, DividerItemDecoration.VERTICAL, drawable)
                }
            }?.let { customViewTypeItemDecoration ->
                binding.rvNbaGames.addItemDecoration(customViewTypeItemDecoration)
            }
        }
        nbaGamesAdapter = NbaGamesAdapter(this@NbaGamesFragment)
        binding.rvNbaGames.adapter = nbaGamesAdapter
    }

    override fun onClickedNbaGame(nbaGame: NbaGame?) {
        val bundle = Bundle()
        bundle.putParcelable(getString(R.string.arg_nba_game), nbaGame)
        findNavController().navigate(R.id.action_gamesFragment_to_gamesDetailsFragment, bundle)
    }
}