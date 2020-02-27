package com.planetaagua.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.planetaagua.R;

import java.util.ArrayList;

public class Estoque extends AppCompatActivity {
    private CheckBox mCascatai, mSuperleve, mOurodaSerra;
    private Button mBtnResultado;
    private TextView mViewResultado;
    private ArrayList<String> mResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estoque);

        mCascatai = findViewById(R.id.check_cascatai);
        mSuperleve = findViewById(R.id.check_superleve);
        mOurodaSerra = findViewById(R.id.check_ourodaserra);

        mBtnResultado = findViewById(R.id.btnenviaestoque);
        mViewResultado = findViewById(R.id.txtresult);
        mResultado = new ArrayList<>();

        mViewResultado.setEnabled(false);

        mCascatai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCascatai.isChecked())
                    mResultado.add("Cascatai");
                else
                    mResultado.remove("Cascatai");
            }
        });
        mSuperleve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSuperleve.isChecked())
                    mResultado.add("SuperLeve");
                else
                    mResultado.remove("SuperLeve");
            }
        });
        mOurodaSerra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOurodaSerra.isChecked())
                    mResultado.add("Ouro da Serra");
                else
                    mResultado.remove("Ouro da Serra");
            }
        });
        mBtnResultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder();

                for (String s : mResultado)
                    stringBuilder.append(s).append("\n ");
                mViewResultado.setText(stringBuilder.toString());
                mViewResultado.setEnabled(false);

            }
        });

    }
}
