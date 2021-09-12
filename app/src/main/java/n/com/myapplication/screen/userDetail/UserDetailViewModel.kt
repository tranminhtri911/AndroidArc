package n.com.myapplication.screen.userDetail

import androidx.lifecycle.MutableLiveData
import n.com.myapplication.base.BaseViewModel
import n.com.myapplication.data.model.User
import n.com.myapplication.util.liveData.SingleLiveEvent
import javax.inject.Inject

class UserDetailViewModel
@Inject constructor() : BaseViewModel() {

    var user = MutableLiveData<User>()

    var actionBack = SingleLiveEvent<Int>()

    fun setUser(data: User) {
        user.value = data
    }

    fun onBackPressed() {
        actionBack.postValue(null)
    }
}