package n.com.myapplication.data.source.local.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import n.com.myapplication.data.model.User
import n.com.myapplication.util.extension.notNull

@Entity(tableName = "USER_DB")
data class UserEntity (
    @PrimaryKey var id: Int = User.UNKNOWN_ID,
    @ColumnInfo(name = "name") var name: String? = "",
    @ColumnInfo(name = "fullName") var fullName: String? = "",
    @ColumnInfo(name = "description") var description: String? = "",
    @ColumnInfo(name = "owner") var owner: String? = "",
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean = false
) {

  fun userToEntity(user: User, gson: Gson): UserEntity {
    this.id = user.id
    this.name = user.name
    this.fullName = user.fullName
    this.owner = objectToJson(user.owner, gson)
    this.isFavorite = user.isFavorite
    return this
  }

  fun userFromEntity(gson: Gson): User {
    val user = User(
            fullName = fullName,
            description = description,
            owner = jsonToObject(owner, gson),
            isFavorite = isFavorite)
    user.id = id
    user.name = name
    return user
  }

  companion object {
    fun objectToJson(owner: User.Owner?, gson: Gson): String? {
      return gson.toJson(owner)
    }

    fun jsonToObject(data: String?, gson: Gson): User.Owner? {
      data.notNull {
        return gson.fromJson(it, User.Owner::class.java)
      }
      return null
    }
  }
}