import interfaces.IServiceMusic;
import models.*;
import services.ServiceMusic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainSwing {
    static IServiceMusic music = new ServiceMusic();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainSwing::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Music Service");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JLabel label = new JLabel("Benvenuto");
        panel.add(label);

        JButton button = new JButton("Visualizza Tabelle");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTables();
            }
        });
        panel.add(button);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
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
}