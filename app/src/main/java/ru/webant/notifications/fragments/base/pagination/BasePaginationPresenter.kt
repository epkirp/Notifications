package ru.webant.notifications.fragments.base.pagination

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.webant.PAGE_LIMIT
import ru.webant.domain.PaginationEntity
import ru.webant.notifications.fragments.base.BasePresenter

abstract class BasePaginationPresenter<T : BasePaginationView<I>, I : Any> : BasePresenter<T>() {

    private var totalItems = 1
    private var countOfPages = 1
    protected var currentPage = 1
    private var isLoading = false
    private val items = ArrayList<I>()

    abstract fun getItems(page: Int = 1, limit: Int = PAGE_LIMIT): Single<PaginationEntity<I>?>

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadFirstPage()
    }

    open fun onSwipeToRefresh() {
        viewState.changePlaceholderVisibilityState(false)
        viewState.changeRefreshVisibilityState(true)
        loadFirstPage()
        viewState.changeRefreshVisibilityState(false)
    }

    open fun onNewPageScrolled() {
        if (isLoading || currentPage - 1 >= countOfPages) {
            return
        }
        loadNewPage()
    }


    protected open fun loadFirstPage() {
        resetData()
        totalItems = 1
        countOfPages = 1

        getItems(currentPage, PAGE_LIMIT)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                viewState.changePlaceholderVisibilityState(false)
                viewState.changeLoaderVisibilityState(true)
                isLoading = true
            }
            .doOnSuccess { response ->
                if (response != null) {
                    viewState.changePlaceholderVisibilityState(response.items.isEmpty())
                }
            }
            .doFinally {
                viewState.changeLoaderVisibilityState(false)
                isLoading = false
            }
            .subscribe({ response ->
                if (response != null) {
                    items.apply {
                        clear()
                        addAll(response.items)
                    }
                    currentPage++
                    totalItems = response.totalItems
                    countOfPages = response.countOfPages
                    viewState.showInitialItems(items)
                } else {
                    onFailureLoad()
                }
            }, {
                onFailureLoad()
            })
            .let(compositeDisposable::add)
    }

    open fun loadNewPage() {
        getItems(currentPage, PAGE_LIMIT)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading = true
                viewState.changePaginationLoaderState(isLoading)
            }
            .doFinally {
                isLoading = false
                viewState.changePaginationLoaderState(isLoading)
            }
            .subscribe({ response ->
                if (response != null) {
                    items.apply {
                        addAll(response.items)
                    }
                    currentPage++
                    totalItems = response.totalItems
                    countOfPages = response.countOfPages
                    viewState.showNewItems(response.items)
                } else {
                    onFailureLoad()
                }
            }, {
                onFailureLoad()
            })
            .let(compositeDisposable::add)
    }

    private fun resetData() {
        isLoading = false
        currentPage = 1
        items.clear()
    }

    private fun onFailureLoad() {
        resetData()

        viewState.changePlaceholderVisibilityState(true)
        viewState.changeRefreshVisibilityState(false)
        viewState.changeLoaderVisibilityState(false)
    }
}