package com.example.greg3d.visittimedispacher.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by greg3d on 22.09.17.
 */
public class MessageDialog {

    public MessageDialog(final Activity activity){
        AlertDialog.Builder ad = new AlertDialog.Builder(activity);
        ad.setTitle("Эбаот");  // заголовок
        //ad.setMessage("Изменить выбранную запись ?"); // сообщение
        ad.setMessage("Visit time dispatcher\nfor SBT lusers\n\nwriten by Greg3D 07.02.2017"); // сообщение
        ad.setIcon(android.R.drawable.ic_dialog_alert);
        ad.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {

            }
        });

        ad.show();
    }
}
