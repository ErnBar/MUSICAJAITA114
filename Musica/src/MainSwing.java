import interfaces.IServiceMusic;
import models.*;
import services.ServiceMusic;
import utils.IScanner;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainSwing {
    static IServiceMusic music = new ServiceMusic();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainSwing::createAndShowGUI);
        //SwingUtilities.invokeLater(MainSwing::createAndShowGUI) è una chiamata che viene utilizzata per eseguire la
        // creazione e la visualizzazione dell'interfaccia grafica Swing in un thread separato, noto come Event Dispatch Thread (EDT).
        //In Java Swing, tutte le operazioni relative all'interfaccia utente,
        // come la creazione di componenti Swing e l'aggiornamento dell'interfaccia grafica,
        // devono essere eseguite all'interno dell'EDT per garantire la coerenza e la sicurezza dell'interfaccia utente.
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Music Service");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));

        JLabel label = new JLabel("Benvenuto");
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.add(label);

        JButton button = new JButton("Visualizza Tabelle");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTables();
            }
        });
        panel.add(button);

        JButton buttonId= new JButton("Ricerca per ID");
        buttonId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTablesID();
            }
        });

        panel.add(buttonId);

        JButton buttonAdd= new JButton("Aggiungi Elemento");
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addElement();
            }
        });
        panel.add(buttonAdd);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private static void addElement(){
        Entity entity=null;
        JTextField tableName = new JTextField(10);
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Nome Tabella: "));
        inputPanel.add(tableName);
        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Inserisci Nome Tabella",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION && tableName.getText().equalsIgnoreCase("record_label")){
            JTextField name=new JTextField(10);
            JPanel inputPanel2 = new JPanel();
            inputPanel2.add(new JLabel("Nome"));
            inputPanel2.add(name);
            result= JOptionPane.showConfirmDialog(null, inputPanel2, "Inserisci i dati richiesti",
                    JOptionPane.OK_CANCEL_OPTION);
            entity=new Record_label(-45,name.getText());
            music.addRecord_Label(entity);
        } else if (result == JOptionPane.OK_OPTION && tableName.getText().equalsIgnoreCase("artist")) {
            JTextField record_label_id=new JTextField(10);
            JTextField name=new JTextField(10);
            JPanel inputPanel3 =new JPanel();
            inputPanel3.add(new JLabel("Record_label_ID"));
            inputPanel3.add(record_label_id);
            inputPanel.add(Box.createVerticalBox());
            inputPanel3.add(new JLabel("Nome"));
            inputPanel3.add(name);
            result=JOptionPane.showConfirmDialog(null, inputPanel3, "Inserisci i dati richiesti",
                    JOptionPane.OK_CANCEL_OPTION);
            entity=new Artist(-45,Integer.parseInt(record_label_id.getText()),name.getText());
            music.addArtist(entity);
        }

    }

    private static void showTables() {
        JFrame tableFrame = new JFrame("Tabelle");
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Recupera e visualizza i dati delle tabelle
        List<Entity> recordLabels = music.record_label();
        List<Entity> artists = music.artist();
        List<Entity> albums = music.album();
        List<Entity> songs = music.song();

        JTable recordLabelTable = createTable(recordLabels);
        JTable artistTable = createTable(artists);
        JTable albumTable = createTable(albums);
        JTable songTable = createTable(songs);

        tabbedPane.addTab("Record Labels", new JScrollPane(recordLabelTable));
        tabbedPane.addTab("Artists", new JScrollPane(artistTable));
        tabbedPane.addTab("Albums", new JScrollPane(albumTable));
        tabbedPane.addTab("Songs", new JScrollPane(songTable));

        panel.add(tabbedPane, BorderLayout.CENTER);

        tableFrame.getContentPane().add(panel);
        tableFrame.pack();
        tableFrame.setLocationRelativeTo(null);
        tableFrame.setVisible(true);
    }

    private static void showTablesID() {
        // Creazione di una finestra di dialogo per l'inserimento dell'ID e del nome della tabella
        JTextField idField = new JTextField(10);
        JTextField tableNameField = new JTextField(10);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("ID: "));
        inputPanel.add(idField);
        inputPanel.add(Box.createHorizontalStrut(15)); // Spaziatura orizzontale tra id e nometabella
        inputPanel.add(new JLabel("Nome tabella: "));
        inputPanel.add(tableNameField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Inserisci ID e Nome Tabella",
                JOptionPane.OK_CANCEL_OPTION);

        // Se l'utente ha premuto OK
        if (result == JOptionPane.OK_OPTION) {
            // Recupero dell'ID e del nome della tabella inseriti dall'utente
            int id = Integer.parseInt(idField.getText());
            String tableName = tableNameField.getText();

            JFrame tableFrame = new JFrame("Ricerca per ID nella tabella scelta.");
            tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel panel = new JPanel(new BorderLayout());

            JTabbedPane tabbedPane = new JTabbedPane();

            // Recupero e visualizz i dati della tabella indicata e dell'id ricercato attraverso il metodo in music
            Entity entity = music.cercaPerId(id, tableName);

            ArrayList<Entity>list=new ArrayList<>();
            list.add(entity);//Addo Entity ad una lista cosi posso usare il createTable
            JTable label = createTable(list);

            // Attraverso i casting riconosco cosa aggiungere
            if (entity instanceof Record_label) {
                tabbedPane.addTab("Record Labels", new JScrollPane(label));
            }
            if (entity instanceof Artist) {
                tabbedPane.addTab("Artists", new JScrollPane(label));
            }
            if (entity instanceof Album) {
                tabbedPane.addTab("Albums", new JScrollPane(label));
            }
            if (entity instanceof Song) {
                tabbedPane.addTab("Songs", new JScrollPane(label));
            }

            panel.add(tabbedPane, BorderLayout.CENTER);

            tableFrame.getContentPane().add(panel);
            tableFrame.pack();
            tableFrame.setLocationRelativeTo(null);
            tableFrame.setVisible(true);
        }
    }

    private static JTable createTable(List<Entity> entities) {
        if (!entities.isEmpty() && entities.get(0) instanceof Record_label) {
            String[] columnNames = {"ID", "Name"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            for (Entity entity : entities) {
                Record_label recordLabel = (Record_label) entity;
                Object[] rowData = {recordLabel.getId(), recordLabel.getName()};
                model.addRow(rowData);
            }

            return new JTable(model);
        } if (!entities.isEmpty() && entities.get(0) instanceof Artist) {
            String[] columnNames = {"ID", "Name","Record_label_id"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            for (Entity entity : entities) {
                Artist artist = (Artist) entity;
                Object[] rowData = {artist.getId(), artist.getName(),artist.getRecord_label_id()};
                model.addRow(rowData);
            }

            return new JTable(model);
        }
        if (!entities.isEmpty() && entities.get(0) instanceof Album) {
            String[] columnNames = {"ID","Artist_id" ,"Name","Date_release",};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            for (Entity entity : entities) {
                Album album = (Album) entity;
                Object[] rowData = {album.getId(),album.getArtist_id(), album.getName(),album.getDate_release()};
                model.addRow(rowData);
            }

            return new JTable(model);
        }
        if (!entities.isEmpty() && entities.get(0) instanceof Song) {
            String[] columnNames = {"ID","Album_id" ,"Name","Duration",};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            for (Entity entity : entities) {
                Song song = (Song) entity;
                Object[] rowData = {song.getId(),song.getAlbum_id(), song.getName(),song.getDuration()};
                model.addRow(rowData);
            }

            return new JTable(model);
        }


        else {
            return new JTable();
        }
    }

    //Creo un metodo per la ricerca per ID che accetti Entity in input.(Quasi uguale a quello sopra)
    //Metodo inutile in quanto uso sempre lo stesso alla fine
//    private static JTable createTableID(Entity entities) {
//        if (entities instanceof Record_label) {
//            String[] columnNames = {"ID", "Name"};
//            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
//            Record_label recordLabel = (Record_label) entities;
//            Object[] rowData = {recordLabel.getId(), recordLabel.getName()};
//            model.addRow(rowData);
//            return new JTable(model);
//        }
//        if (entities instanceof Artist) {
//            String[] columnNames = {"ID", "Name","Record_label_id"};
//            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
//            Artist artist = (Artist) entities;
//            Object[] rowData = {artist.getId(), artist.getName(),artist.getRecord_label_id()};
//            model.addRow(rowData);
//            return new JTable(model);
//        }
//        if (entities instanceof Album) {
//            String[] columnNames = {"ID","Artist_id" ,"Name","Date_release",};
//            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
//            Album album = (Album) entities;
//            Object[] rowData = {album.getId(),album.getArtist_id(), album.getName(),album.getDate_release()};
//            model.addRow(rowData);
//            return new JTable(model);
//        }
//        if (entities instanceof Song) {
//            String[] columnNames = {"ID","Album_id" ,"Name","Duration",};
//            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
//            Song song = (Song) entities;
//            Object[] rowData = {song.getId(),song.getAlbum_id(), song.getName(),song.getDuration()};
//            model.addRow(rowData);
//
//            return new JTable(model);
//        }
//
//
//        else {
//            return new JTable();
//        }
//    }
}