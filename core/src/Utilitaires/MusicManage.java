package Utilitaires;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;


public class MusicManage implements Disposable {
	

    public enum AotMusic
    {
        MENU( "" ), // a tester + implémentation d'un bouton "mute" à faire (code déjà placé)
        LEVEL( "" );

        private final String fileName;

        private AotMusic(String fileName ){
            this.fileName = fileName;
        }

        public String getFileName(){
            return fileName;
        }
    }
    
    private Music musicPlayed;
	private float volume = 1f;
	private boolean enabled = true;
	
	public boolean isEnabled() {
		return enabled;
	}

	public MusicManage(){}
	
	public void play(AotMusic music){
        if( ! enabled ) return;

        //Pour lancer la musique si ça n'est pas déjà fait (test)
        FileHandle musicFile = Gdx.files.internal( music.getFileName() );
        musicPlayed = Gdx.audio.newMusic( musicFile );
        musicPlayed.setVolume( volume );
        musicPlayed.setLooping( true );
        musicPlayed.play();
	}
	
	public void stop(){
		if( musicPlayed != null ) {
            musicPlayed.stop();
            musicPlayed.dispose();
        }
	}
	
	public void setVolume(float volume){
		this.volume = volume;
	}
	
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
		
		if( ! enabled )
			stop();
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void mute(boolean mute){
		if (mute)
			musicPlayed.setVolume( 0 );
		else
			musicPlayed.setVolume( volume );
	}
	
}
