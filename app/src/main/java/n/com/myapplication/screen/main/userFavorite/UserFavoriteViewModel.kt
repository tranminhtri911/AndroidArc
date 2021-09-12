package n.com.myapplication.screen.main.userFavorite

import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.launch
import n.com.myapplication.base.BaseViewModel
import n.com.myapplication.data.model.User
import n.com.myapplication.data.source.repositories.AppDBRepository
import javax.inject.Inject

class UserFavoriteViewModel
@Inject constructor(private val appDB: AppDBRepository) : BaseViewModel() {

    var repoList = MediatorLiveData<MutableList<User>>()

    init {
        viewModelScope.launch {
            repoList.addSource(appDB.getUsers()) { response ->
                repoList.value = response
            }
        }
    }

    fun unFavorite(user: User) {
        viewModelScope.launch {
            appDB.deleteUser(user)
        }
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}