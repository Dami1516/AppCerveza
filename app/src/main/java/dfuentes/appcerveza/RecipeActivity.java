package dfuentes.appcerveza;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

import objetos.FirebaseReferences;
import objetos.coccion_actual;

public class RecipeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinkedList<objetos.recetas> recetas;
    private coccion_actual coccionActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        coccionActual=(coccion_actual) getIntent().getExtras().getSerializable("Coccion");
        setContentView(R.layout.activity_recipe);
        cargarDatos();
    }

    @Override
    public void onStart() {
        super.onStart();
        cheaquearEstadoCoccion();
        // Check if user is signed in (non-null) and update UI accordingly.
    }

    private void cargarDatos(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Traigo todas las recetas;
        DatabaseReference receta_Ref = database.getReference(FirebaseReferences.RECETAS_REFERENCE);
        receta_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable_recetas=dataSnapshot.getChildren();
                recetas=new LinkedList<>();
                for (DataSnapshot rec : iterable_recetas){
                    recetas.addLast(rec.getValue(objetos.recetas.class));
                }
                viewPager = (ViewPager) findViewById(R.id.container);
                ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),recetas.size(),recetas);
                viewPager.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Implementamos el evento "click" del boton button_start
        Button btn_rec = (Button) findViewById(R.id.button_start);
        btn_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), ActualActivity.class);
                intent.putExtra("Coccion",coccionActual);
                intent.putExtra("Receta",recetas.get(viewPager.getCurrentItem()));
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                database.getReference(FirebaseReferences.ESTADO_REFERENCE).setValue(1);
                database.getReference(FirebaseReferences.RECETA_ELEGIDA_REFERENCE).setValue(recetas.get(viewPager.getCurrentItem()).getNombre());
                startActivityForResult(intent, 0);
            }
        });
        cheaquearEstadoCoccion();

        //Fin implementacion "click" boton button_start

    }

    public void cheaquearEstadoCoccion() {
        Button btn_rec = (Button) findViewById(R.id.button_start);
        if (!coccionActual.getArduinoConectado()) {//Si el arduino no esta conectado
            btn_rec.setEnabled(false);
        } else {
            if (coccionActual.getEstado() > 2) //Ya inicio la maceración
                btn_rec.setEnabled(false);
            else
                btn_rec.setEnabled(true);
        }
    }
}