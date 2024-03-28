package com.example.daily.ui.fragment.settingDaiLy.setting.reminders

import android.Manifest
import android.provider.Settings
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.daily.base.BaseFragment
import com.example.daily.broadcast.AlarmReceiver
import com.example.daily.database.Preferences
import com.example.daily.databinding.FragmentRemindersBinding
import com.example.daily.util.KeyWord
import com.example.daily.widget.NewAppWidget
import java.util.Calendar

class RemindersFragment : BaseFragment<FragmentRemindersBinding>() {

    private var pendingIntentNotification: PendingIntent? = null

    private var pendingIntentWidget: PendingIntent? = null

    private lateinit var preferences: Preferences

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRemindersBinding {
        return FragmentRemindersBinding.inflate(inflater)
    }

    override fun init() {
        preferences = Preferences.getInstance(requireContext())
        permistion()
    }

    override fun setUpView() {
        listenerClick()
        setUpTimeNoti()
        setUpTimeWidget()
        restoreSwitchState()
    }

    private fun permistion() {
        val alarmManager: AlarmManager =
            (context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?)!!
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            when {
                alarmManager.canScheduleExactAlarms() -> {
                    requestNotificationPolicyPermission()
                }

                else -> {
                    startActivity(Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
                }
            }
        } else {
            requestNotificationPolicyPermission()
        }
    }

    private fun requestNotificationPolicyPermission() {
        val permission = Manifest.permission.POST_NOTIFICATIONS
        val granted = ActivityCompat.checkSelfPermission(requireContext(), permission) ==
                PackageManager.PERMISSION_GRANTED

        if (granted) {
            listenerClick()
        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU)
                openAppSettings()
            else
                requestPermissions(arrayOf(permission), 1)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                listenerClick()
            } else {
                showPermissionDeniedDialog()
            }
        }
    }

    private fun showPermissionDeniedDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("Notification")
        dialogBuilder.setMessage("You have not granted permission to receive notifications for the application. Please grant permission.")
        dialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            openAppSettings()
        }
        dialogBuilder.setCancelable(false)
        dialogBuilder.show()
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireContext().packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    private fun setUpTimeWidget() {
        val hourWidget = preferences.getString(KeyWord.hour)
        val minuteWidget = preferences.getString(KeyWord.minute)

        val timeText = if (hourWidget.isNullOrEmpty() || minuteWidget.isNullOrEmpty()) {
            "10:00"
        } else {
            "$hourWidget:$minuteWidget"
        }
        binding.tvHourWriting.text = timeText
    }

    private fun setUpTimeNoti() {
        val hour = preferences.getString(KeyWord.hourNoti)
        val minute = preferences.getString(KeyWord.minuteNoti)

        val timeText = if (hour.isNullOrEmpty() || minute.isNullOrEmpty()) {
            "10:00"
        } else {
            "$hour:$minute"
        }
        binding.tvHourNotifition.text = timeText
    }

    private fun restoreSwitchState() {
        val switchState = preferences.getBoolean(KeyWord.switch_state_notifition) ?: false
        val switchStateWidget = preferences.getBoolean(KeyWord.switch_state_widget) ?: false

        if (switchState != null) {
            binding.swichBt.isChecked = switchState
        }

        if (switchStateWidget != null) {
            binding.switchWidget.isChecked = switchStateWidget
        }
    }

    private fun listenerClick() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.swichBt.setOnClickListener {
            val isChecked = binding.swichBt.isChecked
            if (isChecked) showTimePickerDialog() else cancelAlarmNotification()
            saveSwitchState(isChecked)
        }

        binding.switchWidget.setOnClickListener {
            val isCheckWidget = binding.switchWidget.isChecked
            if (isCheckWidget) {
                showTimePickerDialogWidget()
            } else {
                cancelWidget()
            }
            saveSwitchStateWidget(isCheckWidget)
        }
    }

    private fun saveSwitchStateWidget(isCheckWidget: Boolean) {
        preferences.setBoolean(KeyWord.switch_state_widget, isCheckWidget)
    }

    private fun saveSwitchState(isChecked: Boolean) {
        preferences.setBoolean(KeyWord.switch_state_notifition, isChecked)
    }

    fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog =
            TimePickerDialog(
                context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    val intent = Intent(requireContext(), AlarmReceiver::class.java)
                    intent.action = KeyWord.myAction

                    val mHour = view.hour.toString()
                    val mMinute = view.minute.toString()

                    intent.putExtra(KeyWord.time, "$mHour:$mMinute")

                    preferences.setString(KeyWord.hourNoti, mHour)
                    preferences.setString(KeyWord.minuteNoti, mMinute)

                    binding.tvHourNotifition.text = "$mHour:$mMinute"

                    val alarmManager =
                        requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

                    val pendingIntent = PendingIntent.getBroadcast(
                        requireContext(),
                        10,
                        intent,
                        PendingIntent.FLAG_MUTABLE
                    )

                    alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        AlarmManager.INTERVAL_DAY,
                        pendingIntent
                    )

                    this.pendingIntentNotification = pendingIntent
                }, hour, minute, true
            )

        timePickerDialog.setOnCancelListener {
            cancelAlarmNotification()
            binding.swichBt.isChecked = false
        }
        timePickerDialog.show()
    }

    private fun showTimePickerDialogWidget() {

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val intent = Intent(requireContext(), NewAppWidget::class.java)

                intent.action = KeyWord.actionOne
                val mHour = view.hour.toString()
                val mMinute = view.minute.toString()

                intent.putExtra(KeyWord.time, "$mHour:$mMinute")

                preferences.setString(KeyWord.hour, mHour)
                preferences.setString(KeyWord.minute, mMinute)

                binding.tvHourWriting.text = "$mHour:$mMinute"

                val alarmManager =
                    requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val pendingIntent = PendingIntent.getBroadcast(
                    requireContext(),
                    0,
                    intent,
                    PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
                )

                this@RemindersFragment.pendingIntentWidget = pendingIntent

            }, hour, minute, true
        )
        timePickerDialog.setOnCancelListener {
            cancelWidget()
            binding.swichBt.isChecked = false
        }
        timePickerDialog.show()

    }

    private fun cancelAlarmNotification() {
        pendingIntentNotification?.let {
            val alarmManager =
                requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(it)
        }
    }

    private fun cancelWidget() {
        pendingIntentWidget?.let {
            val alarmManager =
                requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(it)
        }
    }


}

