package com.example.calculalor;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resultTv, solutionTv;
    MaterialButton ButtonC, ButtonBracketOpen, ButtonBracketClose;
    MaterialButton Button0, Button1, Button2, Button3, Button4, Button5, Button6, Button7, Button8, Button9;
    MaterialButton ButtonMul, ButtonPlus, ButtonMin, ButtonDivide, ButtonEquals;

    MaterialButton ButtonAcc, ButtonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        defineComponents();
    }

    public void defineComponents(){
        resultTv = findViewById(R.id.resault_tv);
        solutionTv = findViewById(R.id.solution_tv);

        addComponent(Button0, R.id.button_0);
        addComponent(Button1, R.id.button_1);
        addComponent(Button2, R.id.button_2);
        addComponent(Button3, R.id.button_3);
        addComponent(Button4, R.id.button_4);
        addComponent(Button5, R.id.button_5);
        addComponent(Button6, R.id.button_6);
        addComponent(Button7, R.id.button_7);
        addComponent(Button8, R.id.button_8);
        addComponent(Button9, R.id.button_9);

        addComponent(ButtonMul, R.id.button_mul);
        addComponent(ButtonPlus, R.id.button_plus);
        addComponent(ButtonMin, R.id.button_min);
        addComponent(ButtonDivide, R.id.button_divide);
        addComponent(ButtonEquals, R.id.button_eq);

        addComponent(ButtonC, R.id.button_c);
        addComponent(ButtonBracketOpen, R.id.button_open_bracket);
        addComponent(ButtonBracketClose, R.id.button_close_bracket);

        addComponent(ButtonAcc, R.id.button_ac);
        addComponent(ButtonDot, R.id.button_dot);
    }

    public void addComponent(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if(buttonText.equals("AC")){
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }
        if(buttonText.equals("=")){
            solutionTv.setText(resultTv.getText());
            return;
        }
        if(buttonText.equals("C")){
            if(!dataToCalculate.isEmpty()){
                dataToCalculate = dataToCalculate.substring(0,dataToCalculate.length() - 1);
            }
        } else {
            dataToCalculate = dataToCalculate+buttonText;
        }

        solutionTv.setText(dataToCalculate);

        String finalResult = getResults(dataToCalculate);
        if(!finalResult.equals("Err")){
            resultTv.setText(finalResult);
        }
    }

    String getResults(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initSafeStandardObjects();
            return context.evaluateString(scriptable, data, "Javascript", 1, null).toString();


        } catch (Exception e) {
            return "Err";
        }
    }
}
