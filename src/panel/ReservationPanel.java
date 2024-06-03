package panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;

import dto.MovieDTO;
import service.Service;

public class ReservationPanel extends MovieReservationPanel {

	private Service service = Service.getService();
	private List<MovieDTO> movieList;
	
	
	@Override
	public void init() {
		removeAll();
		
		setLayout(null);
		
		JComboBox<MovieDTO> comboBox = new JComboBox<MovieDTO>();
		movieList = service.getAllMovies();
		for(MovieDTO movie : movieList) {
			comboBox.addItem(movie);
		}
		
		add(comboBox);
		comboBox.setBounds(10, 10, 100, 50);
		
		System.out.println(comboBox.getSelectedItem());
	}
	
	class selectMovieListener implements ActionListener{
		private JComboBox<MovieDTO> comboBox;
		selectMovieListener(JComboBox<MovieDTO> comboBox) {
			this.comboBox = comboBox;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(((MovieDTO)comboBox.getSelectedItem()).getMovieNo());
		}
		
	}

}
