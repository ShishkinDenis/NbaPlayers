package com.shishkin.itransition.gui.games

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.gui.nba.NbaGamesUiState
import com.shishkin.itransition.gui.nba.lists.ListItem
import com.shishkin.itransition.network.entities.NbaGame
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class NbaGamesFragment : DaggerFragment(), NbaGameItemListener {

    @Inject
    lateinit var nbaGamesViewModelFactory: NbaGamesViewModelFactory
    lateinit var nbaGamesViewModel: NbaGamesViewModel

    private lateinit var nbaGamesRecyclerView: RecyclerView

    companion object {
        const val VIEW_TYPE_NBA_GAME = 1
        const val VIEW_TYPE_NBA_TEAM = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nbaGamesViewModel =
            ViewModelProviders.of(this, nbaGamesViewModelFactory).get(NbaGamesViewModel::class.java)
        initNbaGamesRecyclerView()
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                nbaGamesViewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is NbaGamesUiState.Success -> {
                            Log.d(
                                "Retrofit",
                                "NbaGamesFragment: size " + uiState.nbaGames?.data?.size.toString()
                            )
                            val listOfNbaGames = uiState.nbaGames?.data
                            val convertedList = convertList(listOfNbaGames)
                            val nbaGamesAdapter =
                                NbaGamesAdapter(convertedList, this@NbaGamesFragment)
                            nbaGamesAdapter.submitList(convertedList)
                            nbaGamesRecyclerView.adapter = nbaGamesAdapter
                        }
                        is NbaGamesUiState.Error -> {
                            Log.d("Retrofit", "NbaGamesFragment: Error")
                        }
                        is NbaGamesUiState.Loading -> {
                            Log.d("Retrofit", "NbaGamesFragment: Loading")
                        }
                        is NbaGamesUiState.Empty -> {
                            Log.d("Retrofit", "NbaGamesFragment: Empty")
                        }
                    }
                }
            }
        }
    }

    private fun initNbaGamesRecyclerView() {
        nbaGamesRecyclerView = view?.findViewById(R.id.rv_nba_games)!!
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        nbaGamesRecyclerView.layoutManager = linearLayoutManager
    }

    override fun onClickedNbaGame(nbaGame: NbaGame) {
        val bundle = Bundle()
        bundle.putParcelable("nbaGame", nbaGame)
        findNavController().navigate(R.id.action_gamesFragment_to_gamesDetailsFragment, bundle)
    }

    private fun convertList(listOfNbaGames: List<NbaGame>?): List<ListItem> {
        val listOfListItem = ArrayList<ListItem>()
        for (item in listOfNbaGames!!) {
            val nbaPlayerListItem = ListItem(item, VIEW_TYPE_NBA_GAME)
            listOfListItem.add(nbaPlayerListItem)
        }
        return listOfListItem
    }

}