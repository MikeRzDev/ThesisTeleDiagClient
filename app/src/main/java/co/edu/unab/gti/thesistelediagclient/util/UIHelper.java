package co.edu.unab.gti.thesistelediagclient.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Calendar;

import co.edu.unab.gti.thesistelediagclient.R;
import co.edu.unab.gti.thesistelediagclient.ui.util.MCoordinatorLayout;
import co.edu.unab.gti.thesistelediagclient.ui.util.ScrollToolbarController;


public class UIHelper {


    private static ProgressDialog progressDialog;
    private static ScrollToolbarController toolbarController;


    public static final void setupToolbarController(MCoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout,
                                                    FloatingActionButton mainFAB, FloatingActionButton scrollToolbarFAB,
                                                    CollapsingToolbarLayout collapsingToolbarLayout, ImageView scrollToolbarImage) {

        toolbarController = new ScrollToolbarController(coordinatorLayout, appBarLayout, mainFAB, scrollToolbarFAB,
                collapsingToolbarLayout, scrollToolbarImage);
    }

    public static final ScrollToolbarController getToolbarController() {
        return toolbarController;
    }

    public static final void simulateAsyncTask(final Activity activity,
                                               final int sleepTimeMillis,
                                               final Runnable callback) {

        if (!activity.isFinishing()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(sleepTimeMillis);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    activity.runOnUiThread(callback);
                }
            }).start();
        }

    }

    public static final void loadImage(ImageView imageView, int drawableId) {
        ImageLoader.getInstance().displayImage("drawable://" + drawableId,
                imageView, AppContext.getImageOptions());
    }

    public static final void loadImage(ImageView imageView, String imgPath) {
        if (imgPath != null) {
            ImageLoader.getInstance().displayImage("file://" + imgPath,
                    imageView, AppContext.getImageOptions());
        } else {
            Log.e("loadImage", "image path is NULL");
        }
    }

    public static final void loadImageRounded(ImageView imageView, int drawableId) {
        ImageLoader.getInstance().displayImage("drawable://" + drawableId,
                imageView, AppContext.getRoundedOptions());
    }

    public static final void loadImageRounded(ImageView imageView, String imgPath) {
        if (imgPath != null) {
            ImageLoader.getInstance().displayImage("file://" + imgPath,
                    imageView, AppContext.getRoundedOptions());
        } else {
            Log.e("loadImage", "image path is NULL");
        }
    }

    public static final void showMultipleOptionDialog(final Activity uiContext,
                                                      String title,
                                                      int iconResource,
                                                      String[] items,
                                                      DialogInterface.OnClickListener listener) {
        if (!uiContext.isFinishing()) {
            AlertDialog alertDialog = new AlertDialog.Builder(uiContext)
                    .setTitle(title)
                    .setIcon(iconResource)
                    .setItems(items, listener)
                    .create();
            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    hideProgressDialog();
                }
            });
            alertDialog.show();
        }
    }

    public static final void showCustomListDialog(final Activity uiContext,
                                                  String title,
                                                  ListAdapter adapter,
                                                  DialogInterface.OnClickListener listener) {
        if (!uiContext.isFinishing()) {
            new AlertDialog.Builder(uiContext)
                    .setTitle(title)
                    .setAdapter(adapter, listener)
                    .show();
        }
    }

    public static final void showInformationDialog(final Activity uiContext,
                                                   String title,
                                                   String message,
                                                   DialogInterface.OnClickListener okListener,
                                                   DialogInterface.OnClickListener cancelListener) {
        if (!uiContext.isFinishing()) {
            new AlertDialog.Builder(uiContext)
                    .setTitle(title)
                    .setIcon(R.drawable.ic_warning_black_24dp)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("Aceptar",
                            okListener)
                    .setNegativeButton("Cancelar", cancelListener)
                    .show();
        }

    }


    public static final void showInformationDialog(final Activity uiContext,
                                                   String title,
                                                   String message,
                                                   DialogInterface.OnClickListener listener) {
        if (!uiContext.isFinishing()) {
            new AlertDialog.Builder(uiContext)
                    .setTitle(title)
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage(message)
                    .setPositiveButton("Aceptar",
                            listener).show();
        }

    }

    public static final void showCustomInformationDialog(final Activity uiContext,
                                                         String title,
                                                         String message,
                                                         String positiveButtonTitle,
                                                         String negativeButtonTitle,
                                                         DialogInterface.OnClickListener okListener,
                                                         DialogInterface.OnClickListener cancelListener) {
        if (!uiContext.isFinishing()) {
            new AlertDialog.Builder(uiContext)
                    .setTitle(title)
                    .setIcon(R.drawable.ic_warning_black_24dp)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(positiveButtonTitle,
                            okListener)
                    .setNegativeButton(negativeButtonTitle, cancelListener)
                    .show();
        }

    }


    public static final void showCustomInformationDialog(final Activity uiContext,
                                                         String title,
                                                         String message,
                                                         String positiveButtonTitle,
                                                         DialogInterface.OnClickListener listener) {
        if (!uiContext.isFinishing()) {
            new AlertDialog.Builder(uiContext)
                    .setTitle(title)
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage(message)
                    .setPositiveButton(positiveButtonTitle,
                            listener).show();
        }

    }


    public static final void showErrorDialog(Activity uiContext,
                                             String title,
                                             String message, DialogInterface.OnClickListener listener) {
        if (!uiContext.isFinishing()) {
            new AlertDialog.Builder(uiContext)
                    .setTitle(title)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage(message)
                    .setPositiveButton("Aceptar",
                            listener).show();
        }
    }


    // Just for use on fragments in same activity
    public static final void showProgressDialog(Activity uiContext, String progressDialogMessage) {
        if (!uiContext.isFinishing()) {
            progressDialog = new ProgressDialog(uiContext);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(progressDialogMessage);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

    public static final void updateProgressDialog(String msg) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.setMessage(msg);
        }
    }

    // Just for use on fragments in same activity
    public static final void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }


    public static final void navigateToBackFragment(FragmentManager fm) {
        fm.popBackStack();
    }


    public static final void changeRootFragment(int idViewToChange,
                                                FragmentManager fm,
                                                Fragment nextFragment) {

        //clears backstack to initiate new route
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);


        final FragmentTransaction ft = fm.beginTransaction();
        ft.replace(idViewToChange, nextFragment);
        ft.commit();

    }

    public static final void changeFragment(int idViewToChange,
                                            FragmentManager fm,
                                            Fragment nextFragment) {

        final int fragmentIndexInBackStack = findFragmentIndexInBackStack(fm,
                nextFragment.getClass().getName());

        if (fragmentIndexInBackStack > 0) {
            fm.popBackStack(fragmentIndexInBackStack,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            final FragmentTransaction ft = fm.beginTransaction();
            ft.replace(idViewToChange, nextFragment);
            ft.addToBackStack(nextFragment.getClass().getName());
            ft.commit();
        }


    }


    public static final void changeFragment(int idViewToChange,
                                            FragmentManager fm,
                                            Fragment nextFragment,
                                            Bundle bundle) {

        if (bundle != null) {
            nextFragment.setArguments(bundle);
        }

        final int fragmentIndexInBackStack = findFragmentIndexInBackStack(fm,
                nextFragment.getClass().getName());

        if (fragmentIndexInBackStack > 0) {
            fm.popBackStack(fragmentIndexInBackStack,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            final FragmentTransaction ft = fm.beginTransaction();
            ft.replace(idViewToChange, nextFragment);
            ft.addToBackStack(nextFragment.getClass().getName());
            ft.commit();
        }
    }

    private static final int findFragmentIndexInBackStack(FragmentManager fm,
                                                          String tagname) {
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {

            final String name = fm.getBackStackEntryAt(i).getName();
            if (name.equalsIgnoreCase(tagname)) {
                return i + 1;
            }
        }
        return 0;
    }


    public static final void displayText(Activity activity, int TextView_id, String text, String format) {
        TextView tv = (TextView) activity.findViewById(TextView_id);
        tv.setText(text);
        if (format != null && format.equals("underline"))
            tv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }

    public static final void displayTextToView(View view, int TextView_id, String text, String format) {
        TextView tv = (TextView) view.findViewById(TextView_id);
        tv.setText(text);
        if (format != null && format.equals("underline"))
            tv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }


    public static final void changeImageArrayAdapter(View row, int imageview_id, int image_drawable_id) {
        ImageView imagen = (ImageView) row.findViewById(imageview_id);
        imagen.setImageDrawable(row.getContext().getResources().getDrawable(image_drawable_id));
    }

    public static final void changePicture(Activity activity, int ImageView_id, int picture_drawable_id) {

        ImageView iv = (ImageView) activity.findViewById(ImageView_id);
        iv.setImageDrawable(activity.getResources().getDrawable(picture_drawable_id));
    }


    public static final String getText(Activity activity, int id) {


        String text = null;

        if (activity.findViewById(id) instanceof EditText) {
            EditText et = (EditText) activity.findViewById(id);
            text = et.getText().toString();
        } else if (activity.findViewById(id) instanceof TextView) {
            TextView tv = (TextView) activity.findViewById(id);
            text = tv.getText().toString();
        }


        return text;
    }

    public static final String getText(View view, int id) {

        String text = null;

        if (view.findViewById(id) instanceof EditText) {
            EditText et = (EditText) view.findViewById(id);
            text = et.getText().toString();
        } else if (view.findViewById(id) instanceof TextView) {
            TextView tv = (TextView) view.findViewById(id);
            text = tv.getText().toString();
        }


        return text;
    }

    public static final ListView setupListView(Activity activity,
                                               int listViewId,
                                               ListAdapter adapter,
                                               AdapterView.OnItemClickListener onItemClickListener) {
        ListView listView = (ListView) activity.findViewById(listViewId);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);
        return listView;
    }

    public static final EditText setupEditText(Activity activity, int editTextId) {
        final EditText editText = (EditText) activity.findViewById(editTextId);

        return editText;
    }

    public static final Button setupButton(Activity activity, int buttonId, View.OnClickListener onClickListener) {
        final Button btn = (Button) activity.findViewById(buttonId);
        btn.setOnClickListener(onClickListener);

        return btn;
    }

    public static final TextView setupTextView(Activity activity, int buttonId, View.OnClickListener onClickListener) {
        final TextView textView = (TextView) activity.findViewById(buttonId);
        textView.setOnClickListener(onClickListener);

        return textView;
    }

    public static final TextView setupTextView(Activity activity, int buttonId) {
        final TextView textView = (TextView) activity.findViewById(buttonId);
        return textView;
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        private static TextView viewToDisplay;

        public static final void setViewToDisplay(TextView edtxt) {
            if (edtxt != null) {
                viewToDisplay = edtxt;
            }
        }


        private static String setDateString(int year, int monthOfYear, int dayOfMonth) {

            // Increment monthOfYear for Calendar/Date -> Time Format setting
            monthOfYear++;
            String mon = "" + monthOfYear;
            String day = "" + dayOfMonth;

            if (monthOfYear < 10)
                mon = "0" + monthOfYear;
            if (dayOfMonth < 10)
                day = "0" + dayOfMonth;

            return year + "-" + mon + "-" + day;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            viewToDisplay.setText(setDateString(year, monthOfYear, dayOfMonth));
        }

    }


}





