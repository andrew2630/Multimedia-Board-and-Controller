package com.andrzejpudzisz.multimediaboardeng;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Data {

    private Server server;

    GUI gui;
    private String hours, minutes, seconds, dayOfWeek, day, month, year;
    private String monSHour, monFHour, tueSHour, tueFHour, wedSHour, wedFHour, thuSHour, thuFHour, friSHour, friFHour;

    /** Server data */
    private Color bgColor;
    private String openFL, openSL;
    private Boolean mon, tue, wed, thu, fri;

    private String h1;
    private String h2;
    private String h3;
    private String h4;
    private String h5;
    /***************/

    Data(GUI gui) {/*
        try {
            Runtime.getRuntime().exec("taskkill /F /IM explorer.exe").waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        this.gui = gui;

        openFL = " ";
        openSL = " ";
        bgColor = Color.LIGHT_GRAY;
        mon = true;
        tue = true;
        wed = true;
        thu = true;
        fri = true;
        h1 = "9:00 - 15:00";
        h2 = "9:00 - 15:00";
        h3 = "9:00 - 15:00";
        h4 = "9:00 - 15:00";
        h5 = "9:00 - 15:00";

        loadData();

        int timeDelay1 = 1000;
        int timeDelay2 = 20000;
        ActionListener time1 = (e) -> {
            hours = LocalDateTime.now().getHour() > 9 ? ""+LocalDateTime.now().getHour() : "0"+LocalDateTime.now().getHour();
            minutes = LocalDateTime.now().getMinute() > 9 ? ""+LocalDateTime.now().getMinute() : "0"+LocalDateTime.now().getMinute();
            seconds = LocalDateTime.now().getSecond() > 9 ? ""+LocalDateTime.now().getSecond() : "0"+LocalDateTime.now().getSecond();
            this.gui.clock.setText(hours + ":" + minutes + ":" + seconds);
            setState();
        };

        ActionListener time2 = (e) -> {
            switch(LocalDateTime.now().getDayOfWeek()) {
                case MONDAY:
                    dayOfWeek = "Monday";
                    break;
                case TUESDAY:
                    dayOfWeek = "Tuesday";
                    break;
                case WEDNESDAY:
                    dayOfWeek = "Wednesday";
                    break;
                case THURSDAY:
                    dayOfWeek = "Thursday";
                    break;
                case FRIDAY:
                    dayOfWeek = "Friday";
                    break;
                case SATURDAY:
                    dayOfWeek = "Saturday";
                    break;
                case SUNDAY:
                    dayOfWeek = "Sunday";
                    break;
            }
            day = LocalDateTime.now().getDayOfMonth() + "";
            switch(LocalDateTime.now().getMonthValue()) {
                case 1:
                    month = "January";
                    break;
                case 2:
                    month = "February";
                    break;
                case 3:
                    month = "March";
                    break;
                case 4:
                    month = "April";
                    break;
                case 5:
                    month = "May";
                    break;
                case 6:
                    month = "June";
                    break;
                case 7:
                    month = "July";
                    break;
                case 8:
                    month = "August";
                    break;
                case 9:
                    month = "September";
                    break;
                case 10:
                    month = "October";
                    break;
                case 11:
                    month = "November";
                    break;
                case 12:
                    month = "December";
                    break;
            }
            year = LocalDateTime.now().getYear() + "";
            this.gui.date.setText(dayOfWeek + ", " + day + " " + month + " " + year);

            checkActualState();
            saveData();
        };

        new Timer(timeDelay1, time1).start();
        new Timer(timeDelay2, time2).start();

        server = new Server(this);
    }

    private Boolean checkInternetConnection() {
        try {
            URL url = new URL("http://www.google.com");
            URLConnection connection = url.openConnection();
            connection.connect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String[] hoursParser(String data) {
        String[] str = new String[2];
        Pattern stringWith2Numbers = Pattern.compile("(\\d+)\\D+(\\d+)\\D*(\\d+)\\D+(\\d+)");
        Matcher matcher = stringWith2Numbers.matcher(data);
        if (!matcher.matches()) {
            str[0] = "0";
            str[1] = "0";
        } else {
            str[0] = matcher.group(1) + ":" + matcher.group(2);
            str[1] = matcher.group(3) + ":" + matcher.group(4);
        }

        return str;
    }

    private void setOpenHours(int n, String startHourTxt, String finishHourTxt) {
        String h = (startHourTxt == "0" && finishHourTxt == "0") ? ("CLOSED") : (startHourTxt + " - " + finishHourTxt);
        switch(n) {
            case 1:
                h1 = h;
                break;
            case 2:
                h2 = h;
                break;
            case 3:
                h3 = h;
                break;
            case 4:
                h4 = h;
                break;
            case 5:
                h5 = h;
                break;
        }
    }

    private Boolean setLabelText(JLabel label, String str) {
        Boolean day;
        if(!str.equals("0 - 0")) {
            label.setText(str);
            day = true;
        } else {
            label.setText("CLOSED");
            day = false;
        }
        return day;
    }

    private void setState() {
        if(checkInternetConnection()) {
            gui.stateFL.setText(openFL);
            gui.stateSL.setText(openSL);
            gui.stateFL.setBackground(bgColor);
            gui.stateSL.setBackground(bgColor);
        } else {
            gui.stateFL.setText(" ");
            gui.stateSL.setText("NO INTERNET CONNECTION");
            gui.stateFL.setBackground(new Color(200,200,200));
            gui.stateSL.setBackground(new Color(200,200,200));
        }

        monSHour = hoursParser(h1)[0];
        monFHour = hoursParser(h1)[1];
        tueSHour = hoursParser(h2)[0];
        tueFHour = hoursParser(h2)[1];
        wedSHour = hoursParser(h3)[0];
        wedFHour = hoursParser(h3)[1];
        thuSHour = hoursParser(h4)[0];
        thuFHour = hoursParser(h4)[1];
        friSHour = hoursParser(h5)[0];
        friFHour = hoursParser(h5)[1];
        setOpenHours(1, monSHour, monFHour);
        setOpenHours(2, tueSHour, tueFHour);
        setOpenHours(3, wedSHour, wedFHour);
        setOpenHours(4, thuSHour, thuFHour);
        setOpenHours(5, friSHour, friFHour);

        mon = setLabelText(gui.monH, monSHour + " - " + monFHour);
        tue = setLabelText(gui.tueH, tueSHour + " - " + tueFHour);
        wed = setLabelText(gui.wedH, wedSHour + " - " + wedFHour);
        thu = setLabelText(gui.thuH, thuSHour + " - " + thuFHour);
        fri = setLabelText(gui.friH, friSHour + " - " + friFHour);
    }

    private void checkActualState() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String sHour = "", fHour = "";
        Boolean open = false;

        switch(LocalDateTime.now().getDayOfWeek()) {
            case MONDAY:
                sHour = monSHour;
                fHour = monFHour;
                open = mon;
                break;
            case TUESDAY:
                sHour = tueSHour;
                fHour = tueFHour;
                open = tue;
                break;
            case WEDNESDAY:
                sHour = wedSHour;
                fHour = wedFHour;
                open = wed;
                break;
            case THURSDAY:
                sHour = thuSHour;
                fHour = thuFHour;
                open = thu;
                break;
            case FRIDAY:
                sHour = friSHour;
                fHour = friFHour;
                open = fri;
                break;
            case SATURDAY:
                open = false;
                break;
            case SUNDAY:
                open = false;
                break;
        }

        try {
            if(open && sdf.parse(hours + ":" + minutes).compareTo(sdf.parse(sHour)) >= 0 &&
                    sdf.parse(hours + ":" + minutes).compareTo(sdf.parse(fHour)) < 0) {

                gui.stateH.setText("OPEN");
                gui.stateH.setBackground(new Color(0,150,0));
            } else {
                gui.stateH.setText("CLOSED");
                gui.stateH.setBackground(new Color(150,0,0));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    protected void finalize() throws Throwable {
        // Invoke the finalizer of our superclass
        // We haven't discussed superclasses or this syntax yet
        super.finalize();
        server.onDestroy();
    }

    private void saveData() {
        try {
            FileWriter fstream = new FileWriter("data.txt");
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(openFL + "&" + openSL + "&" + bgColor.getRGB()
                    + "&" + mon.toString()+ "&" + tue.toString()+ "&" + wed.toString()+ "&" + thu.toString()+ "&" + fri.toString()
                    + "&" + h1 + "&" + h2 + "&" + h3 + "&" + h4 + "&" + h5);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        try {
            File file = new File("data.txt");
            BufferedReader in = new BufferedReader(new FileReader(file));
            String str;
            str = in.readLine();
            in.close();
            String[] parts = str.split("&");
            openFL = parts[0];
            openSL = parts[1];
            bgColor = Color.decode(parts[2]);
            mon = Boolean.getBoolean(parts[3]);
            tue = Boolean.getBoolean(parts[4]);
            wed = Boolean.getBoolean(parts[5]);
            thu = Boolean.getBoolean(parts[6]);
            fri = Boolean.getBoolean(parts[7]);
            h1 = parts[8];
            h2 = parts[9];
            h3 = parts[10];
            h4 = parts[11];
            h5 = parts[12];

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Color getBgColor() {
        return bgColor;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public String getOpenFL() {
        return openFL;
    }

    public void setOpenFL(String openFL) {
        this.openFL = openFL;
    }

    public String getOpenSL() {
        return openSL;
    }

    public void setOpenSL(String openSL) {
        this.openSL = openSL;
    }

    public String getH1() {
        return h1;
    }

    public void setH1(String h1) {
        this.h1 = h1;
    }

    public String getH2() {
        return h2;
    }

    public void setH2(String h2) {
        this.h2 = h2;
    }

    public String getH3() {
        return h3;
    }

    public void setH3(String h3) {
        this.h3 = h3;
    }

    public String getH4() {
        return h4;
    }

    public void setH4(String h4) {
        this.h4 = h4;
    }

    public String getH5() {
        return h5;
    }

    public void setH5(String h5) {
        this.h5 = h5;
    }
}
