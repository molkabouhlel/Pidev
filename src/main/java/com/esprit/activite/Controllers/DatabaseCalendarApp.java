package com.esprit.activite.Controllers;

import com.calendarfx.model.*;
import com.calendarfx.view.CalendarView;
import com.esprit.activite.utils.DataSource;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DatabaseCalendarApp extends Application {
    private Connection cnx = DataSource.getInstance().getConnection();

    @Override
    public void start(Stage primaryStage) {
        CalendarView calendarView = new CalendarView();
        Calendar calendar = createCalendarFromDatabase();

        CalendarSource calendarSource = new CalendarSource("My Calendars");
        calendarSource.getCalendars().addAll(calendar);

        calendarView.getCalendarSources().addAll(calendarSource);

        Scene scene = new Scene(calendarView);
        primaryStage.setTitle("Database Calendar App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Calendar createCalendarFromDatabase() {
        Calendar calendar = new Calendar("Rendez-Vous");
        calendar.setStyle(Calendar.Style.STYLE1);

        String req = "SELECT date_rv, id_coach FROM rendez_vous";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("date_rv");
                LocalDateTime eventDateTime = timestamp.toLocalDateTime();
                String coach = Integer.toString(rs.getInt("id_coach"));
                Entry<String> entry = new Entry<>("rendez-vous");
                entry.changeStartDate(eventDateTime.toLocalDate());
                entry.changeStartTime(eventDateTime.toLocalTime());
                entry.changeEndTime(eventDateTime.toLocalTime().plusHours(1));


                calendar.addEntry(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return calendar;
    }


}
