package com.shishkin.itransition.gui.nba.lists.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shishkin.itransition.gui.nba.lists.ListItem
import com.shishkin.itransition.network.NbaApi
import com.shishkin.itransition.network.entities.NbaPlayer

@ExperimentalPagingApi
class PlayersPagingDataSource(private val nbaApi: NbaApi?) : PagingSource<Int, ListItem>() {
    companion object {
        const val VIEW_TYPE_NBA_PLAYER = 1
        const val VIEW_TYPE_NBA_TEAM = 2
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListItem> {
        val pageNumber = params.key ?: 1
        return try {
            val response = nbaApi?.getAllNbaPlayersPagination(pageNumber)?.data
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


//

/*@ExperimentalPagingApi
class PlayersPagingDataSource(private val nbaApi: NbaApi?) : PagingSource<Int, NbaPlayer>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NbaPlayer> {
        val pageNumber = params.key ?: 1
        return try {
            val response = nbaApi?.getAllNbaPlayersPagination(pageNumber)

            val pagedResponse = response?.data


            LoadResult.Page(
                pagedResponse.orEmpty(),
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = if (pagedResponse?.isEmpty()!!) null else pageNumber + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NbaPlayer>): Int? {
        TODO("Not yet implemented")
    }
}
*/