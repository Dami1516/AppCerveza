package dfuentes.appcerveza;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Timer;
import java.util.TimerTask;

import extras.FirebaseReferences;
import extras.coccion_actual;
import extras.datos_arduino;
import extras.recetas;

public class MainActivity extends AppCompatActivity {

    private NotificationUtils mNotificationUtils;
    private int estadoLed=0;
    coccion_actual coccionActual;
    recetas recetaActual;
    private long timeStampArduino=0;
    datos_arduino datosArduino;
    private boolean seCargaronDatos=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splash);
        mNotificationUtils = new NotificationUtils(this);
        cargarDatos();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
    }

    private void cargarDatos(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference data_Ref = database.getReference(FirebaseReferences.DATA_REFERENCE);
        data_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                datosArduino=dataSnapshot.getValue(datos_arduino.class);
                Log.i("DATA ARDUINO",datosArduino.getIp());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Chequeo estado coccion actual
        DatabaseReference actual_Ref = database.getReference(FirebaseReferences.COCCION_ACTUAL_REFERENCE);
        actual_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                coccionActual=dataSnapshot.getValue(coccion_actual.class);
                if (!seCargaronDatos){
                    setContentView(R.layout.activity_main);
                    inicializar();
                    seCargaronDatos=true;
                }
                corroborarEstado();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // Get token
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.w("MainActivity", "getInstanceId failed", task.getException());
                    return;
                }

                // Get new Instance ID token
                String token = task.getResult().getToken();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                database.getReference(FirebaseReferences.USERS_TOKENS).setValue(token);
                // Log and toast
                String msg = getString(R.string.msg_token_fmt, token);
                Log.i("MainActivity", msg);
            }
        });
        FirebaseMessaging.getInstance().subscribeToTopic("Acciones");
    }

    private void inicializar(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run()
            {
                final TextView textIP=(TextView)findViewById(R.id.text_State_Arduino);
                if (timeStampArduino==0){
                    if (datosArduino!=null)
                        timeStampArduino=datosArduino.getTimestamp();
                    else
                        textIP.setText("Conectandose a Arduino...");
                }
                else {
                    if ((datosArduino.getTimestamp() - timeStampArduino) == 0) {
                        textIP.setText("Arduino fuera de línea");
                        coccionActual.setArduinoConectado(false);
                    }
                    else {
                        textIP.setText("Arduino en línea en " + datosArduino.getIp());
                        coccionActual.setArduinoConectado(true);
                    }
                }
                if (datosArduino!=null)
                    timeStampArduino=datosArduino.getTimestamp();
            }
        };
        // Empezamos dentro de 10ms y luego lanzamos la tarea cada 2000ms
        timer.schedule(task, 10, 2000);

        //Implementamos el evento "click" del boton button_new
        Button btn_new = (Button) findViewById(R.id.button_new);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), ActualActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        //Fin implementacion "click" boton button_new

        //Implementamos el evento "click" del boton button actual
        Button btn_actual = (Button) findViewById(R.id.button_actual);
        btn_actual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), ActualActivity.class);
                intent.putExtra("Coccion",coccionActual);
                //En caso de que este una receta en curso recupero la misma
                intent.putExtra("Receta",recetaActual);
                startActivityForResult(intent, 0);
            }
        });
        //Fin implementacion "click" boton boton_actual

        //Implementamos el evento "click" del boton button_recipe
        Button btn_rec = (Button) findViewById(R.id.button_recipe);
        btn_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), RecipeActivity.class);
                intent.putExtra("Coccion",coccionActual);
                startActivityForResult(intent, 0);
            }
        });
        //Fin implementacion "click" boton button_rec

        //Implementamos el evento "click" del boton button_info
        Button btn_inf = (Button) findViewById(R.id.button_info);
        btn_inf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), RusticActivity.class);
                intent.putExtra("Coccion",coccionActual);
                startActivityForResult(intent, 0);
            }
        });
        //Fin implementacion "click" boton button_info

        //Implementamos el evento "click" del boton button_history
        /*Button btn_his = (Button) findViewById(R.id.button_history);
        btn_his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                database.getReference(FirebaseReferences.ESTADO_REFERENCE).setValue(7);
            }
        });*/
        //Fin implementacion "click" boton button_history
    }

    private void cargarReceta() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Traigo todas las recetas;
        DatabaseReference receta_Ref = database.getReference(FirebaseReferences.RECETAS_REFERENCE);
        receta_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable_recetas = dataSnapshot.getChildren();
                for (DataSnapshot rec : iterable_recetas) {
                    if (((recetas)(rec.getValue(extras.recetas.class))).getNombre().equals(coccionActual.getReceta_elegida()))
                    recetaActual=(recetas)(rec.getValue(extras.recetas.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void corroborarEstado(){
        estadoLed=coccionActual.getEstado();
        if (estadoLed>0){
            Log.i("Estado Boton: ","Cocinando");
            cargarReceta();
            findViewById(R.id.button_new).setVisibility(View.INVISIBLE);
            findViewById(R.id.button_actual).setVisibility(View.VISIBLE);
        }
        else
        {
            Log.i("Estado Boton: ","Parado");
            findViewById(R.id.button_new).setVisibility(View.VISIBLE);
            findViewById(R.id.button_actual).setVisibility(View.INVISIBLE);
        }
    }

}

