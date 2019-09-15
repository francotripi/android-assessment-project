package com.vp.favorites

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.ViewAnimator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.vp.favorites.viewmodel.FavoriteListViewModel

import com.vp.list.GridPagingScrollListener
import com.vp.list.ListAdapter
import com.vp.list.viewmodel.ListState
import com.vp.list.viewmodel.SearchResult
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class FavoriteListFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private var listViewModel: FavoriteListViewModel? = null
    private var gridPagingScrollListener: GridPagingScrollListener? = null
    private var listAdapter: ListAdapter? = null
    private var viewAnimator: ViewAnimator? = null
    private var recyclerView: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var errorTextView: TextView? = null
    private var emptyListTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        listViewModel = ViewModelProviders.of(this, factory).get(FavoriteListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        viewAnimator = view.findViewById(R.id.viewAnimator)
        progressBar = view.findViewById(R.id.progressBar)
        errorTextView = view.findViewById(R.id.errorText)
        emptyListTextView = view.findViewById(R.id.emptyListText)

        initList()

        listViewModel?.getFavoritesMovies()?.observe(this, Observer { favoriteMovieList ->
            if (favoriteMovieList.isNotEmpty()) {
                listViewModel?.observeMovies()?.observe(this, Observer {
                    if (it != null)
                        handleResult(listAdapter, it)
                })
                listViewModel?.searchFavoriteMoviesById(favoriteMovieList)
                showProgressBar()
            } else {
                showEmptyListMessage()
            }
        })
    }

    private fun initList() {
        listAdapter = ListAdapter()
        recyclerView?.adapter = listAdapter
        recyclerView?.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(context,
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3)
        recyclerView?.layoutManager = layoutManager
    }

    private fun showProgressBar() {
        viewAnimator?.let {
            it.displayedChild = it.indexOfChild(progressBar)
        }
    }

    private fun showList() {
        viewAnimator?.let {
            it.displayedChild = it.indexOfChild(recyclerView)
        }
    }

    private fun showError() {
        viewAnimator?.let {
            it.displayedChild = it.indexOfChild(errorTextView)
        }
    }

    private fun showEmptyListMessage() {
        viewAnimator?.let {
            it.displayedChild = it.indexOfChild(emptyListTextView)
        }
    }

    private fun handleResult(listAdapter: ListAdapter?, searchResult: SearchResult) {
        when (searchResult.listState) {
            ListState.LOADED -> {
                if (listAdapter != null) {
                    setItemsData(listAdapter, searchResult)
                    showList()
                }
            }
            ListState.IN_PROGRESS -> {
                showProgressBar()
            }
            else -> {
                showError()
            }
        }
        gridPagingScrollListener?.markLoading(false)
    }

    private fun setItemsData(listAdapter: ListAdapter, searchResult: SearchResult) {
        listAdapter.setItems(searchResult.items)

        if (searchResult.totalResult <= listAdapter.itemCount) {
            gridPagingScrollListener?.markLastPage(true)
        }
    }
}
