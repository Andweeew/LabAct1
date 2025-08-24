package com.example.labact1


import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    // Declare late-initialized variables for the UI elements
    private lateinit var redInputLayout: TextInputLayout
    private lateinit var greenInputLayout: TextInputLayout
    private lateinit var blueInputLayout: TextInputLayout
    private lateinit var redTextInput: TextInputEditText
    private lateinit var greenTextInput: TextInputEditText
    private lateinit var blueTextInput: TextInputEditText
    private lateinit var generateButton: Button
    private lateinit var colorView: View
    private lateinit var titleTextView: TextView
    private lateinit var colorCodeTextView: TextView


    private var originalButtonBackgroundColor: Int = Color.TRANSPARENT
    private var originalButtonTextColor: Int = Color.BLACK
    private var originalColorCodeTextColor: Int = Color.BLACK





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the UI elements
        titleTextView = findViewById(R.id.titleTextView)
        redInputLayout = findViewById(R.id.redInputLayout)
        greenInputLayout = findViewById(R.id.greenInputLayout)
        blueInputLayout = findViewById(R.id.blueInputLayout)
        redTextInput = findViewById(R.id.redTextInput)
        greenTextInput = findViewById(R.id.greenTextInput)
        blueTextInput = findViewById(R.id.blueTextInput)
        generateButton = findViewById(R.id.generateButton)
        colorView = findViewById(R.id.colorView)
        colorCodeTextView = findViewById(R.id.colorCodeTextView)






        generateButton.setOnClickListener {
            val redValue = redTextInput.text.toString().trim()
            val greenValue = greenTextInput.text.toString().trim()
            val blueValue = blueTextInput.text.toString().trim()


            redInputLayout.error = null
            greenInputLayout.error = null
            blueInputLayout.error = null

            val isRedValid = isValidHex(redValue)
            val isGreenValid = isValidHex(greenValue)
            val isBlueValid = isValidHex(blueValue)

            if (isRedValid && isGreenValid && isBlueValid) {
                try {

                    val hexColorString = "#${redValue.uppercase()}${greenValue.uppercase()}${blueValue.uppercase()}"
                    val generatedColor = Color.parseColor(hexColorString)


                    colorView.setBackgroundColor(generatedColor)
                    titleTextView.setTextColor(generatedColor)
                    colorCodeTextView.text = hexColorString


                    if (isColorDark(generatedColor)) {
                        colorCodeTextView.setTextColor(Color.WHITE)
                    } else {
                        colorCodeTextView.setTextColor(Color.BLACK)
                    }


                    generateButton.setBackgroundColor(generatedColor)
                    if (isColorDark(generatedColor)) {
                        generateButton.setTextColor(Color.WHITE)
                    } else {
                        generateButton.setTextColor(Color.BLACK)
                    }



                } catch (e: IllegalArgumentException) {
                    Toast.makeText(this, "Error: Invalid color string format.", Toast.LENGTH_SHORT).show()
                    resetToDefaultAppearance()
                }
            } else {
                if (!isRedValid) redInputLayout.error = "Invalid Red hex value"
                if (!isGreenValid) greenInputLayout.error = "Invalid Green hex value"
                if (!isBlueValid) blueInputLayout.error = "Invalid Blue hex value"
                Toast.makeText(this, "Please fix the invalid input fields.", Toast.LENGTH_SHORT).show()
                resetToDefaultAppearance()
            }
        }
    }




    private fun isValidHex(hex: String): Boolean {
        if (hex.length != 2) return false
        return hex.all { it.isDigit() || (it.uppercaseChar() in 'A'..'F') }
    }

    private fun isColorDark(color: Int): Boolean {
        return ColorUtils.calculateLuminance(color) < 0.5
    }

    private fun resetToDefaultAppearance() {
        titleTextView.setTextColor(Color.BLACK)


        generateButton.setBackgroundColor(originalButtonBackgroundColor)
        generateButton.setTextColor(originalButtonTextColor)


        colorView.setBackgroundColor(Color.LTGRAY)


        colorCodeTextView.text = "#CCCCCC"
        colorCodeTextView.setTextColor(originalColorCodeTextColor)


        redInputLayout.error = null

        greenInputLayout.error = null
        blueInputLayout.error = null
    }
}
