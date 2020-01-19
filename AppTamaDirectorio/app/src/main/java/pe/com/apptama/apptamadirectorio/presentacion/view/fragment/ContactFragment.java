package pe.com.apptama.apptamadirectorio.presentacion.view.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import pe.com.apptama.apptamadirectorio.R;
import pe.com.apptama.apptamadirectorio.presentacion.model.ContactModel;
import pe.com.apptama.apptamadirectorio.presentacion.util.adapter.ContactRecyclerViewAdapter;

public class ContactFragment extends Fragment implements View.OnClickListener {

    View mView;
    private RecyclerView myRecyclerView;
    private List<ContactModel> lstContactModels;
    FloatingActionButton fab_nuevo_contacto;
    private static final String PATH_CONTACT = "contact";

    public ContactFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_contact, container, false);
        myRecyclerView = mView.findViewById(R.id.rv_contact);


        ContactRecyclerViewAdapter adapter = new ContactRecyclerViewAdapter(getContext(), lstContactModels);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(adapter);
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstContactModels = new ArrayList<>();

        /*
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        lstContactModels.add(new ContactModel("Aaron Jone", "(111) 986765645", 0));
        */



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(PATH_CONTACT);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                ContactModel contactModel = dataSnapshot.getValue(ContactModel.class);
                contactModel.setId(dataSnapshot.getKey());

                if (!lstContactModels.contains(contactModel)) {
                    lstContactModels.add(contactModel);
                }
                myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                myRecyclerView.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                ContactModel contactModel = dataSnapshot.getValue(ContactModel.class);
                contactModel.setId(dataSnapshot.getKey());

                for (ContactModel contactModel1 : lstContactModels) {
                    if (contactModel1.getId().equals(contactModel.getId())) {
                        contactModel1.setPhone(contactModel.getPhone());
                        contactModel1.setName(contactModel.getName());
                    }
                }

                myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                myRecyclerView.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                ContactModel contactModel = dataSnapshot.getValue(ContactModel.class);
                contactModel.setId(dataSnapshot.getKey());

                if (lstContactModels.contains(contactModel)) {
                    lstContactModels.remove(contactModel);
                }

                myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                myRecyclerView.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    @Override
    public void onClick(View v) {

        /*
        switch (v.getId()) {
            case R.id.fab_nuevo_contacto:

                Intent intent = new Intent(getContext(), EditContactoActivity.class);
                startActivity(intent);
                break;
        }
        */
    }
}
