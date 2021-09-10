package com.example.flowmvvm.utils.extension

import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlin.reflect.KClass

/**
 * Returns a property delegate to access [ViewModel] by **default** scoped to this [Fragment]:
 * ```
 * class MyFragment : Fragment() {
 *     val viewmodel: MyViewModel by viewmodels()
 * }
 * ```
 *
 * Custom [ViewModelProvider.Factory] can be defined via [factoryProducer] parameter,
 * factory returned by it will be used to create [ViewModel]:
 * ```
 * class MyFragment : Fragment() {
 *     val viewmodel: MyViewModel by viewmodels { myFactory }
 * }
 * ```
 *
 * Default scope may be overridden with parameter [ownerProducer]:
 * ```
 * class MyFragment : Fragment() {
 *     val viewmodel: MyViewModel by viewmodels ({requireParentFragment()})
 * }
 * ```
 *
 * This property can be accessed only after this Fragment is attached i.e., after
 * [Fragment.onAttach()], and access prior to that will result in IllegalArgumentException.
 */
@MainThread
inline fun <reified VM : ViewModel> Fragment.viewModels(
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> = createViewModelLazy(VM::class, { ownerProducer().viewModelStore }, factoryProducer)

/**
 * Returns a property delegate to access parent activity's [ViewModel],
 * if [factoryProducer] is specified then [ViewModelProvider.Factory]
 * returned by it will be used to create [ViewModel] first time. Otherwise, the activity's
 * [androidx.activity.ComponentActivity.getDefaultViewModelProviderFactory](default factory)
 * will be used.
 *
 * ```
 * class MyFragment : Fragment() {
 *     val viewmodel: MyViewModel by activityViewModels()
 * }
 * ```
 *
 * This property can be accessed only after this Fragment is attached i.e., after
 * [Fragment.onAttach()], and access prior to that will result in IllegalArgumentException.
 */
@MainThread
inline fun <reified VM : ViewModel> Fragment.sharedViewModel(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> = createViewModelLazy(
    VM::class, {
        requireActivity().viewModelStore
    }, factoryProducer ?: {
        requireActivity().defaultViewModelProviderFactory
    }
)

/**
 * Helper method for creation of [ViewModelLazy], that resolves `null` passed as [factoryProducer]
 * to default factory.
 */
@MainThread
fun <VM : ViewModel> Fragment.createViewModelLazy(
    viewModelClass: KClass<VM>,
    storeProducer: (() -> ViewModelStore)? = null,
    factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val store = storeProducer ?: { viewModelStore }
    val factoryPromise = factoryProducer ?: { defaultViewModelProviderFactory }
    return ViewModelLazy(viewModelClass, store, factoryPromise)
}

@MainThread
fun <VM : ViewModel> AppCompatActivity.createViewModelLazy(
    viewModelClass: KClass<VM>,
    storeProducer: (() -> ViewModelStore)? = null,
    factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val store = storeProducer ?: { viewModelStore }
    val factoryPromise = factoryProducer ?: { defaultViewModelProviderFactory }
    return ViewModelLazy(viewModelClass, store, factoryPromise)
}