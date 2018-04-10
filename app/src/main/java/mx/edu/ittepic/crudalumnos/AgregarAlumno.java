package mx.edu.ittepic.crudalumnos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Bryan on 24/03/2018.
 */

public class AgregarAlumno extends AppCompatActivity {

    EditText txtNombre, txtDireccion;
    Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_alumno);

        txtNombre = findViewById(R.id.txtNombreAgregar);
        txtDireccion = findViewById(R.id.txtDireccionAgregar);
        btnAgregar = (Button) findViewById(R.id.btnAgregar);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombre = txtNombre.getText().toString();
                String direccion = txtDireccion.getText().toString();
                new AlumnoAPI().execute("http://172.20.2.145:8080/datos1/insertar_alumno.php",
                        nombre, direccion);

                finish();
            }
        });

    }

}
