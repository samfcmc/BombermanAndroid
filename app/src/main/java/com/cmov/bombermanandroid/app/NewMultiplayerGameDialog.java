package com.cmov.bombermanandroid.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

/**
 *
 */
public class NewMultiplayerGameDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_new_multiplayer_game, null)).
                setPositiveButton(R.string.button_dialog_new_multiplayer_game_create,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO: What happens when the user create the game
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
