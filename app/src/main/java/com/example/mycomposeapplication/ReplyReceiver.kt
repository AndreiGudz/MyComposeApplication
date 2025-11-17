package com.example.mycomposeapplication

import android.app.RemoteInput
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ReplyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        val repliedText = remoteInput?.getString(KEY_TEXT_REPLY)

        Toast.makeText(context, "Ответ: $repliedText", Toast.LENGTH_LONG).show()
    }

    companion object {
        const val KEY_TEXT_REPLY = "key_text_reply"
    }
}