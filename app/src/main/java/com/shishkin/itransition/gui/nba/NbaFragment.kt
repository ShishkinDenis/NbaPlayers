package com.shishkin.itransition.gui.nba

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.gui.nba.lists.NbaPlayersAdapter
import com.shishkin.itransition.utils.MyViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
class NbaFragment : DaggerFragment(),NbaPlayersAdapter.NbaPlayerItemListener {
    @Inject
    lateinit var myViewModelFactory: MyViewModelFactory
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
        nbaViewModel = ViewModelProviders.of(this, myViewModelFactory).get(NbaViewModel::class.java)
        initRecyclerView()

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                nbaViewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is NbaPlayersUiState.Success -> {
//                            Log.d("Retrofit", uiState.nbaPlayers?.data?.size.toString())
                            val list = uiState.nbaPlayers?.data
                            val nbaPlayersAdapter = NbaPlayersAdapter(list,this@NbaFragment)
                            testRecycler.adapter = nbaPlayersAdapter
                        }
                        is NbaPlayersUiState.Error -> {
                        }
                        is NbaPlayersUiState.Loading -> {
                        }
                        is NbaPlayersUiState.Empty -> {
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
        findNavController().navigate(R.id.action_nbaFragment_to_nbaDetailsFragment, bundleOf("id" to nbaPlayerId))
    }
}