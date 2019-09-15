package com.vp.list

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.ViewAnimator
import androidx.lifecycle.Observer

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vp.list.viewmodel.ListState
import com.vp.list.viewmodel.SearchResult
import com.vp.list.viewmodel.ListViewModel

import javax.inject.Inject

import dagger.android.support.AndroidSupportInjection

class ListFragment : Fragment(), GridPagingScrollListener.LoadMoreItemsListener, ListAdapter.OnItemClickListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private var listViewModel: ListViewModel? = null
    private var gridPagingScrollListener: GridPagingScrollListener? = null
    private var listAdapter: ListAdapter? = null
    private var viewAnimator: ViewAnimator? = null
    private var recyclerView: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var errorTextView: TextView? = null
    private var currentQuery: String? = "Interview"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        listViewModel = ViewModelProviders.of(this, factory).get(ListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        viewAnimator = view.findViewById(R.id.viewAnimator)
        progressBar = view.findViewById(R.id.progressBar)
        errorTextView = view.findViewById(R.id.errorText)

        if (savedInstanceState != null) {
            currentQuery = savedInstanceState.getString(CURRENT_QUERY)
        }

        initRefreshDataButton(view)
        initBottomNavigation(view)
        initList()
        listViewModel?.observeMovies()?.observe(this, Observer { searchResult ->
            if (searchResult != null) {
                handleResult(listAdapter, searchResult)
            }
        })
        listViewModel?.searchMoviesByTitle(currentQuery, 1)
        showProgressBar()
    }

    private fun initRefreshDataButton(view: View) {
        val refreshDataFloatingButton = view.findViewById<FloatingActionButton>(R.id.floatingRefreshButton)
        refreshDataFloatingButton.setOnClickListener {
            listAdapter?.clearItems()
            listViewModel?.refreshMovies()
            showProgressBar()
        }
    }

    private fun initBottomNavigation(view: View) {
        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.favorites) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("app://movies/favorites"))
                intent.setPackage(requireContext().packageName)
                startActivity(intent)
            }
            true
        }
    }

    private fun initList() {
        listAdapter = ListAdapter()
        listAdapter?.setOnItemClickListener(this)
        recyclerView?.adapter = listAdapter
        recyclerView?.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(context,
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3)
        recyclerView?.layoutManager = layoutManager

        // Pagination
        gridPagingScrollListener = GridPagingScrollListener(layoutManager)
        gridPagingScrollListener!!.setLoadMoreItemsListener(this)
        recyclerView?.addOnScrollListener(gridPagingScrollListener!!)
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
        listAdapter.setItems(searchResult.items.toMutableList())

        if (searchResult.totalResult <= listAdapter.itemCount) {
            gridPagingScrollListener?.markLastPage(true)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(CURRENT_QUERY, currentQuery)
    }

    override fun loadMoreItems(page: Int) {
        gridPagingScrollListener?.markLoading(true)
        listViewModel?.searchMoviesByTitle(currentQuery, page)
    }

    fun submitSearchQuery(query: String) {
        currentQuery = query
        listAdapter?.clearItems()
        listViewModel?.searchMoviesByTitle(query, 1)
        showProgressBar()
    }

    override fun onItemClick(imdbID: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("app://movies/detail"))
        intent.setPackage(requireContext().packageName)
        intent.putExtra("imdbID", imdbID)
        startActivity(intent)
    }

    companion object {
        val TAG = "ListFragment"
        private val CURRENT_QUERY = "current_query"
    }
}
