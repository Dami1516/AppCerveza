package dfuentes.appcerveza;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import extras.FirebaseReferences;
import extras.coccion_actual;
import extras.recetas;

public class RecipeActivity extends AppCompatActivity{

    private ArrayList<recetas> recetas;
    private coccion_actual coccionActual;
    RecyclerView recycler;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recetas=new ArrayList<>();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        coccionActual=(coccion_actual) getIntent().getExtras().getSerializable("Coccion");
        setContentView(R.layout.activity_recipe);
        recuperarRecetas();
    }

    @Override
    public void onStart() {
        super.onStart();
        findViewById(R.id.pg_loading).setVisibility(View.VISIBLE);
        EditText toSearch = findViewById(R.id.textBusqueda);
        toSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                findViewById(R.id.pg_loading).setVisibility(View.VISIBLE);
                cargarDatos(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        findViewById(R.id.pg_loading).setVisibility(View.VISIBLE);
    }

    private void recuperarRecetas() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Traigo todas las recetas;
        DatabaseReference receta_Ref = database.getReference(FirebaseReferences.RECETAS_REFERENCE);
        receta_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable_recetas = dataSnapshot.getChildren();
                for (DataSnapshot rec : iterable_recetas) {
                    recetas.add(rec.getValue(extras.recetas.class));
                }
                EditText toSearch = findViewById(R.id.textBusqueda);
                cargarDatos(toSearch.getText().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recycler = findViewById(R.id.recyclerId);
        recycler.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(linearLayoutManager);
    }

    protected void cargarDatos(String filtro){
        RecipeAdapter adapter=new RecipeAdapter(filtrarRecetas(filtro), new ClickListener() {
            @Override public void onPositionClicked(int position) {
                if (position<0) {//se presiono el boton de detalles
                    Intent intent = new Intent(getApplicationContext(), RecipeSingleActivity.class);
                    intent.putExtra("Coccion", coccionActual);
                    intent.putExtra("Receta", recetas.get(position*(-1)-1));
                    startActivity(intent);
                }
                else {//Se presiono el boton de cocinar
                    if (coccionActual.getArduinoConectado()) {//Chequeo si esta disponible el arduino
                        Intent intent = new Intent(getApplicationContext(), ActualActivity.class);
                        intent.putExtra("Coccion", coccionActual);
                        intent.putExtra("Receta", recetas.get(position));
                        setCoccionActual(position);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Asegurese que el sistema se encuentre online antes de iniciar", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        findViewById(R.id.pg_loading).setVisibility(View.INVISIBLE);
        recycler.setAdapter(adapter);
    }

    protected void setCoccionActual(int indexReceta){
        recetas rec=recetas.get(indexReceta);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference(FirebaseReferences.ESTADO_REFERENCE).setValue(1);
        database.getReference(FirebaseReferences.RECETA_ELEGIDA_REFERENCE).setValue(rec.getNombre());
    }

    protected ArrayList<recetas> filtrarRecetas(String filtro){
        if (filtro.isEmpty()|| filtro.equals(""))
            return recetas;
        ArrayList<recetas> filtradas=new ArrayList<>();
        for (recetas r:recetas) {
            if (r.getNombre().toLowerCase().contains(filtro.toLowerCase())){
                filtradas.add(r);
            }
        }
        if (filtradas.size()==0)
            Toast.makeText(getApplicationContext(),"No se encontraron resultados",Toast.LENGTH_SHORT).show();
        return filtradas;
    }
}