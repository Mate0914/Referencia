package pkg43_01_nagyfeladatfocibajnoksag;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Foablak extends javax.swing.JFrame {

    private String adatbazisNev;
    private List<Csapat> tabella = new ArrayList<>();
    private List<Merkozes> meccsek = new ArrayList<>();
    private DefaultTableModel dlTabella = new DefaultTableModel();
    private DefaultTableModel dlMeccsek = new DefaultTableModel();
    private final DefaultListModel<String> csapatNevek = new DefaultListModel<>();
    private final LabdarugobajnoksagDAO adatbazis = new LabdarugobajnoksagDAOJDBCImpl();

    public List<Merkozes> getMeccsek() {
        return this.meccsek;
    }

    public void setMeccsek(List<Merkozes> meccs) {
        this.meccsek = meccs;
    }

    public List<Csapat> getTabella() {
        return this.tabella;
    }

    public void setTabella(List<Csapat> tab) {
        this.tabella = tab;
    }

    public List<Csapat> tabellaRendez(List<Csapat> tabella) {
        tabella.sort((Csapat egyik, Csapat masik) -> {
            if (masik.getPont() - egyik.getPont() != 0) {
                return masik.getPont() - egyik.getPont();
            } else if (masik.getLottGol() - egyik.getLottGol() != 0) {
                return masik.getLottGol() - egyik.getLottGol();
            }
            return egyik.getKapottGol() - masik.getKapottGol();
        });
        return tabella;
    }

    public List<Merkozes> meccsRendez(List<Merkozes> meccs) {
        meccs.sort((Merkozes egyik, Merkozes masik) -> {
            return egyik.getDatum().compareTo(masik.getDatum());
        });
        return meccs;
    }

    public void tabellaListaz() {
        int helyezes = 1;
        if (dlTabella.getRowCount() > 0) {
            for (int i = dlTabella.getRowCount() - 1; i > -1; i--) {
                dlTabella.removeRow(i);
            }
        }
        for (Csapat tab : tabella) {
            dlTabella.addRow(new Object[]{helyezes + ".", tab.getCsapatnev(), tab.getID(), tab.getLottGol(), tab.getKapottGol(), tab.getPont()});
            helyezes++;
        }
    }

    public void meccsekListaz() {
        if (dlMeccsek.getRowCount() > 0) {
            for (int i = dlMeccsek.getRowCount() - 1; i > -1; i--) {
                dlMeccsek.removeRow(i);
            }
        }
        for (Merkozes me : meccsek) {
            dlMeccsek.addRow(new Object[]{me.getDatum(), me.getHazaiCsapat().getCsapatnev(), me.getVendegCsapat().getCsapatnev(), me.getHazaiGol(), me.getVendegGol(), me.getGyozelem()});

        }
    }

    public void tabellaModositUjMeccs(Merkozes meccs) {
        for (Csapat tab : tabella) {
            if (meccs.getHazai_ID() == tab.getID()) {
                tab.setLottGol(tab.getLottGol() + meccs.getHazaiGol());
                tab.setKapottGol(tab.getKapottGol() + meccs.getVendegGol());
                if (meccs.getGyozelem() == 'H') {
                    tab.setPont(tab.getPont() + 3);
                } else if (meccs.getGyozelem() == 'D') {
                    tab.setPont(tab.getPont() + 1);
                }
            }
            if (meccs.getVendeg_ID() == tab.getID()) {
                tab.setLottGol(tab.getLottGol() + meccs.getVendegGol());
                tab.setKapottGol(tab.getKapottGol() + meccs.getHazaiGol());
                if (meccs.getGyozelem() == 'V') {
                    tab.setPont(tab.getPont() + 3);
                } else if (meccs.getGyozelem() == 'D') {
                    tab.setPont(tab.getPont() + 1);
                }
            }
        }
    }

    public void tabellaTorolMeccs(String ID, String ID2) {
        int id = Integer.parseInt(ID);
        int id2 = Integer.parseInt(ID2);
        int hazaiGol = 0;
        int vendegGol = 0;
        List<Merkozes> mentMeccs = new ArrayList<>();
        for (Merkozes me : meccsek) {
            if (me.getHazai_ID() == id && me.getVendeg_ID() == id2) {
                hazaiGol = me.getHazaiGol();
                vendegGol = me.getVendegGol();
            } else {
                mentMeccs.add(me);
            }
        }
        this.meccsek = mentMeccs;
        for (Csapat tab : tabella) {
            if (tab.getID() == id) {
                tab.setLottGol(tab.getLottGol() - hazaiGol);
                tab.setKapottGol(tab.getKapottGol() - vendegGol);
                if (hazaiGol > vendegGol) {
                    tab.setPont(tab.getPont() - 3);
                } else if (hazaiGol == vendegGol) {
                    tab.setPont(tab.getPont() - 1);
                }
            } else if (tab.getID() == id2) {
                tab.setLottGol(tab.getLottGol() - vendegGol);
                tab.setKapottGol(tab.getKapottGol() - hazaiGol);
                if (hazaiGol < vendegGol) {
                    tab.setPont(tab.getPont() - 3);
                } else if (hazaiGol == vendegGol) {
                    tab.setPont(tab.getPont() - 1);
                }
            }
        }
    }

    public boolean datumEllenoriz(String datum) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(datum);
            return true;
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(rootPane, "Helytelen dátum lett megadva. (ÉÉÉÉ-HH-NN)", "Hiba!", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
    }

    public boolean numberFormatEllenoriz(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(rootPane, "Valahol nem számot adott meg!", "Hiba!", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
    }

    public boolean golokEllenoriz(String strGol, String strGol2) {
        if (numberFormatEllenoriz(strGol) == true && numberFormatEllenoriz(strGol2) == true) {
            int gol = Integer.parseInt(strGol);
            int gol2 = Integer.parseInt(strGol2);
            if (gol < 0 || gol2 < 0) {
                JOptionPane.showMessageDialog(rootPane, "A gólok száma nem lehet negatív!", "Hiba!", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean ujMeccsIdEllenoriz(String ID, String ID2) {
        boolean idLetezik = false;
        boolean id2Letezik = false;
        if (numberFormatEllenoriz(ID) == true && numberFormatEllenoriz(ID) == true) {
            int id = Integer.parseInt(ID);
            int id2 = Integer.parseInt(ID2);
            for (Merkozes me : meccsek) {
                if (me.getHazai_ID() == id && me.getVendeg_ID() == id2) {
                    JOptionPane.showMessageDialog(rootPane, "Ezek a csapatok már játszottak egymással!", "Hiba!", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
            }
            if (id != id2) {
                for (Csapat tab : tabella) {
                    if (tab.getID() == id) {
                        idLetezik = true;
                    }
                    if (tab.getID() == id2) {
                        id2Letezik = true;
                    }
                }
                if (idLetezik != true || id2Letezik != true) {
                    JOptionPane.showMessageDialog(rootPane, "Ilyen ID-vel rendelkező csapat vagy csapatok nincsenek a bajnokságban!", "Hiba!", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
                return true;
            } else {
                JOptionPane.showMessageDialog(rootPane, "Két ugyan olyan ID-vel rendelkező csapatot nem lehet felvinni!", "Hiba!", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        }
        return false;
    }

    public boolean torolModositMeccsIdEllenoriz(String ID, String ID2) {
        int id = Integer.parseInt(ID);
        int id2 = Integer.parseInt(ID2);
        for (Merkozes me : meccsek) {
            if (me.getHazai_ID() == id && me.getVendeg_ID() == id2) {
                return true;
            }
        }
        JOptionPane.showMessageDialog(rootPane, "Ilyen ID-kel rendelkező mérkőzés nem létezik!", "Hiba!", JOptionPane.INFORMATION_MESSAGE);
        return false;
    }

    public Foablak() {
        initComponents();
        setLocationRelativeTo(null);
        jlCsapatnevek.setModel(this.csapatNevek);
        dlTabella = (DefaultTableModel) jtTabella.getModel();
        dlMeccsek = (DefaultTableModel) jtMeccsek.getModel();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jdBetolt = new javax.swing.JDialog();
        jtEv = new javax.swing.JTextField();
        jlBetolt = new javax.swing.JLabel();
        jbBetolt = new javax.swing.JButton();
        jbMeccsek = new javax.swing.JButton();
        jbAdatbazisbaFeltolt = new javax.swing.JButton();
        jlMeccsek = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtTabella = new javax.swing.JTable();
        jdMeccsek = new javax.swing.JDialog();
        jbUjMeccs = new javax.swing.JButton();
        jbRegiModosit = new javax.swing.JButton();
        jlUtolsoTorol = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtMeccsek = new javax.swing.JTable();
        jdUjMeccs = new javax.swing.JDialog();
        jlDatum2 = new javax.swing.JLabel();
        jtDatum = new javax.swing.JTextField();
        jlHazai_ID2 = new javax.swing.JLabel();
        jtHazai_ID2 = new javax.swing.JTextField();
        jlVendeg_ID2 = new javax.swing.JLabel();
        jtVedeg_ID2 = new javax.swing.JTextField();
        jlGol = new javax.swing.JLabel();
        jtGol = new javax.swing.JTextField();
        jlGól3 = new javax.swing.JLabel();
        jtGol2 = new javax.swing.JTextField();
        jbFelvisz = new javax.swing.JButton();
        jdMeccsModosit = new javax.swing.JDialog();
        jlDatum3 = new javax.swing.JLabel();
        jtDatum2 = new javax.swing.JTextField();
        jlHazai_ID3 = new javax.swing.JLabel();
        jlVendeg_ID3 = new javax.swing.JLabel();
        jlGol4 = new javax.swing.JLabel();
        jlGol5 = new javax.swing.JLabel();
        jbModosit = new javax.swing.JButton();
        jtHazai_ID3 = new javax.swing.JTextField();
        jtVendég_ID3 = new javax.swing.JTextField();
        jtGol3 = new javax.swing.JTextField();
        jtGol4 = new javax.swing.JTextField();
        jdTorol = new javax.swing.JDialog();
        jlHazai_ID4 = new javax.swing.JLabel();
        jlVendeg_ID4 = new javax.swing.JLabel();
        jtTorolMeccs = new javax.swing.JTextField();
        jtTorolMeccs2 = new javax.swing.JTextField();
        jbTorol = new javax.swing.JButton();
        jdUjBajnoksag = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        jlCsapatnevek = new javax.swing.JList<>();
        jbAdatbazisLetrehoz = new javax.swing.JButton();
        jtCsapatNev = new javax.swing.JTextField();
        jbHozzaad = new javax.swing.JButton();
        jbCsapatnevTorol = new javax.swing.JButton();
        jlCsapatNev = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtAdatbazisEv = new javax.swing.JTextField();
        jlSikeres = new javax.swing.JLabel();
        jdBajnoksagTorol = new javax.swing.JDialog();
        jbBajnoksagTorol = new javax.swing.JButton();
        jlEv = new javax.swing.JLabel();
        jtEv2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jlIgenNem = new javax.swing.JLabel();
        btBetolt = new javax.swing.JButton();
        btUj = new javax.swing.JButton();
        buttonTorol = new javax.swing.JButton();

        jdBetolt.setTitle("Bajnokság állása");
        jdBetolt.setLocation(new java.awt.Point(600, 200));
        jdBetolt.setMinimumSize(new java.awt.Dimension(680, 500));
        jdBetolt.setResizable(false);

        jlBetolt.setText("Bajnokság (év):");

        jbBetolt.setText("Betölt");
        jbBetolt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBetoltActionPerformed(evt);
            }
        });

        jbMeccsek.setText("Meccsek");
        jbMeccsek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbMeccsekActionPerformed(evt);
            }
        });

        jbAdatbazisbaFeltolt.setBackground(new java.awt.Color(94, 252, 54));
        jbAdatbazisbaFeltolt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbAdatbazisbaFeltolt.setText("Ment");
        jbAdatbazisbaFeltolt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdatbazisbaFeltoltActionPerformed(evt);
            }
        });

        jlMeccsek.setText("Eddig lejátszott meccsek");

        jLabel2.setText("Változások mentése");

        jtTabella.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Helyezés", "Csapatnév", "ID", "Lőtt gól", "Kapott gól", "Pont"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jtTabella);
        if (jtTabella.getColumnModel().getColumnCount() > 0) {
            jtTabella.getColumnModel().getColumn(0).setPreferredWidth(60);
            jtTabella.getColumnModel().getColumn(1).setPreferredWidth(150);
            jtTabella.getColumnModel().getColumn(2).setPreferredWidth(30);
            jtTabella.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        javax.swing.GroupLayout jdBetoltLayout = new javax.swing.GroupLayout(jdBetolt.getContentPane());
        jdBetolt.getContentPane().setLayout(jdBetoltLayout);
        jdBetoltLayout.setHorizontalGroup(
            jdBetoltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdBetoltLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jdBetoltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jdBetoltLayout.createSequentialGroup()
                        .addComponent(jlBetolt)
                        .addGap(18, 18, 18)
                        .addComponent(jtEv, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jbBetolt, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jdBetoltLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jdBetoltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jdBetoltLayout.createSequentialGroup()
                                .addGroup(jdBetoltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdBetoltLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                                        .addGroup(jdBetoltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jbAdatbazisbaFeltolt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jbMeccsek, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jdBetoltLayout.createSequentialGroup()
                                        .addGap(38, 38, 38)
                                        .addComponent(jlMeccsek)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdBetoltLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(53, 53, 53))))))
        );
        jdBetoltLayout.setVerticalGroup(
            jdBetoltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdBetoltLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jdBetoltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlBetolt)
                    .addComponent(jtEv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBetolt))
                .addGap(18, 18, 18)
                .addGroup(jdBetoltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jdBetoltLayout.createSequentialGroup()
                        .addComponent(jlMeccsek)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbMeccsek, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbAdatbazisbaFeltolt, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE))
                .addContainerGap())
        );

        jdMeccsek.setLocation(new java.awt.Point(600, 200));
        jdMeccsek.setMinimumSize(new java.awt.Dimension(680, 500));
        jdMeccsek.setResizable(false);

        jbUjMeccs.setText("Hozzáad");
        jbUjMeccs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbUjMeccsActionPerformed(evt);
            }
        });

        jbRegiModosit.setText("Módosít");
        jbRegiModosit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRegiModositActionPerformed(evt);
            }
        });

        jlUtolsoTorol.setText("Töröl");
        jlUtolsoTorol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jlUtolsoTorolActionPerformed(evt);
            }
        });

        jtMeccsek.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Dátum", "Hazai csapat", "Vendég csapat", "H_Gól", "V_Gól", "Győzelem"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtMeccsek);
        if (jtMeccsek.getColumnModel().getColumnCount() > 0) {
            jtMeccsek.getColumnModel().getColumn(0).setPreferredWidth(100);
            jtMeccsek.getColumnModel().getColumn(1).setPreferredWidth(120);
            jtMeccsek.getColumnModel().getColumn(2).setPreferredWidth(120);
            jtMeccsek.getColumnModel().getColumn(3).setPreferredWidth(50);
            jtMeccsek.getColumnModel().getColumn(4).setPreferredWidth(50);
        }

        javax.swing.GroupLayout jdMeccsekLayout = new javax.swing.GroupLayout(jdMeccsek.getContentPane());
        jdMeccsek.getContentPane().setLayout(jdMeccsekLayout);
        jdMeccsekLayout.setHorizontalGroup(
            jdMeccsekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdMeccsekLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jdMeccsekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbRegiModosit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlUtolsoTorol, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                    .addComponent(jbUjMeccs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jdMeccsekLayout.setVerticalGroup(
            jdMeccsekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdMeccsekLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jdMeccsekLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jdMeccsekLayout.createSequentialGroup()
                        .addComponent(jbUjMeccs, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbRegiModosit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jlUtolsoTorol, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jdUjMeccs.setLocation(new java.awt.Point(800, 350));
        jdUjMeccs.setMinimumSize(new java.awt.Dimension(172, 350));

        jlDatum2.setText("Dátum");

        jlHazai_ID2.setText("Hazai_ID");

        jlVendeg_ID2.setText("Vendég_ID");

        jlGol.setText("Gól");

        jlGól3.setText("Gól");

        jbFelvisz.setText("Felvisz");
        jbFelvisz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbFelviszActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jdUjMeccsLayout = new javax.swing.GroupLayout(jdUjMeccs.getContentPane());
        jdUjMeccs.getContentPane().setLayout(jdUjMeccsLayout);
        jdUjMeccsLayout.setHorizontalGroup(
            jdUjMeccsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdUjMeccsLayout.createSequentialGroup()
                .addGroup(jdUjMeccsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jdUjMeccsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jdUjMeccsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdUjMeccsLayout.createSequentialGroup()
                                .addGroup(jdUjMeccsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtHazai_ID2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlGol))
                                .addGap(14, 14, 14))
                            .addGroup(jdUjMeccsLayout.createSequentialGroup()
                                .addComponent(jlHazai_ID2)
                                .addGap(31, 31, 31)))
                        .addGroup(jdUjMeccsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlVendeg_ID2)
                            .addComponent(jlGól3)
                            .addComponent(jtVedeg_ID2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtGol2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jdUjMeccsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jtGol, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jdUjMeccsLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jlDatum2))
                    .addGroup(jdUjMeccsLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jbFelvisz))
                    .addGroup(jdUjMeccsLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jtDatum, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jdUjMeccsLayout.setVerticalGroup(
            jdUjMeccsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdUjMeccsLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jlDatum2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtDatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jdUjMeccsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlVendeg_ID2)
                    .addComponent(jlHazai_ID2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jdUjMeccsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtVedeg_ID2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtHazai_ID2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jdUjMeccsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlGol)
                    .addComponent(jlGól3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jdUjMeccsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtGol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtGol2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jbFelvisz)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jdMeccsModosit.setLocation(new java.awt.Point(800, 350));
        jdMeccsModosit.setMinimumSize(null);
        jdMeccsModosit.setResizable(false);
        jdMeccsModosit.setSize(new java.awt.Dimension(174, 299));

        jlDatum3.setText("Dátum");

        jlHazai_ID3.setText("Hazai_ID");

        jlVendeg_ID3.setText("Vendég_ID");

        jlGol4.setText("Gól");

        jlGol5.setText("Gól");

        jbModosit.setText("Módosít");
        jbModosit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbModositActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jdMeccsModositLayout = new javax.swing.GroupLayout(jdMeccsModosit.getContentPane());
        jdMeccsModosit.getContentPane().setLayout(jdMeccsModositLayout);
        jdMeccsModositLayout.setHorizontalGroup(
            jdMeccsModositLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdMeccsModositLayout.createSequentialGroup()
                .addGroup(jdMeccsModositLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jdMeccsModositLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jdMeccsModositLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jdMeccsModositLayout.createSequentialGroup()
                                .addGroup(jdMeccsModositLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jtGol3, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                    .addComponent(jtHazai_ID3))
                                .addGap(12, 12, 12)
                                .addGroup(jdMeccsModositLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtVendég_ID3, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                    .addComponent(jtGol4)))
                            .addGroup(jdMeccsModositLayout.createSequentialGroup()
                                .addGroup(jdMeccsModositLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlHazai_ID3)
                                    .addComponent(jlGol4))
                                .addGap(32, 32, 32)
                                .addGroup(jdMeccsModositLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlGol5)
                                    .addComponent(jlVendeg_ID3)))))
                    .addGroup(jdMeccsModositLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jtDatum2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jdMeccsModositLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jbModosit))
                    .addGroup(jdMeccsModositLayout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jlDatum3)))
                .addContainerGap())
        );
        jdMeccsModositLayout.setVerticalGroup(
            jdMeccsModositLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdMeccsModositLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlDatum3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtDatum2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jdMeccsModositLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlHazai_ID3)
                    .addComponent(jlVendeg_ID3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jdMeccsModositLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtVendég_ID3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtHazai_ID3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jdMeccsModositLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlGol4)
                    .addComponent(jlGol5))
                .addGap(7, 7, 7)
                .addGroup(jdMeccsModositLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtGol3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtGol4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jbModosit)
                .addGap(0, 72, Short.MAX_VALUE))
        );

        jdTorol.setLocation(new java.awt.Point(800, 350));
        jdTorol.setMinimumSize(new java.awt.Dimension(245, 200));
        jdTorol.setSize(new java.awt.Dimension(245, 130));

        jlHazai_ID4.setText("Hazai_ID");

        jlVendeg_ID4.setText("Vendég_ID");

        jbTorol.setText("Töröl");
        jbTorol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbTorolActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jdTorolLayout = new javax.swing.GroupLayout(jdTorol.getContentPane());
        jdTorol.getContentPane().setLayout(jdTorolLayout);
        jdTorolLayout.setHorizontalGroup(
            jdTorolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdTorolLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jdTorolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlHazai_ID4)
                    .addComponent(jtTorolMeccs, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jdTorolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtTorolMeccs2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlVendeg_ID4))
                .addGap(25, 25, 25))
            .addGroup(jdTorolLayout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jbTorol)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jdTorolLayout.setVerticalGroup(
            jdTorolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdTorolLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jdTorolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlHazai_ID4)
                    .addComponent(jlVendeg_ID4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jdTorolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtTorolMeccs2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtTorolMeccs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jbTorol)
                .addGap(26, 26, 26))
        );

        jdUjBajnoksag.setLocation(new java.awt.Point(770, 350));
        jdUjBajnoksag.setMinimumSize(new java.awt.Dimension(400, 300));
        jdUjBajnoksag.setResizable(false);

        jScrollPane3.setViewportView(jlCsapatnevek);

        jbAdatbazisLetrehoz.setText("Feltölt");
        jbAdatbazisLetrehoz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdatbazisLetrehozActionPerformed(evt);
            }
        });

        jbHozzaad.setText("Felvisz");
        jbHozzaad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbHozzaadActionPerformed(evt);
            }
        });

        jbCsapatnevTorol.setText("Töröl");
        jbCsapatnevTorol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCsapatnevTorolActionPerformed(evt);
            }
        });

        jlCsapatNev.setText("Csapat neve:");

        jLabel3.setText("Bajnokság (év):");

        javax.swing.GroupLayout jdUjBajnoksagLayout = new javax.swing.GroupLayout(jdUjBajnoksag.getContentPane());
        jdUjBajnoksag.getContentPane().setLayout(jdUjBajnoksagLayout);
        jdUjBajnoksagLayout.setHorizontalGroup(
            jdUjBajnoksagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdUjBajnoksagLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jdUjBajnoksagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbHozzaad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbAdatbazisLetrehoz, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtCsapatNev, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbCsapatnevTorol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jdUjBajnoksagLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtAdatbazisEv, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))
                    .addGroup(jdUjBajnoksagLayout.createSequentialGroup()
                        .addGroup(jdUjBajnoksagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlCsapatNev)
                            .addComponent(jlSikeres, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jdUjBajnoksagLayout.setVerticalGroup(
            jdUjBajnoksagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdUjBajnoksagLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jdUjBajnoksagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jdUjBajnoksagLayout.createSequentialGroup()
                        .addGroup(jdUjBajnoksagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jtAdatbazisEv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jlCsapatNev)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtCsapatNev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbHozzaad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbCsapatnevTorol)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                        .addComponent(jlSikeres, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbAdatbazisLetrehoz))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );

        jdBajnoksagTorol.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jdBajnoksagTorol.setLocation(new java.awt.Point(850, 390));
        jdBajnoksagTorol.setMinimumSize(null);
        jdBajnoksagTorol.setResizable(false);
        jdBajnoksagTorol.setSize(new java.awt.Dimension(195, 260));

        jbBajnoksagTorol.setText("Töröl");
        jbBajnoksagTorol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBajnoksagTorolActionPerformed(evt);
            }
        });

        jlEv.setText("Év:");

        jtEv2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtEv2ActionPerformed(evt);
            }
        });

        jLabel1.setText("A törlés sikeresen végrehajtódott?");

        javax.swing.GroupLayout jdBajnoksagTorolLayout = new javax.swing.GroupLayout(jdBajnoksagTorol.getContentPane());
        jdBajnoksagTorol.getContentPane().setLayout(jdBajnoksagTorolLayout);
        jdBajnoksagTorolLayout.setHorizontalGroup(
            jdBajnoksagTorolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdBajnoksagTorolLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1))
            .addGroup(jdBajnoksagTorolLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jdBajnoksagTorolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlEv)
                    .addComponent(jtEv2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBajnoksagTorol, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdBajnoksagTorolLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlIgenNem, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
        );
        jdBajnoksagTorolLayout.setVerticalGroup(
            jdBajnoksagTorolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdBajnoksagTorolLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jlEv)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtEv2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbBajnoksagTorol)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlIgenNem, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Foci bajnokság");
        setResizable(false);

        btBetolt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btBetolt.setText("Bajnokság betöltés");
        btBetolt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBetoltActionPerformed(evt);
            }
        });

        btUj.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btUj.setText("Új bajnokság");
        btUj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btUjActionPerformed(evt);
            }
        });

        buttonTorol.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        buttonTorol.setText("Bajnokság törlés");
        buttonTorol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTorolActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonTorol, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btUj, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btBetolt, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btBetolt, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btUj, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(buttonTorol, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btBetoltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBetoltActionPerformed
        jdBetolt.setTitle("Bajnokság állása");
        jdBetolt.setVisible(true);
    }//GEN-LAST:event_btBetoltActionPerformed

    private void jbUjMeccsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbUjMeccsActionPerformed
        jdUjMeccs.setTitle("Felvitel");
        jdUjMeccs.setVisible(true);
    }//GEN-LAST:event_jbUjMeccsActionPerformed

    private void jbRegiModositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRegiModositActionPerformed
        jdMeccsModosit.setTitle("Módosít");
        jdMeccsModosit.setVisible(true);
    }//GEN-LAST:event_jbRegiModositActionPerformed

    private void jlUtolsoTorolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jlUtolsoTorolActionPerformed
        jdTorol.setTitle("Töröl");
        jdTorol.setVisible(true);
    }//GEN-LAST:event_jlUtolsoTorolActionPerformed

    private void btUjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btUjActionPerformed
        jdUjBajnoksag.setTitle("Új bajnokság");
        jdUjBajnoksag.setVisible(true);
    }//GEN-LAST:event_btUjActionPerformed

    private void buttonTorolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTorolActionPerformed
        jdBajnoksagTorol.setTitle("Bajnokság törlése");
        jdBajnoksagTorol.setVisible(true);
    }//GEN-LAST:event_buttonTorolActionPerformed

    private void jbAdatbazisLetrehozActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdatbazisLetrehozActionPerformed
        try {
            int ev = Integer.parseInt(jtAdatbazisEv.getText());
            if (ev < 1900 || ev > 2200) {
                JOptionPane.showMessageDialog(rootPane, "Rossz dátum lett megadva! 1900 és 2200 között lehet megadni szamot!", "Hiba!", JOptionPane.INFORMATION_MESSAGE);
            } else if (csapatNevek.getSize() < 2) {
                JOptionPane.showMessageDialog(rootPane, "A bajnokságban legalább 2 csapatnak szerepelnie kell!", "Hiba!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (adatbazis.adatbazisLetrehoz(jtAdatbazisEv.getText()) == true) {
                    if (adatbazis.tablakLetrehoz(jtAdatbazisEv.getText()) == true) {
                        List<String> nevek = new ArrayList<>();
                        for(int i = 0; i < csapatNevek.size(); i++){
                            nevek.add(csapatNevek.get(i));
                        }
                        if (adatbazis.tabellaFeltolt(jtAdatbazisEv.getText(), nevek) == true) {
                            jlSikeres.setText("Létrehozva");
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Hiba lépett fel a csapatnevek feltöltése során!", "Hiba!", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Nem hozta létre a táblákat!", "Hiba!", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Ez a bajnokság már létezik!", "Hiba!", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(rootPane, "Csak évszámot fogad el a program!", "Hiba!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jbAdatbazisLetrehozActionPerformed

    private void jbHozzaadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbHozzaadActionPerformed
        if (!csapatNevek.contains(jtCsapatNev.getText()) && csapatNevek.size() < 30) {
            csapatNevek.addElement(jtCsapatNev.getText());
        } else {
            JOptionPane.showMessageDialog(rootPane, "Ilyen nevű csapat már létezik vagy több csapatot adtál meg mint 30!", "Hiba!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jbHozzaadActionPerformed

    private void jbCsapatnevTorolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCsapatnevTorolActionPerformed
        if (!csapatNevek.isEmpty()) {
            csapatNevek.removeElementAt(csapatNevek.size() - 1);
        }
    }//GEN-LAST:event_jbCsapatnevTorolActionPerformed

    private void jbFelviszActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFelviszActionPerformed
        if (numberFormatEllenoriz(jtHazai_ID2.getText()) == true && numberFormatEllenoriz(jtVedeg_ID2.getText()) == true
                && datumEllenoriz(jtDatum.getText()) == true && ujMeccsIdEllenoriz(jtHazai_ID2.getText(), jtVedeg_ID2.getText()) == true
                && golokEllenoriz(jtGol.getText(), jtGol2.getText()) == true) {
            try {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date datum = format.parse(jtDatum.getText());
                java.sql.Date sqlDate = new java.sql.Date(datum.getTime());
                Csapat hazai = new Csapat();
                Csapat vendeg = new Csapat();
                for (Csapat tab : tabella) {
                    if (Integer.parseInt(jtHazai_ID2.getText()) == tab.getID()) {
                        hazai = tab;
                    }
                    if (Integer.parseInt(jtVedeg_ID2.getText()) == tab.getID()) {
                        vendeg = tab;
                    }
                }
                Merkozes meccs = new Merkozes(this.meccsek.size() + 1, sqlDate, Integer.parseInt(jtHazai_ID2.getText()), Integer.parseInt(jtVedeg_ID2.getText()),
                        Integer.parseInt(jtGol.getText()), Integer.parseInt(jtGol2.getText()), hazai, vendeg);
                meccsek.add(meccs);
                meccsRendez(meccsek);
                meccsekListaz();
                tabellaModositUjMeccs(meccs);
                tabellaRendez(tabella);
                tabellaListaz();
            } catch (ParseException ex) {
                Logger.getLogger(Foablak.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_jbFelviszActionPerformed

    private void jbTorolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbTorolActionPerformed
        if (numberFormatEllenoriz(jtTorolMeccs.getText()) == true && numberFormatEllenoriz(jtTorolMeccs2.getText()) == true) {
            if (torolModositMeccsIdEllenoriz(jtTorolMeccs.getText(), jtTorolMeccs2.getText()) == true) {
                tabellaTorolMeccs(jtTorolMeccs.getText(), jtTorolMeccs2.getText());
                meccsekListaz();
                tabellaRendez(tabella);
                tabellaListaz();
            }
        }
    }//GEN-LAST:event_jbTorolActionPerformed

    private void jbModositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbModositActionPerformed
        if (datumEllenoriz(jtDatum2.getText()) == true && numberFormatEllenoriz(jtHazai_ID3.getText()) == true && numberFormatEllenoriz(jtVendég_ID3.getText()) == true
                && torolModositMeccsIdEllenoriz(jtHazai_ID3.getText(), jtVendég_ID3.getText()) == true && golokEllenoriz(jtGol3.getText(), jtGol4.getText()) == true) {
            try {
                int idMent = 1;
                int ID = Integer.parseInt(jtHazai_ID3.getText());
                int ID2 = Integer.parseInt(jtVendég_ID3.getText());
                for (int i = 0; i < meccsek.size() - 1; i++) {
                    if (meccsek.get(i).getHazai_ID() == ID && meccsek.get(i).getVendeg_ID() == ID2) {
                        idMent = i + 1;
                    }
                }
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date datum = format.parse(jtDatum2.getText());
                java.sql.Date sqlDate = new java.sql.Date(datum.getTime());
                Csapat hazai = new Csapat();
                Csapat vendeg = new Csapat();
                for (Csapat tab : tabella) {
                    if (Integer.parseInt(jtHazai_ID3.getText()) == tab.getID()) {
                        hazai = tab;
                    }
                    if (Integer.parseInt(jtVendég_ID3.getText()) == tab.getID()) {
                        vendeg = tab;
                    }
                }
                Merkozes meccs = new Merkozes(idMent, sqlDate, Integer.parseInt(jtHazai_ID3.getText()), Integer.parseInt(jtVendég_ID3.getText()),
                        Integer.parseInt(jtGol3.getText()), Integer.parseInt(jtGol4.getText()), hazai, vendeg);
                tabellaTorolMeccs(jtHazai_ID3.getText(), jtVendég_ID3.getText());
                meccsek.add(meccs);
                tabellaModositUjMeccs(meccs);
                meccsRendez(meccsek);
                meccsekListaz();
                tabellaRendez(tabella);
                tabellaListaz();
            } catch (ParseException ex) {
                Logger.getLogger(Foablak.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jbModositActionPerformed

    private void jbBajnoksagTorolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBajnoksagTorolActionPerformed
        int opcio = JOptionPane.showConfirmDialog(this, "Bizotos törli a bajnokságot a rendszerből? (" + jtEv2.getText() + ")", "Megerősítés", JOptionPane.YES_NO_OPTION);
        if (opcio == JOptionPane.YES_OPTION) {
            if (adatbazis.adatbazisTorol(jtEv2.getText()) == true) {
                jlIgenNem.setText("Igen");
            } else {
                jlIgenNem.setText("Nem");
            }
        }
    }//GEN-LAST:event_jbBajnoksagTorolActionPerformed

    private void jtEv2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtEv2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtEv2ActionPerformed

    private void jbAdatbazisbaFeltoltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdatbazisbaFeltoltActionPerformed
        int opcio = JOptionPane.showConfirmDialog(this, "Biztosan módosítja az adatbázist?", "Megerősítés", JOptionPane.YES_NO_OPTION);
        if (opcio == JOptionPane.YES_OPTION) {
            if (adatbazis.adatbazisTabellaModosit(adatbazisNev, tabella) == true) {
                if (adatbazis.adatbazisMerkozesModosit(adatbazisNev, meccsek) != true) {
                    JOptionPane.showMessageDialog(rootPane, "Nem sikerült a Mérkőzés tábla módosítása!", "Hiba!", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Nem sikerült a Tabella módosítása!", "Hiba!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jbAdatbazisbaFeltoltActionPerformed

    private void jbMeccsekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbMeccsekActionPerformed
        if (this.tabella.isEmpty() == false) {
            jdMeccsek.setTitle("Mérkőzések");
            jdMeccsek.setVisible(true);
            meccsekListaz();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Töltsön be bajnokságot!", "Hiba!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jbMeccsekActionPerformed

    private void jbBetoltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBetoltActionPerformed
        if (adatbazis.tabellaBetolt(jtEv.getText()).size() > 0) {
            this.adatbazisNev = jtEv.getText();
            this.tabella = adatbazis.tabellaBetolt(this.adatbazisNev);
            this.tabella = tabellaRendez(tabella);
            this.meccsek = adatbazis.merkozesBetolt(this.adatbazisNev);
            this.meccsek = meccsRendez(meccsek);
            tabellaListaz();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Ilyen bajnokság nem létezik az adatbázisban! Biztosan jó évszámot adott meg?", "Hiba!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jbBetoltActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Foablak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Foablak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Foablak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Foablak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Foablak().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btBetolt;
    private javax.swing.JButton btUj;
    private javax.swing.JButton buttonTorol;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton jbAdatbazisLetrehoz;
    private javax.swing.JButton jbAdatbazisbaFeltolt;
    private javax.swing.JButton jbBajnoksagTorol;
    private javax.swing.JButton jbBetolt;
    private javax.swing.JButton jbCsapatnevTorol;
    private javax.swing.JButton jbFelvisz;
    private javax.swing.JButton jbHozzaad;
    private javax.swing.JButton jbMeccsek;
    private javax.swing.JButton jbModosit;
    private javax.swing.JButton jbRegiModosit;
    private javax.swing.JButton jbTorol;
    private javax.swing.JButton jbUjMeccs;
    private javax.swing.JDialog jdBajnoksagTorol;
    private javax.swing.JDialog jdBetolt;
    private javax.swing.JDialog jdMeccsModosit;
    private javax.swing.JDialog jdMeccsek;
    private javax.swing.JDialog jdTorol;
    private javax.swing.JDialog jdUjBajnoksag;
    private javax.swing.JDialog jdUjMeccs;
    private javax.swing.JLabel jlBetolt;
    private javax.swing.JLabel jlCsapatNev;
    private javax.swing.JList<String> jlCsapatnevek;
    private javax.swing.JLabel jlDatum2;
    private javax.swing.JLabel jlDatum3;
    private javax.swing.JLabel jlEv;
    private javax.swing.JLabel jlGol;
    private javax.swing.JLabel jlGol4;
    private javax.swing.JLabel jlGol5;
    private javax.swing.JLabel jlGól3;
    private javax.swing.JLabel jlHazai_ID2;
    private javax.swing.JLabel jlHazai_ID3;
    private javax.swing.JLabel jlHazai_ID4;
    private javax.swing.JLabel jlIgenNem;
    private javax.swing.JLabel jlMeccsek;
    private javax.swing.JLabel jlSikeres;
    private javax.swing.JButton jlUtolsoTorol;
    private javax.swing.JLabel jlVendeg_ID2;
    private javax.swing.JLabel jlVendeg_ID3;
    private javax.swing.JLabel jlVendeg_ID4;
    private javax.swing.JTextField jtAdatbazisEv;
    private javax.swing.JTextField jtCsapatNev;
    private javax.swing.JTextField jtDatum;
    private javax.swing.JTextField jtDatum2;
    private javax.swing.JTextField jtEv;
    private javax.swing.JTextField jtEv2;
    private javax.swing.JTextField jtGol;
    private javax.swing.JTextField jtGol2;
    private javax.swing.JTextField jtGol3;
    private javax.swing.JTextField jtGol4;
    private javax.swing.JTextField jtHazai_ID2;
    private javax.swing.JTextField jtHazai_ID3;
    private javax.swing.JTable jtMeccsek;
    private javax.swing.JTable jtTabella;
    private javax.swing.JTextField jtTorolMeccs;
    private javax.swing.JTextField jtTorolMeccs2;
    private javax.swing.JTextField jtVedeg_ID2;
    private javax.swing.JTextField jtVendég_ID3;
    // End of variables declaration//GEN-END:variables
}
