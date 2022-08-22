package com.saxomoose.frontend.ui.auth.login

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class SuccessfulRegistrationDialogFragment : DialogFragment()
{
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Uses the Builder class for convenient dialog construction.
            val builder = AlertDialog.Builder(it)
            builder.setMessage("An email with an activation link was sent to your mailbox. Please follow the instructions in the email.")
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, _ ->
                    dialog.cancel()
                })
            // Creates the AlertDialog object and return it.
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}