package com.shishkin.itransition.gui.games

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shishkin.itransition.gui.utils.ListItem
import com.shishkin.itransition.network.NbaApi
import com.shishkin.itransition.network.entities.NbaGame

class NbaGamesPagingDataSource(private val nbaApi: NbaApi?) : PagingSource<Int, ListItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListItem> {
        val pageNumber = params.key ?: 1
        return try {
            val response = nbaApi?.getAllNbaGamesPagination(pageNumber)?.data
            val pagedResponse = convertList(response)

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
        listOfNbaGames?.forEach {
            val nbaGameListItem = ListItem(it, VIEW_TYPE_NBA_GAME)
            val nbaTeamListItem = ListItem(it, VIEW_TYPE_NBA_GAME_TEAM)
            listOfListItem.add(nbaGameListItem)
            listOfListItem.add(nbaTeamListItem)
        }
        return listOfListItem
    }
}

