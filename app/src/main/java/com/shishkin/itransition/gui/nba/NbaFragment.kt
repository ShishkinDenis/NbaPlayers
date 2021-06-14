package com.shishkin.itransition.gui.nba

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.shishkin.itransition.NbaPlayersUiState
import com.shishkin.itransition.R
import com.shishkin.itransition.db.NbaPlayer
import com.shishkin.itransition.db.NbaPlayerData
import com.shishkin.itransition.di.MyApplication
import kotlinx.android.synthetic.main.fragment_nba.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

@InternalCoroutinesApi
class NbaFragment : Fragment() {

    // TODO   view/data binding
    lateinit var button: Button

    //    TODO Factory for viewModel implementation
    @Inject
    lateinit var nbaViewModel: NbaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nba, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MyApplication.appComponent.inject(this)

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