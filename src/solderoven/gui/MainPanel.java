package solderoven.gui;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import solderoven.i18n.I18N;
import solderoven.models.AppModel;
import solderoven.models.TemperatureChartModel;

/**
 * @author Daan Pape
 */
public class MainPanel extends JPanel{
    
    /**
     * The application main model.
     */
    private AppModel model;
    
    /**
     * MainPanel constructor.
     * @param model the main application model.
     */
    public MainPanel(AppModel model){
        this.model = model;
        
        // Construct the GUI for the app
        constructGUI();
    }
    
    /**
     * Construct the application GUI
     */
    private void constructGUI(){
        
        // Add the status panel with a titled border
        StatusPanel sPanel = new StatusPanel(model.getBoardModel());
        TitledBorder sBorder = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), 
                I18N.getInstance().getString("titleStatus"),
                TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION
                );
        sPanel.setBorder(sBorder);
        this.add(sPanel);
        
        // Add the manual control panel
        ManualPanel mPanel = new ManualPanel(model);
        TitledBorder mBorder = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), 
                I18N.getInstance().getString("titleControl"),
                TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION
                );
        mPanel.setBorder(mBorder);
        this.add(mPanel);
        
        this.add(new TemperatureChart(new TemperatureChartModel(model.getBoardModel())));
    }
}
