import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JDialog implements ActionListener {
    private JTextField nama, usia, berat, telepon;
    private JComboBox<String> golongan;
    private JRadioButton laki, perempuan;
    private JTextArea hasil;
    private JButton daftar, reset;

    public Main() {
        setTitle("Formulir Pendaftaran Donor Darah");
        setSize(700, 650);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JMenuBar bar = new JMenuBar();
        JMenu pendaftaran = new JMenu("Pendaftaran");
        JMenuItem formulir = new JMenuItem("Formulir Pendonor");
        JMenuItem informasi = new JMenuItem("Informasi Mengenai Donor Darah");
        JMenuItem keluar = new JMenuItem("Keluar");

        JMenu bantuan = new JMenu("Bantuan");
        JMenuItem tentang = new JMenuItem("Tentang Aplikasi");

        for (JMenuItem item : new JMenuItem[]{formulir, informasi, keluar, tentang}) {
            item.addActionListener(this);
        }

        pendaftaran.add(formulir);
        pendaftaran.add(informasi);
        pendaftaran.addSeparator();
        pendaftaran.add(keluar);
        bantuan.add(tentang);
        bar.add(pendaftaran);
        bar.add(bantuan);
        setJMenuBar(bar);

        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel judul = new JLabel("FORMULIR PENDAFTARAN DONOR DARAH");
        judul.setFont(new Font("Arial", Font.BOLD, 18));
        header.add(judul);
        add(header, BorderLayout.NORTH);

        // GridLayout
        JPanel form = new JPanel(new GridLayout(6, 2, 8, 10));
        form.setBorder(BorderFactory.createTitledBorder("Data Calon Pendonor"));

        nama = new JTextField();
        usia = new JTextField();
        berat = new JTextField();
        telepon = new JTextField();
        golongan = new JComboBox<>(new String[]{"Pilih golongan darah", "A", "B", "AB", "O"});
        laki = new JRadioButton("Laki-laki");
        perempuan = new JRadioButton("Perempuan");

        ButtonGroup gender = new ButtonGroup();
        gender.add(laki);
        gender.add(perempuan);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(laki);
        genderPanel.add(perempuan);

        form.add(new JLabel("Nama lengkap:"));
        form.add(nama);
        form.add(new JLabel("Usia (tahun):"));
        form.add(usia);
        form.add(new JLabel("Berat badan (kg):"));
        form.add(berat);
        form.add(new JLabel("Nomor telepon:"));
        form.add(telepon);
        form.add(new JLabel("Golongan darah:"));
        form.add(golongan);
        form.add(new JLabel("Jenis kelamin:"));
        form.add(genderPanel);

        JPanel tengah = new JPanel(new BorderLayout(8, 8));
        tengah.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));
        tengah.add(form, BorderLayout.NORTH);

        hasil = new JTextArea(8, 40);
        hasil.setEditable(false);
        hasil.setLineWrap(true);
        hasil.setWrapStyleWord(true);
        hasil.setFont(new Font("Monospaced", Font.PLAIN, 13));
        hasil.setMargin(new Insets(10, 10, 10, 10));
        hasil.setText("Hasil pendaftaran akan ditampilkan di sini.");

        JScrollPane scroll = new JScrollPane(hasil);
        scroll.setBorder(BorderFactory.createTitledBorder("Hasil Pendaftaran"));
        tengah.add(scroll, BorderLayout.CENTER);
        add(tengah, BorderLayout.CENTER);

        JPanel tombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        daftar = new JButton("Daftar Donor");
        reset = new JButton("Reset");

        daftar.addActionListener(this);
        reset.addActionListener(this);
        tombol.add(daftar);
        tombol.add(reset);
        add(tombol, BorderLayout.SOUTH);
    }

    private void resetForm() {
        nama.setText("");
        usia.setText("");
        berat.setText("");
        telepon.setText("");
        golongan.setSelectedIndex(0);
        laki.setSelected(false);
        perempuan.setSelected(false);
        hasil.setText("Hasil pendaftaran akan ditampilkan di sini.");
    }

    // Override dari ActionListener
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (e.getSource() == daftar) {
            String n = nama.getText().trim();
            String u = usia.getText().trim();
            String b = berat.getText().trim();
            String t = telepon.getText().trim();
            String g = (String) golongan.getSelectedItem();
            String jk = laki.isSelected() ? "Laki-laki" : perempuan.isSelected() ? "Perempuan" : "";

            if (n.isEmpty() || u.isEmpty() || b.isEmpty() || t.isEmpty() || jk.isEmpty() || golongan.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(
                    this,
                    "Mohon untuk melengkapi semua data terlebih dahulu.",
                    "Data belum lengkap",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            try {
                int umur = Integer.parseInt(u);
                double bb = Double.parseDouble(b);

                if (umur <= 0 || bb <= 0) {throw new NumberFormatException();}
                String ringkasan =
                    "========================================\n"
                    + "       PENDAFTAR DONOR DARAH\n"
                    + "========================================\n\n"
                    + "Nama Lengkap   : " + n + "\n"
                    + "Usia           : " + umur + " tahun\n"
                    + "Berat Badan    : " + bb + " kg\n"
                    + "Nomor Telepon  : " + t + "\n"
                    + "Golongan Darah : " + g + "\n"
                    + "Jenis Kelamin  : " + jk + "\n\n";

                hasil.setText(ringkasan);
                hasil.setCaretPosition(0);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    this,
                    "Usia dan berat badan harus berupa angka positif.",
                    "Input tidak valid",
                    JOptionPane.ERROR_MESSAGE
                );
            }

        } else if (e.getSource() == reset) {
            resetForm();

        } else if (cmd.equals("Informasi Donor Darah")) {
            JOptionPane.showMessageDialog(
                this,
                "INFORMASI DONOR DARAH\n\n"
                + "Donor darah merupakan kegiatan memberikan\n"
                + "darah sebagai bentuk bantuan kepada orang yang membutuhkan.\n\n"
                + "Calon pendonor perlu mengikuti pemeriksaan\n"
                + "terlebih dahulu oleh petugas kesehatan.\n\n"
                + "Informasi kelayakan dan persyaratan donor\n"
                + "dapat ditanyakan langsung kepada petugas\n"
                + "atau unit pelayanan donor darah.",
                "Informasi Donor Darah",
                JOptionPane.INFORMATION_MESSAGE
            );

        } else if (cmd.equals("Keluar")) {
            int konfirmasi = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin keluar?", "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

    if (konfirmasi == JOptionPane.YES_OPTION) {
        dispose();
    }

        } else if (cmd.equals("Tentang Aplikasi")) {
            JOptionPane.showMessageDialog(
                this,
                "Formulir Pendaftaran Donor Darah merupakan formulir yang digunakan untuk melakukan pendaftaran sebagai peserta donor darah.\n",
                "Tentang Aplikasi",
                JOptionPane.INFORMATION_MESSAGE
            );

        } else if (cmd.equals("Formulir Pendonor")) {
            nama.requestFocus();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}