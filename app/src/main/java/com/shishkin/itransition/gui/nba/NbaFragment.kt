package com.shishkin.itransition.gui.nba

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shishkin.itransition.R
import com.shishkin.itransition.gui.utils.CustomViewTypeItemDecoration
import com.shishkin.itransition.network.entities.NbaPlayersMapper
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@InternalCoroutinesApi
class NbaFragment : DaggerFragment(), NbaPlayerItemListener {

    // TODO Evgeny можно обойтись наименованиями обычными: viewModelFactory, viewModel
    // По тому что это NBaFragment и так понятно,

    @Inject
    lateinit var nbaViewModelFactory: NbaViewModelFactory

    lateinit var nbaViewModel: NbaViewModel
    lateinit var nbaPlayersListAdapter: NbaPlayersListAdapter

    // TODO Evgeny убрать все findViewById() и разобраться во ViewBinding: https://developer.android.com/topic/libraries/view-binding

    private lateinit var nbaPlayersRecyclerView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nba, container, false)
    }

    // TODO Evgeny зачем тут Supress Lint?
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nbaViewModel =
                ViewModelProviders.of(this, nbaViewModelFactory).get(NbaViewModel::class.java)
        initNbaPlayersRecyclerView()

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                nbaViewModel.playersState.collectLatest { uiState ->
                    uiState.fold(
                            onLoading = {
                                // TODO Evgeny Логирование лучше делать не через Log.d и прочие,
                                // А подключить либу: Timber.
                                Log.d("Retrofit", "NbaFragment: Loading")
                            },
                            onSuccess = {
                                if (it.isNullOrEmpty()) {
                                    Log.d("Retrofit", "NbaFragment: Empty")
                                } else {
                                    nbaPlayersListAdapter.submitList(NbaPlayersMapper().invoke(it))
                                }
                            },
                            onError = { throwable, message ->
                                Log.d("Retrofit", "NbaFragment: Error: " + message)
                            }
                    )
                }
            }
        }
    }

    // TODO Evgeny зачем добавлял? Не ндао ставить заглушки на ругань @SuppressLint("UseCompatLoadingForDrawables")
    private fun initNbaPlayersRecyclerView() {
        // TODO Evgeny: Удалить все !! из приложения. !! - это плохо и зло
        nbaPlayersRecyclerView = view?.findViewById(R.id.rv_nba_players)!!
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        nbaPlayersRecyclerView.layoutManager = linearLayoutManager

        nbaPlayersListAdapter =
                NbaPlayersListAdapter(this@NbaFragment)
        nbaPlayersRecyclerView.adapter = nbaPlayersListAdapter

        nbaPlayersRecyclerView.addItemDecoration(
                CustomViewTypeItemDecoration(nbaPlayersRecyclerView.context,
                        linearLayoutManager.orientation, resources.getDrawable(R.drawable.divider_drawable))

        // TODO Evgeny: раз ругается на resources.getDrawable(R.drawable.divider_drawable), значит что-то не так
        // Подсказка: использовать ContextCompat.getDrawable()
        )
    }

    override fun onClickedNbaPlayer(nbaPlayerId: Int) {
        val bundle = Bundle()
        // TODO Evgeny: смотри NbaPlayerIdModule
        bundle.putInt("id", nbaPlayerId)
        findNavController().navigate(R.id.action_nbaFragment_to_nbaDetailsFragment, bundle)
    }

}