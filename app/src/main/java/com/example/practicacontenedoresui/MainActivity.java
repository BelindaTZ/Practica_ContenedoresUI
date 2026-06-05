package com.example.practicacontenedoresui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    static final String SUPABASE_URL = "https://hqqbmmpllbumovrrsujl.supabase.co";
    static final String SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhxcWJtbXBsbGJ1bW92cnJzdWpsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODA2ODU0NDgsImV4cCI6MjA5NjI2MTQ0OH0.nml-6Uk3HZthBFPpE5wibekiiQH8r3g0rqdQSaYv6RA";
    static final String BASE_FOTO_URL = "https://sga.uteq.edu.ec";

    private static final String URL_ALUMNOS  = SUPABASE_URL + "/rest/v1/Alumnos?select=*&order=nombres";
    private static final String URL_MATERIAS = SUPABASE_URL + "/rest/v1/Materias?select=*&order=nivel";

    private final String[] SEMESTRES = {
            "Primero", "Segundo", "Tercero", "Cuarto", "Quinto",
            "Sexto", "Séptimo", "Octavo", "Noveno", "Décimo"
    };

    private final List<Alumno>   todosLosAlumnos  = new ArrayList<>();
    private final List<Materia>  todasLasMaterias = new ArrayList<>();
    private final List<String>   nombresMaterias  = new ArrayList<>();

    private AlumnoAdapter    alumnoAdapter;
    private ArrayAdapter<String> materiaAdapter;
    private List<Alumno>     alumnosMostrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets sb = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(sb.left, sb.top, sb.right, sb.bottom);
            return insets;
        });

        ListView listView       = findViewById(R.id.listView);
        Spinner  spinnerSem     = findViewById(R.id.spinnerSemestre);
        Spinner  spinnerMat     = findViewById(R.id.spinnerMaterias);

        alumnoAdapter = new AlumnoAdapter(this, alumnosMostrados);
        listView.setAdapter(alumnoAdapter);

        ArrayAdapter<String> semAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, SEMESTRES);
        semAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSem.setAdapter(semAdapter);

        materiaAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, nombresMaterias);
        materiaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMat.setAdapter(materiaAdapter);

        spinnerSem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                filtrarMaterias(pos + 1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        cargarAlumnos(queue);
        cargarMaterias(queue);
    }

    private void filtrarMaterias(int nivel) {
        nombresMaterias.clear();
        for (Materia m : todasLasMaterias) {
            if (m.getNivel() == nivel) {
                nombresMaterias.add(m.getNombre());
            }
        }
        materiaAdapter.notifyDataSetChanged();
    }

    private void cargarAlumnos(RequestQueue queue) {
        JsonArrayRequest req = new JsonArrayRequest(
                Request.Method.GET, URL_ALUMNOS, null,
                response -> {
                    try {
                        todosLosAlumnos.clear();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject o = response.getJSONObject(i);
                            todosLosAlumnos.add(new Alumno(
                                    o.optString("foto", ""),
                                    o.optString("nombres", "Sin nombre"),
                                    o.optString("correo", ""),
                                    o.optString("paralelo", ""),
                                    o.optString("telefono", "")
                            ));
                        }
                        Collections.sort(todosLosAlumnos,
                                (a, b) -> a.getNombres().compareTo(b.getNombres()));
                        alumnosMostrados.clear();
                        alumnosMostrados.addAll(todosLosAlumnos);
                        alumnoAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> android.util.Log.e("SUPABASE", "Error alumnos: " + error.getMessage())
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> h = new HashMap<>();
                h.put("apikey", SUPABASE_KEY);
                h.put("Authorization", "Bearer " + SUPABASE_KEY);
                return h;
            }
        };
        queue.add(req);
    }

    private void cargarMaterias(RequestQueue queue) {
        JsonArrayRequest req = new JsonArrayRequest(
                Request.Method.GET, URL_MATERIAS, null,
                response -> {
                    try {
                        todasLasMaterias.clear();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject o = response.getJSONObject(i);
                            todasLasMaterias.add(new Materia(
                                    o.optInt("id"),
                                    o.optString("nombre", ""),
                                    o.optInt("nivel")
                            ));
                        }
                        Spinner spinnerSem = findViewById(R.id.spinnerSemestre);
                        filtrarMaterias(spinnerSem.getSelectedItemPosition() + 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> android.util.Log.e("SUPABASE", "Error materias: " + error.getMessage())
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> h = new HashMap<>();
                h.put("apikey", SUPABASE_KEY);
                h.put("Authorization", "Bearer " + SUPABASE_KEY);
                return h;
            }
        };
        queue.add(req);
    }
}
