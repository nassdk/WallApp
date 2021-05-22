package com.nassdk.wallapp.library.coreui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.core.lifecycle.asMviLifecycle
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.view.MviView
import com.arkivanov.mvikotlin.extensions.coroutines.events
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.nassdk.wallapp.library.coreui.mvi.ViewConnections
import com.nassdk.wallapp.library.coreui.util.mapNotNull

abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {

    open fun prepareUi() = Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareUi()
    }

    private fun <Model : Any, Event : Any, State : Any, Intent : Any>
            MviView<Model, Event>.bind(
        store: Store<Intent, State, *>,
        stateToModel: (State) -> Model,
        eventToIntent: (Event) -> Intent
    ) {
        com.arkivanov.mvikotlin.extensions.coroutines.bind(
            this@BaseFragment.lifecycle.asMviLifecycle(),
            BinderLifecycleMode.START_STOP
        ) {
            store.states.mapNotNull(stateToModel) bindTo this@bind
            this@bind.events.mapNotNull(eventToIntent) bindTo store
        }
    }

    protected fun <Model : Any, Event : Any, State : Any, Intent : Any>
            MviView<Model, Event>.bind(
        store: Store<Intent, State, *>,
        viewConnections: ViewConnections<State, Intent, Model, Event>
    ) {
        bind(store, viewConnections.stateToModel, viewConnections.eventToIntent)
    }
}