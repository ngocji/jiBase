package comx.y.z.kotlinbase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessActivities;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.HistoryClient;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.request.SessionInsertRequest;
import com.google.android.gms.fitness.request.SessionReadRequest;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GoogleFitHelper {
    public static <T> boolean enableIfNeed(T target, int requestCode) {
        try {
            FitnessOptions fitnessOptions = FitnessOptions.builder()
                    .addDataType(DataType.TYPE_HEART_POINTS, FitnessOptions.ACCESS_WRITE)
                    .addDataType(DataType.TYPE_WEIGHT, FitnessOptions.ACCESS_WRITE)
                    .addDataType(DataType.TYPE_ACTIVITY_SEGMENT, FitnessOptions.ACCESS_WRITE)
                    .build();

            Scope scopesActivity = new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE);
            Context context = getContext(target);

            GoogleSignInAccount account = getSignInAccount(context);

            if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
                if (target instanceof Fragment) {
                    GoogleSignIn.requestPermissions(
                            ((Fragment) target),
                            requestCode,
                            GoogleSignIn.getLastSignedInAccount(context),
                            scopesActivity);
                } else if (target instanceof FragmentActivity) {
                    GoogleSignIn.requestPermissions(
                            ((FragmentActivity) target),
                            requestCode,
                            GoogleSignIn.getLastSignedInAccount(context),
                            scopesActivity);
                } else {
                    throw new Exception("Error target!");
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void sendEditWeight(Context context, float weight, boolean isShowSuccess) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        if (account == null) return;
        DataSet weightDataSet = createWeightDataSet(context, weight);

        Fitness.getHistoryClient(context, account)
                .insertData(weightDataSet)
                .addOnSuccessListener(a -> {
                    if (isShowSuccess) {
                        Toast.makeText(context, "Synchronization was success. You can check it in Google fit!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    if (isShowSuccess) {
                        Toast.makeText(context, "Synchronization was failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void sendWorkout(Context context, String name, long time, boolean isShowSuccess) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        if (account == null) return;
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.MILLISECOND, (int) -time);
        long startTime = calendar.getTimeInMillis();

        DataSource activity = new DataSource.Builder()
                .setAppPackageName(context.getPackageName())
                .setDataType(DataType.TYPE_ACTIVITY_SEGMENT)
                .setType(DataSource.TYPE_RAW)
                .build();

        DataPoint exercisePoint = DataPoint.builder(activity)
                .setTimestamp(time, TimeUnit.MILLISECONDS)
                .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
                .setActivityField(Field.FIELD_ACTIVITY, FitnessActivities.CALISTHENICS)
                .build();


        DataSet activitySegments = DataSet.builder(activity)
                .add(exercisePoint)
                .build();

        Session session = new Session.Builder()
                .setName(name)
                .setIdentifier("Workout session - " + context.getPackageName())
                .setActivity(FitnessActivities.CALISTHENICS)
                .setStartTime(startTime, TimeUnit.MILLISECONDS)
                .setEndTime(endTime, TimeUnit.MILLISECONDS)
                .build();

// Build a session insert request
        SessionInsertRequest insertRequest = new SessionInsertRequest.Builder()
                .setSession(session)
                .addDataSet(activitySegments)
                .build();

        Fitness.getSessionsClient(context, account)
                .insertSession(insertRequest)
                .addOnSuccessListener(aVoid -> {
                    Log.d("GoogleFit: ", "Success");
                    if (isShowSuccess) {
                        Toast.makeText(context, "Synchronization was success. You can check it in Google fit!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d("GoogleFit: ", "Failed");
                    if (isShowSuccess) {
                        Toast.makeText(context, "Synchronization was failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void stopSession(Context context) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        if (account == null) return;

        SessionReadRequest readRequest = new SessionReadRequest.Builder()
                .build();

        Fitness.getSessionsClient(context, account)
                .readSession(readRequest)
                .addOnSuccessListener(sessionReadResponse -> {
                    Log.d("GoogleFit: ", "get session success");

                    // Get a list of the sessions that match the criteria to check the result.
                    List<Session> sessions = sessionReadResponse.getSessions();
                    if (sessions == null || sessions.isEmpty()) return;
                    for (Session session : sessions) {
                        Fitness.getSessionsClient(context, account)
                                .stopSession(session.getIdentifier());
                    }
                    //
                    Toast.makeText(context, "Stop synchronization was success.", Toast.LENGTH_SHORT).show();
                });
    }

    @NotNull
    private static GoogleSignInAccount getSignInAccount(Context context) {
        FitnessOptions fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_HEART_POINTS, FitnessOptions.ACCESS_WRITE)
                .addDataType(DataType.TYPE_WEIGHT, FitnessOptions.ACCESS_WRITE)
                .addDataType(DataType.TYPE_WORKOUT_EXERCISE, FitnessOptions.ACCESS_WRITE)
                .build();
        return GoogleSignIn.getAccountForExtension(context, fitnessOptions);
    }

    private static <T> Context getContext(T target) throws Exception {
        if (target instanceof Fragment) {
            return ((Fragment) target).requireContext();
        } else if (target instanceof FragmentActivity) {
            return ((FragmentActivity) target);
        } else throw new Exception("Error target");
    }


    private static DataSet createWeightDataSet(Context context, float weight) {
        if (weight <= 0f) return null;
        // Create a data source
        DataSource dataSource =
                new DataSource.Builder()
                        .setAppPackageName(context)
                        .setDataType(DataType.TYPE_WEIGHT)
                        .setType(DataSource.TYPE_RAW)
                        .build();

        // the number of new steps.
        DataPoint dataPoint = DataPoint.builder(dataSource)
                .setField(Field.FIELD_WEIGHT, weight)
                .setTimestamp(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .build();

        // create data set
        return DataSet.builder(dataSource)
                .add(dataPoint)
                .build();
    }

}
