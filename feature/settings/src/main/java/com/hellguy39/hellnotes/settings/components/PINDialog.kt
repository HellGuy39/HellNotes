package com.hellguy39.hellnotes.settings.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.components.CustomDialog
import com.hellguy39.hellnotes.components.CustomDialogState
import java.io.Serializable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PINDialog(
    state: CustomDialogState,
    selection: PinDialogSelection,
) {
    var pin by remember { mutableStateOf("") }
    var isPinConfirmed by remember { mutableStateOf(false) }
    var confirmedPin by remember { mutableStateOf("") }

    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val resetDialog: () -> Unit = {
        pin = ""
        isPinConfirmed = false
        confirmedPin = ""

        isError = false
        errorMessage = ""
    }

    val maxChar = 4

    val isPinExist = selection.existingPin.isNotEmpty() && selection.existingPin.isNotBlank()

    val dialogTitle =
        if (isPinExist)
            if (isPinConfirmed)
                "Enter new pin"
            else
                "Enter current pin"
        else
            if (isPinConfirmed)
                "Repeat new pin"
            else
                "Enter new pin"

    CustomDialog(
        showDialog = state.visible,
        onClose = { state.dismiss() },
        title = dialogTitle,
        applyBottomSpace = false,
        limitMinHeight = false
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = pin,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { newText ->
                    if (newText.length <= maxChar) {
                        pin = newText
                        isError = false
                        errorMessage = ""
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                placeholder = {
                    Text(
                        text = "PIN",
                    )
                },
                isError = isError,
                supportingText = {
                    if (isError) {
                        Text(text = errorMessage)
                    }
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        if (pin.length == 4) {

                            if (isPinExist) {
                                if (isPinConfirmed) {
                                    selection.onPinEntered.invoke(pin)
                                    resetDialog()
                                    state.dismiss()
                                } else {
                                    if (pin == selection.existingPin) {
                                        isPinConfirmed = true
                                        confirmedPin = pin
                                        pin = ""
                                    } else {
                                        isError = true
                                        errorMessage = "Wrong pin"
                                    }
                                }
                            } else {
                                if (isPinConfirmed) {
                                    if (pin == confirmedPin) {
                                        selection.onPinEntered.invoke(pin)
                                        resetDialog()
                                        state.dismiss()
                                    } else {
                                        isError = true
                                        errorMessage = "Wrong pin"
                                    }
                                } else {
                                    isPinConfirmed = true
                                    confirmedPin = pin
                                    pin = ""
                                }
                            }

                        } else {
                            isError = true
                            errorMessage = "PIN must have 4 numbers"
                        }
                    }
                ) {
                    Text(text = "Enter")
                }
            }
        }       
    }
}

data class PinDialogSelection(
    val existingPin: String,
    val onPinEntered: (newPin: String) -> Unit
)