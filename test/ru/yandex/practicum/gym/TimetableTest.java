package ru.yandex.practicum.gym;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size()); //Проверить, что за понедельник вернулось одно занятие
        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size()); //Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size()); // Проверить, что за понедельник вернулось одно занятие

        assertEquals(2, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size()); // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        assertSame(thursdayChildTrainingSession, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).get(0));
        assertSame(thursdayAdultTrainingSession, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).get(1));

        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());// Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size()); //Проверить, что за понедельник в 13:00 вернулось одно занятие
        assertEquals(0, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0)).size()); //Проверить, что за понедельник в 14:00 не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeMultipleSessionsSameTime() {
        Timetable timetable = new Timetable();

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coachChild = new Coach("Васильев", "Николай", "Сергеевич");
        Group groupAdult = new Group("Пилатес", Age.ADULT, 60);
        Coach coachAdult = new Coach("Иванова", "Татьяна", "Григорьевна");

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coachChild, DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession mondayAdultTrainingSession = new TrainingSession(groupAdult, coachAdult, DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayAdultTrainingSession);
        timetable.addNewTrainingSession(mondayChildTrainingSession);

        assertEquals(2, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(10, 0)).size()); // Проверить, что за понедельник в 10:00 вернулось два занятия
    }

    @Test
    void testForEmptyTimetable() { // Проверим, как работают методы с полностью пустым расписанием
        Timetable timetable = new Timetable();

        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        assertEquals(0, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(10, 0)).size());
        assertEquals(0, timetable.getCountByCoaches().size());
    }

    @Test
    void testGetCountByCoachesSingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        assertEquals(1, timetable.getCountByCoaches().size()); // Проверить, что вернулся всего 1 тренер
    }

    @Test
    void testGetCountByCoachesMultipleSessions() {
        Timetable timetable = new Timetable();

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coachChild = new Coach("Васильев", "Николай", "Сергеевич");
        Group groupAdult = new Group("Пилатес", Age.ADULT, 60);
        Coach coachAdult = new Coach("Иванова", "Татьяна", "Григорьевна");

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coachChild, DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession mondayAdultTrainingSession = new TrainingSession(groupAdult, coachAdult, DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession tuesdayChildTrainingSession = new TrainingSession(groupChild, coachChild, DayOfWeek.TUESDAY, new TimeOfDay(15, 0));
        TrainingSession oneMoreTuesdayChildTrainingSessions = new TrainingSession(groupChild, coachChild, DayOfWeek.TUESDAY, new TimeOfDay(17, 0));

        timetable.addNewTrainingSession(mondayAdultTrainingSession);
        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(tuesdayChildTrainingSession);
        timetable.addNewTrainingSession(oneMoreTuesdayChildTrainingSessions);


        assertEquals(2, timetable.getCountByCoaches().size()); // Проверить, что вернулось 2 тренера
        assertSame(coachChild, timetable.getCountByCoaches().get(0).getCoach()); // Проверить, что тренера возвращаются с правильном порядке: сначала тот, у которого больше сессий
        assertEquals(3, timetable.getCountByCoaches().get(0).getCount()); // Проверить корректность работы счетчика тренировок
    }

    @Test
    void testGetCountByCoachesSameFullName() {
        Timetable timetable = new Timetable();

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coachChild = new Coach("Васильев", "Николай", "Сергеевич");
        Group groupAdult = new Group("Пилатес", Age.ADULT, 60);
        Coach coachAdult = new Coach("Иванова", "Татьяна", "Григорьевна");
        Coach sameCoachAdult = new Coach("Иванова", "Татьяна", "Григорьевна");

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coachChild, DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession mondayAdultTrainingSession = new TrainingSession(groupAdult, coachAdult, DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession tuesdayAdultTrainingSession = new TrainingSession(groupAdult, sameCoachAdult, DayOfWeek.TUESDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(mondayAdultTrainingSession);
        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(tuesdayAdultTrainingSession);

        assertEquals(2, timetable.getCountByCoaches().get(0).getCount()); // Проверить корректность работы счетчика и сортировки, если один тренер
                                                                                   // подается как разный объект
    }

    @Test
    void testGetCountByCoachesSameCount() {
        Timetable timetable = new Timetable();

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coachChild = new Coach("Васильев", "Николай", "Сергеевич");
        Group groupAdult = new Group("Пилатес", Age.ADULT, 60);
        Coach coachAdult = new Coach("Иванова", "Татьяна", "Григорьевна");

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coachChild, DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession mondayAdultTrainingSession = new TrainingSession(groupAdult, coachAdult, DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession tuesdayChildTrainingSession = new TrainingSession(groupChild, coachChild, DayOfWeek.TUESDAY, new TimeOfDay(15, 0));
        TrainingSession tuesdayAdultTrainingSession = new TrainingSession(groupAdult, coachAdult, DayOfWeek.TUESDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(mondayAdultTrainingSession);
        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(tuesdayAdultTrainingSession);
        timetable.addNewTrainingSession(tuesdayChildTrainingSession);

        assertEquals(2, timetable.getCountByCoaches().size());            // Проверить, что метод не ломает счетчик, когда значения равны
        assertEquals(2, timetable.getCountByCoaches().get(0).getCount());
        assertEquals(2, timetable.getCountByCoaches().get(1).getCount());
    }
}
