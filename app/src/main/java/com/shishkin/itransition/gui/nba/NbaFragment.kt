package com.shishkin.itransition.gui.nba

import android.annotation.SuppressLint
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
import com.shishkin.itransition.gui.nba.NbaPlayersUiState.*
import com.shishkin.itransition.network.entities.ListItem
import com.shishkin.itransition.network.entities.NbaPlayer
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@InternalCoroutinesApi
class NbaFragment : DaggerFragment(), NbaPlayerItemListener {

    @Inject
    lateinit var nbaViewModelFactory: NbaViewModelFactory
    lateinit var nbaViewModel: NbaViewModel
//    lateinit var nbaPlayersListAdapter: NbaPlayersListAdapter
    lateinit var nbaPlayersAdapter: NbaPlayersAdapter
    private lateinit var nbaPlayersRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nba, container, false)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nbaViewModel =
            ViewModelProviders.of(this, nbaViewModelFactory).get(NbaViewModel::class.java)
        initNbaPlayersRecyclerView()

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                nbaViewModel.uiState.collectLatest {
                        uiState ->
                    when (uiState) {
                        is Success -> {
                            val list = uiState.nbaPlayers?.data
                            nbaPlayersAdapter.submitList(list)
                        }
                        is Error -> {
                            Log.d("Retrofit", "NbaFragment: Error")
                        }
                        is Loading -> {
                            Log.d("Retrofit", "NbaFragment: Loading")
                        }
                        is Empty -> {
                            Log.d("Retrofit", "NbaFragment: Empty")
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initNbaPlayersRecyclerView() {
        nbaPlayersRecyclerView = view?.findViewById(R.id.rv_nba_players)!!
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        nbaPlayersRecyclerView.layoutManager = linearLayoutManager

//        TODO for multiType
//        nbaPlayersListAdapter =
//            NbaPlayersListAdapter(this@NbaFragment)
//        nbaPlayersRecyclerView.adapter = nbaPlayersListAdapter

        nbaPlayersAdapter =
            NbaPlayersAdapter(this@NbaFragment)
        nbaPlayersRecyclerView.adapter = nbaPlayersAdapter

//        TODO for multiType
//        nbaPlayersRecyclerView.addItemDecoration(
//            CustomViewTypeItemDecoration(nbaPlayersRecyclerView.context,
//            linearLayoutManager.orientation,resources.getDrawable(R.drawable.divider_drawable))
//        )

    }

    override fun onClickedNbaPlayer(nbaPlayerId: Int) {
        val bundle = Bundle()
        bundle.putInt("id", nbaPlayerId)
        findNavController().navigate(R.id.action_nbaFragment_to_nbaDetailsFragment, bundle)
    }

    private fun convertList(listOfNbaPlayers: List<NbaPlayer>): List<ListItem> {
        val VIEW_TYPE_NBA_PLAYER = 1
        val VIEW_TYPE_NBA_TEAM = 2

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