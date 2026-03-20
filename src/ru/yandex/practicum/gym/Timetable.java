package ru.yandex.practicum.gym;

import com.sun.source.tree.Tree;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();
    private HashMap<DayOfWeek, List<TrainingSession>> trainingsPerWeek = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        if (!timetable.containsKey(trainingSession.getDayOfWeek())) {
            timetable.put(trainingSession.getDayOfWeek(), new TreeMap<>());
        }

        TreeMap<TimeOfDay, List<TrainingSession>> timetablePerDay = timetable.get(trainingSession.getDayOfWeek());

        if (!timetablePerDay.containsKey(trainingSession.getTimeOfDay())) {
            timetablePerDay.put(trainingSession.getTimeOfDay(), new ArrayList<>());
        }

        timetablePerDay.get(trainingSession.getTimeOfDay()).add(trainingSession);

        List<TrainingSession> trainingsPerDay = new ArrayList<>();
        for (TimeOfDay timeOfDay : timetablePerDay.navigableKeySet()) {
            trainingsPerDay.addAll(timetablePerDay.get(timeOfDay));
        }

        trainingsPerWeek.put(trainingSession.getDayOfWeek(), trainingsPerDay);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        if (!trainingsPerWeek.containsKey(dayOfWeek)) {
            return new ArrayList<>();
        }
        return trainingsPerWeek.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (!timetable.containsKey(dayOfWeek)) {
            return new ArrayList<>();
        }

        if (!timetable.get(dayOfWeek).containsKey(timeOfDay)) {
            return new ArrayList<>();
        }

        return timetable.get(dayOfWeek).get(timeOfDay);
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        HashMap<String, CounterOfTrainings> countsByCoaches = new HashMap<>();

        for (List<TrainingSession> trainingsPerDay : trainingsPerWeek.values()) {
            for (TrainingSession trainingSession : trainingsPerDay) {
                Coach coach = trainingSession.getCoach();
                String fullName = coach.getSurname() + " " + coach.getName() + " " + coach.getMiddleName();

                if (!countsByCoaches.containsKey(fullName)) {
                    countsByCoaches.put(fullName, new CounterOfTrainings(coach, 1));
                } else {
                    countsByCoaches.get(fullName).addCount();
                }
            }
        }

        List<CounterOfTrainings> result = new ArrayList<>(countsByCoaches.values());
        result.sort((a, b) -> b.getCount() - a.getCount());
        return result;
    }
}
