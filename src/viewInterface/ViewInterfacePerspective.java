package viewInterface;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;

import model.Perspective;
import presenter.PresenterPerspective;
import view.ViewPerspective;

public class ViewInterfacePerspective implements PropertyChangeListener {
	// Attributes
	private ViewPerspective viewPerspective;
	private PresenterPerspective presenterPerspective;

	private int mouseX;
	private int mouseY;

	// Methods
	public ViewInterfacePerspective(ViewPerspective viewPerspective, PresenterPerspective presenterPerspective) {
		this.viewPerspective = viewPerspective;
		this.presenterPerspective = presenterPerspective;

		// register the controller as the listener of the model
		this.presenterPerspective.addListener(this);

		setUpViewInteraction();

		presenterPerspective.setPerspective();
	}

	private void setUpViewInteraction() {
		viewPerspective.getbCloseView().setAction(new AbstractAction("Close View") {
			public void actionPerformed(ActionEvent arg0) {
				viewPerspective.dispose();
			}
		});
		viewPerspective.getbSave().setAction(new AbstractAction("Save") {
			public void actionPerformed(ActionEvent arg0) {
				//Undo 
				presenterPerspective.savePerspective();
			}
		});
		viewPerspective.getbUndo().setAction(new AbstractAction("Undo") {
			public void actionPerformed(ActionEvent arg0) {
				//Undo 
				presenterPerspective.undoAction();
			}
		});
		viewPerspective.getImagePanel().addMouseWheelListener(new MouseAdapter() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				int wheelRotation = e.getWheelRotation();
				double zoomPerScroll = 0.1;

				if (wheelRotation < 0) {
					presenterPerspective.changeZoom(zoomPerScroll);
				} else {
					presenterPerspective.changeZoom(-zoomPerScroll);
				}

				viewPerspective.repaint();
			}
		});
		viewPerspective.getImagePanel().addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		viewPerspective.getImagePanel().addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				int translationHorizontal = mouseX - e.getX();
				int translationVertical = mouseY - e.getY();

				presenterPerspective.changeTranslation(-translationHorizontal, -translationVertical);

				mouseX = e.getX();
				mouseY = e.getY();

				viewPerspective.repaint();
			}
		});
	}

	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		Object newVal = evt.getNewValue();
		if ("setPerspective".equalsIgnoreCase(propName)) {
			viewPerspective.setPerspectiveInPanel(((Perspective) newVal));
		}
	}
}
