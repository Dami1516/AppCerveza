package dfuentes.appcerveza;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import objetos.coccion_actual;

public class RecipeSingleActivity extends AppCompatActivity {

    private coccion_actual coccionActual;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        coccionActual=(coccion_actual) getIntent().getExtras().getSerializable("Coccion");
        setContentView(R.layout.activity_recipe_single);
    }
}
