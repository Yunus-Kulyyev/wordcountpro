package com.graspery.www.wordcountpro;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.pdf.PdfDocument;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.StrictMode;
import android.print.PrintAttributes;
import android.print.pdf.PrintedPdfDocument;
import android.provider.MediaStore;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;

import androidx.annotation.NonNull;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.ml.vision.text.RecognizedLanguage;
import com.graspery.www.wordcountpro.GoogleApi.AccessTokenLoader;
import com.graspery.www.wordcountpro.GoogleApi.ApiFragment;
import com.graspery.www.wordcountpro.GoogleApi.Models.EntityInfo;
import com.graspery.www.wordcountpro.GoogleApi.Models.SentimentInfo;
import com.graspery.www.wordcountpro.GoogleApi.Models.TokenInfo;
import com.graspery.www.wordcountpro.GoogleApi.SyntaxDialog;
import com.graspery.www.wordcountpro.TextReview.TextReviewDashboard;
import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;
import com.opencsv.CSVWriter;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener,
                                                                Runnable, ApiFragment.Callback{
    private EditText textDisplay;
    private TextView charactersCount;
    private TextView wordsCount;
    private TextView sentencesCount;
    private TextView paragraphsCount;
    private EditText inputEditText;
    private ImageView clearButton;
    private Button scanButton;
    private ScrollView mScrollView;
    private TextView disclaimer;

    private TextView totChar;
    private TextView totWhite;
    private TextView totWords;
    private TextView totSent;
    private TextView totPar;
    private TextView difWords;
    private TextView complexity;
    private TextView readability;

    private TextView adultAverage;
    private TextView studentAverage;
    private TextView proAverage;

    private TextView longestWord;
    private TextView longestSentence;
    private ArrayList<String> eachWord;

    private HashMap<String, Integer> occurences;
    private HashMap<Integer, Integer> lengthOccurences;

    private ArrayList<Word> mostOccured;
    private GridLayout gridLayout;
    private GridLayout wordCountGrid;

    private ProgressBar mProgressBar;
    private ImageButton cameraButton;
    private ImageView imageView;
    private boolean cameraPermission;
    private Uri file;
    private ImageButton uploadImageButton;
    public static final int GET_FROM_GALLERY = 3;

    private final String URL_KEY = "http://words.bighugelabs.com/api/2/f523e910c19b5470cbe6cdb0386cc4c5/";
    private LinearLayout recommendationLayout;

    /*private AdView adView;*/

    private LineChart chart;

    private TextToSpeech mTextToSpeech;
    private Button speak;
    private Button copyClipboard;

    private SharedPreferences mSharedPreferences;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private Switch mSwitch;
    private SeekBar mSeekBar;
    private TextView progressTextView;
    private int minimumWordLength;

    private LinearLayout rateLinear;
    private LinearLayout contactLinear;
    private LinearLayout aboutLinear;
    private LinearLayout translateLinear;

    private DrawerLayout drawer;
    private SQLiteDatabaseHandler db;
    private ListView archiveListView;
    private ImageView clearArchive;

    private FirebaseAnalytics mFirebaseAnalytics;

    private Bitmap tempBitmap;
    private String GRAMMAR_BOT_KEY = "AF5B9M2X";

    private DatabaseReference mDatabase;

    private Intent mShareIntent;
    private OutputStream os;

    private FloatingActionButton fob;
    int numOfcharacters;

    //private Button signIn;
    //private LinearLayout signInMessageBox;
    //private ImageView userImageView;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private boolean isSigned;
    private static final String FRAGMENT_API = "api";
    private static final int LOADER_ACCESS_TOKEN = 1;

    //For Saving
    List<String[]> data;

    private int request_code =1, FILE_SELECT_CODE =101;
    private String TAG ="mainactivty";

    private TokenInfo[] tokens;
    int pageNumber;
    private TextView pageNumb;
    private ImageButton prevBtn;
    private ImageButton nextBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPreferences = this.getSharedPreferences("com.graspery.www.wordcountpro", MODE_PRIVATE);
        if(mSharedPreferences.getString("theme", "light").equalsIgnoreCase("dark")) {
            setTheme(R.style.DarkAppTheme);
        } else if(mSharedPreferences.getString("theme", "light").equalsIgnoreCase("super_dark")){
            setTheme(R.style.SuperDarkAppTheme);
        } else {
            setTheme(R.style.AppTheme);
        }


        if(!mSharedPreferences.getString("language", "none").equalsIgnoreCase("none")) {
            String languageToLoad  = mSharedPreferences.getString("language", "en"); // your language
            Locale locale;

            if(mSharedPreferences.getString("language", "none").equalsIgnoreCase("pt-rBR")) {
                //create a string for country
                String country = "BR";
                languageToLoad = "pt";
                locale = new Locale(languageToLoad, country);
            } else if(mSharedPreferences.getString("language", "none").equalsIgnoreCase("es-rAR")) {
                //create a string for country
                String country = "AR";
                languageToLoad = "es";
                locale = new Locale(languageToLoad, country);
            } else {
                locale = new Locale(languageToLoad);
            }

            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        }

        setContentView(R.layout.activity_navigation);

        isSigned = false;
        mAuth = FirebaseAuth.getInstance();

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // Read from the database
        /*mDatabase.child("popular").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String builder = "Trending Words\n\n";
                int counter = 1;
                for(DataSnapshot snap : dataSnapshot.getChildren()) {
                    builder = builder + counter + ": " + snap.getKey() + "\n";
                    counter++;
                }

                builder = builder + "\n\n\n";
                disclaimer.setText(builder);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });*/

        db = new SQLiteDatabaseHandler(this);

        checkIfSignedIn();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        initializeObjects();


        data = new ArrayList<String[]>();

        fob = findViewById(R.id.save_float_button);
        fob.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff3b30")));
        fob.setOnClickListener(null);

    }

    private ApiFragment getApiFragment() {
        return (ApiFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_API);
    }

    private void prepareApi() {
        // Initiate token refresh
        getSupportLoaderManager().initLoader(LOADER_ACCESS_TOKEN, null,
                new LoaderManager.LoaderCallbacks<String>() {
                    @Override
                    public Loader<String> onCreateLoader(int id, Bundle args) {
                        return new AccessTokenLoader(MainActivity.this);
                    }

                    @Override
                    public void onLoadFinished(Loader<String> loader, String token) {
                        getApiFragment().setAccessToken(token);
                    }

                    @Override
                    public void onLoaderReset(Loader<String> loader) {
                    }
                });
    }

    public void analyzeApi(String text) {
        ProgressBar sentimentProgress = findViewById(R.id.sentiment_bar);
        sentimentProgress.setIndeterminate(true);
        BoldTextView progressTxt = findViewById(R.id.progress_txt);
        progressTxt.setText("calculating...");
        ProgressBar entityProgress = findViewById(R.id.entity_loading_bar);
        entityProgress.setIndeterminate(true);
        entityProgress.setVisibility(View.VISIBLE);

        ProgressBar syntaxProgress = findViewById(R.id.syntax_loading_bar);
        syntaxProgress.setIndeterminate(true);
        syntaxProgress.setVisibility(View.VISIBLE);

        getApiFragment().analyzeEntities(text);
        getApiFragment().analyzeSentiment(text);
        getApiFragment().analyzeSyntax(text);
    }

    @Override
    public void onEntitiesReady(EntityInfo[] entities) {
        List<EntityInfo> entityInfoList = new ArrayList<>();

        for(EntityInfo entity : entities) {
            if(entity.salience*100 > 1) {
                entityInfoList.add(entity);
            }
        }

        ProgressBar entityProgress = findViewById(R.id.entity_loading_bar);
        entityProgress.setIndeterminate(false);
        entityProgress.setVisibility(View.GONE);

        ListView entityListview = findViewById(R.id.entity_listview);
        EntitiyCustomListview customAdapter;
        if(mSharedPreferences.getString("theme", "light").equalsIgnoreCase("dark") ||
                mSharedPreferences.getString("theme", "light").equalsIgnoreCase("super_dark")) {
            customAdapter = new EntitiyCustomListview(this, R.layout.entity_row, entityInfoList, 1);
        } else {
            customAdapter = new EntitiyCustomListview(this, R.layout.entity_row, entityInfoList, 0);
        }
        entityListview .setAdapter(customAdapter);
        entityListview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mScrollView.requestDisallowInterceptTouchEvent(true);
                int action = event.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_UP:
                        mScrollView.requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }

        });
    }

    @Override
    public void onSentimentReady(SentimentInfo sentiment) {
        ProgressBar sentimentProgress = findViewById(R.id.sentiment_bar);
        sentimentProgress.setIndeterminate(false);

        float sentimentScore = 0;
        try {
            sentimentScore = (sentiment.score+1)*100 / 2;
        } catch (ArithmeticException e) {
            sentimentScore = 0;
        }

        DecimalFormat f = new DecimalFormat("##");
        BoldTextView progressTxt = findViewById(R.id.progress_txt);
        progressTxt.setText(f.format(sentimentScore) + "");

        sentimentProgress.setProgress((int)sentimentScore);
        if(sentimentScore <= 25) {
            sentimentProgress.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#DD1C1A")));
        } else if(sentimentScore >= 75) {
            sentimentProgress.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#6EEB83")));
        } else {
            sentimentProgress.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FFC971")));
        }
    }

    @Override
    public void onSyntaxReady(TokenInfo[] tokens) {
        /*if (mViewPager.getCurrentItem() == API_SYNTAX) {
            showResults();
        }
        mAdapter.setTokens(tokens);*/
        final String text = textDisplay.getText().toString();
        final int pageLength = 100;                             //75 words
        final int totPageNumber = (int)Math.ceil((double)(countWords(text))/pageLength);
        pageNumber = 1;
        this.tokens = tokens.clone();
        ProgressBar syntaxProgress = findViewById(R.id.syntax_loading_bar);
        syntaxProgress.setVisibility(View.INVISIBLE);

        pageNumb = findViewById(R.id.page_number_textview);

        pageNumb.setText("1/" + totPageNumber);

        prevBtn = findViewById(R.id.previous_page_button);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNumber--;
                if(pageNumber >= 1) {
                    pageNumb.setText(pageNumber + "/" + totPageNumber);
                    syntaxPageSetup(text, pageNumber, totPageNumber);
                } else {
                    pageNumber++;
                }
            }
        });
        nextBtn = findViewById(R.id.next_page_button);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNumber++;
                if(pageNumber <= totPageNumber) {
                    pageNumb.setText(pageNumber + "/" + totPageNumber);
                    syntaxPageSetup(text, pageNumber, totPageNumber);
                } else {
                    pageNumber--;
                }
            }
        });

        syntaxPageSetup(text, pageNumber, totPageNumber);
    }

    public void syntaxPageSetup(String text, int page, int totPageNumber) {
        try {
            int localCharCount = countCharacters(text);
            int charDivision = localCharCount / totPageNumber;
            int tokenDivision = (int) Math.ceil((double) tokens.length / totPageNumber);
            int startPos = (page - 1) * tokenDivision;
            int endPos = (page) * tokenDivision;
            if (endPos > tokens.length) {
                endPos = tokens.length;
            }

            TextView syntaxTextView = findViewById(R.id.syntax_textview);
            if (page * charDivision <= localCharCount) {
                syntaxTextView.setText(text.substring((page - 1) * charDivision, (page) * charDivision));
            } else {
                syntaxTextView.setText(text.substring((page - 1) * charDivision));
            }

            for (int i = startPos; i < endPos; i++) {
                final int pos = i;
                Link link = new Link(tokens[i].text)
                        .setTextColor(Color.parseColor("#259B24"))                  // optional, defaults to holo blue
                        .setTextColorOfHighlightedLink(Color.parseColor("#0D3D0C")) // optional, defaults to holo blue
                        .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                        .setUnderlined(false)                                       // optional, defaults to true
                        .setBold(true)                                              // optional, defaults to false
                        .setOnClickListener(new Link.OnClickListener() {
                            @Override
                            public void onClick(String clickedText) {
                                SyntaxDialog syntaxDialog = new SyntaxDialog(MainActivity.this, tokens[pos]);
                                syntaxDialog.show();
                            }
                        });
                LinkBuilder.on(syntaxTextView)
                        .addLink(link)
                        .build(); // create the clickable links
            }
        } catch (ArithmeticException e) {
            Toast.makeText(this, "Oops, arithmetic error.", Toast.LENGTH_SHORT).show();
        }

        /*for(final TokenInfo token : tokens) {
            //Toast.makeText(this, token.text + " - " + token.lemma + " - " + token.label + " - " + token.partOfSpeech + " - " + token.headTokenIndex, Toast.LENGTH_LONG).show();
            // can use a specific string or a regex pattern
            Link link = new Link(token.text)
                    .setTextColor(Color.parseColor("#259B24"))                  // optional, defaults to holo blue
                    .setTextColorOfHighlightedLink(Color.parseColor("#0D3D0C")) // optional, defaults to holo blue
                    .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                    .setUnderlined(false)                                       // optional, defaults to true
                    .setBold(true)                                              // optional, defaults to false
                    .setOnClickListener(new Link.OnClickListener() {
                        @Override
                        public void onClick(String clickedText) {
                            SyntaxDialog syntaxDialog = new SyntaxDialog(MainActivity.this, token);
                            syntaxDialog.show();
                        }
                    });
            LinkBuilder.on(syntaxTextView)
                    .addLink(link)
                    .build(); // create the clickable links
        }*/
    }

    public void checkIfSignedIn() {
        isSigned = true;
        //currentUser = mAuth.getCurrentUser();

        //signInMessageBox = findViewById(R.id.sign_in_message_box);
        /*userImageView = findViewById(R.id.user_icon_imageview);
        userImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentUser != null) {
                    LogoutDialog logoutDialog = new LogoutDialog(MainActivity.this, currentUser, mDatabase);
                    logoutDialog.show();
                    logoutDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            currentUser = mAuth.getCurrentUser();
                            if(currentUser != null) {
                                isSigned = true;
                                RelativeLayout entityLinear = findViewById(R.id.entities_linear);
                                entityLinear.setVisibility(View.VISIBLE);
                                RelativeLayout syntaxLinear = findViewById(R.id.syntax_linear);
                                syntaxLinear.setVisibility(View.VISIBLE);
                                LinearLayout sentimentLinear = findViewById(R.id.sentiment_linear);
                                sentimentLinear.setVisibility(View.VISIBLE);
                                //signInMessageBox.setVisibility(View.GONE);
                                userImageView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#41bf56")));
                            } else {
                                isSigned = false;
                                RelativeLayout entityLinear = findViewById(R.id.entities_linear);
                                entityLinear.setVisibility(View.GONE);
                                RelativeLayout syntaxLinear = findViewById(R.id.syntax_linear);
                                syntaxLinear.setVisibility(View.GONE);
                                LinearLayout sentimentLinear = findViewById(R.id.sentiment_linear);
                                sentimentLinear.setVisibility(View.GONE);
                                //signInMessageBox.setVisibility(View.VISIBLE);
                                userImageView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#808080")));
                            }
                        }
                    });
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        });
*/
        /*if(currentUser != null) {
            isSigned = true;
            signInMessageBox.setVisibility(View.GONE);
            userImageView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#41bf56")));

            RelativeLayout entityLinear = findViewById(R.id.entities_linear);
            entityLinear.setVisibility(View.VISIBLE);
            RelativeLayout syntaxLinear = findViewById(R.id.syntax_linear);
            syntaxLinear.setVisibility(View.VISIBLE);
            LinearLayout sentimentLinear = findViewById(R.id.sentiment_linear);
            sentimentLinear.setVisibility(View.VISIBLE);
            // Prepare the API
            final FragmentManager fm = getSupportFragmentManager();
            if (getApiFragment() == null) {
                fm.beginTransaction().add(new ApiFragment(), FRAGMENT_API).commit();
            }
            prepareApi();
        } else {
            RelativeLayout syntaxLinear = findViewById(R.id.syntax_linear);
            syntaxLinear.setVisibility(View.GONE);
            RelativeLayout entityLinear = findViewById(R.id.entities_linear);
            entityLinear.setVisibility(View.GONE);
            LinearLayout sentimentLinear = findViewById(R.id.sentiment_linear);
            sentimentLinear.setVisibility(View.GONE);
            isSigned = false;
            signInMessageBox.setVisibility(View.VISIBLE);
            userImageView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#808080")));
        }*/

        //---------TEMPORARY ALLOWANCE FOR EVERYONE------------//
        //isSigned = true;
        //signInMessageBox.setVisibility(View//.GONE);
        //userImageView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#41bf56")));

        RelativeLayout entityLinear = findViewById(R.id.entities_linear);
        entityLinear.setVisibility(View.VISIBLE);
        RelativeLayout syntaxLinear = findViewById(R.id.syntax_linear);
        syntaxLinear.setVisibility(View.VISIBLE);
        LinearLayout sentimentLinear = findViewById(R.id.sentiment_linear);
        sentimentLinear.setVisibility(View.VISIBLE);
        // Prepare the API
        final FragmentManager fm = getSupportFragmentManager();
        if (getApiFragment() == null) {
            fm.beginTransaction().add(new ApiFragment(), FRAGMENT_API).commit();
        }
        prepareApi();
    }



    @Override
    protected void onResume() {
        super.onResume();
        checkIfSignedIn();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void startCountAnimation(final TextView textToAnimate, int number) {
        ValueAnimator animator = ValueAnimator.ofInt(0, number);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textToAnimate.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }

    void updateArchiveListview() {
        final List<Archive> archives = db.allArchive();
        archiveListView = findViewById(R.id.archive_listview);

        if (archives != null) {
            ListAdapter customAdapter = new ListAdapter(this, R.layout.archive_row, archives,
                    mSharedPreferences.getString("theme", "light"), archives.size());

            archiveListView.setAdapter(customAdapter);

            archiveListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    disclaimer.setVisibility(View.INVISIBLE);
                    occurences.clear();
                    lengthOccurences.clear();
                    recommendationLayout.removeAllViews();
                    mScrollView.setVisibility(View.VISIBLE);
                    String text = archives.get(position).getText();

                    int charCount = countCharacters(text);
                    int whtCount = countWhitespace(text);
                    int worCount = countWords(text);
                    int senCount = countSentences(text);
                    int parCount = countParagraphs(text);

                    totChar.setText(" " + charCount + "");
                    totWhite.setText(" " + whtCount + "");
                    totWords.setText(" " + worCount + "");
                    totSent.setText(" " + senCount +  "");
                    totPar.setText(" " + parCount + "");

                    difWords.setText(" " + occurences.size() + "");
                    readability.setText(" " + round(readibilityCalculator(text), 1) + "");
                    complexity.setText(" " + round(occurences.size()*1.0f / (worCount*1.0f)*100, 1) + "%");

                    charactersCount.setText(" " + charCount + "");
                    wordsCount.setText(" " + worCount + "");
                    sentencesCount.setText(" " + senCount + "");
                    paragraphsCount.setText(" " + parCount + "");

                    addDetailsGrid(worCount);
                    addWordLengthGrid(worCount);
                    speedSetup(worCount);

                    textDisplay.setText(Html.fromHtml(formattedOverview(text)));
                    populateSuggestions();

                    fob.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#41bf56")));
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .repeat(3)
                            .playOn(findViewById(R.id.save_float_button));
                    fob.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialogsForSaving();
                        }
                    });


                    if(isSigned) {
                        analyzeApi(text);
                    }
                    drawer.closeDrawer(GravityCompat.START);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTextToSpeech.isSpeaking()) {
            mTextToSpeech.stop();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mTextToSpeech.isSpeaking()) {
            mTextToSpeech.stop();
        }
    }

    private void initializeObjects() {

        startAnimation(this);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/helvetica.otf");
        Typeface typefaceBold = Typeface.createFromAsset(getAssets(), "fonts/helvetica_heavy.otf");


        FloatingActionButton settingsButton = findViewById(R.id.settings_float_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsDialog settingsDialog = new SettingsDialog(MainActivity.this);
                settingsDialog.show();
                settingsDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        MainActivity.this.finish();
                        MainActivity.this.startActivity(MainActivity.this.getIntent());

                        //NEEDS TO BE REMOVED----------------WARNING!!!!!!!!!!!!!!!!!!
                        //startActivity(new Intent(MainActivity.this, TextReviewDashboard.class));
                    }
                });
            }
        });

        copyClipboard = findViewById(R.id.copy_button);
        copyClipboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text", textDisplay.getText().toString());
                clipboard.setPrimaryClip(clip);
            }
        });

        speak = findViewById(R.id.speak_button);

        if(mSharedPreferences.getString("theme", "light").equalsIgnoreCase("dark") ||
                mSharedPreferences.getString("theme", "light").equalsIgnoreCase("super_dark")) {
            if(mSharedPreferences.getString("theme", "light").equalsIgnoreCase("dark")) {
                navigationView.setBackgroundColor(getResources().getColor(R.color.dark_theme_background));
            } else {
                navigationView.setBackgroundColor(getResources().getColor(R.color.super_dark_theme_background));
            }

            copyClipboard.setBackground(getResources().getDrawable(R.drawable.copy_clipboard_white));
            speak.setBackground(getResources().getDrawable(R.drawable.speak_button_white));

            ImageView rateImage = findViewById(R.id.rate_me_image);
            rateImage.setImageDrawable(getResources().getDrawable(R.drawable.rate_white));
            NormalTextView rateText = findViewById(R.id.rate_me_textview);
            rateText.setTextColor(getResources().getColor(R.color.dark_theme_text_color));
            ImageView contactMeImage = findViewById(R.id.contact_me_image);
            contactMeImage.setImageDrawable(getResources().getDrawable(R.drawable.contact_me_white));
            NormalTextView contactMeText = findViewById(R.id.contact_me_textview);
            contactMeText.setTextColor(getResources().getColor(R.color.dark_theme_text_color));
            ImageView aboutMe = findViewById(R.id.about_image);
            aboutMe.setImageDrawable(getResources().getDrawable(R.drawable.about_icon_white));
            NormalTextView aboutMeTextView = findViewById(R.id.about_textview);
            aboutMeTextView.setTextColor(getResources().getColor(R.color.dark_theme_text_color));
            ImageView translateImage = findViewById(R.id.translate_image);
            translateImage.setImageDrawable(getResources().getDrawable(R.drawable.earth_white));
            NormalTextView translateText = findViewById(R.id.translate_textview);
            translateText.setTextColor(getResources().getColor(R.color.dark_theme_text_color));
        }

        mTextToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "0");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "text_to_speech");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "button");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                if(status != TextToSpeech.ERROR) {
                    mTextToSpeech.setLanguage(Locale.US);
                    speak.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String toSpeak = textDisplay.getText().toString();

                            if(mTextToSpeech.isSpeaking()) {
                                mTextToSpeech.stop();
                            } else {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    mTextToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
                                } else {
                                    mTextToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                }
                            }

                        }
                    });

                } else {
                    speak.setBackground(getDrawable(R.drawable.speak_unavailable_button));
                }
            }
        });

        rateLinear = findViewById(R.id.rate_layout);
        rateLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "rate_from_app");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "button_rate");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(myAppLinkToMarket);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(MainActivity.this, " unable to find market app", Toast.LENGTH_LONG).show();
                }
            }
        });

        contactLinear = findViewById(R.id.contact_layout);
        contactLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL  , new String[] { "graspery@gmail.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "Text Analyzer Pro user");

                startActivity(Intent.createChooser(intent, "Email via..."));
            }
        });

        aboutLinear = findViewById(R.id.about_layout);
        aboutLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager manager = MainActivity.this.getPackageManager();
                PackageInfo info;
                Element versionElement;
                try {
                    info = manager.getPackageInfo(MainActivity.this.getPackageName(), PackageManager.GET_ACTIVITIES);
                    versionElement = new Element();
                    versionElement.setTitle("Version " + info.versionName + "\n\n" + getResources().getString(R.string.encounter_problem));
                } catch (PackageManager.NameNotFoundException e) {
                    versionElement = new Element();
                    versionElement.setTitle(getResources().getString(R.string.encounter_problem));
                }

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "2");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "about_page");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "button-about");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


                Element linkedIn = new Element();
                linkedIn.setTitle("LinkedIn");
                linkedIn.setIconDrawable(R.drawable.linkedin_icon);
                linkedIn.setIconTint(R.color.colorAccent);
                String url = "https://www.linkedin.com/in/yunus-kulyyev-62b421120/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                linkedIn.setIntent(i);

                View aboutPage = new AboutPage(MainActivity.this)
                        .setDescription(getResources().getString(R.string.app_description))
                        .isRTL(false)
                        .setCustomFont("fonts/helvetica.otf")
                        .setImage(R.mipmap.ic_launcher)
                        .addItem(versionElement)
                        .addGroup(getResources().getString(R.string.connect_with_us))
                        .addInstagram("aka_yuska", getResources().getString(R.string.instagram))
                        .addItem(linkedIn)
                        .addYoutube("UCB9P9T4UjTvdnKaGi3k90Jg", getResources().getString(R.string.youtube))
                        .create();

                AboutUsDialog aud = new AboutUsDialog(MainActivity.this, aboutPage);
                aud.show();
            }
        });

        updateArchiveListview();
        clearArchive = findViewById(R.id.clear_archive);
        clearArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getResources().getString(R.string.clear_archive))
                        .setMessage(getResources().getString(R.string.are_you_sure))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                db.deleteAll();
                                updateArchiveListview();
                                Toast.makeText(MainActivity.this, getResources().getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        translateLinear = findViewById(R.id.translate_layout);
        translateLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL  , new String[] { "graspery@gmail.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "Hey I can help you translate text analyzer");
                intent.putExtra(Intent.EXTRA_TEXT, "I greatly appreciate your time helping me with translation. " +
                        "Please include your name and what language you can translate into and I will send you a list of words to translate. Thanks!");

                startActivity(Intent.createChooser(intent, "Email via..."));
            }
        });

        TextView archiveTitle = findViewById(R.id.archive_title);
        archiveTitle.setTypeface(typefaceBold);

        recommendationLayout = findViewById(R.id.recom_layout);

        occurences = new HashMap<>();
        lengthOccurences = new HashMap<>();
        eachWord = new ArrayList<>();
        mostOccured = new ArrayList<>();

        wordCountGrid = findViewById(R.id.word_length_grid);

        mScrollView = findViewById(R.id.scroll_view);
        mProgressBar = findViewById(R.id.progress_dialog);
        gridLayout = findViewById(R.id.details_grid);

        disclaimer = findViewById(R.id.disclaimer);
        disclaimer.setText(Html.fromHtml(
                "<br><u>Max Müller</u>- German translation.</br>" +
                "<br><u>Ahmet Sönmez</u>- Turkish translation.</br>" +
                "<br><u>Vinícius Pereira</u>- Brazilian translation.</br>" +
                "<br><u>Jakub Chwastek</u>- Polish translation.</br>" +
                "<br><u>Filippo Natuzzi</u>- Italian translation.</br>" +
                "<br><u>Anton</u>- Russian translation.</br>" +
                "<br><u>Beto Guimarães</u>- Pt-Brazilian translation.</br>" +
                "<br><u>Carlos Araujo</u>- Pt-Brazilian translation.</br>" +
                "<br><u>Jahan Simon</u>- French translation.</br>" +
                "<br><u>Alberto Matías</u>- Es-Argentinian translation.</br>" +
                "<br><u>Kristína Ištvánová</u>- Slovakian translation.</br>" +
                "<br><u>Monica Jansen</u>- Norwegian translation.</br>" +
                "<br><u>夏目 智仁(Tomo)</u>- Japanese translation.</br>" +
                        "<br><u>Evgen Gil</u>- Ukranian translation.</br>"));


        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setTypeface(typefaceBold);
        TextView char_t = findViewById(R.id.char_t);
        char_t.setTypeface(typefaceBold);
        TextView word_t = findViewById(R.id.word_t);
        word_t.setTypeface(typefaceBold);
        TextView sen_t = findViewById(R.id.sent_t);
        sen_t.setTypeface(typefaceBold);
        TextView par_t = findViewById(R.id.par_t);
        par_t.setTypeface(typefaceBold);
        TextView title_occurred = findViewById(R.id.occurence_title);
        title_occurred.setTypeface(typefaceBold);
        TextView title_overview = findViewById(R.id.overview_title);
        title_overview.setTypeface(typefaceBold);
        TextView title_analysis = findViewById(R.id.analysis_title);
        title_analysis.setTypeface(typefaceBold);
        TextView title_count = findViewById(R.id.word_length_title);
        title_count.setTypeface(typefaceBold);
        TextView title_distr = findViewById(R.id.distr);
        title_distr.setTypeface(typefaceBold);
        TextView title_speed = findViewById(R.id.reading_title);
        title_speed.setTypeface(typefaceBold);
        TextView title_recom = findViewById(R.id.recommendation_title);
        title_recom.setTypeface(typefaceBold);
        TextView anChar = findViewById(R.id.an_char);
        anChar.setTypeface(typefaceBold);
        TextView anWhite = findViewById(R.id.an_white);
        anWhite.setTypeface(typefaceBold);
        TextView anWord = findViewById(R.id.an_word);
        anWord.setTypeface(typefaceBold);
        TextView anSent = findViewById(R.id.an_sent);
        anSent.setTypeface(typefaceBold);
        TextView anPar = findViewById(R.id.an_par);
        anPar.setTypeface(typefaceBold);
        TextView anDiff = findViewById(R.id.an_diff);
        anDiff.setTypeface(typefaceBold);
        TextView anVar = findViewById(R.id.an_var);
        anVar.setTypeface(typefaceBold);
        TextView anRead = findViewById(R.id.an_read);
        anRead.setTypeface(typefaceBold);
        TextView anAdult = findViewById(R.id.an_adult);
        anAdult.setTypeface(typefaceBold);
        TextView anStudent = findViewById(R.id.an_student);
        anStudent.setTypeface(typefaceBold);
        TextView anPro = findViewById(R.id.an_pro);
        anPro.setTypeface(typefaceBold);
        TextView anCustom = findViewById(R.id.custom_speed);
        anCustom.setTypeface(typefaceBold);
        TextView entitiesTitle = findViewById(R.id.entities_title);
        entitiesTitle.setTypeface(typefaceBold);
        TextView sentimentTitle = findViewById(R.id.sentiment_title);
        sentimentTitle.setTypeface(typefaceBold);
        TextView sentimentExpl = findViewById(R.id.sentiment_explanation);
        sentimentExpl.setTypeface(typeface);
        TextView syntaxtTitle = findViewById(R.id.syntax_title);
        syntaxtTitle.setTypeface(typefaceBold);
        TextView syntaxtExplanation = findViewById(R.id.syntax_explanation);
        syntaxtExplanation.setTypeface(typeface);

        final TextView resultSpeed = findViewById(R.id.custom_speed_result);
        resultSpeed.setTypeface(typeface);
        final TextView customSpeedTracker = findViewById(R.id.custom_speed_numbers);
        customSpeedTracker.setTypeface(typeface);

        SeekBar speedSeekbar = findViewById(R.id.speed_seekbar);
        speedSeekbar.setProgress(200);

        speedSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                customSpeedTracker.setText((progress) + " " + MainActivity.this.getResources().getString(R.string.words_per_minute));
                float min = (float)numOfcharacters/(progress);
                resultSpeed.setText((progress) + " " + MainActivity.this.getResources().getString(R.string.words_per_minute) + " = " + round(min, 2) + " " + MainActivity.this.getString(R.string.minutes));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                float min = (float)numOfcharacters/(seekBar.getProgress());
                resultSpeed.setText((seekBar.getProgress()) + " " + MainActivity.this.getResources().getString(R.string.words_per_minute) + " = " + round(min, 2) + " " + MainActivity.this.getString(R.string.minutes));
            }
        });


        totChar = findViewById(R.id.total_char_count);
        totChar.setTypeface(typeface);
        totWhite = findViewById(R.id.total_white_count);
        totWhite.setTypeface(typeface);
        totWords = findViewById(R.id.total_word_count);
        totWords.setTypeface(typeface);
        totSent = findViewById(R.id.total_sentence_count);
        totSent.setTypeface(typeface);
        totPar = findViewById(R.id.total_paragraph_count);
        totPar.setTypeface(typeface);
        difWords = findViewById(R.id.different_words);
        difWords.setTypeface(typeface);
        complexity = findViewById(R.id.complexity);
        complexity.setTypeface(typeface);
        readability = findViewById(R.id.readability);
        readability.setTypeface(typeface);

        adultAverage = findViewById(R.id.average_adult);
        adultAverage.setTypeface(typeface);
        studentAverage = findViewById(R.id.college_student);
        studentAverage.setTypeface(typeface);
        proAverage = findViewById(R.id.speaker);
        proAverage.setTypeface(typeface);

        longestWord = findViewById(R.id.longes_word);
        longestWord.setTypeface(typeface);
        longestSentence = findViewById(R.id.longest_sentence);
        longestSentence.setTypeface(typeface);

        textDisplay = findViewById(R.id.text_display);
        textDisplay.setTypeface(typeface);
        charactersCount = findViewById(R.id.characters_text);
        charactersCount.setTypeface(typefaceBold);
        wordsCount = findViewById(R.id.words_text);
        wordsCount.setTypeface(typefaceBold);
        sentencesCount = findViewById(R.id.sentences_text);
        sentencesCount.setTypeface(typefaceBold);
        paragraphsCount = findViewById(R.id.paragraphs_text);
        paragraphsCount.setTypeface(typefaceBold);

        scanButton = findViewById(R.id.scan_button);
        scanButton.setTypeface(typefaceBold);
        scanButton.setOnClickListener(null);

        cameraButton = findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(this);

        uploadImageButton = findViewById(R.id.upload_button);
        uploadImageButton.setOnClickListener(this);

        imageView = findViewById(R.id.image_view);

        /*signIn = findViewById(R.id.sign_in_main_//menu);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });*/

        clearButton = findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                occurences.clear();
                lengthOccurences.clear();
                inputEditText.setText("");
            }
        });

        inputEditText = findViewById(R.id.input_edit_text);
        inputEditText.setTypeface(typeface);
        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(count == 0) {
                    clearButton.setVisibility(View.GONE);
                    scanButton.setOnClickListener(null);
                    scanButton.setBackgroundResource(R.drawable.button_frame);
                } else {
                    scanButton.setOnClickListener(MainActivity.this);
                    clearButton.setVisibility(View.VISIBLE);
                    scanButton.setBackgroundResource(R.drawable.button_green_frame);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mScrollView.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.GONE);

                occurences.clear();
                lengthOccurences.clear();
                gridLayout.removeAllViews();

                textDisplay.setText("");
                complexity.setText("");
                totChar.setText("");
                totWhite.setText("");
                totWords.setText("");
                totSent.setText("");
                totPar.setText("");
                difWords.setText("");
                readability.setText("");
                longestSentence.setText("");
                longestWord.setText("");

                startCountAnimation(charactersCount, countCharacters(s.toString()));
                //charactersCount.setText(countCharacters(s.toString()) + "");
                startCountAnimation(wordsCount, countWords(s.toString()));
                //wordsCount.setText(countWords(s.toString()) + "");
                startCountAnimation(sentencesCount, countSentences(s.toString()));
                //sentencesCount.setText(countSentences(s.toString()) + "");
                startCountAnimation(paragraphsCount, countParagraphs(s.toString()));
                //paragraphsCount.setText(countParagraphs(s.toString()) + "");

                if(count == 0) {
                    fob.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff3b30")));
                    fob.setOnClickListener(null);
                    clearButton.setVisibility(View.GONE);
                    disclaimer.setVisibility(View.VISIBLE);
                    scanButton.setOnClickListener(null);
                    scanButton.setBackgroundResource(R.drawable.button_frame);
                } else {
                    clearButton.setVisibility(View.VISIBLE);
                    disclaimer.setVisibility(View.INVISIBLE);
                    scanButton.setOnClickListener(MainActivity.this);
                    scanButton.setBackgroundResource(R.drawable.button_green_frame);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        if(mSharedPreferences.getString("theme", "light").equalsIgnoreCase("dark") ||
                mSharedPreferences.getString("theme", "light").equalsIgnoreCase("super_dark")) {

            if(mSharedPreferences.getString("theme", "light").equalsIgnoreCase("dark")) {
                RelativeLayout relParent = findViewById(R.id.relative_parent);
                relParent.setBackgroundColor(getResources().getColor(R.color.dark_theme_background));
                CardView cardView = findViewById(R.id.parent);
                cardView.setCardBackgroundColor(getResources().getColor(R.color.dark_theme_foreground));
                toolbar.setBackgroundColor(getResources().getColor(R.color.dark_theme_foreground));     //Toolbar
            } else {
                RelativeLayout relParent = findViewById(R.id.relative_parent);
                relParent.setBackgroundColor(getResources().getColor(R.color.super_dark_theme_background));
                CardView cardView = findViewById(R.id.parent);
                cardView.setCardBackgroundColor(getResources().getColor(R.color.super_dark_theme_foreground));
                toolbar.setBackgroundColor(getResources().getColor(R.color.super_dark_theme_foreground));     //Toolbar
            }




            int textColor = getResources().getColor(R.color.dark_theme_text_color);

            archiveTitle.setTextColor(textColor);

            toolbarTitle.setTextColor(textColor);
            char_t.setTextColor(textColor);
            word_t.setTextColor(textColor);
            sen_t.setTextColor(textColor);
            par_t.setTextColor(textColor);
            title_occurred.setTextColor(textColor);
            title_overview.setTextColor(textColor);
            title_analysis.setTextColor(textColor);
            title_count.setTextColor(textColor);
            title_distr.setTextColor(textColor);
            title_speed.setTextColor(textColor);
            title_recom.setTextColor(textColor);

            anChar.setTextColor(textColor);
            anWhite.setTextColor(textColor);
            anWord.setTextColor(textColor);
            anSent.setTextColor(textColor);
            anPar.setTextColor(textColor);
            anDiff.setTextColor(textColor);
            anVar.setTextColor(textColor);
            anRead.setTextColor(textColor);
            anAdult.setTextColor(textColor);
            anStudent.setTextColor(textColor);
            anPro.setTextColor(textColor);
            anCustom.setTextColor(textColor);
            entitiesTitle.setTextColor(textColor);
            sentimentTitle.setTextColor(textColor);
            sentimentExpl.setTextColor(textColor);
            syntaxtTitle.setTextColor(textColor);
            syntaxtExplanation.setTextColor(textColor);


            int secondaryTextColor = Color.parseColor("#CCCCCC");
            totChar.setTextColor(secondaryTextColor);
            totWhite.setTextColor(secondaryTextColor);
            totWords.setTextColor(secondaryTextColor);
            totSent.setTextColor(secondaryTextColor);
            totPar.setTextColor(secondaryTextColor);
            difWords.setTextColor(secondaryTextColor);
            complexity.setTextColor(secondaryTextColor);
            readability.setTextColor(secondaryTextColor);

            adultAverage.setTextColor(secondaryTextColor);
            studentAverage.setTextColor(secondaryTextColor);
            proAverage.setTextColor(secondaryTextColor);
            resultSpeed.setTextColor(secondaryTextColor);
            customSpeedTracker.setTextColor(secondaryTextColor);

            longestWord.setTextColor(secondaryTextColor);
            longestSentence.setTextColor(secondaryTextColor);

            textDisplay.setTextColor(secondaryTextColor);
            charactersCount.setTextColor(secondaryTextColor);
            wordsCount.setTextColor(secondaryTextColor);
            sentencesCount.setTextColor(secondaryTextColor);
            paragraphsCount.setTextColor(secondaryTextColor);

            inputEditText.setHintTextColor(Color.GRAY);
            inputEditText.setTextColor(secondaryTextColor);
        }
    }

    public void speedSetup(int wordNum)
    {
        float min = wordNum/250f;
        adultAverage.setText(" " + round(min, 1) + " " + this.getString(R.string.minutes));

        min = wordNum/300f;
        studentAverage.setText(" " + round(min, 1) + " " + this.getString(R.string.minutes));

        min = wordNum/180f;
        proAverage.setText(" " + round(min, 1) + " " + this.getString(R.string.minutes));
    }

    private void alertDialogsForSaving() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle( "Saving the report" )
                .setIcon(R.drawable.floppy)
                .setMessage("Please choose which format to save as. To save in csv(excel) formate, accept the storage permission:")
                .setNegativeButton("CSV", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        if(isWriteStoragePermissionGranted()) {
                            CSVWriter writer = null;
                            try {
                                String currentDateTime = DateFormat.getDateInstance().format(new Date());
                                String csv = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/TextAnalyzerProAnalysis_" +
                                        currentDateTime + ".csv"); // Here csv file name is MyCsvFile.csv
                                writer = new CSVWriter(new FileWriter(csv));
                                writer.writeAll(data); // data is adding to csv

                                Toast.makeText(MainActivity.this, "CSV created, please check your File Manager", Toast.LENGTH_LONG).show();

                                writer.close();
                            } catch (IOException e) {
                                Toast.makeText(MainActivity.this, "Wasn't able to create CSV", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Need Storage Permission to create csv file", Toast.LENGTH_SHORT).show();
                        }
                        dialoginterface.cancel();
                    }})
                .setPositiveButton("PDF", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        makeAndSharePDF();
                        dialoginterface.cancel();
                    }
                }).show();
    }

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Permissions","Permission is granted");
                return true;
            } else {

                Log.v("Permissions","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("Permissions","Permission is granted");
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.scan_button: {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "3");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "scan");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "button_scan");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                occurences.clear();
                lengthOccurences.clear();
                recommendationLayout.removeAllViews();
                mScrollView.setVisibility(View.VISIBLE);

                String text = inputEditText.getText().toString();

                setScreenUI(text);

                break;
            }
            case R.id.camera_button: {
                recommendationLayout.removeAllViews();
                gridLayout.removeAllViews();
                disclaimer.setVisibility(View.INVISIBLE);
                cameraPermission = true;


                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
                    Toast.makeText(this, getResources().getString(R.string.camera_permission), Toast.LENGTH_SHORT).show();
                    cameraPermission = false;
                }

                if(cameraPermission) {
                    //mScrollView.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.VISIBLE);
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    try {
                        file = Uri.fromFile(getOutputMediaFile());
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
                        //file = Uri.fromFile(getOutputMediaFile());
                        Bundle bundle = new Bundle();
                        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "4");
                        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "camera");
                        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "button_camera");
                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                        startActivityForResult(intent, 100);
                        fob.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#41bf56")));
                        YoYo.with(Techniques.Tada)
                                .duration(700)
                                .repeat(3)
                                .playOn(findViewById(R.id.save_float_button));
                        fob.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialogsForSaving();
                            }
                        });
                    } catch (NullPointerException ne) {
                        Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
            case R.id.upload_button: {
                //FileUploadDialog fileUploadDialog = new FileUploadDialog(this);

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle( "Text Analyzer Pro" )
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage("Select file format")
                        .setNegativeButton("Photo", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                                Bundle bundle = new Bundle();
                                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "5");
                                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "from_gallery");
                                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "button_gallery");
                                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                                fob.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#41bf56")));
                                YoYo.with(Techniques.Tada)
                                        .duration(700)
                                        .repeat(3)
                                        .playOn(findViewById(R.id.save_float_button));
                                fob.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialogsForSaving();
                                    }
                                });
                                dialoginterface.cancel();
                            }})
                        .setPositiveButton("Doc, Docx, Excel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                    // start runtime permission
                                    Boolean hasPermission =( ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                            == PackageManager.PERMISSION_GRANTED);
                                    if (!hasPermission){
                                        Log.e(TAG, "get permision   ");
                                        ActivityCompat.requestPermissions( MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, request_code);
                                    }else {
                                        Log.e(TAG, "get permision-- already granted ");
                                        showFileChooser();
                                    }
                                }else {
                                    //readfile();
                                    showFileChooser();
                                }
                            }
                        }).show();

                //fileUploadDialog.show();




                break;
            }
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            this.startActivityForResult(
                    Intent.createChooser(intent, "Select a file to import"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //Camera Permission
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                cameraPermission = true;
            }
        }
        //File Read Permission
        else if(requestCode == 1) {
            if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                showFileChooser();
            }else {
                Toast.makeText(this, "Need storage permission to read file", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == 2) {
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Log.v("PERMISSIONS","Permission: "+permissions[0]+ "was "+grantResults[0]);
            }else{
            }
        }
    }


    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Text Analyzer");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.d("CameraDemo", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    private void readFile(Uri uri) {
        InputStream inputStream;
        File readFile = null;
        try {
            if (uri.getScheme().equals("readFile")) {
                readFile = new File(uri.toString());
                inputStream = new FileInputStream(readFile);
            } else {
                inputStream = this.getContentResolver().openInputStream(uri);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String text = "";
            String line = "";
            /*while ((line = br.readLine()) != null) {
                // do something with line from readFile
                finalString = finalString + line;
            }*/
            //text = extractText(inputStream).substring(0,3000);
            text = extractText(inputStream);
            //Toast.makeText(this, "Limited to first 3000 characters", Toast.LENGTH_SHORT).show();

            setScreenUI(text);

            br.close();
        } catch (Exception e) {}
    }

    public String extractText(InputStream in) throws Exception {
        XWPFDocument doc = new XWPFDocument(in);
        XWPFWordExtractor ex = new XWPFWordExtractor(doc);
        String text = ex.getText();
        return text;
    }

    private void setScreenUI(String text) {
        occurences.clear();
        lengthOccurences.clear();
        recommendationLayout.removeAllViews();
        mScrollView.setVisibility(View.VISIBLE);

        int charCount = countCharacters(text);
        int whtCount = countWhitespace(text);
        int worCount = countWords(text);
        int senCount = countSentences(text);
        int parCount = countParagraphs(text);

        data.add(new String[]{this.getString(R.string.analysis)});
        data.add(new String[]{this.getString(R.string.total_character_count), charCount+""});
        data.add(new String[]{this.getString(R.string.total_whitespace_count), whtCount+""});
        data.add(new String[]{this.getString(R.string.total_word_count), worCount+""});
        data.add(new String[]{this.getString(R.string.total_sentence_count), senCount+""});
        data.add(new String[]{this.getString(R.string.total_paragraph_count), parCount+""});
        data.add(new String[]{this.getString(R.string.number_different_words), occurences.size() +""});
        data.add(new String[]{this.getString(R.string.word_variety_ratio), round(occurences.size()*1.0f / (worCount*1.0f)*100, 1) + "%"});
        data.add(new String[]{this.getString(R.string.readability), round(readibilityCalculator(text), 1) + ""});
        data.add(new String[]{""});

        totChar.setText(" " + charCount + "");
        totWhite.setText(" " + whtCount + "");
        totWords.setText(" " + worCount + "");
        totSent.setText(" " + senCount +  "");
        totPar.setText(" " + parCount + "");

        difWords.setText(" " + occurences.size() + "");
        readability.setText(" " + round(readibilityCalculator(text), 1) + "");
        complexity.setText(" " + round(occurences.size()*1.0f / (worCount*1.0f)*100, 1) + "%");

        charactersCount.setText(" " + charCount + "");
        wordsCount.setText(" " + worCount + "");
        sentencesCount.setText(" " + senCount + "");
        paragraphsCount.setText(" " + parCount + "");

        addDetailsGrid(worCount);
        addWordLengthGrid(worCount);
        speedSetup(worCount);

        textDisplay.setText(Html.fromHtml(formattedOverview(text)));
        populateSuggestions();

        String currentDateTimeString = DateFormat.getDateInstance().format(new Date());

        List<Archive> archives = db.allArchive();
        Archive newArchive = new Archive(text, currentDateTimeString, archives.size());
        db.addArchive(newArchive);

        updateArchiveListview();

        fob.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#41bf56")));
        YoYo.with(Techniques.Tada)
                .duration(700)
                .repeat(3)
                .playOn(findViewById(R.id.save_float_button));
        fob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogsForSaving();
            }
        });

        if(isSigned) {
            analyzeApi(text);
        }
    }

    Bitmap finalBitmap = null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = null;

        disclaimer.setVisibility(View.INVISIBLE);
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                mProgressBar.setVisibility(View.VISIBLE);
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                ImageEditorDialog imageEditorDialog = new ImageEditorDialog(this, bitmap);
                imageEditorDialog.show();
                imageEditorDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        try {
                            finalBitmap = BitmapFactory.decodeStream(MainActivity.this.openFileInput("text_analyzer_image"));
                            imageView.setImageBitmap(finalBitmap);
                            imageView.setVisibility(View.VISIBLE);

                            if(finalBitmap != null) {
                                firebaseAnalyze();
                            }
                        }catch (FileNotFoundException e) {}
                    }
                });
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if(requestCode == 101) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                readFile(uri);
            }
        }
        else if (requestCode == 100) {       //For camera
            if (resultCode == RESULT_OK) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), file);

                    ImageEditorDialog imageEditorDialog = new ImageEditorDialog(this, bitmap);
                    imageEditorDialog.show();
                    imageEditorDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            try {
                                finalBitmap = BitmapFactory.decodeStream(MainActivity.this.openFileInput("text_analyzer_image"));
                                imageView.setImageBitmap(finalBitmap);
                                imageView.setVisibility(View.VISIBLE);

                                if(finalBitmap != null) {
                                    firebaseAnalyze();
                                }
                            }catch (FileNotFoundException e) {}
                        }
                    });

                } catch (FileNotFoundException e) {
                    Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Capture cancel", Toast.LENGTH_LONG).show();
                mProgressBar.setVisibility(View.INVISIBLE);
                disclaimer.setVisibility(View.VISIBLE);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void firebaseAnalyze() {
        FirebaseApp.initializeApp(this);

        final FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(finalBitmap);

        FirebaseVisionTextRecognizer detector;

        if(!mSharedPreferences.getString("language", "none").equalsIgnoreCase("en")) {
            detector = FirebaseVision.getInstance().getCloudTextRecognizer();
        } else {
            detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        }


        /*FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();*/
        Task<FirebaseVisionText> result =
                detector.processImage(image)
                        .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                                int wordslength = 0;


                                String formattedText = "";
                                String rotatedText = "";

                                for (FirebaseVisionText.TextBlock block: firebaseVisionText.getTextBlocks()) {
                                    String blockText = block.getText();

                                    rotatedText = rotatedText + blockText;
                                    formattedText = formattedText + "<br>" + blockText + "</br>";
                                }



                                int totalWords = countWords(rotatedText);
                                int totalChar = countCharacters(rotatedText);
                                int totalWhite = countWhitespace(rotatedText);
                                int totalSent = countSentences(rotatedText);
                                int totalPar = countParagraphs(rotatedText);

                                addDetailsGrid(totalWords);
                                addWordLengthGrid(totalWords);

                                complexity.setText(" " + round(occurences.size()*1.0f / (totalWords*1.0f)*100, 1) + "%");
                                totChar.setText(" " + totalChar + "");
                                totWhite.setText(" " + totalWhite + "");
                                totWords.setText(" " + totalWords + "");
                                totSent.setText(" " + totalSent +  "");
                                totPar.setText(" " + totalPar + "");

                                difWords.setText(" " + occurences.size() + "");
                                readability.setText(" " + round(readibilityCalculator(rotatedText), 1) + "");

                                charactersCount.setText(" " + totalChar + "");
                                wordsCount.setText(" " + totalWords + "");
                                sentencesCount.setText(" " + totalSent + "");
                                paragraphsCount.setText(" " + totalPar + "");

                                speedSetup(totalWords);
                                mProgressBar.setVisibility(View.INVISIBLE);
                                mScrollView.setVisibility(View.VISIBLE);

                                textDisplay.setText(Html.fromHtml(formattedOverview(formattedText)));

                                wordslength = countWords(rotatedText);
                                if(isSigned) {
                                    analyzeApi(rotatedText);
                                }
                                if(wordslength > 0) {
                                    disclaimer.setVisibility(View.INVISIBLE);
                                    Toast.makeText(MainActivity.this, getResources().getString(R.string.scan_success), Toast.LENGTH_SHORT).show();
                                    String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
                                    List<Archive> archives = db.allArchive();
                                    Archive newArchive = new Archive(textDisplay.getText().toString(), currentDateTimeString, archives.size());
                                    db.addArchive(newArchive);

                                    updateArchiveListview();
                                }
                                mProgressBar.setVisibility(View.INVISIBLE);
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(MainActivity.this, getResources().getString(R.string.scan_fail), Toast.LENGTH_SHORT).show();

                                        disclaimer.setVisibility(View.VISIBLE);
                                        mProgressBar.setVisibility(View.INVISIBLE);
                                    }
                                });
    }

    private String formattedOverview(String text) {
        String formatted = text;
        int count = 0;

        if(mostOccured.size() >= 5) {
            count = 5;
        } else {
            count = mostOccured.size();
        }

        for(int i = 0; i < count; i++) {
            formatted = formatted
                    .replace(mostOccured.get(i).getWord(), "<font color='#4cd964'><b>" + mostOccured.get(i).getWord() + "</b></font>")
                    .replace("\n", "<br></br>");
        }

        return formatted;
    }

    private String formattedOverview(String text, int sizeOfList, String replWord) {
        String formatted = text;

        for(int i = 0; i < sizeOfList; i++) {
            formatted = formatted
                    .replace(replWord, "<font color='#4191C7'><b>" + replWord + "</b></font>")
                    .replace("\n", "<br></br>");
        }

        return formatted;
    }

    private String highlightWords(String text, String searchedWord) {
        String formatted = text;

        formatted = formatted
                .replace(searchedWord, "<font color='#0d47a1'><b>" + searchedWord + "</b></font>")
                .replace("\n", "<br></br>");

        return formatted;
    }

    int spinnerTrackerInt = 0;
    public void volleyStringRequst(String url, final int counter){
        final String REQUEST_TAG = "com.graspery.www.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] lines = response.split(System.getProperty("line.separator"));
                for(String line : lines) {
                    if(line.contains("syn") && line.contains("adverb")) {
                        mostOccured.get(counter).pushSynonym(line.substring(11));
                    }
                    else if(line.contains("syn") && line.contains("adjective")) {
                        mostOccured.get(counter).pushSynonym(line.substring(14));
                    }
                    else if(line.contains("syn") && (line.contains("noun") || line.contains("verb"))) {
                        mostOccured.get(counter).pushSynonym(line.substring(9));
                    }
                }

                String recommendation = "";
                if(mostOccured.get(counter).hasSynonyms()) {
                    recommendation = "<b>" + mostOccured.get(counter).getWord() + "</b><br>" ;
                    for (String word : mostOccured.get(counter).getSynonyms()) {
                        recommendation = recommendation + word + ", ";
                    }

                recommendation = recommendation + "</br>";
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                TextView tv = new TextView(MainActivity.this);
                if(mSharedPreferences.getString("theme", "light").equalsIgnoreCase("dark") ||
                        mSharedPreferences.getString("theme", "light").equalsIgnoreCase("super_dark")) {
                    tv.setTextColor(getResources().getColor(R.color.dark_theme_text_color));
                }
                tv.setLayoutParams(lparams);
                tv.setText(Html.fromHtml(recommendation));
                recommendationLayout.addView(tv);

                ArrayList<Word> dropDownList = new ArrayList<>();
                for(Word word : mostOccured) {
                    boolean flag = false;
                    for(int i = 0; i < dropDownList.size(); i++) {
                        if(dropDownList.get(i).getWord().equals(word.getWord())) {
                            flag = true;
                        }
                    }
                    if(word.hasSynonyms() && !flag) {
                        dropDownList.add(word);
                    }
                }

                LinearLayout suggestionLinear = findViewById(R.id.suggestion_linear_holder);
                suggestionLinear.removeAllViews();
                final ArrayList<Word> dropDownListCopy = dropDownList;

                for(int i = 0; i < dropDownListCopy.size(); i++) {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, 100);
                    lp.weight = 1;
                    LinearLayout rowLayout = new LinearLayout(MainActivity.this);
                    rowLayout.setOrientation(LinearLayout.HORIZONTAL);
                    final TextView originalWord = new TextView(MainActivity.this);
                    originalWord.setText(dropDownListCopy.get(i).getWord());
                    originalWord.setLayoutParams(lp);

                    ImageView arrowImage = new ImageView(MainActivity.this);
                    arrowImage.setImageDrawable(getDrawable(R.drawable.arrow));
                    //arrowImage.setLayoutParams(lp);

                    Spinner spinner = new Spinner(MainActivity.this);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                            android.R.layout.simple_spinner_item, dropDownListCopy.get(i).getSynonyms());
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setLayoutParams(lp);
                    final int posWord = i;
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(spinnerTrackerInt >= dropDownListCopy.size()) {
                                String prevWord = originalWord.getText().toString();
                                originalWord.setText(dropDownListCopy.get(posWord).getSynonyms().get(position));
                                Toast.makeText(MainActivity.this, prevWord + " to " +
                                        dropDownListCopy.get(posWord).getSynonyms().get(position), Toast.LENGTH_SHORT).show();

                                String text = textDisplay.getText().toString();
                                String formatted = text.replaceAll(prevWord, dropDownListCopy.get(posWord).getSynonyms().get(position));
                                textDisplay.setText(Html.fromHtml(formattedOverview(formatted, dropDownListCopy.size(), dropDownListCopy.get(posWord).getSynonyms().get(position))));
                            } else {
                                spinnerTrackerInt++;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    spinner.setAdapter(adapter);

                    rowLayout.addView(originalWord);
                    rowLayout.addView(arrowImage);
                    rowLayout.addView(spinner);
                    suggestionLinear.addView(rowLayout);
                }
                } else {
                    spinnerTrackerInt--;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("THESAURUS: ", "error");
            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }

    public void populateSuggestions() {
        Typeface typefaceBold = Typeface.createFromAsset(getAssets(), "fonts/helvetica_heavy.otf");
        TextView title_recom = findViewById(R.id.recommendation_title);
        title_recom.setVisibility(View.VISIBLE);
        title_recom.setTypeface(typefaceBold);

        for(int i = 0; i < mostOccured.size(); i++) {
            volleyStringRequst(URL_KEY + mostOccured.get(i).getWord() + "/", i);

            //And save them into Firebase Databse
            // Write a message to the database
            //FirebaseDatabase database = FirebaseDatabase.getInstance();
            //DatabaseReference myRef = database.getReference("words").push();
            //myRef.setValue(mostOccured.get(i).getWord());
        }
    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public void addWordLengthGrid(int totalWords){
        wordCountGrid.removeAllViews();
        //occurences.clear();

        wordCountGrid.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        wordCountGrid.setColumnCount(30);
        wordCountGrid.setRowCount(30);
        TextView titleText;
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/helvetica.otf");

        data.add(new String[]{"",this.getString(R.string.word_length), ""});
        data.add(new String[]{this.getString(R.string.word_length),this.getString(R.string.word_count), this.getString(R.string.frequency)});
        titleText = new TextView(this);
        titleText.setText(Html.fromHtml("<b>" + this.getString(R.string.word_length) + "</b>"));
        titleText.setTypeface(typeface);
        if(mSharedPreferences.getString("theme", "light").equalsIgnoreCase("dark") ||
                mSharedPreferences.getString("theme", "light").equalsIgnoreCase("super_dark")) {
            titleText.setTextColor(getResources().getColor(R.color.dark_theme_text_color));
        }

        wordCountGrid.addView(titleText, 0);
        GridLayout.LayoutParams paramA = new GridLayout.LayoutParams();
        paramA.height = GridLayout.LayoutParams.WRAP_CONTENT;
        paramA.width = GridLayout.LayoutParams.WRAP_CONTENT;
        paramA.rightMargin = 2;
        paramA.leftMargin = 50;
        paramA.topMargin = 2;
        paramA.setGravity(Gravity.CENTER);
        paramA.columnSpec = GridLayout.spec(0);
        paramA.rowSpec = GridLayout.spec(0);
        titleText.setLayoutParams (paramA);
        titleText = new TextView(this);
        titleText.setText(Html.fromHtml("<b>" + this.getString(R.string.word_count) + "</b>"));
        titleText.setTypeface(typeface);
        if(mSharedPreferences.getString("theme", "light").equalsIgnoreCase("dark") ||
                mSharedPreferences.getString("theme", "light").equalsIgnoreCase("super_dark")) {
            titleText.setTextColor(getResources().getColor(R.color.dark_theme_text_color));
        }
        wordCountGrid.addView(titleText, 0);
        GridLayout.LayoutParams paramB = new GridLayout.LayoutParams();
        paramB.height = GridLayout.LayoutParams.WRAP_CONTENT;
        paramB.width = GridLayout.LayoutParams.WRAP_CONTENT;
        paramB.rightMargin = 2;
        paramB.leftMargin = 50;
        paramB.topMargin = 2;
        paramB.setGravity(Gravity.CENTER);
        paramB.columnSpec = GridLayout.spec(1);
        paramB.rowSpec = GridLayout.spec(0);
        titleText.setLayoutParams (paramB);
        titleText = new TextView(this);
        titleText.setText(Html.fromHtml("<b>" + this.getString(R.string.frequency) + "</b>"));
        titleText.setTypeface(typeface);
        if(mSharedPreferences.getString("theme", "light").equalsIgnoreCase("dark") ||
                mSharedPreferences.getString("theme", "light").equalsIgnoreCase("super_dark")) {
            titleText.setTextColor(getResources().getColor(R.color.dark_theme_text_color));
        }
        wordCountGrid.addView(titleText, 0);
        GridLayout.LayoutParams paramC = new GridLayout.LayoutParams();
        paramC.height = GridLayout.LayoutParams.WRAP_CONTENT;
        paramC.width = GridLayout.LayoutParams.WRAP_CONTENT;
        paramC.rightMargin = 2;
        paramC.leftMargin = 50;
        paramC.topMargin = 2;
        paramC.setGravity(Gravity.CENTER);
        paramC.columnSpec = GridLayout.spec(2);
        paramC.rowSpec = GridLayout.spec(0);
        titleText.setLayoutParams (paramC);

        int secondaryTextColor = Color.parseColor("#CCCCCC");
        int counter = 0;
        int c = 0;
        int r = 1;
        for(int i = 1; i < 20; i++) {
            if(lengthOccurences.containsKey(i)) {
                titleText = new TextView(this);
                titleText.setText(i + "");
                titleText.setTypeface(typeface);
                if(mSharedPreferences.getString("theme", "light").equalsIgnoreCase("dark") ||
                        mSharedPreferences.getString("theme", "light").equalsIgnoreCase("super_dark")) {
                    titleText.setTextColor(secondaryTextColor);
                }
                wordCountGrid.addView(titleText, counter);

                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                param.rightMargin = 2;
                param.leftMargin = 50;
                param.topMargin = 2;
                param.setGravity(Gravity.CENTER);
                param.columnSpec = GridLayout.spec(c);
                param.rowSpec = GridLayout.spec(r);
                titleText.setLayoutParams(param);

                c++;

                titleText = new TextView(this);
                titleText.setText(lengthOccurences.get(i) + "");
                titleText.setTypeface(typeface);
                if(mSharedPreferences.getString("theme", "light").equalsIgnoreCase("dark") ||
                        mSharedPreferences.getString("theme", "light").equalsIgnoreCase("super_dark")) {
                    titleText.setTextColor(secondaryTextColor);
                }
                wordCountGrid.addView(titleText, counter);
                GridLayout.LayoutParams param1 = new GridLayout.LayoutParams();
                param1.height = GridLayout.LayoutParams.WRAP_CONTENT;
                param1.width = GridLayout.LayoutParams.WRAP_CONTENT;
                param1.rightMargin = 50;
                param1.leftMargin = 50;
                param1.topMargin = 2;
                param1.setGravity(Gravity.CENTER);
                param1.columnSpec = GridLayout.spec(c);
                param1.rowSpec = GridLayout.spec(r);
                titleText.setLayoutParams(param1);

                c++;

                titleText = new TextView(this);
                titleText.setText(round((lengthOccurences.get(i) * 1f / totalWords) * 100, 1) + "%");
                //titleText.setText(lengthOccurences.get(i) + " / " + totalWords);
                titleText.setTypeface(typeface);
                if(mSharedPreferences.getString("theme", "light").equalsIgnoreCase("dark") ||
                        mSharedPreferences.getString("theme", "light").equalsIgnoreCase("super_dark")) {
                    titleText.setTextColor(secondaryTextColor);
                }
                wordCountGrid.addView(titleText, counter);
                GridLayout.LayoutParams param2 = new GridLayout.LayoutParams();
                param2.height = GridLayout.LayoutParams.WRAP_CONTENT;
                param2.width = GridLayout.LayoutParams.WRAP_CONTENT;
                param2.rightMargin = 50;
                param2.leftMargin = 50;
                param2.topMargin = 2;
                param2.setGravity(Gravity.CENTER);
                param2.columnSpec = GridLayout.spec(c);
                param2.rowSpec = GridLayout.spec(r);
                titleText.setLayoutParams(param2);

                c = 0;
                r++;
                counter++;
                data.add(new String[]{i + "",lengthOccurences.get(i) + "", round((lengthOccurences.get(i) * 1f / totalWords) * 100, 1) + "%"});
            }
        }

        chart = findViewById(R.id.chart1);
        chart.setViewPortOffsets(50, 0, 0, 0);
        //chart.setBackgroundColor(Color.rgb(104, 241, 175));
        // no description text
        chart.getDescription().setEnabled(false);
        // enable touch gestures
        chart.setTouchEnabled(false);
        // enable scaling and dragging
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);
        chart.setMaxHighlightDistance(300);

        XAxis x = chart.getXAxis();
        x.setTypeface(typeface);
        if(mSharedPreferences.getString("theme","light").equalsIgnoreCase("dark")) {
            x.setTextColor(getResources().getColor(R.color.dark_theme_text_color));
        }
        x.setLabelCount(20, false);
        x.setAxisMinimum(0);
        x.setEnabled(true);
        x.setDrawGridLines(false);
        x.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        x.setDrawAxisLine(false);
        x.setAxisLineWidth(0f);
        x.setAxisLineColor(Color.RED);


        YAxis y = chart.getAxisLeft();
        y.setTypeface(typeface);
        if(mSharedPreferences.getString("theme","light").equalsIgnoreCase("dark")) {
            y.setTextColor(getResources().getColor(R.color.dark_theme_text_color));
        }
        y.setLabelCount(15, false);
        y.setAxisMinimum(0f);
        //y.setTextColor(Color.WHITE);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(false);

        y.setAxisLineWidth(0f);
        y.setDrawAxisLine(false);
        y.setAxisLineColor(Color.RED);

        chart.getAxisRight().setEnabled(false);
        chart.getLegend().setEnabled(false);
        //chart.animateXY(2500, 4000);

        // don't forget to refresh the drawing
        chart.invalidate();

        setData();
    }

    private void setData() {

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            if(lengthOccurences.containsKey(i)) {
                int val = lengthOccurences.get(i);
                values.add(new Entry(i, val));
            } else {
                values.add(new Entry(i, 0));
            }
        }

        LineDataSet set1;

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/helvetica.otf");
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, getResources().getString(R.string.word_length));
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(true);
            set1.setLineWidth(2f);
            set1.setCircleRadius(2f);
            set1.setCircleColor(Color.parseColor("#4cd964"));
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.parseColor("#4cd964"));
            set1.setFillColor(Color.parseColor("#4cd964"));
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // create a data object with the data sets
            LineData data = new LineData(set1);
            data.setValueTypeface(typeface);

            if(!mSharedPreferences.getString("theme", "light").equalsIgnoreCase("light")) {
                data.setValueTextColor(getResources().getColor(R.color.primaryTextColor));
            }

            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            chart.setData(data);
        }
    }

    public void addDetailsGrid(int totalWords){
        gridLayout.removeAllViews();
        //occurences.clear();

        gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        gridLayout.setColumnCount(30);
        gridLayout.setRowCount(30);
        TextView titleText;
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/helvetica.otf");

        Map<String, Integer> sortedMapAsc = sortByComparator(occurences, false);
        Iterator it = sortedMapAsc.entrySet().iterator();

        //To save in CSV format
        data.add(new String[]{"", this.getString(R.string.occurence_title), ""});
        data.add(new String[]{this.getString(R.string.word), this.getString(R.string.occurrences), this.getString(R.string.frequency)});

        titleText = new TextView(this);
        titleText.setText(Html.fromHtml("<b>" + this.getString(R.string.word) + "</b>"));
        titleText.setTypeface(typeface);
        if(mSharedPreferences.getString("theme", "light").equalsIgnoreCase("dark") ||
                mSharedPreferences.getString("theme", "light").equalsIgnoreCase("super_dark")) {
            titleText.setTextColor(getResources().getColor(R.color.dark_theme_text_color));
        }
        gridLayout.addView(titleText, 0);
        GridLayout.LayoutParams paramA = new GridLayout.LayoutParams();
        paramA.height = GridLayout.LayoutParams.WRAP_CONTENT;
        paramA.width = GridLayout.LayoutParams.WRAP_CONTENT;
        paramA.rightMargin = 2;
        paramA.leftMargin = 50;
        paramA.topMargin = 2;
        paramA.setGravity(Gravity.CENTER);
        paramA.columnSpec = GridLayout.spec(0);
        paramA.rowSpec = GridLayout.spec(0);
        titleText.setLayoutParams (paramA);
        titleText = new TextView(this);
        titleText.setText(Html.fromHtml("<b>" + this.getString(R.string.occurrences) + "</b>"));
        titleText.setTypeface(typeface);
        if(mSharedPreferences.getString("theme", "light").equalsIgnoreCase("dark") ||
                mSharedPreferences.getString("theme", "light").equalsIgnoreCase("super_dark")) {
            titleText.setTextColor(getResources().getColor(R.color.dark_theme_text_color));
        }
        gridLayout.addView(titleText, 0);
        GridLayout.LayoutParams paramB = new GridLayout.LayoutParams();
        paramB.height = GridLayout.LayoutParams.WRAP_CONTENT;
        paramB.width = GridLayout.LayoutParams.WRAP_CONTENT;
        paramB.rightMargin = 2;
        paramB.leftMargin = 50;
        paramB.topMargin = 2;
        paramB.setGravity(Gravity.CENTER);
        paramB.columnSpec = GridLayout.spec(1);
        paramB.rowSpec = GridLayout.spec(0);
        titleText.setLayoutParams (paramB);
        titleText = new TextView(this);
        titleText.setText(Html.fromHtml("<b>" + this.getString(R.string.frequency) + "</b>"));
        titleText.setTypeface(typeface);
        if(mSharedPreferences.getString("theme", "light").equalsIgnoreCase("dark") ||
                mSharedPreferences.getString("theme", "light").equalsIgnoreCase("super_dark")) {
            titleText.setTextColor(getResources().getColor(R.color.dark_theme_text_color));
        }
        gridLayout.addView(titleText, 0);
        GridLayout.LayoutParams paramC = new GridLayout.LayoutParams();
        paramC.height = GridLayout.LayoutParams.WRAP_CONTENT;
        paramC.width = GridLayout.LayoutParams.WRAP_CONTENT;
        paramC.rightMargin = 2;
        paramC.leftMargin = 50;
        paramC.topMargin = 2;
        paramC.setGravity(Gravity.CENTER);
        paramC.columnSpec = GridLayout.spec(2);
        paramC.rowSpec = GridLayout.spec(0);
        titleText.setLayoutParams (paramC);

        int secondaryTextColor = Color.parseColor("#CCCCCC");
        int counter = 0;
        int c = 0;
        int r = 1;

        while (it.hasNext() && counter < 20) {
            Map.Entry pair = (Map.Entry)it.next();

            titleText = new TextView(this);
            titleText.setText(pair.getKey()  + "");
            titleText.setTypeface(typeface);
            if(mSharedPreferences.getString("theme", "light").equalsIgnoreCase("dark") ||
                    mSharedPreferences.getString("theme", "light").equalsIgnoreCase("super_dark")) {
                titleText.setTextColor(secondaryTextColor);
            }

            final String pKey = pair.getKey().toString();
            titleText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("text", pKey);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(MainActivity.this, pKey, Toast.LENGTH_SHORT).show();
                }
            });

            gridLayout.addView(titleText, counter);

            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.height = GridLayout.LayoutParams.WRAP_CONTENT;
            param.width = GridLayout.LayoutParams.WRAP_CONTENT;
            param.rightMargin = 2;
            param.leftMargin = 50;
            param.topMargin = 2;
            param.setGravity(Gravity.CENTER);
            param.columnSpec = GridLayout.spec(c);
            param.rowSpec = GridLayout.spec(r);
            titleText.setLayoutParams (param);

            c++;

            titleText = new TextView(this);
            titleText.setText(pair.getValue().toString());
            titleText.setTypeface(typeface);
            if(mSharedPreferences.getString("theme", "light").equalsIgnoreCase("dark") ||
                    mSharedPreferences.getString("theme", "light").equalsIgnoreCase("super_dark")) {
                titleText.setTextColor(secondaryTextColor);
            }
            gridLayout.addView(titleText, counter);
            GridLayout.LayoutParams param1 = new GridLayout.LayoutParams();
            param1.height = GridLayout.LayoutParams.WRAP_CONTENT;
            param1.width = GridLayout.LayoutParams.WRAP_CONTENT;
            param1.rightMargin = 50;
            param1.leftMargin = 50;
            param1.topMargin = 2;
            param1.setGravity(Gravity.CENTER);
            param1.columnSpec = GridLayout.spec(c);
            param1.rowSpec = GridLayout.spec(r);
            titleText.setLayoutParams (param1);

            c++;

            titleText = new TextView(this);
            titleText.setText( round((Integer.parseInt(pair.getValue().toString())*1f/totalWords)*100, 1) + "%");
            titleText.setTypeface(typeface);
            if(mSharedPreferences.getString("theme", "light").equalsIgnoreCase("dark") ||
                    mSharedPreferences.getString("theme", "light").equalsIgnoreCase("super_dark")) {
                titleText.setTextColor(secondaryTextColor);
            }
            gridLayout.addView(titleText, counter);
            GridLayout.LayoutParams param2 = new GridLayout.LayoutParams();
            param2.height = GridLayout.LayoutParams.WRAP_CONTENT;
            param2.width = GridLayout.LayoutParams.WRAP_CONTENT;
            param2.rightMargin = 50;
            param2.leftMargin = 50;
            param2.topMargin = 2;
            param2.setGravity(Gravity.CENTER);
            param2.columnSpec = GridLayout.spec(c);
            param2.rowSpec = GridLayout.spec(r);
            titleText.setLayoutParams (param2);

            c = 0;
            r++;
            it.remove(); // avoids a ConcurrentModificationException
            counter++;

            data.add(new String[]{pair.getKey()  + "", pair.getValue().toString(), round((Integer.parseInt(pair.getValue().toString())*1f/totalWords)*100, 1) + "%"});
        }

    }

    private Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
    {

        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        int counter = 0;
        for (Map.Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
            if(entry.getKey().length() > 3 && counter < 5) {
                mostOccured.add(new Word(entry.getKey()));
                counter++;
            }
        }

        //populateSuggestions();

        return sortedMap;
    }

    public float readibilityCalculator(String text) {
        if(countCharacters(text) > 0) {
            int words = countWords(text);
            int sentences = countSentences(text);

            if(sentences == 0) {
                sentences = 1;
            }

            float ASL = words / sentences;

            int sylbWords = 0;

            for (int i = 0; i < eachWord.size(); i++) {
                int strikes = 0;
                for (int j = 0; j < eachWord.get(i).length(); j++) {
                    char x = eachWord.get(i).charAt(j);
                    if (x == 'a' || x == 'e' || x == 'i' || x == 'o' || x == 'u' || x == 'y') {
                        strikes++;
                    }
                }
                try {
                    if (strikes >= 3) {
                        sylbWords++;
                    }
                } catch (ArithmeticException ae) {
                    //....
                }
            }

            if(sylbWords == 0) {
                sylbWords = 1;
            }


            float PHW = 0;
            try {
                PHW = sylbWords / words;
            } catch (ArithmeticException ae){}

            float result = (ASL + PHW) * 0.4f;

            return result;
        }

        return 0;
    }

    private int countCharacters(String s) {
        String line;
        Scanner scanner = new Scanner(s);

        int characterCount = 0;

        // Reading line by line from the
        // file until a null is returned
        while(scanner.hasNextLine())
        {
            line = scanner.nextLine();
            if(!(line.equals("")))
            {

                characterCount += line.length();
            }
        }

        return characterCount;
    }

    private int countWords(String s) {
        String line;
        Scanner scanner = new Scanner(s);
        // Initializing counters
        int countWord = 0;
        String word = "";
        occurences.clear();
        lengthOccurences.clear();
        mostOccured.clear();
        /*int counter = 0;*/
        // Reading line by line from the
        // file until a null is returned
        while(scanner.hasNextLine())
        {
            line = scanner.nextLine();

            if(!(line.equals("")))
            {
                // \\s+ is the space delimiter in java
                String[] wordList = line.split("\\s+");
                //countWord += wordList.length;

                for(String singleWord : wordList) {
                    //String cleanedWord = singleWord.replaceAll("\\W", "");
                    String cleanedWord = singleWord.replaceAll("[.,?!():;/\"]", "");

                    //String strippedInput = input.replaceAll("\\W", "");
                    if(cleanedWord.length() >= minimumWordLength) {
                        countWord++;
                    }

                    eachWord.add(cleanedWord.toLowerCase());

                    if(word.length() < cleanedWord.length()) {
                        word = cleanedWord;
                    }

                    if(occurences.containsKey(cleanedWord) && cleanedWord.length() > 3){
                        try {
                            occurences.put(cleanedWord, occurences.get(cleanedWord) + 1);
                        } catch (NullPointerException e) {}
                    }
                    else
                    {
                        occurences.put(cleanedWord, 1);
                    }
                    if(lengthOccurences.containsKey(cleanedWord.length()) && cleanedWord.length() > 1){
                        try {
                            lengthOccurences.put(cleanedWord.length(), lengthOccurences.get(cleanedWord.length()) + 1);
                        } catch (NullPointerException e) {}
                    }
                    else
                    {
                        lengthOccurences.put(cleanedWord.length(), 1);
                    }
                }
            }
        }
        longestWord.setText(Html.fromHtml("<b>" + this.getString(R.string.longest_word) + "</b><br>" + word + "</br>"));

        numOfcharacters = countWord;
        return countWord;
    }

    private int countSentences(String s) {
        String line;
        Scanner scanner = new Scanner(s);

        int sentenceCount = 0;
        String sentence = "";

        // Reading line by line from the
        // file until a null is returned
        while(scanner.hasNextLine())
        {
            line = scanner.nextLine().trim();
            if(!(line.equals("")))
            {
                // [!?.:]+ is the sentence delimiter in java
                String[] sentenceList = line.split("[!?.:]+");
                for(String singelSentence : sentenceList) {
                    if(!singelSentence.matches(".*[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz].*")) {
                        sentenceCount--;
                    }
                    if(sentence.length() < singelSentence.length()) {
                        sentence = singelSentence;
                    }
                }

                sentenceCount += sentenceList.length;
            }
        }

        longestSentence.setText(Html.fromHtml("<b>" + this.getString(R.string.longest_sentence) + "</b><br>" + sentence + "</br>"));
        return sentenceCount;
    }

    private int countParagraphs(String s) {
        String line;
        String previousLine = "good";
        Scanner scanner = new Scanner(s);
        int paragraphCount = 0;

        // Reading line by line from the
        // file until a null is returned
        while(scanner.hasNextLine())
        {
            line = scanner.nextLine();
            if(line.equals("") && !previousLine.equals(""))
            {
                paragraphCount++;
            }
            previousLine = line;
        }


        return paragraphCount;
    }

    private int countWhitespace(String s) {
        String line;
        Scanner scanner = new Scanner(s);

        int whitespaceCount = 0;
        int countWord = 0;

        // Reading line by line from the
        // file until a null is returned
        while(scanner.hasNextLine())
        {
            line = scanner.nextLine();

            if(!(line.equals("")))
            {
                // \\s+ is the space delimiter in java
                String[] wordList = line.split("\\s+");

                countWord += wordList.length;
                whitespaceCount += countWord -1;
            }
        }

        return whitespaceCount;
    }

    public static void startAnimation(final Activity activity) {
        final int start = Color.parseColor("#0B3042");
        final int end = Color.parseColor("#9B3F42");
        //final int mid = Color.parseColor("#9B3F42");
        //final int end = Color.parseColor("#0B3F42");
        final int mid = Color.parseColor("#0B3F42");
        //final int end = Color.TRANSPARENT;

/*
        android:centerColor="#9B3F42"
        android:endColor="#0B3F42"
        android:startColor="#0B3042"
*/

        final ArgbEvaluator evaluator = new ArgbEvaluator();
        View preloader = activity.findViewById(R.id.gradient_preloader_view);
        preloader.setVisibility(View.VISIBLE);
        final GradientDrawable gradient = (GradientDrawable) preloader.getBackground();

        ValueAnimator animator = TimeAnimator.ofFloat(0.0f, 1.0f);
        animator.setDuration(1500);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float fraction = valueAnimator.getAnimatedFraction();
                int newStrat = (int) evaluator.evaluate(fraction, start, end);
                int newMid = (int) evaluator.evaluate(fraction, mid, start);
                int newEnd = (int) evaluator.evaluate(fraction, end, mid);
                int[] newArray = {newStrat, newMid, newEnd};
                gradient.setColors(newArray);
            }
        });

        animator.start();
    }

    public static void stopAnimation(final int view, final Activity activity){
        ObjectAnimator.ofFloat(activity.findViewById(view), "alpha", 0f).setDuration(125).start();
    }

    /** PDF Gen should run in own thread to not slow the GUI */
    public void makeAndSharePDF() {
        mProgressBar.setVisibility(View.VISIBLE);
        new CountDownTimer(1500, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                new Thread(MainActivity.this).start();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        }.start();

    }

    public void run() {
        PrintAttributes printAttrs = new PrintAttributes.Builder().
                setColorMode(PrintAttributes.COLOR_MODE_COLOR).
                setMediaSize(PrintAttributes.MediaSize.NA_LETTER).
                setResolution(new PrintAttributes.Resolution("zooey", PRINT_SERVICE, 300, 300)).
                setMinMargins(PrintAttributes.Margins.NO_MARGINS).
                build();
        PdfDocument document = new PrintedPdfDocument(this, printAttrs);

        View content = findViewById(R.id.linear_analysis);
        View contentSpeed = findViewById(R.id.reading_speed_grid);
        View content2 = findViewById(R.id.sentence_and_speed_linear);
        View content3 = findViewById(R.id.top_occured_linear);
        View content4 = findViewById(R.id.word_length_linear);
        View content5 = findViewById(R.id.chart_linear);

        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(2480, 3508, 1).setContentRect(new Rect(150,150,2380,3408)).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        content.draw(page.getCanvas());

        page.getCanvas().translate(1000, 150);
        content2.draw(page.getCanvas());

        page.getCanvas().translate(-1100, content.getHeight() - 50);
        content3.draw(page.getCanvas());

        page.getCanvas().translate(1100, 0);
        content4.draw(page.getCanvas());

        /*page.getCanvas().translate(0, content4.getHeight()-50);
        content5.draw(page.getCanvas());*/

        page.getCanvas().translate(-1020, content3.getHeight() + 50);
        contentSpeed.draw(page.getCanvas());

        document.finishPage(page);

        /* Find the Letter Size Height depending on the Letter Size Ratio and given Page Width */
        View content6 = findViewById(R.id.text_display);
        //int letterSizeHeight = (int)((float)(11*content6.getWidth())/8.5);

        //int numberOfPages = (content6.getHeight()/letterSizeHeight) + 1;
        int numberOfPages = (content6.getHeight()/3508) + 1;
        int lastPage = 3;

        for (int i = 0; i < numberOfPages; i++) {

            //int webMarginTop = i*letterSizeHeight;
            int webMarginTop = i*3258;

            PdfDocument.PageInfo pageInfo6 = new PdfDocument.PageInfo.Builder(2480, 3508, i+5).setContentRect(new Rect(600,150,2380,3408)).create();
            PdfDocument.Page page6 = document.startPage(pageInfo6);

            /* Scale Canvas */
            page6.getCanvas().translate(0, -webMarginTop);
            content6.draw(page6.getCanvas());

            lastPage = i+5;
            document.finishPage(page6);
        }


        ImageView content7 = findViewById(R.id.image_view);
        if (content7.getDrawable() != null) {
            PdfDocument.PageInfo pageInfo7 = new PdfDocument.PageInfo.Builder(2480, 3508, lastPage+1).setContentRect(new Rect(500,500,2380,3408)).create();
            PdfDocument.Page page7 = document.startPage(pageInfo7);
            content7.draw(page7.getCanvas());
            document.finishPage(page7);
        }

        // Here you could add more pages in a longer doc app, but you'd have
        // to handle page-breaking yourself in e.g., write your own word processor...

        // Now write the PDF document to a file; it actually needs to be a file
        // since the Share mechanism can't accept a byte[]. though it can
        // accept a String/CharSequence. Meh.
        try {
            File pdfDirPath = new File(getFilesDir(), "pdfs");
            pdfDirPath.mkdirs();
            File file = new File(pdfDirPath, "analysis.pdf");
            Uri contentUri = FileProvider.getUriForFile(this, "com.example.fileprovider", file);
            os = new FileOutputStream(file);
            document.writeTo(os);
            document.close();
            os.close();

            shareDocument(contentUri);
        } catch (IOException e) {
            throw new RuntimeException("Error generating file", e);
        }
    }

    private void shareDocument(Uri uri) {
        mShareIntent = new Intent();
        mShareIntent.setAction(Intent.ACTION_SEND);
        mShareIntent.setType("application/pdf");
        // Assuming it may go via eMail:
        String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
        mShareIntent.putExtra(Intent.EXTRA_SUBJECT, "Document Analysis - " + currentDateTimeString);
        // Attach the PDf as a Uri, since Android can't take it as bytes yet.
        mShareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(mShareIntent);
        return;
    }
}
