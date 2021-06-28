package com.shishkin.itransition.gui.nba

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.gui.utils.CustomViewTypeItemDecoration
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@InternalCoroutinesApi

class NbaFragment : DaggerFragment(), NbaPlayerPaginationItemListener {
    //            TODO for Room + Paging 3
//class NbaFragment : DaggerFragment(), NbaPlayerItemListener {



    @Inject
    lateinit var nbaViewModelFactory: NbaViewModelFactory
    lateinit var nbaViewModel: NbaViewModel
    lateinit var nbaPlayersPaginationAdapter: NbaPlayersPaginationAdapter
//    lateinit var nbaPlayersAdapter: NbaPlayersAdapter

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

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {

            nbaViewModel.fetchPlayersPagination().collectLatest { pagingData ->
                nbaPlayersPaginationAdapter.submitData(pagingData)
            }

//            TODO for Room + Paging 3
//            nbaViewModel.fetchPlayersDb().collectLatest {
//                    pagingData ->
//                nbaPlayersAdapter.submitData(pagingData)
//            }

        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initNbaPlayersRecyclerView() {
        nbaPlayersRecyclerView = view?.findViewById(R.id.rv_nba_players)!!
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        nbaPlayersRecyclerView.layoutManager = linearLayoutManager

        nbaPlayersPaginationAdapter =
            NbaPlayersPaginationAdapter(this@NbaFragment)
        nbaPlayersRecyclerView.adapter = nbaPlayersPaginationAdapter

        nbaPlayersRecyclerView.addItemDecoration(
            CustomViewTypeItemDecoration(nbaPlayersRecyclerView.context,
            linearLayoutManager.orientation,resources.getDrawable(R.drawable.divider_drawable))
        )

//            TODO for Room + Paging 3
//        nbaPlayersAdapter = NbaPlayersAdapter(this@NbaFragment)
//        nbaPlayersRecyclerView.adapter = nbaPlayersAdapter


    }

    override fun onClickedNbaPlayer(nbaPlayerId: Int) {
        val bundle = Bundle()
        bundle.putInt("id", nbaPlayerId)
        findNavController().navigate(R.id.action_nbaFragment_to_nbaDetailsFragment, bundle)
    }
}