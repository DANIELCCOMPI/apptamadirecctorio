package pe.com.apptama.apptamadirectorio.presentacion.util.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pe.com.apptama.apptamadirectorio.R;
import pe.com.apptama.apptamadirectorio.presentacion.model.ContactModel;

public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ContactViewHolder> {

    private static final String PATH_CONTACT = "contact";
    Context mContext;
    List<ContactModel> mContactModels;
    Dialog myDialog;

    public ContactRecyclerViewAdapter(Context mContext, List<ContactModel> mContactModels) {
        this.mContext = mContext;
        this.mContactModels = mContactModels;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View mView;
        mView = LayoutInflater.from(mContext).inflate(R.layout.item_contact, viewGroup, false);
        ContactViewHolder viewHolder = new ContactViewHolder(mView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder contactViewHolder, final int i) {

        final ContactModel contactModel = mContactModels.get(i);

        contactViewHolder.tv_name.setText(mContactModels.get(i).getName());
        contactViewHolder.tv_phone.setText(mContactModels.get(i).getPhone());
        contactViewHolder.iv_photo.setImageResource(mContactModels.get(i).getPhoto() == 0 ? R.drawable.ic_person : mContactModels.get(i).getPhoto());

        contactViewHolder.iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Dialog ini
                myDialog = new Dialog(mContext);
                myDialog.setContentView(R.layout.dialog_contact);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView tv_dialgo_name = myDialog.findViewById(R.id.tv_dialog_name);
                TextView tv_dialog_phone = myDialog.findViewById(R.id.tv_dialog_phone);
                CircleImageView civ_dialog_photo = myDialog.findViewById(R.id.civ_dialog_photo);
                Button btn_remove = myDialog.findViewById(R.id.btn_remove);

                tv_dialgo_name.setText(contactModel.getName());
                tv_dialog_phone.setText(contactModel.getPhone());
                civ_dialog_photo.setImageResource(contactModel.getPhoto() == 0 ? R.drawable.ic_person : contactModel.getPhoto());

                btn_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference(PATH_CONTACT);
                        reference.child(contactModel.getId()).removeValue();
                        myDialog.dismiss();
                    }
                });

                myDialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mContactModels.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name, tv_phone;
        private CircleImageView iv_photo;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_nombre_contact);
            tv_phone = itemView.findViewById(R.id.tv_phone_contact);
            iv_photo = itemView.findViewById(R.id.civ_contact);
        }
    }

}
