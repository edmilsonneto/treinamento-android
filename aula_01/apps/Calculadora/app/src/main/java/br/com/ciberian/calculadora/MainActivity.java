package br.com.ciberian.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextView txvVisor;
    private String visor = "";
    private String operadorAtual = "";
    private String resultado = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txvVisor = (TextView)findViewById(R.id.txvVisor);
        txvVisor.setText(visor);
    }

    private void atualizarVisor(){
        txvVisor.setText(visor);
    }

    public void onClickNumero(View v){
        if(resultado != ""){
            limpar();
            atualizarVisor();
        }
        Button b = (Button) v;
        visor += b.getText();
        atualizarVisor();
    }

    public void onClickOperador(View v){
        if(visor == "") return;

        Button b = (Button)v;

        if(resultado != ""){
            String _display = resultado;
            limpar();
            visor = _display;
        }

        if(operadorAtual != ""){
            if(ehOperador(visor.charAt(visor.length()-1))){
                visor = visor.replace(visor.charAt(visor.length()-1), b.getText().charAt(0));
                atualizarVisor();
                return;
            }else{
                getResultado();
                visor = resultado;
                resultado = "";
            }
            operadorAtual = b.getText().toString();
        }
        visor += b.getText();
        operadorAtual = b.getText().toString();
        atualizarVisor();
    }

    private boolean ehOperador(char op){
        switch (op){
            case '+':
            case '-':
            case 'x':
            case '÷':return true;
            default: return false;
        }
    }

    private void limpar(){
        visor = "";
        operadorAtual = "";
        resultado = "";
    }

    public void onClickLimpar(View v){
        limpar();
        atualizarVisor();
    }

    private double calcular(String a, String b, String op){
        switch (op){
            case "+": return Double.valueOf(a) + Double.valueOf(b);
            case "-": return Double.valueOf(a) - Double.valueOf(b);
            case "x": return Double.valueOf(a) * Double.valueOf(b);
            case "÷": try{
                return Double.valueOf(a) / Double.valueOf(b);
            }catch (Exception e){
                Log.d("Calc", e.getMessage());
            }
            default: return -1;
        }
    }

    private boolean getResultado(){
        if(operadorAtual == "") return false;
        String[] operation = visor.split(Pattern.quote(operadorAtual));
        if(operation.length < 2) return false;
        resultado = String.valueOf(calcular(operation[0], operation[1], operadorAtual));
        return true;
    }

    public void onClickIgual(View v){
        if(visor == "") return;
        if(!getResultado()) return;
        txvVisor.setText(visor + "\n" + String.valueOf(resultado));
    }
}
