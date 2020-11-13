package ru.webant.notifications.fragments.base.pagination

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.webant.notifications.fragments.base.BaseView

interface BasePaginationView<I : Any> : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showNewItems(items: List<I>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showInitialItems(items: ArrayList<I>)

    @StateStrategyType(SingleStateStrategy::class)
    fun changePlaceholderVisibilityState(isVisible: Boolean)

    @StateStrategyType(SingleStateStrategy::class)
    fun changeLoaderVisibilityState(isVisible: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeRefreshVisibilityState(isVisible: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changePaginationLoaderState(isVisible: Boolean)
}
