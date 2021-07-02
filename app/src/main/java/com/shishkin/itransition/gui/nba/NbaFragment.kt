package com.shishkin.itransition.gui.nba

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
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class NbaFragment : DaggerFragment(), NbaPlayerItemListener {

    @Inject
    lateinit var viewModelFactory: NbaViewModelFactory
    private lateinit var viewModel: NbaViewModel
    private lateinit var nbaPlayersListAdapter: NbaPlayersListAdapter

    // TODO Evgeny убрать все findViewById() и разобраться во ViewBinding: https://developer.android.com/topic/libraries/view-binding

    private var nbaPlayersRecyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nba, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(NbaViewModel::class.java)
        initNbaPlayersRecyclerView()

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.playersState.collectLatest { uiState ->
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

    private fun initNbaPlayersRecyclerView() {
        nbaPlayersRecyclerView = view?.findViewById(R.id.rv_nba_players)!!
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        nbaPlayersRecyclerView?.layoutManager = linearLayoutManager
        nbaPlayersListAdapter = NbaPlayersListAdapter(this@NbaFragment)
        nbaPlayersRecyclerView?.adapter = nbaPlayersListAdapter

//        TODO какая есть альтернатива использования четырых! let?
        context?.let { it ->
            ContextCompat.getDrawable(it, R.drawable.divider_drawable)?.let {
                nbaPlayersRecyclerView?.context?.let { it1 ->
                    CustomViewTypeItemDecoration(
                        it1, linearLayoutManager.orientation, it)
                }
            }?.let {
                nbaPlayersRecyclerView?.addItemDecoration(it)
            }
        }
    }

    override fun onClickedNbaPlayer(nbaPlayerId: Int) {
        val bundle = Bundle()
        bundle.putInt(getString(R.string.nba_player_id), nbaPlayerId)
        findNavController().navigate(R.id.action_nbaFragment_to_nbaDetailsFragment, bundle)
    }
}