package com.example.examenibai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    //Declarar variables necesarias
    private static final String TAG = "aaa";
    private LinearLayout linearLayoutPrincipal;
    static FirebaseFirestore db;
    private Button btnRefresh;
    private ArrayList<Biblioteca> Ar_libros= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Localizas el linear layout en el que quieres añadir los datos
        linearLayoutPrincipal = findViewById(R.id.linearPrincipal);

        //Encontrar el boton de refresh
        Button btnRefresh = findViewById(R.id.btnRefresh);

        //Crear la conexion con la base de datos
        db = FirebaseFirestore.getInstance();

        //Conectas con la base de datos para poder acceder a los datos
        db.collection("Biblioteca")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                                //Crear un constructor con el objeto tipo QueryDocumentSnapshot
                                Biblioteca libro = new Biblioteca(document);


                                //llamas a un metodo creado que pasa los datos a linearlayout y cargas el arraylist
                                anadirLibroUI(libro);
                                Ar_libros.add(libro);
                            }
                        } else {
                            Log.e("ERRORCARGADATOS", "No se han podido obtener los datos");
                        }



                    }
                });
        //Trigger del boton para refrescar
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ComprobarDatos();
            }
        });
    }

    private void ComprobarDatos() {

        //Conectas con la base de datos para poder acceder a los datos
        db.collection("Biblioteca")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                                //Crear un constructor con el objeto tipo QueryDocumentSnapshot
                                Biblioteca libro = new Biblioteca(document);
                                String exist= "";

                                //Recorre el arraylist
                                for (int i = 0; i < Ar_libros.size();i++) {


                                    Log.d("ISBNs", "BBDD: "+document.getString("ISBN")+" ArrayList: "+Ar_libros.get(i).getISBN());
                                    //Si  existe el libro deja la variable exist como Bai y  para de comparar con esta posicion del Arraylist
                                    if (libro.getISBN().equals(Ar_libros.get(i).getISBN())) {
                                        Log.d("Accion", "Libro añadido previamente");
                                        exist ="Bai";

                                        break;
                                    } else {

                                        //Si no existe el libro deja la variable exist como Ez
                                        Log.d("Accion", "Añadiendo Libro");
                                        exist ="Ez";
                                    }

                                }
                                //Si existe es "Ez" te añade el libro que ha traido de la BBDD en el userinterface y lo añade al arraylist
                                if (exist == "Ez"){
                                    Log.d("No existe", "Entro y añado"+ libro.getISBN());
                                    anadirLibroUI(libro);
                                    Ar_libros.add(libro);
                                }
                            }
                        } else {
                            Log.e("ERRORCARGADATOS", "No se han podido obtener los datos");
                        }
                    }
                });
    }

    private void anadirLibroUI(Biblioteca libro) {

        //Crear un linear layout cada vez que recive datos y la horientacion del mismo
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        //Crear textview para mostrarlos
        TextView tvISBN = new TextView(this);
        TextView tvTitulo = new TextView(this);
        TextView tvAutor = new TextView(this);
        TextView tvPublicacion = new TextView(this);

        //Cargar los datos en los textViews
        tvISBN.setText(libro.getISBN());
        tvTitulo.setText(libro.getTitulo());
        tvAutor.setText(libro.getAutor());
        tvPublicacion.setText(libro.getAnnoPublicacion());

        //Le añado padding a los text view para distanciarlos
        tvISBN.setPadding(25,0,50,15);
        tvTitulo.setPadding(50,0,50,15);
        tvAutor.setPadding(50,0,50,15);
        tvPublicacion.setPadding(50,0,50,15);

        //Añade los textView al linear layout
        linearLayout.addView(tvISBN);
        linearLayout.addView(tvTitulo);
        linearLayout.addView(tvAutor);
        linearLayout.addView(tvPublicacion);

        // Añades el linear layout que acabas de crear al layout principal
        linearLayoutPrincipal.addView(linearLayout);

    }

}