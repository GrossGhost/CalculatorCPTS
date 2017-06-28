package com.example.gross.calculatorcpts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String MATH_OPERATORS = "+-*/|&^";

    private StringBuilder mathStr = new StringBuilder();

    @BindView(R.id.textView)
    TextView calcTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6,
            R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnPlus, R.id.btnMinus, R.id.btnMultiple,
            R.id.btnDiv, R.id.btnOpenedBracket, R.id.btnClosedBracket})
    public void onNumberClick(Button clickedButton) {

        mathStr.append(clickedButton.getText());
        calcTxt.setText(mathStr);

    }

    @OnClick(R.id.btnDot)
    public void onDotClick() {
        if (!isLastNumberHasDot(mathStr)) {
            mathStr.append('.');
            calcTxt.setText(mathStr);
        }
    }

    @OnClick(R.id.btnBackspace)
    public void onBackspaceClick() {
        if (mathStr.length() != 0) {
            mathStr.deleteCharAt(mathStr.length() - 1);
            calcTxt.setText(mathStr);
        }
    }

    public boolean isLastNumberHasDot(StringBuilder str) {
        StringBuilder builder = new StringBuilder();
        int length = str.length() - 1;
        for (int i = length; i >= 0 && !(MATH_OPERATORS.contains("" + str.charAt(i))); i--) {
            builder.append(str.charAt(i));
        }
        return builder.toString().contains(".");
    }

    @OnClick({R.id.btnAND, R.id.btnOR, R.id.btnXOR})
    public void onBitOperClick(View view) {
        switch (view.getId()) {
            case R.id.btnAND:
                mathStr.append('&');
                break;
            case R.id.btnOR:
                mathStr.append('|');
                break;
            case R.id.btnXOR:
                mathStr.append('^');
                break;
        }
        calcTxt.setText(mathStr);
    }

    @OnClick(R.id.btnEqual)
    public void onEqualClick() {
        if (mathStr.length() != 0) {
            MathParser parser = new MathParser();
            double result;
            try {
                result = (parser.eval(mathStr.toString()));
                mathStr.setLength(0);
                mathStr.append(result);
                calcTxt.setText(mathStr);

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Incorrect Input\n" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
