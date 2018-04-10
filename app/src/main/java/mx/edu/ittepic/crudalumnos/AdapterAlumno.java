package mx.edu.ittepic.crudalumnos;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Bryan on 23/03/2018.
 */

public class AdapterAlumno extends RecyclerView.Adapter<AdapterAlumno.ViewHolderAlumno>
                            implements View.OnClickListener{

    ArrayList<Alumno> listAlumnos;
    private View.OnClickListener listener;

    public AdapterAlumno(ArrayList<Alumno> listAlumnos) {
        this.listAlumnos = listAlumnos;
    }

    @Override
    public ViewHolderAlumno onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);
        view.setOnClickListener(this);
        return new ViewHolderAlumno(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderAlumno holder, int position) {
        holder.txtNombre.setText(listAlumnos.get(position).getNombre());
        holder.txtDireccion.setText(listAlumnos.get(position).getDireccion());
    }

    @Override
    public int getItemCount() {
        return listAlumnos.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolderAlumno extends RecyclerView.ViewHolder {

        TextView txtNombre, txtDireccion;

        public ViewHolderAlumno(View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtDireccion = itemView.findViewById(R.id.txtDireccion);
        }

    }
}
