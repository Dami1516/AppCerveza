package dfuentes.appcerveza;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import objetos.recetas;

public class RecipeFragment extends Fragment {
    int position;
    private TextView textView;
    private recetas rec;
    View view;

    public static Fragment getInstance(int position, recetas rec) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        bundle.putSerializable("rec",rec);
        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setArguments(bundle);
        return recipeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("pos");
        rec = (recetas) getArguments().getSerializable("rec");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setearReceta();
    }

    private void setearReceta(){
        ((TextView) getView().findViewById(R.id.titulo)).setText(rec.getNombre());
        mostrarMaltas();
        mostrarLupulos();/*
        textView = (TextView) view.findViewById(R.id.alcohol_cerveza);
        textView.setText("Alc.: "+String.valueOf(rec.getAlcohol())+"%");
        textView = (TextView) view.findViewById(R.id.ibu_cerveza);
        textView.setText("IBU´s: "+String.valueOf(rec.getIbu()));
        textView = (TextView) view.findViewById(R.id.di_cerveza);
        textView.setText("DI: "+String.valueOf(rec.getDi()));
        textView = (TextView) view.findViewById(R.id.df_cerveza);
        textView.setText("DF: "+String.valueOf(rec.getDf()));
        textView = (TextView) view.findViewById(R.id.color_cerveza);
        textView.setText("Color: "+String.valueOf(rec.getColor())+"EBC");
        textView = (TextView) view.findViewById(R.id.levadura_cerveza);
        textView.setText("Levadura: "+rec.getLevadura());*/

    }

    private void mostrarMaltas(){
        //Primero la cabezera
        TableRow currentRow=new TableRow(getContext());
        TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

        //Cabecera Nombre
        TextView currentTextView = new TextView(getContext());
        currentTextView.setText("Tipo de Malta");
        currentTextView.setTextSize(18);
        currentTextView.setTextColor(Color.WHITE);

        currentRow.setLayoutParams(params);
        currentRow.addView(currentTextView);

        //Cabecera cantidad
        currentTextView = new TextView(getContext());
        currentTextView.setText("Cantidad");
        currentTextView.setTextSize(18);
        currentTextView.setTextColor(Color.WHITE);

        currentRow.setLayoutParams(params);
        currentRow.addView(currentTextView);

        ((TableLayout) getView().findViewById(R.id.tabla_maltas)).addView(currentRow);
        ((TableLayout) getView().findViewById(R.id.tabla_maltas)).setColumnStretchable(0, true);
        for (Map.Entry<String,Object> entry : rec.getMaltas().entrySet()){
            currentRow=new TableRow(getContext());
            params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

            //Campo con el nombre de la malta
            currentTextView = new TextView(getContext());
            currentTextView.setText(entry.getKey().toString());
            currentTextView.setTextSize(18);
            currentTextView.setTextColor(Color.WHITE);

            currentRow.setLayoutParams(params);
            currentRow.addView(currentTextView);

            //Campo con la cantidad
            currentTextView = new TextView(getContext());
            if (entry.getValue().toString().charAt(0)=='.') {
                Integer valor = (int)(Double.parseDouble(entry.getValue().toString()) * 1000);
                currentTextView.setText(currentTextView.getText() + valor.toString() + " Gramos\n");
            }
            else
                currentTextView.setText(currentTextView.getText()+entry.getValue().toString()+" Kg\n");
            currentTextView.setTextSize(18);
            currentTextView.setTextColor(Color.WHITE);

            currentRow.setLayoutParams(params);
            currentRow.addView(currentTextView);

            ((TableLayout) getView().findViewById(R.id.tabla_maltas)).addView(currentRow);
        }
    }

    private void mostrarLupulos(){
        //Primero la cabezera
        TableRow currentRow=new TableRow(getContext());
        TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

        //Cabecera Minuto
        TextView currentTextView = new TextView(getContext());
        currentTextView.setText("Variedad");
        currentTextView.setTextSize(18);
        currentTextView.setTextColor(Color.WHITE);

        currentRow.setLayoutParams(params);
        currentRow.addView(currentTextView);

        //Cabecera Nombre
        currentTextView = new TextView(getContext());
        currentTextView.setText("Minuto de agregado");
        currentTextView.setTextSize(18);
        currentTextView.setTextColor(Color.WHITE);

        currentRow.setLayoutParams(params);
        currentRow.addView(currentTextView);

        //Cabecera cantidad
        currentTextView = new TextView(getContext());
        currentTextView.setText("Cantidad (gr)");
        currentTextView.setTextSize(18);
        currentTextView.setTextColor(Color.WHITE);

        currentRow.setLayoutParams(params);
        currentRow.addView(currentTextView);

        ((TableLayout) getView().findViewById(R.id.tabla_lupulos)).addView(currentRow);
        ((TableLayout) getView().findViewById(R.id.tabla_lupulos)).setColumnStretchable(0, true);
        for (Map.Entry<String,Object> entry : rec.getLupulos().entrySet()){//Obtengo cada variedad
            TreeMap<String,Object> ordenado = new TreeMap<String, Object>(((HashMap<String,Object>)entry.getValue()));
            for (Map.Entry<String,Object> entrada : ordenado.entrySet()) {//Obtengo para cada variedad sus filas
                currentRow = new TableRow(getContext());
                params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);

                //Campo con el nombre del lupulo
                currentTextView = new TextView(getContext());
                currentTextView.setText(entry.getKey().toString());
                currentTextView.setTextSize(18);
                currentTextView.setTextColor(Color.WHITE);

                currentRow.setLayoutParams(params);
                currentRow.addView(currentTextView);

                //Campo con el minuto de agregado
                currentTextView = new TextView(getContext());
                currentTextView.setText(entrada.getKey().toString());
                currentTextView.setTextSize(18);
                currentTextView.setTextColor(Color.WHITE);

                currentRow.setLayoutParams(params);
                currentRow.addView(currentTextView);

                //Campo con la cantidad
                currentTextView = new TextView(getContext());
                currentTextView.setText(entrada.getValue().toString() + " Gramos\n");
                currentTextView.setTextSize(18);
                currentTextView.setTextColor(Color.WHITE);

                currentRow.setLayoutParams(params);
                currentRow.addView(currentTextView);

                ((TableLayout) getView().findViewById(R.id.tabla_lupulos)).addView(currentRow);
            }
        }
    }
}