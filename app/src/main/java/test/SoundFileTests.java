package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class SoundFileTests {

    @Test
    public void engineStartSound_FileExists_TestIsSuccessful(){
        File tempFile = new File("C:/Users/NiamhMcCartney/Documents/CSC2044 Project/csc2044-45-1920-g8/app/src/main/assets/sound/engineStart.mp3");
        boolean success = tempFile.exists();

        assertTrue(success);
    }

    @Test
    public void engineStopSound_FileExists_TestIsSuccessful(){
        File tempFile = new File("C:/Users/NiamhMcCartney/Documents/CSC2044 Project/csc2044-45-1920-g8/app/src/main/assets/sound/engineStop.mp3");
        boolean success = tempFile.exists();

        assertTrue(success);
    }

    @Test
    public void backgroundMusic_FileExists_NameInvalid_TestIsUnsuccessful(){
        File tempFile = new File("C:/Users/NiamhMcCartney/Documents/CSC2044 Project/csc2044-45-1920-g8/app/src/main/assets/sound/engine123.mp3");
        boolean success = tempFile.exists();

        assertFalse(success);
    }

}

