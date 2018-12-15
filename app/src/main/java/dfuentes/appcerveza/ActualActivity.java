package dfuentes.appcerveza;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import objetos.FirebaseReferences;
import objetos.coccion_actual;
import objetos.recetas;

public class ActualActivity extends AppCompatActivity {

    coccion_actual coccionActual;
    recetas receta;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_coccion_actual);
        coccionActual=(coccion_actual) getIntent().getExtras().getSerializable("Coccion");
        receta=(recetas) getIntent().getExtras().getSerializable("Receta");
        actualizarDatos();
        Log.i("RECETA",receta.getLevadura());
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run()
            {
                viewSegunEstado();
            }
        };
        // Empezamos dentro de 10ms y luego lanzamos la tarea cada 2000ms
        timer.schedule(task, 10, 2000);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected void viewSegunEstado(){
        TextView contenido = ((TextView) findViewById(R.id.contenido));
        switch (coccionActual.getEstado()){
            case 1: //Receta Elegida,esperando para iniciar
                contenido.setText("Por favor verifique que se encuentren conectadas las resistencias a la olla y al controlador, luego verifique tambien que el termomemetro de la olla se encuentra dentro de la misma, por último pulse el botón del controlador para dar inicio al proceso");
                break;
            case 2:
                contenido.setText("Calentando agua para maceración:\nTemperatura actual: "+coccionActual.getTemp_olla()+"ºC\n\n\nTemperatura objetivo: ("+receta.getTemp_mace()+" ºC)"+receta.getTemp_mace()+" ºC)");
                break;
            case 3:
                contenido.setText("El agua ya llego a la temperatura objetivo, por favor vierta la misma en el macerador, coloque los granos y el termometro y luego pulse continuar");
                break;
        }
    }

    private void actualizarDatos(){
        //Chequeo estado coccion actual
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference actual_Ref = database.getReference(FirebaseReferences.COCCION_ACTUAL_REFERENCE);
        actual_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                coccionActual=dataSnapshot.getValue(coccion_actual.class);
                updateTextos();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateTextos(){
        ((TextView) findViewById(R.id.titulo)).setText(coccionActual.getReceta_elegida());


    }

}
