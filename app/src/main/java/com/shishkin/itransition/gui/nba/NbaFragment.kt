package com.shishkin.itransition.gui.nba

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.NbaPlayersUiState
import com.shishkin.itransition.R
import com.shishkin.itransition.gui.nba.lists.adapters.NbaPlayersAdapter
import com.shishkin.itransition.utils.MyViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
class NbaFragment : DaggerFragment() {

    // TODO   view/data binding
    lateinit var button: Button

    @Inject
    lateinit var myViewModelFactory: MyViewModelFactory
    lateinit var nbaViewModel: NbaViewModel

    lateinit var testRecycler: RecyclerView

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

        button = view.findViewById(R.id.btnGoToNbaPlayerInfo)
        button.setOnClickListener {
            findNavController().navigate(R.id.action_nbaFragment_to_nbaDetailsFragment)
        }

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                nbaViewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is NbaPlayersUiState.Success -> {
                            Log.d("Retrofit", uiState.nbaPlayers?.data?.size.toString())
                            Log.d(
                                "Retrofit",
                                uiState.nbaPlayers?.data?.get(1)?.first_name.toString()
                            )
                            val list = uiState.nbaPlayers?.data
                            val nbaPlayersAdapter = NbaPlayersAdapter(list)
                            testRecycler.adapter = nbaPlayersAdapter
                        }
                        is NbaPlayersUiState.Error -> {
                        }
                        is NbaPlayersUiState.Loading -> {
                        }
                        is NbaPlayersUiState.Empty -> {
                        }

//                        Flow
//                    (it as NbaPlayersUiState.Success).let {
//                        val list : List<NbaPlayer>? =  it.nbaPlayers?.getNbaPlayersData()
//                        Log.d("Retrofit", it.nbaPlayers?.getNbaPlayersData()?.size.toString())
//                        Log.d("Retrofit", list?.get(1)?.getName().toString())
//                    }

                    }
                }
            }
        }

    }

    private fun initRecyclerView() {
        testRecycler = view?.findViewById<RecyclerView>(R.id.nba_rv)!!
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        testRecycler.layoutManager = linearLayoutManager

    }
}