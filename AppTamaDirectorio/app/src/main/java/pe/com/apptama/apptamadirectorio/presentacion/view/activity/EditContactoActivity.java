package pe.com.apptama.apptamadirectorio.presentacion.view.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pe.com.apptama.apptamadirectorio.R;
import pe.com.apptama.apptamadirectorio.presentacion.model.ContactModel;

public class EditContactoActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText tie_nombre, tie_apellido, tie_telefono, tie_anexo, tie_email, tie_empresa, tie_cargo_empresa;
    Button btn_listo;
    private static final String PATH_CONTACT = "contact";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contacto);


        tie_nombre = findViewById(R.id.tie_nombre);
        tie_apellido = findViewById(R.id.tie_apellido);
        tie_telefono = findViewById(R.id.tie_telefono);
        tie_anexo = findViewById(R.id.tie_anexo);
        tie_email = findViewById(R.id.tie_email);
        tie_empresa = findViewById(R.id.tie_empresa);
        tie_cargo_empresa = findViewById(R.id.tie_cargo_empresa);

        btn_listo = findViewById(R.id.btn_listo);
        btn_listo.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {


        btn_listo.setEnabled(false);

        ContactModel contactModel = new ContactModel(tie_nombre.getText().toString().trim(),
                tie_telefono.getText().toString().trim(), 0);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(PATH_CONTACT);

        reference.push().setValue(contactModel);

        /*
        tie_nombre.setText("");
        tie_apellido.setText("");
        tie_telefono.setText("");
        tie_anexo.setText("");
        tie_email.setText("");
        tie_empresa.setText("");
        tie_cargo_empresa.setText("");
        */

        finish();
    }
}
