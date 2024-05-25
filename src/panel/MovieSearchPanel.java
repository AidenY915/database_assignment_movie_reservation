package panel;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MovieSearchPanel extends MovieReservationPanel{
	public MovieSearchPanel() {
		super();
		init();
		
	}

	@Override
	public void init() {
		setLayout(null);
		
		
		JLabel titleLabel= new JLabel("영화 검색");
		
		JLabel movieName=new JLabel("영화 제목");
		JLabel directorName=new JLabel("감독 이름");
		JLabel actorName= new JLabel("배우 이름");
		JLabel genre= new JLabel("장르");
		
		JTextField movieNameField= new JTextField();
		JTextField directorNameField= new JTextField();
		JTextField actorNameField= new JTextField();
		JTextField genreField= new JTextField();
		
		
		add(titleLabel);
		add(movieName);
		add(directorName);
		add(actorName);
		add(genre);
		add(movieNameField);
		add(directorNameField);
		add(actorNameField);
		add(genreField);
		
		
		titleLabel.setBounds(50 ,50,50,50);
		
		
		

		
		
	}

}

