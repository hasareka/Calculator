package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.math.BigDecimal;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView result,solution;
    MaterialButton buttonC, buttonBracketOpen, buttonBracketClose;
    MaterialButton buttonDivide, ButtonMultiply, ButtonPlus, ButtonMinus, ButtonEqual;
    MaterialButton Button0,Button1,Button2,Button3,Button4,Button5,Button6,Button7,Button8,Button9;
    MaterialButton ButtonAC,ButtonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result);
        solution = findViewById(R.id.solution);

        assignId(buttonC, R.id.button_c);
        assignId(buttonBracketOpen, R.id.open_bracket);
        assignId(buttonBracketClose, R.id.close_bracket);
        assignId(buttonDivide, R.id.button_divide);
        assignId(ButtonMultiply, R.id.button_multiply);
        assignId(ButtonPlus, R.id.button_plus);
        assignId(ButtonMinus, R.id.button_miner);
        assignId(ButtonEqual, R.id.button_equal);
        assignId(Button0, R.id.button_0);
        assignId(Button1, R.id.button_1);
        assignId(Button2, R.id.button_2);
        assignId(Button3, R.id.button_3);
        assignId(Button4, R.id.button_4);
        assignId(Button5, R.id.button_5);
        assignId(Button6, R.id.button_6);
        assignId(Button7, R.id.button_7);
        assignId(Button8, R.id.button_8);
        assignId(Button9, R.id.button_9);
        assignId(ButtonAC, R.id.button_ac);
        assignId(ButtonDot, R.id.button_dot);


    }

    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solution.getText().toString();

        if (buttonText.equals("AC")) {
            solution.setText("");
            result.setText("0");
            return;
        }

        if (buttonText.equals("=")) {
            solution.setText(result.getText());
            return;
        }

        if (buttonText.equals("C")) {
            if (!dataToCalculate.isEmpty()) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }
        } else {
            dataToCalculate = dataToCalculate + buttonText;
        }
        solution.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);

        if (!finalResult.equals("Error") && !finalResult.isEmpty()) {
            result.setText(finalResult);
        } else if (finalResult.isEmpty()) {
            result.setText("0");
        }
    }


    String getResult(String data) {
        if (data.isEmpty()) {
            return "";
        }

        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            Object result = context.evaluateString(scriptable, data, "Javascript", 1, null);

            BigDecimal bigDecimalResult = new BigDecimal(result.toString());
            String finalResult = bigDecimalResult.toPlainString();

            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;

        } catch (Exception e) {
            return "Error";
        } finally {
            Context.exit();
        }
    }

}