package com.davidev.chronos.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.davidev.chronos.databinding.ActivityAddTaskBinding
import com.davidev.chronos.datasource.TaskDataSource
import com.davidev.chronos.extensions.format
import com.davidev.chronos.extensions.text
import com.davidev.chronos.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*


class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // initializing binding
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)


        insertListeners()
    }

    // TODO
    private fun insertListeners() {

        // Date
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()


            datePicker.addOnPositiveButtonClickListener {
                //binding.tilDate.editText?.setText(Date(it).format())
                val timeZone = TimeZone.getDefault()

                // the use of "* -1" is to fix the local date/time offset selected by user
                val offset = timeZone.getOffset(Date().time) * -1

                binding.tilDate.text = Date(it + offset).format()

            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")

        }

        // Time
        binding.tilHour.editText?.setOnClickListener {

            val timePicker = MaterialTimePicker.Builder()
                //.setTitleText("Select the time that you will receive the reminder")
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            timePicker.addOnPositiveButtonClickListener {
                val minute =
                    if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute

                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

                binding.tilHour.text = "${hour}:${minute}"

            }
            timePicker.show(supportFragmentManager, "TIME_PICKER_TAG")

        }

        // Buttons
        binding.btnCancel.setOnClickListener {

            finish()
        }

        binding.btnCreate.setOnClickListener {

            val task = Task(
                title = binding.tilTitle.text,
                date = binding.tilDate.text,
                hour = binding.tilHour.text,
           //     id = intent.getIntExtra(TASK_ID, 0)
            )

            // TODO
            TaskDataSource.insertTask(task)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}