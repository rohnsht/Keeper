package com.rohanshrestha.keeper;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rohanshrestha.keeper.adapter.PasswordAdapter;
import com.rohanshrestha.keeper.data.Credential;
import com.rohanshrestha.keeper.utils.FirebaseUtils;

import java.util.ArrayList;


public class CredentialFragment extends Fragment {

    private final String TAG = "CredentialFragment";

    private RecyclerView recyclerView;
    private PasswordAdapter adapter;
    private View emptyView;

    private ArrayList<String> credentialIds;
    private DatabaseReference mDatabase;
    private ChildEventListener credentialListener;

    //private OnFragmentInteractionListener mListener;

    public CredentialFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_credential, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(FirebaseUtils.getUid());
        mDatabase.keepSynced(true);

        recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), new LinearLayoutManager(getActivity()).getOrientation()));
        emptyView = layout.findViewById(R.id.emptyView);
        emptyView.setVisibility(View.GONE);

        setUpItemTouchHelper();
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewActivity.class);
                intent.putExtra(NewActivity.PARAM_MODE, NewActivity.MODE_ADD);
                startActivity(intent);
            }
        });
        ArrayList<Credential> credentials = new ArrayList<>();
        credentialIds = new ArrayList<>();
        adapter = new PasswordAdapter(getActivity(), credentials);
        recyclerView.setAdapter(adapter);

        credentialListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Credential credential = dataSnapshot.getValue(Credential.class);
                credentialIds.add(dataSnapshot.getKey());
                adapter.addItem(credential);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String credentialKey = dataSnapshot.getKey();
                Credential newCredential = dataSnapshot.getValue(Credential.class);
                int index = credentialIds.indexOf(credentialKey);
                if (index > -1) {
                    adapter.replace(index, newCredential);
                } else {
                    Log.d(TAG, "onChildChanged:unknown_child:" + credentialKey);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String credentialKey = dataSnapshot.getKey();

                // [START_EXCLUDE]
                int index = credentialIds.indexOf(credentialKey);
                if (index > -1) {
                    // Remove data from the list
                    credentialIds.remove(index);
                    adapter.removeItem(index);
                } else {
                    Log.d(TAG, "onChildRemoved:unknown_child:" + credentialKey);
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addChildEventListener(credentialListener);
    }

   /* @Override
    public void onStop() {
        super.onStop();
        if (credentialListener != null)
            mDatabase.removeEventListener(credentialListener);
    }*/

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    private void setUpItemTouchHelper() {
        ItemTouchHelper.Callback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int swipedPosition = viewHolder.getAdapterPosition();
                final String credentialkey = credentialIds.get(swipedPosition);
                if (direction == ItemTouchHelper.RIGHT) {
                    Credential credential = adapter.getItem(swipedPosition);
                    Intent intent = new Intent(getActivity(), NewActivity.class);
                    intent.putExtra(NewActivity.PARAM_MODE, NewActivity.MODE_EDIT);
                    intent.putExtra(NewActivity.PARAM_CREDENTIAL, credential);
                    intent.putExtra(NewActivity.PARAM_KEY, credentialkey);
                    startActivity(intent);
                    adapter.notifyItemChanged(swipedPosition);

                } else if (direction == ItemTouchHelper.LEFT) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Delete credential?");
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.dialog_delete, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mDatabase.child(credentialkey).removeValue();
                        }
                    });
                    builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            adapter.notifyItemChanged(swipedPosition);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    if (dX > 0) { // right swipe
                        ((PasswordAdapter.MyViewHolder) viewHolder).setRightSwipeView();
                    } else if (dX < 0) {
                        ((PasswordAdapter.MyViewHolder) viewHolder).setLeftSwipeView();
                    }
                    getDefaultUIUtil().onDraw(c, recyclerView, itemView.findViewById(R.id.swipeable), dX, dY, actionState, isCurrentlyActive);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                getDefaultUIUtil().clearView(viewHolder.itemView.findViewById(R.id.swipeable));
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    public interface OnFragmentInteractionListener {
        void onEdit(Credential credential, String key);
    }

}
