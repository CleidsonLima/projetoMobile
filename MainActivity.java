import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextBillAmount;
    private EditText editTextDescription;
    private SeekBar seekBarTipPercentage;
    private TextView textViewTipPercentage;
    private Button btnCalculate;
    private TextView textViewTipAmount;
    private TextView textViewTotalAmount;

    private double billAmount = 0.0;
    private double tipPercentage = 0.15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextBillAmount = findViewById(R.id.editTextBillAmount);
        editTextDescription = findViewById(R.id.editTextDescription);
        seekBarTipPercentage = findViewById(R.id.seekBarTipPercentage);
        textViewTipPercentage = findViewById(R.id.textViewTipPercentage);
        btnCalculate = findViewById(R.id.btnCalculate);
        textViewTipAmount = findViewById(R.id.textViewTipAmount);
        textViewTotalAmount = findViewById(R.id.textViewTotalAmount);

        // Adiciona um listener ao SeekBar para atualizar a porcentagem de gorjeta
        seekBarTipPercentage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tipPercentage = progress / 100.0;
                updateTipPercentageTextView();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Adiciona um listener ao EditText para atualizar o valor da conta
        editTextBillAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                try {
                    billAmount = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    billAmount = 0.0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    public void calculateTip(View view) {
        double tipAmount = billAmount * tipPercentage;
        double totalAmount = billAmount + tipAmount;

        String formattedTipAmount = NumberFormat.getCurrencyInstance().format(tipAmount);
        String formattedTotalAmount = NumberFormat.getCurrencyInstance().format(totalAmount);

        textViewTipAmount.setText("Valor da Gorjeta: " + formattedTipAmount);
        textViewTotalAmount.setText("Total (Conta + Gorjeta): " + formattedTotalAmount);
    }

    private void updateTipPercentageTextView() {
        String formattedTipPercentage = String.format("Porcentagem de Gorjeta: %.0f%%", tipPercentage * 100);
        textViewTipPercentage.setText(formattedTipPercentage);
    }
}