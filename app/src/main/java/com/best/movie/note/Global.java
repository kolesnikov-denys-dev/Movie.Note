package com.best.movie.note;

import android.app.Application;


import com.best.movie.note.model.dagger.component.AppDataComponent;
import com.best.movie.note.model.dagger.component.DaggerAppDataComponent;

public class Global extends Application {

    private static AppDataComponent sAppDataComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        sAppDataComponent = DaggerAppDataComponent.create();
    }

    public static AppDataComponent getAppComponent() {
        return sAppDataComponent;
    }
}
