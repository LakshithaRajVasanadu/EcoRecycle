package ui;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.repository.RmosRepository;

public class RmosUI extends JPanel{
	private Rmos rmos;

    
    public static final String LOGIN_PANEL = "AdminPanel";
    public static final String MAIN_PANEL = "RmosMainPanel";

	
	
	public RmosUI(String name) {
		rmos = new RmosRepository().getRmosByName(name);
		
        this.setLayout(new CardLayout());

        this.add(new AdminPanel(this, rmos), LOGIN_PANEL);
        this.add(new RmosMainPanel(rmos), MAIN_PANEL);
		
		//this.add(new AdminPanel());
		setVisible(true);
		
	}

	
}