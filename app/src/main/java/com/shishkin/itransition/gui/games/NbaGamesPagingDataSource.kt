package com.shishkin.itransition.gui.games

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shishkin.itransition.network.NbaApi
import com.shishkin.itransition.network.entities.ListItem
import com.shishkin.itransition.network.entities.NbaGame

@ExperimentalPagingApi
class NbaGamesPagingDataSource(private val nbaApi: NbaApi?) : PagingSource<Int, ListItem>() {

    companion object {
        const val VIEW_TYPE_NBA_GAME = 1
        const val VIEW_TYPE_NBA_GAME_TEAM = 2
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListItem> {
        val pageNumber = params.key ?: 1
        return try {
            val response = nbaApi?.getAllNbaGamesPagination(pageNumber)?.data
            val pagedResponse = convertList(response!!)

            LoadResult.Page(
                    pagedResponse,
                    prevKey = if (pageNumber == 1) null else pageNumber - 1,
                    nextKey = if (pagedResponse.isEmpty()) null else pageNumber + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListItem>): Int? {
        TODO("Not yet implemented")
    }


    private fun convertList(listOfNbaGames: List<NbaGame>?): List<ListItem> {
        val listOfListItem = ArrayList<ListItem>()
        for (item in listOfNbaGames!!) {
            val nbaGameListItem = ListItem(item, VIEW_TYPE_NBA_GAME)
            val nbaTeamListItem = ListItem(item, VIEW_TYPE_NBA_GAME_TEAM)
            listOfListItem.add(nbaGameListItem)
            listOfListItem.add(nbaTeamListItem)
        }
        return listOfListItem
    }
}

