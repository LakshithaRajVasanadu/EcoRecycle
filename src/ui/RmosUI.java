package ui;

import javax.swing.JPanel;

import com.ecoRecycle.model.Rcm;
import com.ecoRecycle.model.Rmos;
import com.ecoRecycle.repository.RmosRepository;

public class RmosUI extends JPanel{
	private Rmos rmos;
	
	public RmosUI(String name) {
		rmos = new RmosRepository().getRmosByName(name);
		
		this.add(new AdminPanel());
		setVisible(true);
		
	}

}
