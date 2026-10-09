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

/**
 * An andorid calaculator application
 *
 * @author Alden Wyatt
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resultTv, solutionTv;
    MaterialButton ButtonC, ButtonBracketOpen, ButtonBracketClose;
    MaterialButton Button0, Button1, Button2, Button3, Button4, Button5, Button6, Button7, Button8, Button9;
    MaterialButton ButtonMul, ButtonPlus, ButtonMin, ButtonDivide, ButtonEquals;

    MaterialButton ButtonAcc, ButtonDot;

    /**
     * Intitilaizes the activity layout
     *
     * @param savedInstanceState Saved state bundle
     * */
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

    /**
     *  Defines all the componets by calling addCompnent
     * */
    public void defineComponents(){
        resultTv = findViewById(R.id.resault_tv);
        resultTv.setText("Alden Wyatt");
        solutionTv = findViewById(R.id.solution_tv);
        solutionTv.setText("11111111");

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

    /**
     * assigns buttons to the xml and sets a onclick listener
     *
     * @param btn refrence to the Material button to assign to
     * @param id refrence to the id of the xml button to assign it to
     * */
    public void addComponent(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    /**
     * gets the text of the button clicked and runs different operations
     * depending on it's value
     *
     * @param view trigger of the onclick event
     * */
    @Override
    public void onClick(View view){
        // gets the button that is clicked
        MaterialButton button = (MaterialButton) view;
        // gets the text of the button as a string
        String buttonText = button.getText().toString();
        // data to calculate by getting what is currentlyt in the solutionTv
        String dataToCalculate = solutionTv.getText().toString();

        // clears both Tvs if AC is pressed
        if(buttonText.equals("AC")){
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }
        // sets the solutionTv text to the reasultTv text if "=" is pressed
        if(buttonText.equals("=")){
            solutionTv.setText(resultTv.getText());
            return;
        }
        // if dataTocaluate is not empty is deletes it's leftmost digit
        if(buttonText.equals("C")){
            if(!dataToCalculate.isEmpty()){
                dataToCalculate = dataToCalculate.substring(0,dataToCalculate.length() - 1);
            }
        } else {
            //otherwise the data to caluate has the button's value appended
            dataToCalculate = dataToCalculate+buttonText;
        }
        //set the solutionTv text to the dataToCaluate
        solutionTv.setText(dataToCalculate);
        // gets the final reault by passing the data to getResults
        String finalResult = getResults(dataToCalculate);
        // if finalResult does not contain an error then it sets the resaultTv to the final result
        if(!finalResult.equals("Err")){
            resultTv.setText(finalResult);
        }
    }

    /**
     * evulates the passed in string data and returns the reaust as a string
     *
     * @param data the equation to be processed
     * @return returns the resault of the equation passed in, or "Err" is there is a exception
     * */
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
