package n.com.myapplication.data.source.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import n.com.myapplication.data.model.User
import n.com.myapplication.data.source.local.dao.AppDatabase
import n.com.myapplication.data.source.local.dao.UserEntity

interface AppDBRepository {

    fun getUsers(): LiveData<MutableList<User>>

    //  fun getUsersPageList(): DataSource.Factory<Int, User>

    suspend fun insertOrUpdateUser(user: User)

    fun updateAllUser(users: List<User>)

    suspend fun deleteUser(user: User)
}

class AppDBRepositoryImpl
constructor(private val appDB: AppDatabase, private val gson: Gson) : AppDBRepository {

    override fun getUsers(): LiveData<MutableList<User>> {
        return transformUserEntity(appDB.userDao().getUsers())
    }


    /*   override fun getUsersPageList(): DataSource.Factory<Int, User> {
           return appDB.userDao().getUsersPageList()
       }
   */
    override suspend fun insertOrUpdateUser(user: User) {
        withContext(Dispatchers.IO) {
            val userEntity = UserEntity().userToEntity(user, gson)
            appDB.userDao().insertOrUpdateUser(userEntity)
        }
    }

    override fun updateAllUser(users: List<User>) {

    }

    override suspend fun deleteUser(user: User) {
        withContext(Dispatchers.IO) {
            val userEntity = UserEntity().userToEntity(user, gson)
            appDB.userDao().deleteUser(userEntity)
        }
    }

    private fun transformUserEntity(data: LiveData<List<UserEntity>>): LiveData<MutableList<User>> {
        return Transformations.map(data) { result ->
            val list = mutableListOf<User>()
            result?.forEach { entity ->
                list.add(entity.userFromEntity(gson))
            }
            list
        }
    }
}
