package com.shishkin.itransition.gui.nba.lists.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shishkin.itransition.network.NbaApi
import com.shishkin.itransition.network.entities.NbaPlayer

@ExperimentalPagingApi
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
