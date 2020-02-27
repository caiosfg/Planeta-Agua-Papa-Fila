package com.planetaagua.Controllers;

import android.app.Application;
import android.widget.Button;

import com.mazenrashed.printooth.Printooth;
import com.mazenrashed.printooth.utilities.Printing;

public class PrinterClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Printooth.INSTANCE.init(this);

    }
}
