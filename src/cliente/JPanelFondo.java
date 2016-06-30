package cliente;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class JPanelFondo extends JPanel {

	private Image fondo;

	public void paintComponent(Graphics g) {
		// Mandamos que pinte la imagen en el panel
		if (this.fondo != null) {
			g.drawImage(this.fondo, 0, 0, 900, 700, null);
		}
		super.paintComponent(g);
	}

	public void setBackground(String imagePath) {

		// Construimos la imagen y se la asignamos al atributo background.
		this.setOpaque(false);
		this.fondo = new ImageIcon(imagePath).getImage();
		repaint();
	}

}
