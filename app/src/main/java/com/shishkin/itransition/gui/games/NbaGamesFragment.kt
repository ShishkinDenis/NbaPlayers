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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.network.entities.NbaGame
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class NbaGamesFragment : DaggerFragment(), NbaGameItemListener {

    @Inject
    lateinit var viewModelFactory: NbaGamesViewModelFactory
    private lateinit var viewModel: NbaGamesViewModel
    private lateinit var nbaGamesAdapter: NbaGamesAdapter
    private var nbaGamesRecyclerView: RecyclerView? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
                ViewModelProviders.of(this, viewModelFactory).get(NbaGamesViewModel::class.java)
        initNbaGamesRecyclerView()

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.fetchGamesPagination().collectLatest { pagingData ->
                pagingData?.let { nbaGamesAdapter.submitData(it) }
            }
        }
    }

    private fun initNbaGamesRecyclerView() {
        nbaGamesRecyclerView = view?.findViewById(R.id.rv_nba_games)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        nbaGamesRecyclerView?.layoutManager = linearLayoutManager

        val itemDecor = DividerItemDecoration(nbaGamesRecyclerView?.context, DividerItemDecoration.VERTICAL)
        context?.let {
            ContextCompat.getDrawable(it,R.drawable.divider_drawable)?.let {
                itemDecor.setDrawable(it)
            }
        }
        nbaGamesRecyclerView?.addItemDecoration(itemDecor)
        nbaGamesAdapter = NbaGamesAdapter(this@NbaGamesFragment)
        nbaGamesRecyclerView?.adapter = nbaGamesAdapter
    }

    override fun onClickedNbaGame(nbaGame: NbaGame) {
        val bundle = Bundle()
        bundle.putParcelable(getString(R.string.nba_game), nbaGame)
        findNavController().navigate(R.id.action_gamesFragment_to_gamesDetailsFragment, bundle)
    }
}