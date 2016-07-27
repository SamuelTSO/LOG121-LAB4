//MVP inspirer de Bnrdo - http://stackoverflow.com/questions/5217611/the-mvc-pattern-and-swing

package viewInterface;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

import model.CollectionImage;
import model.ImageData;
import model.commande.Close;
import model.commande.GestionnaireCommande;
import model.commande.Load;
import presenter.PresenterMenu;
import view.ViewMenu;

public class ViewInterfaceMenu implements PropertyChangeListener{
	//Attributes
    private ViewMenu view;
    private PresenterMenu presenter;
    //Methods
    public ViewInterfaceMenu(ViewMenu view, PresenterMenu presenter){
        this.view = view;
        this.presenter = presenter;

        //register the controller as the listener of the model
        this.presenter.addListener(this);

        setUpViewInteraction();
    }
    private void setUpViewInteraction(){
        view.getbLoadFile().setAction(new AbstractAction("Choose Folder") { 
            public void actionPerformed(ActionEvent arg0) {
            	GestionnaireCommande gestCmd = new GestionnaireCommande();
            	
            	gestCmd.add(new Load(presenter));
            	gestCmd.executeAll();
            }
        });
        
        view.getbOpenImage().setAction(new AbstractAction("Open Image") { 
            public void actionPerformed(ActionEvent arg0) {
            	presenter.generateImagePerspectiveMVP(view.getSelectedListIndex());
            }
        });
        //...
    }
    public void propertyChange(PropertyChangeEvent evt){
        String propName = evt.getPropertyName();
        Object newVal = evt.getNewValue();

        if("loadFile".equalsIgnoreCase(propName)){
        	CollectionImage c = CollectionImage.getInstance();
        	DefaultListModel<ImageIcon> listModel = view.getListModel();
        	listModel.clear();
        	
        	for(ImageData imgdata : c.getImageList()){
        		listModel.addElement(imgdata.getImageIcon());
        	}   
        }
    }
}