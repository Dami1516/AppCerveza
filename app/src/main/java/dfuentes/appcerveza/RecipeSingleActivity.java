package dfuentes.appcerveza;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import extras.coccion_actual;
import extras.recetas;

public class RecipeSingleActivity extends AppCompatActivity {

    private coccion_actual coccionActual;
    private recetas receta;
    private HashMap<Integer,String> mapColores;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        coccionActual=(coccion_actual) getIntent().getExtras().getSerializable("Coccion");
        receta=(recetas) getIntent().getExtras().getSerializable("Receta");
        setContentView(R.layout.activity_recipe_single);
        cargarMapColores();
        cargarUI();
    }

    protected void cargarUI(){
        ((TextView)findViewById(R.id.titleRecipe)).setText(receta.getNombre());
        ((TextView)findViewById(R.id.ibuRecipe)).setText("Amargor: "+receta.getIbu()+" IBU");
        ((TextView)findViewById(R.id.alcoholRecipe)).setText("Alcohol: "+receta.getAlcohol()+"%");
        if (obtenerColor(receta.getColor())=="")
            ((TextView)findViewById(R.id.color)).setText("Color no disponible");
        else
            ((TextView)findViewById(R.id.color)).setBackgroundColor(Color.parseColor(obtenerColor(receta.getColor())));
        ((TextView)findViewById(R.id.levaduraRecipe)).setText("Levadura: "+receta.getLevadura());
        ((TextView)findViewById(R.id.diRecipe)).setText("DI: "+receta.getDi());
        ((TextView)findViewById(R.id.dfRecipe)).setText("DF: "+receta.getDf());
    }

    protected String obtenerColor(long SRM) {
        String tr = "";
        if (mapColores.containsKey((int) SRM)) {
            return mapColores.get((int) SRM);
        }
        //Si llego aca busco el mas cercano
        for (Map.Entry<Integer, String> entries : mapColores.entrySet()) {
            if (mapColores.containsKey(entries.getKey())) {//Si existe
                if (entries.getKey() <= SRM) {
                    tr = entries.getValue();
                } else
                    return tr;
            }
        }
        return tr;
    }

    protected void cargarMapColores(){
        mapColores=new HashMap<>();
        mapColores.put(2,"#ffff45");
        mapColores.put(3,"#ffe93e");
        mapColores.put(4,"#fed849");
        mapColores.put(6,"#ffa846");
        mapColores.put(9,"#f49f44");
        mapColores.put(12,"#d77f59");
        mapColores.put(15,"#94523a");
        mapColores.put(18,"#804541");
        mapColores.put(20,"#5b342f");
        mapColores.put(24,"#4c3b2b");
        mapColores.put(30,"#38302e");
        mapColores.put(40,"#31302c");
    }
}
