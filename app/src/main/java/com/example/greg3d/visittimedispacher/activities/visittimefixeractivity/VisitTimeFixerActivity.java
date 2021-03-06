package com.example.greg3d.visittimedispacher.activities.visittimefixeractivity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.greg3d.visittimedispacher.activities.drawers.NavigationDrawerFragment;
import com.example.greg3d.visittimedispacher.R;
import com.example.greg3d.visittimedispacher.activities.visittimefixeractivity.controls.AverageTimeNumbers;
import com.example.greg3d.visittimedispacher.activities.visittimefixeractivity.controls.Controls;
import com.example.greg3d.visittimedispacher.activities.visittimefixeractivity.controls.DateNumbers;
import com.example.greg3d.visittimedispacher.activities.visittimefixeractivity.controls.DateTextViewNumbers;
import com.example.greg3d.visittimedispacher.activities.visittimefixeractivity.controls.Labels;
import com.example.greg3d.visittimedispacher.activities.visittimefixeractivity.controls.MissingTimeNumbers;
import com.example.greg3d.visittimedispacher.activities.visittimefixeractivity.controls.TimeToExitNumbers;
import com.example.greg3d.visittimedispacher.command.DoEntranceCommand;
import com.example.greg3d.visittimedispacher.command.DoExitCommand;
import com.example.greg3d.visittimedispacher.command.SetDateFilterCommand;
import com.example.greg3d.visittimedispacher.controller.DBController;
import com.example.greg3d.visittimedispacher.activities.visittimefixeractivity.controls.ImageRow;
import com.example.greg3d.visittimedispacher.css.CssManager;
import com.example.greg3d.visittimedispacher.dialog.DatePickerDialogImpl;
import com.example.greg3d.visittimedispacher.dialog.YesNoDialog;
import com.example.greg3d.visittimedispacher.framework.factory.ActivityFactory;
import com.example.greg3d.visittimedispacher.framework.helpers.ViewHelper;
import com.example.greg3d.visittimedispacher.helpers.DBHelper;
import com.example.greg3d.visittimedispacher.helpers.NumbersImageBitmapHelper;
import com.example.greg3d.visittimedispacher.helpers.NumbersTextViewHelper;
import com.example.greg3d.visittimedispacher.helpers.Tools;
import com.example.greg3d.visittimedispacher.timer.TimerManager;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class VisitTimeFixerActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, View.OnClickListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private long missingTime = 0;
    private long lastEntranceTime = 0;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    public static VisitTimeFixerActivity instance;

    private Date date;

    private Controls controls;

    private Labels labels;

    private AverageTimeNumbers averageTimeNumbers;

    private MissingTimeNumbers missingTimeNumbers;

    private TimeToExitNumbers timeToExitNumbers;

    private DateNumbers dateNumbers;

    private DateTextViewNumbers dateTextViewNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_time_fixer);

        this.instance = this;

        controls = new Controls();
        ActivityFactory.InitActivity(this, controls);
        ActivityFactory.setListener(this, controls);

        labels = new Labels();
        ActivityFactory.InitActivity(this, labels);
        ActivityFactory.InitFonts(this, labels, CssManager.getLabelCss());

        averageTimeNumbers = new AverageTimeNumbers();
        ActivityFactory.InitActivity(this, averageTimeNumbers);

        missingTimeNumbers = new MissingTimeNumbers();
        ActivityFactory.InitActivity(this, missingTimeNumbers);


        timeToExitNumbers = new TimeToExitNumbers();
        ActivityFactory.InitActivity(this, timeToExitNumbers);

        dateTextViewNumbers = new DateTextViewNumbers();
        ActivityFactory.InitActivity(this, dateTextViewNumbers);

        dateNumbers = new DateNumbers();
        ActivityFactory.InitActivity(this, dateNumbers);

                mNavigationDrawerFragment = (NavigationDrawerFragment)
                        getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        new DBHelper(this);

        createNumbersBitmap();

        setDate(Calendar.getInstance().getTime());
        refresh();
        unScaleControls();

//        //dateImageRow.fillRow("123456789123456789123456789");
//        //dateImageRow.fillRow("абвгдежзиклмнопрстуфхц");
//        //dateImageRow.fillRow("  среднее   оставшееся  ");
//        setTimerBitmap();
//        //dateImageRow.fillRow("  среднее  недоработка  ");
//        //dateImageRow.fillRow("  дата  01 01 2017      ");
    }

    public static void setTimeToExit(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendar.getTimeInMillis() + DBController.getMissingTimeToLong());
        //instance.controls.timeToOutTextView.setText(Tools.getTimeToString(calendar.getTime()));
    }

    public void setTimeToExit(long milliseconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        //instance.controls.timeToOutTextView.setText(Tools.getTimeToString(calendar.getTime()));
        instance.setTimeToExit(Tools.getTimeToString(calendar.getTime()));
        //instance.setTimeToExit("56:78:90");
    }

