package com.planetaagua.Views;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.mazenrashed.printooth.Printooth;
import com.mazenrashed.printooth.data.printable.ImagePrintable;
import com.mazenrashed.printooth.data.printable.Printable;
import com.mazenrashed.printooth.data.printable.RawPrintable;
import com.mazenrashed.printooth.data.printable.TextPrintable;
import com.mazenrashed.printooth.data.printer.DefaultPrinter;
import com.mazenrashed.printooth.ui.ScanningActivity;
import com.mazenrashed.printooth.utilities.Printing;
import com.mazenrashed.printooth.utilities.PrintingCallback;
import com.planetaagua.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PrintingCallback {

    private ViewHolder mViewHolder = new ViewHolder();
    private static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("DD/MM/YYYY");
    Printing printing;
    Button btn_print, btn_print_image, btn_unpair_pair,getbtnestoque,btnImpSenha;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        getbtnestoque = (Button) findViewById(R.id.btnestoque);
        btnImpSenha = (Button)findViewById(R.id.impsenha);
        btn_print_image = (Button)findViewById(R.id.btnPrintImages);

        this.mViewHolder.txtSenha = findViewById(R.id.senha);
        this.mViewHolder.editFila = findViewById(R.id.codFila);
        this.mViewHolder.txtVenda = findViewById(R.id.venda);
        this.mViewHolder.txtEstoque = findViewById(R.id.estoque);
        this.mViewHolder.txtDate = findViewById(R.id.txtDay);

        //Data Corrente
        this.mViewHolder.txtDate.setText(SIMPLE_DATE_FORMAT.format(Calendar.getInstance().getTime()));

        getbtnestoque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEstoque();
            }
        });


        btnImpSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printText();
            }
        });

        btn_print_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printImages();
            }
        });



    }

    private void initView() {
        btn_print = (Button) findViewById(R.id.impsenha);
        btn_print_image = (Button) findViewById(R.id.btnPrintImages);
        btn_unpair_pair = (Button) findViewById(R.id.btnPiarUnpair);

        if (printing != null) {
            printing.setPrintingCallback(this);
        }

        btn_unpair_pair.setOnClickListener(view -> {
            if (Printooth.INSTANCE.hasPairedPrinter())
                Printooth.INSTANCE.removeCurrentPrinter();
            else {
                startActivityForResult(new Intent(MainActivity.this, ScanningActivity.class), ScanningActivity.SCANNING_FOR_PRINTER);
                changePairAndUnpair();
            }
        });


        btn_print_image.setOnClickListener(v -> {
            if (!Printooth.INSTANCE.hasPairedPrinter())
                startActivityForResult(new Intent(MainActivity.this, ScanningActivity.class), ScanningActivity.SCANNING_FOR_PRINTER);
            else
                printImages();
        });

        btn_print.setOnClickListener(v -> {
            if (!Printooth.INSTANCE.hasPairedPrinter())
                startActivityForResult(new Intent(MainActivity.this, ScanningActivity.class), ScanningActivity.SCANNING_FOR_PRINTER);
            else
                printText();
        });

        changePairAndUnpair();


    }

    private void printText() {
        ArrayList<Printable> printables = new ArrayList<>();
        printables.add(new RawPrintable.Builder(new byte[]{27, 100, 4}).build());

        printables.add(new TextPrintable.Builder()
                .setText("Planeta Agua ! Sua Senha e =")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());

        //Alterar Texto
        printables.add(new TextPrintable.Builder()
                .setText("Planeta Agua ! Senha = ")
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_60())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASIZED_MODE_BOLD())
                .setUnderlined(DefaultPrinter.Companion.getUNDERLINED_MODE_ON())
                .setNewLinesAfter(1)
                .build());

        printing.print(printables);
    }

    private void printImages() {
        ArrayList<Printable> printables = new ArrayList<>();

        //Load Imagem vindo da Internet
        Picasso.get()
                .load("https://lh4.googleusercontent.com/proxy/8icAGJktw3lSkoEqn-e3r5qqR0lLEoVe-WwnHNXgXu9SSRu4r68r-7NbulUKCkvl9fzWlksZt9UoUC2JNYin9AXfuP_mzJaPJ_r7jDn3K8-EG1iEpq1CogOCGhiDzUcvGw")
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        printables.add(new ImagePrintable.Builder(bitmap).build());

                        printing.print(printables);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }

    private void changePairAndUnpair() {
        if (Printooth.INSTANCE.hasPairedPrinter())
            btn_unpair_pair.setText(new StringBuilder("Unpair").append(Printooth.INSTANCE.getPairedPrinter().getName().toString()));
        else
            btn_unpair_pair.setText("Pair with Printer");
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.impsenha) {
            String value = this.mViewHolder.editFila.getText().toString();

            if ("".equals(value)) {
                Toast.makeText(this, this.getString(R.string.informe_valor), Toast.LENGTH_LONG).show();
            } else {

            }
        }

    }

    @Override
    public void connectingWithPrinter() {
        Toast.makeText(this, "Conectando a Impressora", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void connectionFailed(String s) {
        Toast.makeText(this, "Falha:" + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String s) {
        Toast.makeText(this, "Erro Inesperado:" + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void printingOrderSentSuccessfully() {
        Toast.makeText(this, "Solicitacao enviada a impressora", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ScanningActivity.SCANNING_FOR_PRINTER &&
                resultCode == Activity.RESULT_OK)
            initPrinting();
        changePairAndUnpair();
    }

    private void initPrinting() {
        if (!Printooth.INSTANCE.hasPairedPrinter()) {
            printing = Printooth.INSTANCE.printer();
            if (printing != null) {
                printing.setPrintingCallback(this);
            }
        }
    }


    private static class ViewHolder {
        TextView txtSenha;
        EditText editFila;
        TextView txtVenda;
        TextView txtEstoque;
        TextView txtDate;

    }

    public void openEstoque(){
        Intent intent = new Intent(this,Estoque.class);
        startActivity(intent);
    }
}
