package com.example.shoppinglistcomposelesson.add_item_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglistcomposelesson.data.AddItem
import com.example.shoppinglistcomposelesson.data.AddItemRepository
import com.example.shoppinglistcomposelesson.data.ShoppingListItem
import com.example.shoppinglistcomposelesson.dialog.DialogController
import com.example.shoppinglistcomposelesson.dialog.DialogEvent
import com.example.shoppinglistcomposelesson.main_screen.MainScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val repository: AddItemRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(), DialogController {
    var itemsList: Flow<List<AddItem>>? = null
    var addItem: AddItem? = null
    var shoppingListItem: ShoppingListItem? = null
    var listId: Int = -1
     init {
         listId = savedStateHandle.get<String>("listId")?.toInt()!!
         itemsList = repository.getAllItemsById(listId)
         viewModelScope.launch {
            shoppingListItem = repository.getListItemById(listId)
         }
    }
    var itemText = mutableStateOf("")
        private set
    override var dialogTitle = mutableStateOf("Edit name:")
        private set
    override var editableText = mutableStateOf("")
        private set
    override var openDialog = mutableStateOf(false)
        private set
    override var showEditableText = mutableStateOf(true)
        private set

    fun onEvent(event: AddItemEvent){
        when(event){
            is AddItemEvent.OnItemSave -> {
                viewModelScope.launch {
                    if (listId == -1) return@launch
                    repository.insertItem(
                        AddItem(
                            addItem?.id,
                            itemText.value,
                            addItem?.isCheck ?: false,
                            listId
                        )
                    )
                    itemText.value = ""
                    addItem = null
                }
                updateShoppingListCount()
            }
            is AddItemEvent.OnShowEditDialog -> {
                addItem = event.item
                openDialog.value = true
                editableText.value = addItem?.name ?: ""
            }
            is AddItemEvent.OnTextChange -> {
                itemText.value = event.text
            }
            is AddItemEvent.OnDelete -> {
                viewModelScope.launch {
                    repository.deleteItem(event.item)
                }
                updateShoppingListCount()
            }
            is AddItemEvent.OnCheckedChange -> {
                viewModelScope.launch {
                    repository.insertItem(event.item)
                }
                updateShoppingListCount()
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
                openDialog.value = false
                itemText.value = editableText.value
                editableText.value = ""
            }
            is DialogEvent.OnTextChange -> {
                editableText.value = event.text
            }
        }
    }

    private  fun updateShoppingListCount(){
        viewModelScope.launch {
            itemsList?.collect{ list ->
                var counter = 0
                list.forEach { item ->
                    if(item.isCheck) counter++
                }
                shoppingListItem?.copy(
                    allItemsCount = list.size,
                    allSelectedItemsCount = counter
                )?.let { shItem ->
                    repository.insertItem(
                        shItem
                    )
                }
            }
        }
    }
}
