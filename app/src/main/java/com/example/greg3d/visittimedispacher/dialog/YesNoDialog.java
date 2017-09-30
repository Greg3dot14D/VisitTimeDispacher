package com.example.greg3d.visittimedispacher.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.greg3d.visittimedispacher.command.Command;

/**
 * Created by greg3d on 23.04.17.
 */
public class YesNoDialog {
        private AlertDialog.Builder ad;
        private Activity activity;
        private boolean closeActivity = false;
        public YesNoDialog(final Activity activity, final Command command, String message){
            this.activity = activity;
            ad = new AlertDialog.Builder(activity);
            ad.setTitle("Ахтунг");  // заголовок
            //ad.setMessage("Изменить выбранную запись ?"); // сообщение
            ad.setMessage(message); // сообщение
            ad.setIcon(android.R.drawable.ic_dialog_alert);

            ad.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    command.execute();
                    if(closeActivity)
                        activity.finish();
                }
            });

            ad.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                }
            });
        }

    public void show(){ad.show();}

    public void showAndCloseActivity(){
        closeActivity = true;
        ad.show();
    }
}
