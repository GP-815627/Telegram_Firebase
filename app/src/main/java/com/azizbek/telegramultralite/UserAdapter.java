package com.azizbek.telegramultralite;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    Context context;
    ArrayList<User> alluserarraylist;
    MainActivity mainActivity;

    public UserAdapter(Context context, ArrayList<User> alluserarraylist) {
        this.context = context;
        this.alluserarraylist = alluserarraylist;
        mainActivity = (MainActivity) context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.telegram_layout, parent, false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        if (mainActivity.USERNAME.equals(alluserarraylist.get(position).getUsername())) {

            alluserarraylist.remove(position);

        } else {

            holder.textViewusername.setText(alluserarraylist.get(position).getUsername());
            Glide.with(context).load(alluserarraylist.get(position).getProfilimage()).placeholder(R.drawable.telegram).into(holder.circleImageViewprofil);

        }

        holder.textViewusername.setText(alluserarraylist.get(position).getUsername());
        Glide.with(context).load(alluserarraylist.get(position).getProfilimage()).placeholder(R.drawable.telegram).into(holder.circleImageViewprofil);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivityChat.class);
                intent.putExtra("myusername", mainActivity.USERNAME);
                intent.putExtra("username", alluserarraylist.get(position).getUsername());
                intent.putExtra("surname", alluserarraylist.get(position).getSurname());
                intent.putExtra("userimage", alluserarraylist.get(position).getProfilimage());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return alluserarraylist.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        TextView textViewusername, textViewmessage, textViewhour;
        CircleImageView circleImageViewprofil;
        RelativeLayout relativeLayout;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.relativelay1);
            circleImageViewprofil = itemView.findViewById(R.id.circleprofilimage);
            textViewusername = itemView.findViewById(R.id.textviewusername);
            textViewmessage = itemView.findViewById(R.id.texviewmessage);
            textViewhour = itemView.findViewById(R.id.textviewhour);


        }
    }

}
