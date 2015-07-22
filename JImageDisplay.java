import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * This class holds the image that we will create
 */
public class JImageDisplay extends javax.swing.JComponent {
    
    /** This field holds the awt image **/
    private BufferedImage image;
    
    
    /** 
     * Initializes new display object, with image dimensions equal to the 
     * width and height in the coordinates.
     */
    public JImageDisplay( int width, int height ) {
        image = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_RGB);
        // Setting preferred size
        java.awt.Dimension dim = new java.awt.Dimension(width, height);
        super.setPreferredSize(dim);
    }
    
    
    /**
     * Paints the component with the graphics
     */
    @Override
    protected void paintComponent( Graphics g ) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    }
    
    
    /**
     * Sets all pixels in the image to black.
     */
    public void clearImage() {
        for (int i = 0; i < image.getHeight(); i++) { 
            for (int j = 0; i < image.getWidth(); i++) {
                image.setRGB(i, j, 0);
            }
        }
    }
    
    
    /**
     * Sets pixel with given coordinates (x, y) to the color specified by 
     * rgbColor.
     */
    public void drawPixel(int x, int y, int rgbColor) {
        image.setRGB(x, y, rgbColor);
    }
    
    
    /**
     * Accessor for image object.
     */
    public BufferedImage getBufferedImage() {
        return image;
    }
    
}
