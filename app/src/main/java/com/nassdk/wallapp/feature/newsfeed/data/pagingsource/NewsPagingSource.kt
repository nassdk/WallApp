package com.nassdk.wallapp.feature.newsfeed.data.pagingsource

//class NewsPagingSource @Inject constructor(
//    private val api: ApiService,
//    private val mapper: NewsFeedMapper
//) : PagingSource<Int, PostModel>() {
//
//    private var cursor: String? = ""
//
//    override fun getRefreshKey(state: PagingState<Int, PostModel>): Int? {
//
//        val anchorPosition = state.anchorPosition ?: return null
//        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
//        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostModel> {
//
//        try {
//            val response = api.loadNewsFeed()
//
//            return if (response.isSuccessful) {
//
//                val mappedResponse = response.body()?.let {
//                    mapper.map(model = it)
//                }
//
//                cursor = mappedResponse?.cursor
//
//                LoadResult.Page(
//                    data = mappedResponse?.posts.orEmpty(),
//                    prevKey = 1,
//                    nextKey = 1
//                )
//
//            } else LoadResult.Error(HttpException(response))
//
//        } catch (e: HttpException) {
//            return LoadResult.Error(e)
//        } catch (e: Exception) {
//            return LoadResult.Error(e)
//        }
//    }
//}