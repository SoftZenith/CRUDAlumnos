package mx.edu.ittepic.crudalumnos;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<Alumno> listaAlumnos;
    static AdapterAlumno adapter;
    RecyclerView rccAlumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        listaAlumnos = new ArrayList<>();
        rccAlumnos = findViewById(R.id.rcvAlumnos);
        rccAlumnos.setLayoutManager(new LinearLayoutManager(this));

        //llenarAlumnos();

        adapter = new AdapterAlumno(listaAlumnos);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ModificarAlumno.class);
                intent.putExtra("id", listaAlumnos.get(rccAlumnos.getChildAdapterPosition(view)).getId());
                intent.putExtra("nombre", listaAlumnos.get(rccAlumnos.getChildAdapterPosition(view)).getNombre());
                intent.putExtra("direccion", listaAlumnos.get(rccAlumnos.getChildAdapterPosition(view)).getDireccion());
                startActivity(intent);
                adapter.notifyDataSetChanged();
            }
        });

        rccAlumnos.setAdapter(adapter);

        loadSwipe();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getBaseContext(), AgregarAlumno.class);
                startActivity(intent);
            }
        });

        new AlumnoAPI().execute();
        adapter.notifyDataSetChanged();
    }

    public void loadSwipe(){
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //viewHolder.getAdapterPosition() para obtener la posición del elemento arratrado
                try {
                    String id = listaAlumnos.get(viewHolder.getAdapterPosition()).getId() + "";
                    Toast.makeText(getApplicationContext(), id +" Was deleted", Toast.LENGTH_SHORT).show();
                    listaAlumnos.remove(viewHolder.getAdapterPosition());
                    new AlumnoAPI().execute("http://172.20.2.145:8080/datos1/borrar_alumno.php", id);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Lista vacia", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Paint color = new Paint();

                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                    View itemView = viewHolder.itemView;

                    if(dX>0){ //movimiento en horizontal
                        color.setColor(Color.parseColor("#ed001a"));
                        RectF fondo = new RectF((float)itemView.getLeft(),(float)itemView.getTop(),dX,(float)itemView.getBottom());
                        c.drawRect(fondo, color);
                    }else{
                        color.setColor(Color.parseColor("#ed001a"));
                        RectF fondo = new RectF((float)itemView.getRight()+dX,(float)itemView.getTop(), itemView.getRight(),(float)itemView.getBottom());
                        c.drawRect(fondo, color);
                    }
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);

        itemTouchHelper.attachToRecyclerView(rccAlumnos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
