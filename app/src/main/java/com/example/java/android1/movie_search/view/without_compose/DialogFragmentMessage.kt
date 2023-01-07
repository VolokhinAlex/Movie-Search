package com.example.java.android1.movie_search.view.without_compose

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DialogFragmentMessage(private val message: String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(this.context).setTitle("Failed").setMessage(message)
            .setCancelable(false)
            .setPositiveButton("Ok") { dialogInterface, _ -> dialogInterface.dismiss() }.create()
    }

}