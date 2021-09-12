package n.com.myapplication.screen.main

import n.com.myapplication.base.BaseViewModel
import n.com.myapplication.util.liveData.SingleLiveEvent
import javax.inject.Inject

class MainActivityViewModel
@Inject constructor() : BaseViewModel() {

    var currentTab = MainActivity.TAB1

    var actionShowDrawer = SingleLiveEvent<Int>()
}