package com.example.shoppinglistcomposelesson.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppinglistcomposelesson.ui.theme.DarkText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainDialog(
    dialogController: DialogController
) {
    if(dialogController.openDialog.value){
        AlertDialog(onDismissRequest = {
            dialogController.onDialogEvent(DialogEvent.OnCancel) },
            title = null,
            text = {
                Column (modifier = Modifier.fillMaxWidth()){
                    Text(
                        text = dialogController.dialogTitle.value,
                        style = TextStyle(
                            color = DarkText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    if(dialogController.showEditableText.value) TextField(
                        value = dialogController.editableText.value,
                        onValueChange ={ text ->
                            dialogController.onDialogEvent(DialogEvent.OnTextChange(text))
                        },
                        label = {
                            Text(text = "List name:")
                        },
                        singleLine = true
                    )
                }
            },
            confirmButton = { 
                TextButton(onClick = {
                    dialogController.onDialogEvent(DialogEvent.OnConfirm)
                }) {
                    Text(text = "OK")
                }
            },
                    dismissButton = {
                TextButton(onClick = {
                    dialogController.onDialogEvent(DialogEvent.OnCancel)
                }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}
