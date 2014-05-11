package com.cmov.bombermanandroid.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 *
 */
public class NewMultiplayerGameDialog extends DialogFragment {

     @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_new_multiplayer_game, null);
        final EditText gameName = (EditText) dialogView.findViewById(R.id.editText_dialog_new_multiplayer_game_name);
        final EditText maxPlayers = (EditText) dialogView.findViewById(R.id.editText_dialog_new_multiplayer_game_max);

        builder.setView(dialogView).
                setPositiveButton(R.string.button_dialog_new_multiplayer_game_create,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                              // The inputs for the owner are returned to the MultiplayerActivity
                              ((MultiplayerActivity) getActivity()).doCreateMultiplayerClick(gameName.getText().toString(),
                                      maxPlayers.getText().toString());
                            }
                        }
                ).
                setNegativeButton(R.string.button_dialog_new_multiplayer_game_cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NewMultiplayerGameDialog.this.getDialog().cancel();
                            }
                        }
                );

        return builder.create();
    }
}
