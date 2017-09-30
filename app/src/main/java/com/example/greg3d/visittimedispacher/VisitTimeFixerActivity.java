package com.example.greg3d.visittimedispacher;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.greg3d.visittimedispacher.command.DoEntranceCommand;
import com.example.greg3d.visittimedispacher.command.DoExitCommand;
import com.example.greg3d.visittimedispacher.command.SetDateFilterCommand;
import com.example.greg3d.visittimedispacher.controller.DBController;
import com.example.greg3d.visittimedispacher.controls.ImageRow;
import com.example.greg3d.visittimedispacher.css.CssManager;
import com.example.greg3d.visittimedispacher.css.LabelCss;
import com.example.greg3d.visittimedispacher.dialog.DatePickerDialogImpl;
import com.example.greg3d.visittimedispacher.dialog.YesNoDialog;
import com.example.greg3d.visittimedispacher.framework.annotations.FindBy;
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

    public Controls controls;

    public static class Controls{
        @FindBy(R.id.ib_in)
        public ImageButton entrance_Button;

        @FindBy(R.id.ib_out)
        public ImageButton exit_Button;

        @FindBy(R.id.f_filterDateTextView)
        public TextView filterDate_TextView;

        //@FindBy(R.id.f_timeToOutTextView)
        //public TextView timeToOutTextView;
    }

    public Labels labels;

    public static class Labels{

        @FindBy(R.id.editText_averageTimeLabel)
        public TextView averageTime;

        @FindBy(R.id.editText_missingTimeLabel)
        public TextView missingTime;
    }

    public interface ITimeNumbers{
        ImageView ah1 = null;

        ImageView ah2 = null;

        ImageView ahm_spliter = null;

        ImageView am1 = null;

        ImageView am2 = null;

        ImageView ams_spliter = null;

        ImageView as1 = null;

        ImageView as2 = null;
    }

    public AverageTimeNumbers averageTimeNumbers;

    public static class AverageTimeNumbers implements ITimeNumbers{

        @FindBy(R.id.iv_ah1)
        public ImageView ah1;

        @FindBy(R.id.iv_ah2)
        public ImageView ah2;

        @FindBy(R.id.iv_ahm_spliter)
        public ImageView ahm_spliter;

        @FindBy(R.id.iv_am1)
        public ImageView am1;

        @FindBy(R.id.iv_am2)
        public ImageView am2;

        @FindBy(R.id.iv_ams_spliter)
        public ImageView ams_spliter;

        @FindBy(R.id.iv_as1)
        public ImageView as1;

        @FindBy(R.id.iv_as2)
        public ImageView as2;
    }

    public ImageRow dateImageRow;


    public MissingTimeNumbers missingTimeNumbers;

    public static class MissingTimeNumbers implements ITimeNumbers{

        @FindBy(R.id.iv_mh1)
        public ImageView ah1;

        @FindBy(R.id.iv_mh2)
        public ImageView ah2;

        @FindBy(R.id.iv_mhm_spliter)
        public ImageView ahm_spliter;

        @FindBy(R.id.iv_mm1)
        public ImageView am1;

        @FindBy(R.id.iv_mm2)
        public ImageView am2;

        @FindBy(R.id.iv_mms_spliter)
        public ImageView ams_spliter;

        @FindBy(R.id.iv_ms1)
        public ImageView as1;

        @FindBy(R.id.iv_ms2)
        public ImageView as2;
    }

    public TimeToExitNumbers timeToExitNumbers;

    public static class TimeToExitNumbers implements ITimeNumbers{

        @FindBy(R.id.iv_a17)
        public ImageView ah1;

        @FindBy(R.id.iv_a18)
        public ImageView ah2;

        @FindBy(R.id.iv_a19)
        public ImageView ahm_spliter;

        @FindBy(R.id.iv_a20)
        public ImageView am1;

        @FindBy(R.id.iv_a21)
        public ImageView am2;

        @FindBy(R.id.iv_a22)
        public ImageView ams_spliter;

        @FindBy(R.id.iv_a23)
        public ImageView as1;

        @FindBy(R.id.iv_a24)
        public ImageView as2;
    }

    public DateNumbers dateNumbers;

    public static class DateNumbers{

        @FindBy(R.id.iv_a2)
        public ImageView dd1;

        @FindBy(R.id.iv_a3)
        public ImageView dd2;

        @FindBy(R.id.iv_a4)
        public ImageView dm_spliter;

        @FindBy(R.id.iv_a5)
        public ImageView mm1;

        @FindBy(R.id.iv_a6)
        public ImageView mm2;

        @FindBy(R.id.iv_a7)
        public ImageView my_spliter;

        @FindBy(R.id.iv_a8)
        public ImageView yy1;

        @FindBy(R.id.iv_a9)
        public ImageView yy2;

        @FindBy(R.id.iv_a10)
        public ImageView yy3;

        @FindBy(R.id.iv_a11)
        public ImageView yy4;
    }

    public DateTextViewNumbers dateTextViewNumbers;

    public static class DateTextViewNumbers{
        @FindBy(R.id.tv_a2)
        public TextView dd1;

        @FindBy(R.id.tv_a3)
        public TextView dd2;

        @FindBy(R.id.tv_a4)
        public TextView dm_spliter;

        @FindBy(R.id.tv_a5)
        public TextView mm1;

        @FindBy(R.id.tv_a6)
        public TextView mm2;

        @FindBy(R.id.tv_a7)
        public TextView my_spliter;

        @FindBy(R.id.tv_a8)
        public TextView yy1;

        @FindBy(R.id.tv_a9)
        public TextView yy2;

        @FindBy(R.id.tv_a10)
        public TextView yy3;

        @FindBy(R.id.tv_a11)
        public TextView yy4;
    }


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
        //ActivityFactory.InitTextViews(this, dateTextViewNumbers, "fonts/ds-digital-bold.ttf");

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

        //dateImageRow = new ImageRow(numbersBitMap);
        dateImageRow = new ImageRow(createCirilicBitmap());

        ActivityFactory.InitActivity(this, dateImageRow);

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

    private HashMap<String, Bitmap> createCirilicBitmap(){
        HashMap<String, Bitmap> bitMap = new HashMap<>();
        int width = 80;
        int heigh = 118;
        int hShift = 10;

        //int xOffset = 54;
        //int yOffset = 35;
        int xOffset = 0;
        int yOffset = 0;
        Bitmap bitmapSource = BitmapFactory.decodeResource(getResources(), R.drawable.c3);
        String[] row1 = "абвгдежзикл".split("");
        String[] row2 = "мнопрстуфхц".split("");
        String[] row3 = "чшщъыьэюя?!".split("");
        String[] rowNum = "1234567890 ".split("");

        for(int i = 0; i < 11; i ++){
            //bitMap.put(row1[i + 1], Bitmap.createBitmap(bitmapSource, 40 + i * width, 20, width, heigh));
            bitMap.put(row1[i + 1], Bitmap.createBitmap(bitmapSource, xOffset + i * width, yOffset, width, heigh));
            bitMap.put(row2[i+1], Bitmap.createBitmap(bitmapSource, xOffset + i * width, yOffset + (heigh + hShift), width, heigh));
            bitMap.put(row3[i+1], Bitmap.createBitmap(bitmapSource, xOffset + i * width, yOffset + (heigh + hShift) * 2, width, heigh));
            bitMap.put(rowNum[i+1], Bitmap.createBitmap(bitmapSource, xOffset + i * width, bitmapSource.getHeight() - heigh, width, heigh));
        }
        return bitMap;
    }


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
        TextView e1 = (TextView)instance.findViewById(R.id.editText_averageTimeValue);
        //TextView e2 = (TextView)instance.findViewById(R.id.editText_missingTimeValue);
        TextView h2 = (TextView)instance.findViewById(R.id.editText_missingTimeLabel);

        String missingTimeHeader = "ОСТАВШЕЕСЯ";
        String missingTime = DBController.getMissingTimeToSimpleString();
        if(missingTime.contains("-")) {
            missingTimeHeader = "ПЕРЕРАБОТКА";
            missingTime = missingTime.replace("-","").replace(" ", "");
        }

        h2.setText(missingTimeHeader);
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
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
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
//        else if(v.idEquals(controls.exit_Button)) {
//            //scaleAnimation(controls.exit_Button);
//            rotateAnimation(controls.exit_Button);
//            new YesNoDialog(this, new DoExitCommand(), "Изменить выбранную запись ?").show();
//        }
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
