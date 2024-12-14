 package audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class AudioPlayer {
    public static int MENU_1 = 0;
    public static int LEVEL_1 = 1;
    public static int LEVEL_2 = 2;
    public static int LEVEL_3 = 3;
    public static int LEVEL_4 = 4;
    public static int LEVEL_5 = 5;

    public static int DIE = 0;
    public static int JUMP = 1;
    public static int GAMEOVER = 2;
    public static int LVL_COMPLETED = 3;
    public static int ATTACK_ONE = 4;
    public static int ATTACK_TWO = 5;
    public static int ATTACK_THREE = 6;
    public static int LEVEL_UP = 7;
    public static int CLICK = 8;
    public static int HIT = 9;
    public static int BOSS1_ATTACK1 = 10;
    public static int BOSS1_DEAD = 11;
    public static int HIT_BOSS = 12;
    public static int BOSS2_DEAD = 13;
    public static int BOSS3_DEAD = 14;
    public static int BOSS4_DEAD = 15;
    public static int RUN = 16;
    public static int THROW = 17;
    public static int BOSS4_ATTACK = 18;
    public static int BOSS3_ATTACK = 19;
    public static int BOSS2_ATTACK = 20;

    private Clip[] songs,effects;
    private int currentSongId;
    private float volume=0.75f;
    private boolean songMute;
    private boolean effectMute;
    private Random rand = new Random();

    public AudioPlayer(){
        loadSongs();
        loadEffect();
        playSong(MENU_1);

    }
    private void loadSongs(){
        String[] names = {"menu1","level11","level2","level3","level4","level5"};
        songs =new Clip[names.length];
        for(int i=0;i<songs.length;i++)
            songs[i]=getClip(names[i]);
    }

    private void loadEffect(){
        String[] effectNames = {"die", "jump", "gameover", "lvlcompleted", "attack1", "attack2", "attack3","levelUp","Click","hit","boss1_attack1","boss1_dead","hit_boss","boss2_dead","boss3_dead","boss4_dead","run","throwSound","boss4_attack","boss3_attack","boss2_attack"};
        effects=new Clip[effectNames.length];
        for(int i=0;i<effects.length;i++)
            effects[i]=getClip(effectNames[i]);


        updateEffectsVolume();

    }
    private Clip getClip(String name){
        URL url = getClass().getResource("/audio/"+name+".wav");
        AudioInputStream audio;
        try {
            audio = AudioSystem.getAudioInputStream(url);
            Clip c= AudioSystem.getClip();
            c.open(audio);
            return c;
        }catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
            e.printStackTrace();
        }

        return null;
    }

    public void toggleSongMute(){
        this.songMute=!songMute;
        for(Clip c:songs){
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songMute);
        }
    }
    public void toggleEffectMute(){
        this.effectMute=!effectMute;
        for(Clip c:effects){
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(effectMute);
        }
        if(!effectMute)
            playEffect(CLICK);

    }

    public void setVolume(float volume){
        this.volume=volume;
        updateSongVolume();
        updateEffectsVolume();
    }
    public void stopSong(){
        if(songs[currentSongId].isActive())
            songs[currentSongId].stop();
    }
    public void stopEffect(int effect){
        if(effects[effect].isActive())
            effects[effect].stop();
    }
    public void setLevelSong(int lvlIndex){
        if(lvlIndex == 0)
            playSong(LEVEL_1);
        if(lvlIndex == 1)
            playSong(LEVEL_2);
        if(lvlIndex == 2)
            playSong(LEVEL_3);
        if(lvlIndex == 3)
            playSong(LEVEL_4);
        if(lvlIndex == 4)
            playSong(LEVEL_5);
    }
    public void lvlCompleted(){

        stopSong();
        playEffect(LVL_COMPLETED);
    }

    public void playAttackSound(){
        int start = 4;
        start+=rand.nextInt(3);
        playEffect(start);

    }

    public void playEffect(int effect) {
        effects[effect].setMicrosecondPosition(0);
        effects[effect].start();
        if (effect == RUN) {
            effects[effect].loop(Clip.LOOP_CONTINUOUSLY);
        } 
        
    }

    public void playSong(int song){
        stopSong();
        currentSongId = song;
        updateSongVolume();
        songs[currentSongId].setMicrosecondPosition(0);
        songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
    }

    private void updateSongVolume(){

        FloatControl gainControl =(FloatControl) songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum()-gainControl.getMinimum();
        float gain =(range*volume)+gainControl.getMinimum();
        gainControl.setValue(gain);


    }
    private void updateEffectsVolume(){

        for(Clip c:effects) {
            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }

    }

    
}
