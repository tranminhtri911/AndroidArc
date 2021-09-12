package n.com.myapplication.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import n.com.myapplication.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM USER_DB")
    fun getUsers(): LiveData<List<UserEntity>>

/*
    @Query("SELECT * FROM USER_DB")
    fun getUsersPageList(): DataSource.Factory<Int, User>
*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAllUser(users: List<UserEntity>)

    @Delete
    fun deleteUser(user: UserEntity)

    @Query("DELETE FROM USER_DB")
    fun deleteAll()
}