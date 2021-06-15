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
import com.shishkin.itransition.R
import com.shishkin.itransition.db.NbaPlayer
import com.shishkin.itransition.utils.MyViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

@InternalCoroutinesApi
class NbaFragment : DaggerFragment() {

    // TODO   view/data binding
    lateinit var button: Button

        @Inject
        lateinit var myViewModelFactory: MyViewModelFactory
        lateinit var nbaViewModel: NbaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nba, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      nbaViewModel = ViewModelProviders.of(this, myViewModelFactory).get(NbaViewModel::class.java)

        button = view.findViewById(R.id.btnGoToNbaPlayerInfo)
        button.setOnClickListener {
            findNavController().navigate(R.id.action_nbaFragment_to_nbaDetailsFragment)
        }
        lifecycleScope.launch {

            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                nbaViewModel.uiState.collect {
//                    Получаю в логе null. Почему?
                    val list : List<NbaPlayer>? = nbaViewModel.nbaPlayerData.getNbaPlayersData()
                    Log.d("Retrofit", list?.get(1)?.getName().toString())

//                    uiState ->
//                    when (uiState) {
//                        is NbaPlayersUiState.Success ->
//                        is NbaPlayersUiState.Error ->
//                    }

                }
            }
        }

//        TODO Delete
        //    Coroutines + LiveData
//        nbaViewModel.fetchData.observe(viewLifecycleOwner, Observer {
//            val list : List<NbaPlayer>? = it?.getData()
//            Log.d("Retrofit", list?.get(1)?.getName().toString())
//        })


//        Call
//        nbaViewModel.fetchNbaPlayersData()

    }
}