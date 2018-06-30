package net.tecgurus.app.tecgurusapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.tecgurus.app.tecgurusapp.R;
import net.tecgurus.app.tecgurusapp.activities.UpdateUserActivity;
import net.tecgurus.app.tecgurusapp.db.beans.UserBean;
import net.tecgurus.app.tecgurusapp.db.helpers.UserHelper;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    //region Variables
    private List<UserBean> mListUsers;
    private Context mContext;
    //endregion

    //region Constructor
    public UserAdapter(List<UserBean> users, Context context){
        this.mListUsers = users;
        this.mContext = context;
    }
    //endregion

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserBean userBean = mListUsers.get(position);
        holder.bind(userBean, position);
    }

    @Override
    public int getItemCount() {
        return mListUsers.size();
    }

    //region Classes
    class UserViewHolder extends RecyclerView.ViewHolder{
        //region Views
        private TextView mCompleteName;
        private TextView mPassword;
        private TextView mAddress;
        private ImageView mEditUser;
        private ImageView mDeleteUser;
        private LinearLayout mItem;
        //endregion

        UserViewHolder(View itemView) {
            super(itemView);
            mCompleteName = itemView.findViewById(R.id.item_user_name);
            mPassword = itemView.findViewById(R.id.item_user_password);
            mAddress = itemView.findViewById(R.id.item_user_address);
            mEditUser = itemView.findViewById(R.id.item_user_update);
            mDeleteUser = itemView.findViewById(R.id.item_user_delete);
            mItem = itemView.findViewById(R.id.item_user_item);
        }

        @SuppressLint("SetTextI18n")
        void bind(final UserBean userBean, final int position){
            mCompleteName.setText(userBean.getName() + " " + userBean.getLastname());
            mPassword.setText(userBean.getPassword());
            mAddress.setText(userBean.getAddress());

            mDeleteUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UserHelper.getInstance(mContext).deleteUserByUsername(userBean.getUsername());
                    mListUsers.remove(position);
                    notifyItemRemoved(position);
                }
            });

            mEditUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent openUpdateActivity = new Intent(mContext, UpdateUserActivity.class);
                    openUpdateActivity.putExtra(UpdateUserActivity.KEY_USERNAME, userBean.getUsername());
                    mContext.startActivity(openUpdateActivity);
                }
            });

            mItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent openUpdateActivity = new Intent(mContext, UpdateUserActivity.class);
                    openUpdateActivity.putExtra(UpdateUserActivity.KEY_USERNAME, userBean.getUsername());
                    mContext.startActivity(openUpdateActivity);
                }
            });
        }
    }
    //endregion
}
