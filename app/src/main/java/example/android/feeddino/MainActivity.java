package example.android.feeddino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView stageNumber, appleNumber, meetDynoText, feedDynoText, feedBtnText;
    ImageView dynoImage, feedBtnImage;
    LinearLayout feedBtn;
    int fedAppleNum;
    private SoundPool soundPool;
    int eatSound, stageSound, winSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stageNumber = findViewById(R.id.stageNum);
        appleNumber = findViewById(R.id.appleNum);
        meetDynoText = findViewById(R.id.meetDynoText);
        feedDynoText = findViewById(R.id.feedRuleText);
        feedBtnText = findViewById(R.id.feedBtnText);
        dynoImage = findViewById(R.id.dynoImage);
        feedBtnImage = findViewById(R.id.feedBtnImage);
        feedBtn = findViewById(R.id.feedBtn);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
        }

        eatSound = soundPool.load(this, R.raw.eat_sound, 1);
        stageSound = soundPool.load(this, R.raw.stage_change_sound, 1);
        winSound = soundPool.load(this, R.raw.game_win_sound, 1);

        dynoImage.setImageResource(R.drawable.stage_one_dyno);

        feedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fedAppleNum++;
                if (fedAppleNum > 20){
                    fedAppleNum = 0;
                    appleNumber.setText("Total apples eaten : " + fedAppleNum);
                    changeDino(fedAppleNum);
                }else {
                    appleNumber.setText("Total apples eaten : " + fedAppleNum);
                    changeDino(fedAppleNum);
                }

            }
        });




    }

    public void changeDino(int fedAppleNum){

        Animation zoomIn = AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoom_in);


        switch (fedAppleNum){

            case 0:
                soundPool.play(stageSound, 1, 1, 0, 0, 1);
                viewAnimatedChange(MainActivity.this, stageNumber, R.string.stage1);
                viewAnimatedChange(MainActivity.this, meetDynoText, R.string.meet_dyno_your_new_pet);
                viewAnimatedChange(MainActivity.this, feedDynoText, R.string.feed_dyno_info);
                feedBtnText.setText(R.string.feed_apple);
                feedBtnImage.setImageResource(R.drawable.red_apple);
                feedBtn.setBackground(getDrawable(R.drawable.feed_button_background));
                viewAnimatedChange(MainActivity.this,dynoImage,R.drawable.stage_one_dyno);
                break;
            case 5:
                soundPool.play(stageSound, 1, 1, 0, 0, 1);
                viewAnimatedChange(MainActivity.this, stageNumber, R.string.stage2);
                viewAnimatedChange(MainActivity.this,dynoImage,R.drawable.stage_two_dyno);
                break;
            case 10:
                soundPool.play(stageSound, 1, 1, 0, 0, 1);
                viewAnimatedChange(MainActivity.this, stageNumber, R.string.stage3);
                viewAnimatedChange(MainActivity.this,dynoImage,R.drawable.stage_three_dyno);
                break;
            case 15:
                soundPool.play(stageSound, 1, 1, 0, 0, 1);
                viewAnimatedChange(MainActivity.this, stageNumber, R.string.stage4);
                viewAnimatedChange(MainActivity.this,dynoImage,R.drawable.stage_four_dyno);
                break;
            case 20:
                soundPool.play(winSound, 1, 1, 0, 0, 1);
                viewAnimatedChange(MainActivity.this, stageNumber, R.string.stage5);
                viewAnimatedChange(MainActivity.this, feedDynoText, R.string.congrats_text);
                viewAnimatedChange(MainActivity.this, meetDynoText, R.string.you_won);
                feedBtnText.setText(R.string.play_again);
                feedBtnImage.setImageResource(R.drawable.video_game);
                feedBtn.setBackground(getDrawable(R.drawable.game_restart_btn_bg));
                viewAnimatedChange(MainActivity.this,dynoImage,R.drawable.stage_five_dyno);
                break;
            default:
                soundPool.play(eatSound, 1, 1, 0, 0, 1);
                viewZoomAnimatedChange(MainActivity.this,dynoImage);
        }

    }

    public static void viewAnimatedChange(Context context, final ImageView imageView, final int imageRes) {
        final Animation anim_out = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                imageView.setImageResource(imageRes);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {}
                });
                imageView.startAnimation(anim_in);
            }
        });
        imageView.startAnimation(anim_out);
    }

    public static void viewZoomAnimatedChange(Context context, final ImageView imageView) {
        final Animation anim_out = AnimationUtils.loadAnimation(context, R.anim.zoom_in);
        final Animation anim_in  = AnimationUtils.loadAnimation(context, R.anim.zoom_out);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                //imageView.setImageResource(imageRes);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {}
                });
                imageView.startAnimation(anim_in);
            }
        });
        imageView.startAnimation(anim_out);
    }

    public static void viewAnimatedChange(Context context, final TextView textView, final int string) {
        final Animation anim_out = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                textView.setText(string);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {}
                });
                textView.startAnimation(anim_in);
            }
        });
        textView.startAnimation(anim_out);
    }


}
