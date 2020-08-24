package br.com.salariomes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonCalculate.setOnClickListener(this)
        buttonReset.setOnClickListener(this)
    }

    override fun onClick(view: View?) {

        val id = view?.id

        if (id == R.id.buttonCalculate) {

            if (valueHour.text.isNotEmpty() && dayWorked.text.isNotEmpty() && hourWorked.text.isNotEmpty()) {

                try {

                    var valueHour = valueHour.text.toString().toDouble()
                    var dayWorked = dayWorked.text.toString().toDouble()
                    var hourWorked = hourWorked.text.toString().toDouble()

                    var salaryTotal = calculateSalary(valueHour, dayWorked, hourWorked)
                    var inss = calculateInss(salaryTotal)
                    var fgts = calculateFgts(salaryTotal)
                    var salaryLiq = salaryTotal - inss - fgts

                    showMoney.text = "RS ${"%.2f".format(salaryTotal)}"
                    showInssValue.text = "RS ${"%.2f".format(inss)}"
                    showFgtsValue.text = "R$ ${"%.2f".format(fgts)}"
                    showSalaryliquido.text = "R$ ${"%.2f".format(salaryLiq)}"


                } catch (erro: NullPointerException) {

                    Toast.makeText(
                        applicationContext,
                        R.string.message_erro_calculo,
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

            } else {

                Toast.makeText(applicationContext, R.string.message_erro, Toast.LENGTH_LONG)
                    .show()
            }

        } else if(id == R.id.buttonReset){

            clearFields()
        }

    }

    private fun clearFields() {

        valueHour.setText("")
        dayWorked.setText("")
        hourWorked.setText("")

        showMoney.text = "R$ 0.0"
        showInssValue.text = "R$ 0.0"
        showFgtsValue.text = "R$ 0.0"
        showSalaryliquido.text = "R$ 0.0"
    }
}

private fun showValue(salaryTotal: Double, salaryLiq: Double, inss: Double, fgts: Double) {


}

private fun calculateFgts(salaryTotal: Double): Double {

    var fgts: Double = salaryTotal * 0.08
    return fgts
}


private fun calculateInss(salaryTotal: Double): Double {

    var valueInss = 0.0


    when {

        salaryTotal <= 1045.00 -> {

            valueInss += salaryTotal * (7.5 / 100)
        }

        salaryTotal in 1045.00..2089.60 -> {

            valueInss += salaryTotal * 0.09
        }
        salaryTotal in 2089.60..3134.40 -> {

            valueInss += salaryTotal * (12 / 100)
        }

        salaryTotal in 3134.40..6101.06 -> {

            valueInss += salaryTotal * (14 / 100)
        }

    }

    return valueInss

}




private fun calculateSalary(valueHour: Double, dayWorked: Double, hourWorked: Double): Double {

    return valueHour * dayWorked * hourWorked

}


