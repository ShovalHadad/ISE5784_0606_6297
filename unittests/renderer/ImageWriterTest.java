package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    void testWriteToImage() {
        ImageWriter imageWriter = new ImageWriter("yellow", 800, 500);
        for(int i = 0; i < imageWriter.getNx(); i++){
            for(int j = 0; j< imageWriter.getNy(); j++){
                imageWriter.writePixel(i, j ,new Color(java.awt.Color.YELLOW));
            }
        }
        imageWriter.writeToImage();

        // we added that for fun =)
        imageWriter = new ImageWriter("smile", 800, 500);
        for(int i = 0; i < imageWriter.getNx(); i++){
            for(int j = 0; j< imageWriter.getNy(); j++){
               if(((i >= 250 & i < 600) & (j <= 50 || j >= 450))  //first and last line (0 and 9)
               ||(((i >= 200 & i < 250 )|| (i >= 600 & i < 650)) & ((j <= 100 & j > 50 ) || (j >= 400 & j < 450))) //lines 1 and 8
                || (((i >= 150 & i < 200 )|| (i >= 650 & i < 700) || (i >= 300 & i < 350) || (i >= 500 & i < 550)) &
                       ((j <= 150 & j > 100 ) || (j >= 350 & j < 400)))  // lines 2 and 7
                       || ((i >= 350 & i < 500) & (j >= 350 & j < 400))  // line 7 (the smile of smile)
                       ||(((i >= 300 & i < 350)||(i >= 500 & i < 550)) & (j >= 150 & j < 250))  // the eyes of smile
               || (((i >= 100 & i < 150)||(i >= 700 & i < 750)) & (j >= 150 & j < 350))  // columns 2 and 13
               ||(((i >= 250 & i < 300)||(i >= 550 & i < 600)) & (j >= 300 & j < 350))){  // line 6 (part of the smile)
                   imageWriter.writePixel(i, j ,new Color(java.awt.Color.BLACK));
               }else {
                   imageWriter.writePixel(i, j, new Color(java.awt.Color.YELLOW));
               }
            }
        }
        imageWriter.writeToImage();
    }
}