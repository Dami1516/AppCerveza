package dfuentes.appcerveza;

import android.media.Image;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import extras.FirebaseReferences;
import extras.recetas;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolderResult> {

    private ArrayList<recetas> listRecetas;
    private final ClickListener listener;

    public RecipeAdapter(ArrayList<recetas> listRecetas, ClickListener listener) {
        this.listRecetas = listRecetas;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolderResult onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recipe,null,false);
        return new ViewHolderResult(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderResult viewHolderResult, int i) {
        viewHolderResult.asignarDatos(listRecetas.get(i));
    }

    @Override
    public int getItemCount() {
        return listRecetas.size();
    }

    protected class ViewHolderResult extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView titleRecipe;
        TextView ibuRecipe;
        TextView alcoholRecipe;
        ImageView imageRecipe;
        Button buttonCocinar;
        Button buttonDetalles;
        private WeakReference<ClickListener> listenerRef;

        protected ViewHolderResult(@NonNull View itemView, ClickListener listener) {
            super(itemView);
            listenerRef = new WeakReference<>(listener);
            titleRecipe=itemView.findViewById(R.id.titleRecipe);
            ibuRecipe=itemView.findViewById(R.id.ibuRecipe);
            alcoholRecipe=itemView.findViewById(R.id.alcoholRecipe);
            imageRecipe=itemView.findViewById(R.id.thumbnail);
            itemView.setOnClickListener(this);
            buttonCocinar=itemView.findViewById(R.id.buttonCocinar);
            buttonCocinar.setOnClickListener(this);
            buttonDetalles=itemView.findViewById(R.id.buttonDetalles);
            buttonDetalles.setOnClickListener(this);
        }

        protected void asignarDatos(recetas o) {
            titleRecipe.setText(o.getNombre());
            ibuRecipe.setText("IBU: "+String.valueOf(o.getIbu()));
            alcoholRecipe.setText("Alcohol: "+String.valueOf(o.getAlcohol())+" %");
            switch (o.getCreador()) {
                case "malta":
                    imageRecipe.setImageResource(R.drawable.malta);
                    break;
                case "catalina":
                    imageRecipe.setImageResource(R.drawable.catalina);
                    break;
                case "mapache":
                    imageRecipe.setImageResource(R.drawable.mapache);
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.buttonCocinar:
                    listenerRef.get().onPositionClicked(getAdapterPosition());
                    break;
                case R.id.buttonDetalles:
                    listenerRef.get().onPositionClicked(getAdapterPosition()*(-1)-1);
                    break;
            }

        }
    }
}
