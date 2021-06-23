package com.shishkin.itransition.gui.nba

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.gui.nba.lists.ListItem
import com.shishkin.itransition.gui.nba.lists.withoutpagination.NbaPlayersAdapter
import com.shishkin.itransition.gui.nba.lists.pagination.NbaPlayersPaginationAdapter
import com.shishkin.itransition.network.entities.NbaPlayer
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
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
    lateinit var nbaPlayersPaginationAdapter: NbaPlayersPaginationAdapter

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

        /*    lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                nbaViewModel.uiState.collect { uiState ->

                    when (uiState) {
                        is NbaPlayersUiState.Success -> {
                            Log.d(
                                "Retrofit",
                                "NbaFragment: size " + uiState.nbaPlayers?.data?.size.toString()
                            )
                            val listOfNbaPlayers = uiState.nbaPlayers?.data
                            val convertedList = listOfNbaPlayers?.let { convertList(it) }
//                            TODO remove list from constructor
                            val nbaPlayersAdapter =
                                NbaPlayersAdapter(convertedList, this@NbaFragment)
                            nbaPlayersAdapter.submitList(convertedList)
                            nbaPlayersRecyclerView.adapter = nbaPlayersAdapter
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

        }*/

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            nbaViewModel.fetchPlayersPagination().collectLatest { pagingData ->
                nbaPlayersPaginationAdapter.submitData(pagingData)
            }
        }
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initNbaPlayersRecyclerView() {
        nbaPlayersRecyclerView = view?.findViewById(R.id.rv_nba_players)!!
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        nbaPlayersRecyclerView.layoutManager = linearLayoutManager

        val itemDecor = DividerItemDecoration(nbaPlayersRecyclerView.context, VERTICAL)
        itemDecor.setDrawable(resources.getDrawable(R.drawable.divider_drawable))
        nbaPlayersRecyclerView.addItemDecoration(itemDecor)

        nbaPlayersPaginationAdapter =
            NbaPlayersPaginationAdapter()
        nbaPlayersRecyclerView.adapter = nbaPlayersPaginationAdapter

//        TODO divider every two items
//        nbaPlayersRecyclerView.addItemDecoration(
//            CustomPositionItemDecoration(context?.getDrawable(R.drawable.divider_drawable)!!)
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