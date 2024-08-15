package com.example.shoppinglistcomposelesson.main_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglistcomposelesson.data.ShoppingListItem
import com.example.shoppinglistcomposelesson.data.ShoppingListRepository
import com.example.shoppinglistcomposelesson.dialog.DialogController
import com.example.shoppinglistcomposelesson.dialog.DialogEvent
import com.example.shoppinglistcomposelesson.shopping_list_screen.ShoppingListEvent
import com.example.shoppinglistcomposelesson.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: ShoppingListRepository
): ViewModel(), DialogController {
    override var dialogTitle = mutableStateOf("List name:")
        private set
    override var editableText = mutableStateOf("")
        private set
    override var openDialog = mutableStateOf(false)
        private set
    override var showEditableText = mutableStateOf(true)
        private set
    fun onEvent(event: MainScreenEvent){
        when(event){
            is MainScreenEvent.OnItemSave -> {
                if (editableText.value.isEmpty()) return
                viewModelScope.launch {
                    repository.insertItem(
                        ShoppingListItem(
                            null,
                            editableText.value,
                            "12/12/2023 13:00",
                             0,
                             0
                        )
                    )
                }
            }
            is MainScreenEvent.OnShowEditDialog -> {
                openDialog.value = true
            }
        }
    }
    override fun onDialogEvent(event: DialogEvent) {
        when(event){
            is DialogEvent.OnCancel -> {
                openDialog.value = false
                editableText.value = ""
            }
            is DialogEvent.OnConfirm -> {
                onEvent(MainScreenEvent.OnItemSave)
                openDialog.value = false
                editableText.value = ""
            }
            is DialogEvent.OnTextChange -> {
                editableText.value = event.text
            }
        }
    }
}
