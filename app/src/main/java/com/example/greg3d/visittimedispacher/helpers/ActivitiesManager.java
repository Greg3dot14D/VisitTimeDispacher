package com.example.greg3d.visittimedispacher.helpers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.greg3d.visittimedispacher.activities.editrecordactivity.EditRecordActivity;
import com.example.greg3d.visittimedispacher.activities.filelistactivity.FileListActivity;
import com.example.greg3d.visittimedispacher.activities.someactivity.SomeActivity;
import com.example.greg3d.visittimedispacher.activities.visittimefixeractivity.VisitTimeFixerActivity;

/**
 * Created by greg3d on 26.04.17.
 */
public class ActivitiesManager {

    public static void startSomeActivity(Fragment fragment){
        Intent intent = new Intent(VisitTimeFixerActivity.instance , SomeActivity.class);
        fragment.startActivity(intent);
    }

    public static void startFileListActivity(Fragment fragment){
        Intent intent = new Intent(VisitTimeFixerActivity.instance , FileListActivity.class);
        fragment.startActivity(intent);
    }
    public static void startEditRecordActivity(Activity activity, long id){
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        Intent intent = new Intent(activity, EditRecordActivity.class);
        //intent.replaceExtras(bundle);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

}
