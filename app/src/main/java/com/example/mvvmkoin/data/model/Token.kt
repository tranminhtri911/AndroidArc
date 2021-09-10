package  com.example.mvvmkoin.data.model

import com.google.gson.annotations.Expose

class Token {
  @Expose
  var tokenType: String? = null
  @Expose
  var accessToken: String? = null
  @Expose
  var refreshToken: String? = null
}
