package remoteslider;

/**
 * This class creates a server for the android application RemoteSlider.
 * @author Miguel Blanco Godón [github(meluchoMZ)]
 */
public class RemoteSlider {

    /**
     * This method starts the application RemoteSliderDesktop.
     * @param args a string array with no purpose.
     */
    public static void main(String args[]) {
        GUI gui = new GUI();
        GUI.configureGUI(gui);
    }
    
}
