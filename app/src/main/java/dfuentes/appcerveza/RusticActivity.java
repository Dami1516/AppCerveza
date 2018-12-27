package dfuentes.appcerveza;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import extras.FirebaseReferences;
import extras.coccion_actual;

public class RusticActivity extends AppCompatActivity implements View.OnClickListener {
    private coccion_actual coccionActual;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coccionActual=(coccion_actual) getIntent().getExtras().getSerializable("Coccion");
        setContentView(R.layout.activity_rustic);
        cargarUI();
        setearDatabaseCoccion();
    }

    protected void cargarUI(){
        final Button btn_on = (Button) findViewById(R.id.buttonOn);
        btn_on.setOnClickListener(this);

        final Button btn_off = (Button) findViewById(R.id.buttonOff);
        btn_off.setOnClickListener(this);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run()
            {
                final TextView temp=(TextView)findViewById(R.id.tempRustic);
                temp.setText("Temperatura: "+coccionActual.getTemp_olla()+" °");
                Log.d("Timer","Paso por el timer con temp "+coccionActual.getTemp_olla());
            }
        };
        // Empezamos dentro de 10ms y luego lanzamos la tarea cada 2000ms
        timer.schedule(task, 10, 2000);
        if (coccionActual.getEstado()==0) {
            btn_off.setEnabled(false);
            btn_on.setEnabled(true);
        }
        else
        {
            btn_on.setEnabled(false);
            btn_off.setEnabled(true);
        }
    }

    protected void setearDatabaseCoccion(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Chequeo estado coccion actual
        DatabaseReference actual_Ref = database.getReference(FirebaseReferences.COCCION_ACTUAL_REFERENCE);
        actual_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                coccionActual=dataSnapshot.getValue(coccion_actual.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected void encender(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference(FirebaseReferences.ESTADO_REFERENCE).setValue(7);
        findViewById(R.id.buttonOn).setEnabled(false);
        findViewById(R.id.buttonOff).setEnabled(true);
    }
    protected void apagar(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference(FirebaseReferences.ESTADO_REFERENCE).setValue(0);
        findViewById(R.id.buttonOff).setEnabled(false);
        findViewById(R.id.buttonOn).setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonOn:
                encender();
                break;
            case R.id.buttonOff:
                apagar();
                break;
        }
    }
}
