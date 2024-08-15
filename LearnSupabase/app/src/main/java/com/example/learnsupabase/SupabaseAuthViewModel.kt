package com.example.learnsupabase

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnsupabase.data.model.Note
import com.example.learnsupabase.data.model.UserState
import com.example.learnsupabase.data.network.SupabaseClient.client
import com.example.learnsupabase.utils.SharedPreferenceHelper
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

class SupabaseAuthViewModel: ViewModel() {
    private val _userState = mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState> = _userState

    fun signUp(
        context: Context,
        userEmail: String,
        userPassword: String
    ){
        viewModelScope.launch {
            try {
                _userState.value = UserState.Loading
                client.gotrue.signUpWith(Email){
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                _userState.value = UserState.Success("Registered user successfully!")
            } catch (e: Exception){
                _userState.value = UserState.Error("Error: ${e.message}")
            }
        }
    }
     private fun saveToken(context: Context){
        viewModelScope.launch {
            val accessToken = client.gotrue.currentAccessTokenOrNull()
            val sharedPref = SharedPreferenceHelper(context)
            sharedPref.saveStringData("accessToken",accessToken)
        }
    }
    private fun getToken(context: Context): String? {
        val sharedPref = SharedPreferenceHelper(context)
        return sharedPref.getStringData("accessToken")
    }
    fun login(
        context: Context,
        userEmail: String,
        userPassword: String,
    ){
      viewModelScope.launch {
          _userState.value = UserState.Loading
          try {

                client.gotrue.loginWith(Email) {
                    email = userEmail
                    password = userPassword
                }
              saveToken(context)
              _userState.value = UserState.Success("Logged in successfully!")
          } catch (e: Exception){
              _userState.value = UserState.Error("Error: ${e.message}")
          }
      }
    }
    fun logout(context: Context){
        val sharedPref = SharedPreferenceHelper(context)
        viewModelScope.launch {
            try {
                _userState.value = UserState.Loading
                client.gotrue.logout()
                sharedPref.clearPreferences()
                _userState.value =UserState.Success("Logged out successfully!")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error: ${e.message}")
            }
        }
    }
    fun isUserLoggedIn(
        context: Context
    ){
        viewModelScope.launch {
            try {
                val token = getToken(context)
                if (token.isNullOrEmpty()){
                    _userState.value = UserState.Error("User is not logged in!")
                } else {
                    client.gotrue.retrieveUser(token)
                    client.gotrue.refreshCurrentSession()
                    saveToken(context)
                    _userState.value = UserState.Success("User is already logged in!")
                }
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error: ${e.message}")
            }
        }
    }

    fun saveNote(){
        viewModelScope.launch {
            try {
                _userState.value = UserState.Loading
                client.postgrest["test"].insert(
                    Note(
                        note = "This is my first note."
                    )
                )
                _userState.value = UserState.Success("Added note successfully!")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error: ${e.message}")
            }
        }
    }
    fun getNote() {
        viewModelScope.launch {
            try {
                _userState.value = UserState.Loading
                val data = client.postgrest["test"]
                    .select().decodeSingle<Note>()
                _userState.value = UserState.Success("Data: ${data.note}")
            } catch (e: Exception){
                _userState.value = UserState.Error("Error: ${e.message}")
            }
        }
    }
    fun updateNote(){
        viewModelScope.launch {
            try {
                _userState.value = UserState.Loading
                client.postgrest["test"]
                    .update(
                        {
                            Note::note setTo "This is the updated note"
                        }
                    ){
                        Note::id eq 1
                    }
                _userState.value = UserState.Success("Note updated successfully!")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error: ${e.message}")
            }
        }
    }

    fun deleteNote(){
        viewModelScope.launch {
            try {
                _userState.value = UserState.Loading
                client.postgrest["test"]
                    .delete {
                        Note::id eq 1
                    }
                _userState.value = UserState.Success("Note delete successfully!")
            } catch (e: Exception){
                _userState.value = UserState.Error("Error: ${e.message}")
            }
        }
    }
}

