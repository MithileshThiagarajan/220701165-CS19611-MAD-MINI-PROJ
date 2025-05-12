package com.example.calorie

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var ageInput: EditText
    private lateinit var weightInput: EditText
    private lateinit var heightInput: EditText
    private lateinit var genderGroup: RadioGroup
    private lateinit var activitySpinner: Spinner
    private lateinit var calculateButton: Button
    private lateinit var resultText: TextView

    private val activityLevels = mapOf(
        "Sedentary (little or no exercise)" to 1.2,
        "Lightly Active (light exercise/sports 1–3 days/week)" to 1.375,
        "Moderately Active (moderate exercise 3–5 days/week)" to 1.55,
        "Very Active (hard exercise 6–7 days/week)" to 1.725,
        "Super Active (very hard exercise/physical job)" to 1.9
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ageInput = findViewById(R.id.ageInput)
        weightInput = findViewById(R.id.weightInput)
        heightInput = findViewById(R.id.heightInput)
        genderGroup = findViewById(R.id.genderGroup)
        activitySpinner = findViewById(R.id.activitySpinner)
        calculateButton = findViewById(R.id.calculateButton)
        resultText = findViewById(R.id.resultText)

        // Populate Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, activityLevels.keys.toList())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        activitySpinner.adapter = adapter

        calculateButton.setOnClickListener {
            calculateCalories()
        }
    }

    private fun calculateCalories() {
        val age = ageInput.text.toString().toIntOrNull()
        val weight = weightInput.text.toString().toDoubleOrNull()
        val height = heightInput.text.toString().toDoubleOrNull()

        if (age == null || weight == null || height == null) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val gender = when (genderGroup.checkedRadioButtonId) {
            R.id.maleRadio -> "male"
            R.id.femaleRadio -> "female"
            else -> "male"
        }

        val bmr = if (gender == "male") {
            10 * weight + 6.25 * height - 5 * age + 5
        } else {
            10 * weight + 6.25 * height - 5 * age - 161
        }

        val activityFactor = activityLevels[activitySpinner.selectedItem.toString()] ?: 1.2
        val dailyCalories = bmr * activityFactor

        resultText.text = "You need approximately %.0f calories/day.".format(dailyCalories)
    }
}
