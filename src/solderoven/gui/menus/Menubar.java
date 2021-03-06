package solderoven.gui.menus;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import solderoven.actions.LoadProfileAction;
import solderoven.exception.OvenBoardException;
import solderoven.gui.manualpwm.PWMFrame;
import solderoven.i18n.I18N;
import solderoven.models.AppModel;

/**
 * @author Daan Pape
 */
public class Menubar extends JMenuBar implements PropertyChangeListener{
    
    /**
     * The application model
     */
    private AppModel model;
    
    /**
     * The frame to which this menu is attached.
     */
    private JFrame parentFrame;
    
    /**
     * The File menu connect button
     */
    JMenuItem connect;
    
    /**
     * The File menu disconnect button
     */
    JMenuItem disconnect;
    
    /**
     * Constructor
     * @param model the main application model.
     * @param parentFrame the frame to which this MenuBar is attached.
     */
    public Menubar(AppModel model, JFrame parentFrame){
        this.model = model;
        this.parentFrame = parentFrame;
        
        // Create and add all the menus
        createAndAddFileMenu();
        createAndAddProcessMenu();
        createAndAddHelpMenu();
        
        // Register as listeners 
        model.getBoardModel().addPropertyChangeListener(this);
    }
    
    /**
     * Create and display the help menu
     */
    private void createAndAddFileMenu(){
        // Construct and add the menu
        JMenu file = new JMenu(I18N.getInstance().getString("fileMenu"));
        
        // Construct the close button
        JMenuItem close = new JMenuItem(new AbstractAction(I18N.getInstance().getString("btnClose")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        close.setMnemonic(I18N.getInstance().getString("btnCloseMnemonic").charAt(0));
        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        
        // Construct the options button
        JMenuItem options = new JMenuItem(new AbstractAction(I18N.getInstance().getString("btnOptions")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show options pane
            }
        });
        options.setMnemonic(I18N.getInstance().getString("btnOptionsMnemonic").charAt(0));
        
        // Construct the load profile button
        JMenuItem loadProfile = new JMenuItem(new LoadProfileAction(model, parentFrame));
        loadProfile.setMnemonic(I18N.getInstance().getString("btnLoadMnemonic").charAt(0));
        loadProfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        
        // Construct the connect button
        connect = new JMenuItem(new AbstractAction(I18N.getInstance().getString("btnConnect")){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Connect to the board
                    model.getOvenBoard().connectToBoard();
                } catch (OvenBoardException ex) {
                    //TODO: handle
                }
            }       
        });
        connect.setMnemonic(I18N.getInstance().getString("btnConnectMnemonic").charAt(0));
        connect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        
        // Construct the disconnect button
        disconnect = new JMenuItem(new AbstractAction(I18N.getInstance().getString("btnDisconnect")){
            @Override
            public void actionPerformed(ActionEvent e) {
                // Connect to the board
                model.getOvenBoard().closeConnection();
            }       
        });
        disconnect.setMnemonic(I18N.getInstance().getString("btnDisconectMnemonic").charAt(0));
        disconnect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        
        // Set the enabled state of the connect and disconnect buttons
        connect.setEnabled(!model.getBoardModel().isConnected());
        disconnect.setEnabled(model.getBoardModel().isConnected());        
        
        // Add the menu items
        file.add(loadProfile);
        file.add(connect);
        file.add(disconnect);
        file.add(options);
        file.add(close);
        
        // Set the File menu Mnemonic and add to the menu bar
        file.setMnemonic(I18N.getInstance().getString("fileMenuMnemonic").charAt(0));
        this.add(file);
    }
    
    /**
     * Create and add the process menu
     */
    private void createAndAddProcessMenu(){
        // Construct and add the menu
        JMenu processMenu = new JMenu(I18N.getInstance().getString("processMenu"));
        
        // Construct the help button
        JMenuItem pwmSettings = new JMenuItem(new AbstractAction(I18N.getInstance().getString("btnPWMSettings")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the PWMFrame
                new PWMFrame(model).setVisible(true);
            }
        });
        pwmSettings.setMnemonic(I18N.getInstance().getString("btnPWMSettingsMnemonic").charAt(0));
        
        // Add the menu items
        processMenu.add(pwmSettings);
        
        // Set the File menu Mnemonic and add to the menu bar
        processMenu.setMnemonic(I18N.getInstance().getString("processMenuMnemonic").charAt(0));
        this.add(processMenu);
    }
    
    /**
     * Create the help menu
     */
    private void createAndAddHelpMenu(){
        // Construct and add the menu
        JMenu helpmenu = new JMenu(I18N.getInstance().getString("helpMenu"));
        
        // Construct the help button
        JMenuItem help = new JMenuItem(new AbstractAction(I18N.getInstance().getString("btnHelp")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        help.setMnemonic(I18N.getInstance().getString("btnHelpMnemonic").charAt(0));
        help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));    
        
        // Add the menu items
        helpmenu.add(help);
        
        // Set the File menu Mnemonic and add to the menu bar
        helpmenu.setMnemonic(I18N.getInstance().getString("helpMenuMnemonic").charAt(0));
        this.add(helpmenu);
    }
    
    /**
     * This event handler is notified when one of the models has a changed property.
     * @param evt the event data.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch(evt.getPropertyName()){
            case "connectionState":
                // Set the enabled state of the connect and disconnect buttons
                connect.setEnabled(!model.getBoardModel().isConnected());
                disconnect.setEnabled(model.getBoardModel().isConnected());  
                break;
        }
    }
}
