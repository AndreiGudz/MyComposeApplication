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
        // Получаем текст ответа из RemoteInput
        val repliedText = getReplyText(intent) ?: "Текст не дошёл"
        val notificationId = intent.getIntExtra(NOTIFICATION_ID, -1)

        Log.d("ReplyReceiver", "Ответ получен: $repliedText")
        Log.d("ReplyReceiver", "ID уведомления: $notificationId")

        // Показываем Toast с ответом
        Toast.makeText(context, "Ответ: $repliedText", Toast.LENGTH_LONG).show()

        // Закрываем уведомление
        if (notificationId != -1) {
            NotificationManagerCompat.from(context).cancel(notificationId)
        }
    }

    private fun getReplyText(intent: Intent): String? {
        return RemoteInput.getResultsFromIntent(intent)?.getCharSequence(KEY_TEXT_REPLY)?.toString()
    }

    companion object {
        const val KEY_TEXT_REPLY = "key_text_reply"
    }
}