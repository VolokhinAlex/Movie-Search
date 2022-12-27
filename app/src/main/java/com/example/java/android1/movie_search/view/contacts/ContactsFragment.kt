package com.example.java.android1.movie_search.view.contacts

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.view.compose.search.SearchBar
import com.example.java.android1.movie_search.view.compose.search.SearchState
import com.example.java.android1.movie_search.view.compose.search.rememberSearchState
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class ContactsFragment : Fragment() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireActivity()).apply {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
        setContent {
            val viewModel by remember {
                mutableStateOf(ContactsViewModel())
            }
            viewModel.contactsLiveData.observeAsState()
            val searchState: SearchState = rememberSearchState()
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SearchBar(
                    query = searchState.query,
                    onQueryChange = { searchState.query = it },
                    onSearchFocusChange = { searchState.focused = it },
                    onClearQuery = { searchState.query = TextFieldValue("") },
                    onBack = { searchState.query = TextFieldValue("") },
                    searching = searchState.searching,
                    focused = searchState.focused,
                    searchHint = "Search a contact By Name"
                )

                CheckPermission(
                    arrayOf(
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.CALL_PHONE
                    ), searchState, viewModel
                )
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ContactsFragment()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach {
            if (it.value) {
                getContacts()
            }
        }
    }

    @Composable
    private fun CheckPermission(
        permissions: Array<String>,
        searchState: SearchState,
        viewModel: ContactsViewModel
    ) {
        context?.let { context ->
            when {
                permissions.all {
                    ContextCompat.checkSelfPermission(
                        context,
                        it
                    ) == PackageManager.PERMISSION_GRANTED
                } -> {
                    val contacts = getContacts()
                    LaunchedEffect(searchState.query.text) {
                        searchState.searching = true
                        viewModel.filter(searchState.query.text, contacts)
                        if (searchState.query.text == "") {
                            searchState.searching = false
                        }
                    }
                    when (val contactsState = viewModel.contactsLiveData.value) {
                        is ContactsState.Error -> {}
                        ContactsState.Loading -> {
                            ShowContacts(contacts)
                        }
                        is ContactsState.Success -> {
                            ShowContacts(contactsState.data)
                        }
                        else -> {}
                    }
                }
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    Dialog(
                        "Доступ к контактам и звонкам",
                        "Доступ к контактам нужен, чтобы была возможность отобразить их и осуществлять звонки."
                    )
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun requestPermission() {
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.CALL_PHONE
            )
        )
    }

    @SuppressLint("Range")
    private fun getContacts(): List<Contacts> {
        val contacts: MutableList<Contacts> = mutableListOf()
        context?.let {
            val contentResolver = it.contentResolver
            val contactsCursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )
            contactsCursor?.let { cursor ->
                for (i in 0..cursor.count) {
                    if (cursor.moveToPosition(i)) {
                        val contactId =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                        val hasPhone =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                        if (hasPhone == "1") {
                            val phones = contentResolver.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                                null, null
                            )
                            if (phones != null) {
                                while (phones.moveToNext()) {
                                    phones.let { phone ->
                                        contacts.add(
                                            Contacts(
                                                cursor.getString(
                                                    cursor.getColumnIndex(
                                                        ContactsContract.Contacts.DISPLAY_NAME
                                                    )
                                                ),
                                                phone.getString(
                                                    phones.getColumnIndex(
                                                        ContactsContract.CommonDataKinds.Phone.NUMBER
                                                    )
                                                )
                                            )
                                        )
                                    }
                                }
                            }
                            phones?.close()
                        } else {
                            contacts.add(
                                Contacts(
                                    cursor.getString(
                                        cursor.getColumnIndex(
                                            ContactsContract.Contacts.DISPLAY_NAME
                                        )
                                    ),
                                    null
                                )
                            )
                        }
                    }
                }
            }
            contactsCursor?.close()
        }
        return contacts
    }

    @Composable
    private fun ShowContacts(contacts: List<Contacts>) {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), content = {
            itemsIndexed(contacts) { _, item ->
                Card(
                    modifier = Modifier
                        .clickable {
                            item.phone?.let {
                                startActivity(
                                    Intent(
                                        Intent.ACTION_CALL,
                                        Uri.parse("tel:${it}")
                                    )
                                )
                            }
                        }
                        .fillMaxWidth()
                        .padding(bottom = 15.dp)
                ) {
                    item.name?.let {
                        Text(
                            text = it,
                            modifier = Modifier.padding(10.dp),
                            fontSize = 20.sp
                        )
                    }
                    item.phone?.let {
                        Text(
                            text = it,
                            modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
                            fontSize = 20.sp
                        )
                    }
                }
            }
        })
    }

    @Composable
    fun Dialog(title: String, description: String) {
        val dialogState = remember {
            mutableStateOf(true)
        }
        if (dialogState.value) {
            AlertDialog(
                onDismissRequest = { dialogState.value = false },
                confirmButton = {
                    Button(onClick = {
                        dialogState.value = false
                        requestPermission()
                    }) { Text(text = "Allow") }
                },
                dismissButton = {
                    Button(onClick = { dialogState.value = false }) {
                        Text(text = "Deny")
                    }
                },
                title = { Text(text = title) },
                text = { Text(text = description) }
            )
        }
    }

}

data class Contacts(
    val name: String?,
    val phone: String?
)