package panel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import service.Service;

public class TableViewPanel extends MovieReservationPanel {

    private String tableName;
    private JTable dataTable;
    private Service service = Service.getService();

    public TableViewPanel(String tableName) {
        super();
        this.tableName = tableName;
        init();
    }

    @Override
    public void init() {
        removeAll();
        setLayout(null);

        JLabel titleLabel = new JLabel(tableName + " 테이블 보기 페이지");
        titleLabel.setBounds(440, 50, 200, 50);
        add(titleLabel);

        dataTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBounds(100, 150, 800, 400);
        add(scrollPane);

        JButton loadButton = new JButton("테이블 데이터 조회");
        loadButton.setBounds(400, 100, 200, 50);
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTableData();
            }
        });
        add(loadButton);
    }

    private void loadTableData() {
        String data = service.viewTableData(tableName);
        updateTableData(data);
    }

    private void updateTableData(String data) {
        String[] lines = data.split("\n");
        if (lines.length > 0) {
       
            String[] columnNames = lines[0].split("\t");
            Vector<String> columnVector = new Vector<>();
            for (String columnName : columnNames) {
                columnVector.add(columnName);
            }

         
            Vector<Vector<String>> dataVector = new Vector<>();
            for (int i = 1; i < lines.length; i++) {
                String[] rowValues = lines[i].split("\t");
                Vector<String> rowVector = new Vector<>();
                for (String value : rowValues) {
                    rowVector.add(value);
                }
                dataVector.add(rowVector);
            }

            dataTable.setModel(new javax.swing.table.DefaultTableModel(dataVector, columnVector));
        }
    }
}
