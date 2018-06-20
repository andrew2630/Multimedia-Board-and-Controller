package com.andrzejpudzisz.multimediaboardeng;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

class GUI extends JFrame implements ActionListener {

    JLabel title, room, open, mon, tue, wed, thu, fri, monH, tueH, wedH, thuH, friH, now, stateH;
    JLabel clock, date, stateFL, stateSL;

    GUI() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);

        makeLayout();

        setVisible(true);

        new Data(this);
    }

    private void makeLayout() {
        getContentPane().setLayout(new GridBagLayout());

        title = new JLabel("John Smith's Office", SwingConstants.CENTER);
        GridBagConstraints titleC = new GridBagConstraints();
        titleC.gridx = 0;
        titleC.gridy = 0;
        titleC.gridwidth = 4;
        titleC.gridheight = 5;
        titleC.weightx = 1.0;
        titleC.weighty = 0.25;
        titleC.fill = GridBagConstraints.BOTH;

        room = new JLabel("410", SwingConstants.CENTER);
        GridBagConstraints roomC = new GridBagConstraints();
        roomC.gridx = 0;
        roomC.gridy = 5;
        roomC.gridwidth = 4;
        roomC.gridheight = 2;
        roomC.weightx = 1.0;
        roomC.weighty = 0.1;
        roomC.fill = GridBagConstraints.BOTH;

        open = new JLabel("Open at:", SwingConstants.CENTER);
        GridBagConstraints openC = new GridBagConstraints();
        openC.gridx = 0;
        openC.gridy = 7;
        openC.gridwidth = 2;
        openC.gridheight = 1;
        openC.weightx = 0.5;
        openC.weighty = 0.05;
        openC.fill = GridBagConstraints.BOTH;

        mon = new JLabel("Monday", SwingConstants.CENTER);
        GridBagConstraints monC = new GridBagConstraints();
        monC.gridx = 0;
        monC.gridy = 8;
        monC.weightx = 0.25;
        monC.weighty = 0.05;
        monC.fill = GridBagConstraints.BOTH;

        tue = new JLabel("Tuesday", SwingConstants.CENTER);
        GridBagConstraints tueC = new GridBagConstraints();
        tueC.gridx = 0;
        tueC.gridy = 9;
        tueC.weightx = 0.25;
        tueC.weighty = 0.05;
        tueC.fill = GridBagConstraints.BOTH;

        wed = new JLabel("Wednesday", SwingConstants.CENTER);
        GridBagConstraints wedC = new GridBagConstraints();
        wedC.gridx = 0;
        wedC.gridy = 10;
        wedC.weightx = 0.25;
        wedC.weighty = 0.05;
        wedC.fill = GridBagConstraints.BOTH;

        thu = new JLabel("Thursday", SwingConstants.CENTER);
        GridBagConstraints thuC = new GridBagConstraints();
        thuC.gridx = 0;
        thuC.gridy = 11;
        thuC.weightx = 0.25;
        thuC.weighty = 0.05;
        thuC.fill = GridBagConstraints.BOTH;

        fri = new JLabel("Friday", SwingConstants.CENTER);
        GridBagConstraints friC = new GridBagConstraints();
        friC.gridx = 0;
        friC.gridy = 12;
        friC.weightx = 0.25;
        friC.weighty = 0.05;
        friC.fill = GridBagConstraints.BOTH;

        monH = new JLabel("9:00 - 15:00", SwingConstants.CENTER);
        GridBagConstraints monHC = new GridBagConstraints();
        monHC.gridx = 1;
        monHC.gridy = 8;
        monHC.weightx = 0.25;
        monHC.weighty = 0.05;
        monHC.fill = GridBagConstraints.BOTH;

        tueH = new JLabel("9:00 - 15:00", SwingConstants.CENTER);
        GridBagConstraints tueHC = new GridBagConstraints();
        tueHC.gridx = 1;
        tueHC.gridy = 9;
        tueHC.weightx = 0.25;
        tueHC.weighty = 0.05;
        tueHC.fill = GridBagConstraints.BOTH;

        wedH = new JLabel("9:00 - 15:00", SwingConstants.CENTER);
        GridBagConstraints wedHC = new GridBagConstraints();
        wedHC.gridx = 1;
        wedHC.gridy = 10;
        wedHC.weightx = 0.25;
        wedHC.weighty = 0.05;
        wedHC.fill = GridBagConstraints.BOTH;

        thuH = new JLabel("CLOSED", SwingConstants.CENTER);
        GridBagConstraints thuHC = new GridBagConstraints();
        thuHC.gridx = 1;
        thuHC.gridy = 11;
        thuHC.weightx = 0.25;
        thuHC.weighty = 0.05;
        thuHC.fill = GridBagConstraints.BOTH;

        friH = new JLabel("9:00 - 15:00", SwingConstants.CENTER);
        GridBagConstraints friHC = new GridBagConstraints();
        friHC.gridx = 1;
        friHC.gridy = 12;
        friHC.weightx = 0.25;
        friHC.weighty = 0.05;
        friHC.fill = GridBagConstraints.BOTH;

        now = new JLabel("Now:", SwingConstants.CENTER);
        GridBagConstraints nowC = new GridBagConstraints();
        nowC.gridx = 0;
        nowC.gridy = 13;
        nowC.gridheight = 2;
        nowC.weightx = 0.25;
        nowC.weighty = 0.1;
        nowC.fill = GridBagConstraints.BOTH;

        stateH = new JLabel("OPEN", SwingConstants.CENTER);
        GridBagConstraints stateHC = new GridBagConstraints();
        stateHC.gridx = 1;
        stateHC.gridy = 13;
        stateHC.gridheight = 2;
        stateHC.weightx = 0.25;
        stateHC.weighty = 0.1;
        stateHC.fill = GridBagConstraints.BOTH;

        clock = new JLabel("", SwingConstants.CENTER);
        GridBagConstraints clockC = new GridBagConstraints();
        clockC.gridx = 2;
        clockC.gridy = 7;
        clockC.gridwidth = 2;
        clockC.gridheight = 5;
        clockC.weightx = 0.5;
        clockC.weighty = 0.25;
        clockC.fill = GridBagConstraints.BOTH;

        date = new JLabel("", SwingConstants.CENTER);
        GridBagConstraints dateC = new GridBagConstraints();
        dateC.gridx = 2;
        dateC.gridy = 12;
        dateC.gridwidth = 2;
        dateC.gridheight = 3;
        dateC.weightx = 0.5;
        dateC.weighty = 0.15;
        dateC.fill = GridBagConstraints.BOTH;

        stateFL = new JLabel("YOU CAN ENTER", SwingConstants.CENTER);
        GridBagConstraints stateFLC = new GridBagConstraints();
        stateFLC.gridx = 0;
        stateFLC.gridy = 15;
        stateFLC.gridwidth = 4;
        stateFLC.gridheight = 3;
        stateFLC.weightx = 1.0;
        stateFLC.weighty = 0.15;
        stateFLC.fill = GridBagConstraints.BOTH;

        stateSL = new JLabel("NO CLIENTS", SwingConstants.CENTER);
        GridBagConstraints stateSLC = new GridBagConstraints();
        stateSLC.gridx = 0;
        stateSLC.gridy = 18;
        stateSLC.gridwidth = 4;
        stateSLC.gridheight = 2;
        stateSLC.weightx = 1.0;
        stateSLC.weighty = 0.1;
        stateSLC.fill = GridBagConstraints.BOTH;

        LinkedList<JLabel> daysHours = new LinkedList<>();
        daysHours.add(mon);
        daysHours.add(tue);
        daysHours.add(wed);
        daysHours.add(thu);
        daysHours.add(fri);
        daysHours.add(monH);
        daysHours.add(tueH);
        daysHours.add(wedH);
        daysHours.add(thuH);
        daysHours.add(friH);
        daysHours.add(open);
        daysHours.add(now);
        daysHours.add(stateH);
        daysHours.add(room);

        LinkedList<JLabel> otherLabels = new LinkedList<>();
        otherLabels.add(title);
        otherLabels.add(clock);
        otherLabels.add(date);
        otherLabels.add(stateFL);
        otherLabels.add(stateSL);

        getContentPane().add(title, titleC);
        getContentPane().add(room, roomC);
        getContentPane().add(open, openC);
        getContentPane().add(mon, monC);
        getContentPane().add(monH, monHC);
        getContentPane().add(tue, tueC);
        getContentPane().add(tueH, tueHC);
        getContentPane().add(wed, wedC);
        getContentPane().add(wedH, wedHC);
        getContentPane().add(thu, thuC);
        getContentPane().add(thuH, thuHC);
        getContentPane().add(fri, friC);
        getContentPane().add(friH, friHC);
        getContentPane().add(now, nowC);
        getContentPane().add(stateH, stateHC);
        getContentPane().add(clock, clockC);
        getContentPane().add(date, dateC);
        getContentPane().add(stateFL, stateFLC);
        getContentPane().add(stateSL, stateSLC);

        for(JLabel d : daysHours) {
            d.setOpaque(true);
            d.setFont(new Font("Roboto", Font.BOLD, 24));
        }

        for(JLabel l : otherLabels) {
            l.setOpaque(true);
            l.setFont(new Font("Roboto", Font.PLAIN, 24));
        }

        title.setFont(title.getFont().deriveFont(80.0f));
        room.setFont(room.getFont().deriveFont(40.0f));
        now.setFont(now.getFont().deriveFont(35.0f));
        stateH.setFont(stateH.getFont().deriveFont(35.0f));
        stateH.setForeground(new Color(255,255,255));
        clock.setFont(clock.getFont().deriveFont(120.0f));
        date.setFont(date.getFont().deriveFont(40.0f));
        stateFL.setFont(stateFL.getFont().deriveFont(65.0f));
        stateFL.setForeground(new Color(255,255,255));
        stateSL.setFont(stateSL.getFont().deriveFont(40.0f));
        stateSL.setForeground(new Color(255,255,255));

        title.setBackground(new Color(230,230,230));
        room.setBackground(new Color(230, 230, 230));
        open.setBackground(new Color(230, 200, 130));
        now.setBackground(new Color(230,200,130));
        clock.setBackground(new Color(240,230,200));
        date.setBackground(new Color(240,230,200));
        stateH.setBackground(new Color(0,150,0));
        stateFL.setBackground(new Color(0, 200, 0));
        stateSL.setBackground(new Color(0,200,0));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