//    private void setTimerBitmap(){
////        int width = 25 * 2;
////        int heigh = 12 * 2;
////
////        int xOffset = 72 * 2;
////        int yOffset = 58 * 2;
//
//        int width = 18 * 2;
//        int heigh = 11 * 2;
//
//        int xOffset = 73 * 2;
//        int yOffset = 43 * 2;
//        Bitmap bitmapSource = BitmapFactory.decodeResource(getResources(), R.drawable.pledit);
//        //Bitmap bitmap = Bitmap.createBitmap(bitmapSource, xOffset, yOffset, width, heigh);
//
//        findViewById(R.id.container).setBackground(new BitmapDrawable(
//                Bitmap.createBitmap(bitmapSource, 128 * 2, 83 * 2, 100 * 2, 10 * 2)
//        ));
//
//        findViewById(R.id.iv_t1).setBackground(new BitmapDrawable(
//                Bitmap.createBitmap(bitmapSource, 73 * 2, 43 * 2, 18 * 2, 24)
//        ));
//        findViewById(R.id.iv_t2).setBackground(new BitmapDrawable(
//                Bitmap.createBitmap(bitmapSource, 73 * 2, 58 * 2, 18 * 2, 24)
//        ));
//        findViewById(R.id.iv_t3).setBackground(new BitmapDrawable(
//                Bitmap.createBitmap(bitmapSource, 73 * 2, 58 * 2, 18 * 2, 24)
//        ));
//        findViewById(R.id.iv_t4).setBackground(new BitmapDrawable(
//                Bitmap.createBitmap(bitmapSource, 100 * 2, 43 * 2, 48 * 2, 24)
//        ));
//
//        bitmapSource = BitmapFactory.decodeResource(getResources(), R.drawable.eqmain);
//
////        findViewById(R.id.tr_timers).setBackground(new BitmapDrawable(
////                Bitmap.createBitmap(bitmapSource, 0 * 2, bitmapSource.getHeight() - (22) * 2, 112 * 2, 22 * 2)
////        ));
//
//        findViewById(R.id.tr_timers).setBackground(new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.display_eq_bkg)));
//
//        bitmapSource = BitmapFactory.decodeResource(getResources(), R.drawable.avs);
//        findViewById(R.id.lr_bottom_1).setBackground(new BitmapDrawable(
//                Bitmap.createBitmap(bitmapSource, 16 * 2, (17) * 2, 48 * 2, 4 * 2)
//        ));
//
//        findViewById(R.id.lr_bottom_2).setBackground(new BitmapDrawable(
//                Bitmap.createBitmap(bitmapSource, 67 * 2, (17) * 2, 12 * 2, 4 * 2)
//        ));
//        findViewById(R.id.lr_bottom_3).setBackground(new BitmapDrawable(
//                Bitmap.createBitmap(bitmapSource, bitmapSource.getWidth() - 15 * 2, (17) * 2, 15 * 2, 4 * 2)
//        ));
//
//        findViewById(R.id.lr_left).setBackground(new BitmapDrawable(
//                Bitmap.createBitmap(bitmapSource, 0 * 2, (16) * 2, 7 * 2, bitmapSource.getHeight() - 18 * 2)
//        ));
//        findViewById(R.id.lr_right).setBackground(new BitmapDrawable(
//                Bitmap.createBitmap(bitmapSource, 9 * 2, (16) * 2, 5 * 2, bitmapSource.getHeight() - 18 * 2)
//        ));
//    }

    private NumbersImageBitmapHelper numbersBitMapHelper;

    private void createNumbersBitmap(){
        numbersBitMapHelper = new NumbersImageBitmapHelper(this, R.drawable.numfont);

        for(int i = 0; i < 10; i ++)
            numbersBitMapHelper.putBitmap(String.valueOf(i), i);

        numbersBitMapHelper.putBitmap(":", 12);
        numbersBitMapHelper.putBitmap(" ", 13);
        numbersBitMapHelper.putBitmap("-", 15);
    }


    public void setAverageTime(String time){

        numbersBitMapHelper.setTextToImaging(time);

        numbersBitMapHelper
                .setImageByCharIndex(averageTimeNumbers.ah1, 0)
                .setImageByCharIndex(averageTimeNumbers.ah2, 1)
                .setImageByChar(averageTimeNumbers.ahm_spliter, ":")
                .setImageByCharIndex(averageTimeNumbers.am1, 2)
                .setImageByCharIndex(averageTimeNumbers.am2, 3)
                .setImageByChar(averageTimeNumbers.ams_spliter, ":")
                .setImageByCharIndex(averageTimeNumbers.as1, 4)
                .setImageByCharIndex(averageTimeNumbers.as2, 5);
    }

    public void setMissingTime(String time){
        numbersBitMapHelper.setTextToImaging(time);
        numbersBitMapHelper
                .setImageByCharIndex(missingTimeNumbers.ah1, 0)
                .setImageByCharIndex(missingTimeNumbers.ah2, 1)
                .setImageByChar(missingTimeNumbers.ahm_spliter, ":")
                .setImageByCharIndex(missingTimeNumbers.am1, 2)
                .setImageByCharIndex(missingTimeNumbers.am2, 3)
                .setImageByChar(missingTimeNumbers.ams_spliter, ":")
                .setImageByCharIndex(missingTimeNumbers.as1, 4)
                .setImageByCharIndex(missingTimeNumbers.as2, 5);
    }

    public void setDateNumbers(String date){
        setDateNumbersToImageView(date);
        //setDateNumbersToTextView(date);
    }

    private void setDateNumbersToImageView(String date){
        numbersBitMapHelper.setTextToImaging(date);
        numbersBitMapHelper
                .setImageByCharIndex(dateNumbers.dd1, 0)
                .setImageByCharIndex(dateNumbers.dd2, 1)
                .setImageByChar(dateNumbers.dm_spliter, "-")
                .setImageByCharIndex(dateNumbers.mm1, 3)
                .setImageByCharIndex(dateNumbers.mm2, 4)
                .setImageByChar(dateNumbers.my_spliter, "-")
                .setImageByCharIndex(dateNumbers.yy1, 6)
                .setImageByCharIndex(dateNumbers.yy2, 7)
                .setImageByCharIndex(dateNumbers.yy3, 8)
                .setImageByCharIndex(dateNumbers.yy4, 9);
    }

    private void setDateNumbersToTextView(String date){
        NumbersTextViewHelper helper = new NumbersTextViewHelper();

        helper.setTextToView(date);
        helper
                .setImageByCharIndex(dateTextViewNumbers.dd1, 0)
                .setImageByCharIndex(dateTextViewNumbers.dd2, 1)
                .setImageByChar(dateTextViewNumbers.dm_spliter, "-")
                .setImageByCharIndex(dateTextViewNumbers.mm1, 3)
                .setImageByCharIndex(dateTextViewNumbers.mm2, 4)
                .setImageByChar(dateTextViewNumbers.my_spliter, "-")
                .setImageByCharIndex(dateTextViewNumbers.yy1, 6)
                .setImageByCharIndex(dateTextViewNumbers.yy2, 7)
                .setImageByCharIndex(dateTextViewNumbers.yy3, 8)
                .setImageByCharIndex(dateTextViewNumbers.yy4, 9)
        ;
    }

    public void setTimeToExit(String time){
        numbersBitMapHelper.setTextToImaging(time);
        numbersBitMapHelper
                .setImageByCharIndex(timeToExitNumbers.ah1, 0)
                .setImageByCharIndex(timeToExitNumbers.ah2, 1)
                .setImageByChar(timeToExitNumbers.ahm_spliter, ":")
                .setImageByCharIndex(timeToExitNumbers.am1, 3)
                .setImageByCharIndex(timeToExitNumbers.am2, 4)
                .setImageByChar(timeToExitNumbers.ams_spliter, ":")
                .setImageByCharIndex(timeToExitNumbers.as1, 6)
                .setImageByCharIndex(timeToExitNumbers.as2, 7);
}

    public void setMissingTimeByTimer(){
        String time = Tools.msToSimpleString(this.missingTime - ( Calendar.getInstance().getTimeInMillis() - lastEntranceTime));

        String missingTimeHeader = "ОСТАВШЕЕСЯ";
        if(time.contains("-")) {
            missingTimeHeader = "ПЕРЕРАБОТКА";
            time = time.replace("-","").replace(" ", "");
        }
        setMissingTime(time);
        ((TextView)instance.findViewById(R.id.editText_missingTimeLabel)).setText(missingTimeHeader);
    }

    private boolean getIsInState(){
        try {
            return DBController.dateInEqualsDateOut();
        }catch(Exception e){
            return false;
        }
    }

    private void doEntrance(){
        this.setX(controls.entrance_Button);
        this.setArrow(controls.exit_Button);
        this.unScaleControls();
    }

    private void doExit(){
        this.setX(controls.exit_Button);
        this.setArrow(controls.entrance_Button);
        this.unScaleControls();
    }


    private void rotateControls(){
        Animation anomate = AnimationUtils.loadAnimation(this, R.anim.rotateright);
        controls.entrance_Button.startAnimation(anomate);
        controls.exit_Button.startAnimation(anomate);
    }

    public void rotateAnimation(ImageButton button){
        button.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotateright));
    }

    private void scaleControls(){
        Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale);
        controls.entrance_Button.startAnimation(scale);
        controls.exit_Button.startAnimation(scale);
    }

    public void unScaleControls(){

        Animation unscale = AnimationUtils.loadAnimation(this, R.anim.unscale);
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(rotate);
        set.addAnimation(unscale);
        controls.entrance_Button.startAnimation(set);
        controls.exit_Button.startAnimation(set);
    }

    public void scaleAnimation(ImageButton button){
        Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale);
        button.startAnimation(scale);
    }

    public void setX(ImageButton button){
        button.setBackgroundResource(R.drawable.delete_button);
    }

    public void setArrow(ImageButton button){
        button.setBackgroundResource(R.drawable.arrow_button);
    }

    public void unScaleAnimation(){
        Animation unScale = AnimationUtils.loadAnimation(this, R.anim.unscale);
        controls.entrance_Button.startAnimation(unScale);
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
        //controls.filterDate_TextView.setText("Дата : " + Tools.getDateToString(date));
        controls.filterDate_TextView.setText("");
        this.setDateNumbers(Tools.getDateToString(date));
    }

    public static void refresh(){
        String missingTimeHeader = "ОСТАВШЕЕСЯ";
        String missingTime = DBController.getMissingTimeToSimpleString();
        if(missingTime.contains("-")) {
            missingTimeHeader = "ПЕРЕРАБОТКА";
            missingTime = missingTime.replace("-","").replace(" ", "");
        }

        instance.labels.missingTime.setText(missingTimeHeader);
        instance.setAverageTime(DBController.getAverageTimeToSimpleString());
        instance.setMissingTime(missingTime);

        if(instance.getIsInState()) {
            instance.missingTime = DBController.getMissingTimeToLong();
            instance.lastEntranceTime = DBController.getLastDateInMS();
            instance.setTimeToExit(instance.lastEntranceTime + instance.missingTime);
            TimerManager.startTimer();
            instance.doEntrance();
        }
        else {
            TimerManager.stopTimer();
            instance.doExit();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            // TODO - подправить работу с меню
            //case 1:
            //    mTitle = getString(R.string.title_section1);
            //    break;
//            case 2:
//                mTitle = getString(R.string.title_section2);
//                break;
//            case 3:
//                mTitle = getString(R.string.title_section3);
//                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.visit_time_fixer, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onClick(View view) {
//        ViewHelper v = new ViewHelper(view);
//
//        if(v.idEquals(controls.entrance_Button)) {
//            scaleAnimation(controls.entrance_Button);
//            rotateAnimation(controls.entrance_Button);
//            new YesNoDialog(this, new DoEntranceCommand(), "Добавить запись ?").show();
//        }
//        else if(v.idEquals(controls.exit_Button)) {
//            //scaleAnimation(controls.exit_Button);
//            rotateAnimation(controls.exit_Button);
//            new YesNoDialog(this, new DoExitCommand(), "Изменить выбранную запись ?").show();
//        }
//        else if(v.idEquals(controls.filterDate_TextView)) {
//                new DatePickerDialogImpl(this, date, new SetDateFilterCommand()).show();
//        }
//    }

    @Override
    public void onClick(View view) {
        ViewHelper v = new ViewHelper(view);

        if(v.idEquals(controls.entrance_Button)) {
            scaleAnimation(controls.entrance_Button);
            rotateAnimation(controls.entrance_Button);
            if(!getIsInState())
                new YesNoDialog(this, new DoEntranceCommand(), "Добавить запись ?").show();
            else
                new YesNoDialog(this, new DoExitCommand(), "Изменить выбранную запись ?").show();
        }
        else if(v.idEquals(controls.filterDate_TextView)) {
            new DatePickerDialogImpl(this, date, new SetDateFilterCommand()).show();
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_visit_time_fixer, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((VisitTimeFixerActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
