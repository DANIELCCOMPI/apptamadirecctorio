package pe.com.apptama.apptamadirectorio.presentacion.view.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pe.com.apptama.apptamadirectorio.R;
import pe.com.apptama.apptamadirectorio.presentacion.util.adapter.ViewPagerAdapter;
import pe.com.apptama.apptamadirectorio.presentacion.view.fragment.CallFragment;
import pe.com.apptama.apptamadirectorio.presentacion.view.fragment.ContactFragment;
import pe.com.apptama.apptamadirectorio.presentacion.view.fragment.FavFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String PATH_START = "start";
    private static final String PATH_MESSAGE = "message";

    private static final String PATH_PROFILE = "profile";
    private static final String PATH_CODE = "code";

    private Button btn_send;
    private EditText et_message;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tbl_contacto);
        viewPager = findViewById(R.id.vpr_contacto);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Add fragment here
        adapter.AddFragment(new ContactFragment(), "");
        adapter.AddFragment(new CallFragment(), "");
        adapter.AddFragment(new FavFragment(), "");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_group);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_call);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_star);


        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("Hola")
                .setContentText("Bienvenido")
                .setAutoCancel(true);



        /*
        final TextView tv_message = findViewById(R.id.tv_message);


        et_message = findViewById(R.id.et_message);
        btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
        */

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference(PATH_START).child(PATH_MESSAGE);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //tv_message.setText(dataSnapshot.getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,
                        "Error al consultar firebase",
                        Toast.LENGTH_LONG).show();
            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, EditContactoActivity.class);
                startActivity(intent);

                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                */

            }
        });
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
        } else if (id == R.id.action_acerca) {
            final TextView tv_code = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            );

            tv_code.setLayoutParams(params);
            tv_code.setGravity(Gravity.CENTER_HORIZONTAL);
            tv_code.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference(PATH_PROFILE).child(PATH_CODE);

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    tv_code.setText(dataSnapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, "No se puede cargar el código.", Toast.LENGTH_LONG).show();
                }
            });

            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle(R.string.main_dialog_title)
                    .setPositiveButton(R.string.main_dialog_ok, null);

            builder.setView(tv_code);
            builder.show();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_send:
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference reference = database.getReference(PATH_START).child(PATH_MESSAGE);

                reference.setValue(et_message.getText().toString().trim());
                et_message.setText("");

                break;
        }
    }
}
