package com.example.mycomposeapplication

import android.app.RemoteInput
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.app.Application
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.edit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ReplyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        val repliedText = remoteInput?.getString(KEY_TEXT_REPLY) ?: "Текст не дошёл"
        val notificationId = intent.getIntExtra("notification_id", -1)

        Log.d("ReplyReceiver", "${context.packageName} - ${intent.dataString}")
        Log.d("ReplyReceiver", "remoteInput $remoteInput\n" +
                "repliedText $repliedText\n" +
                "notificationId $notificationId")

        Toast.makeText(context, "Ответ: $repliedText", Toast.LENGTH_LONG).show()

        if (notificationId != -1)
            NotificationManagerCompat.from(context).cancel(notificationId)
    }

    companion object {
        const val KEY_TEXT_REPLY = "key_text_reply"
    }
}