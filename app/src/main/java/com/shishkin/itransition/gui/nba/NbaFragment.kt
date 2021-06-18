package com.shishkin.itransition.gui.nba

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
import com.shishkin.itransition.gui.nba.lists.ListItem
import com.shishkin.itransition.gui.nba.lists.NbaPlayersAdapter
import com.shishkin.itransition.network.entities.NbaPlayer
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
class NbaFragment : DaggerFragment(), NbaPlayersAdapter.NbaPlayerItemListener {

    companion object {
        const val VIEW_TYPE_NBA_PLAYER = 1
        const val VIEW_TYPE_NBA_TEAM = 2
    }

    @Inject
    lateinit var nbaViewModelFactory: NbaViewModelFactory
    lateinit var nbaViewModel: NbaViewModel

    private lateinit var testRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nba, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nbaViewModel =
            ViewModelProviders.of(this, nbaViewModelFactory).get(NbaViewModel::class.java)
        initRecyclerView()

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                nbaViewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is NbaPlayersUiState.Success -> {
                            Log.d(
                                "Retrofit",
                                "NbaFragment: size " + uiState.nbaPlayers?.data?.size.toString()
                            )
                            val listOfNbaPlayers = uiState.nbaPlayers?.data
                            val list = listOfNbaPlayers?.let { convertList(it) }
                            val nbaPlayersAdapter = NbaPlayersAdapter(list, this@NbaFragment)
                            nbaPlayersAdapter.submitList(list)
                            testRecycler.adapter = nbaPlayersAdapter
                        }
                        is NbaPlayersUiState.Error -> {
                            Log.d("Retrofit", "NbaFragment: Error")
                        }
                        is NbaPlayersUiState.Loading -> {
                            Log.d("Retrofit", "NbaFragment: Loading")
                        }
                        is NbaPlayersUiState.Empty -> {
                            Log.d("Retrofit", "NbaFragment: Empty")
                        }
                    }
                }
            }
        }

    }

    private fun initRecyclerView() {
        testRecycler = view?.findViewById(R.id.nba_rv)!!
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        testRecycler.layoutManager = linearLayoutManager
    }

    override fun onClickedNbaPlayer(nbaPlayerId: Int) {
        val bundle = Bundle()
        bundle.putInt("id", nbaPlayerId)
        findNavController().navigate(R.id.action_nbaFragment_to_nbaDetailsFragment, bundle)
    }

    private fun convertList(listOfNbaPlayers: List<NbaPlayer>): List<ListItem> {
        val listOfListItem = ArrayList<ListItem>()
        for (item in listOfNbaPlayers) {
            val nbaPlayerListItem = ListItem(item, VIEW_TYPE_NBA_PLAYER)
            val nbaTeamListItem = ListItem(item.team, VIEW_TYPE_NBA_TEAM)
            listOfListItem.add(nbaPlayerListItem)
            listOfListItem.add(nbaTeamListItem)
        }
        return listOfListItem
    }


}