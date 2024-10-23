package com.jibase.helper

import android.widget.EditText
import androidx.annotation.CheckResult
import androidx.appcompat.widget.SearchView
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

//editText.textChanges().debounce(300)
//.onEach { ... }
//.launchIn(lifecycleScope)

@CheckResult
fun EditText.toTextChangeFlow(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = doOnTextChanged { text, _, _, _ -> trySend(text) }
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }
}

fun SearchView.toTextChangeFlow(): Flow<CharSequence?> {
    return callbackFlow<CharSequence?> {
        val listener = object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                trySend(newText)
                return false
            }
        }

        setOnQueryTextListener(listener)

        awaitClose { setOnQueryTextListener(null) }
    }
        .onStart { emit(query) }
}