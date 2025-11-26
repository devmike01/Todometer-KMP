package dev.sergiobelda.todometer.common.reminder


actual class TodoAlarmManager (){
    actual fun set(timeInSeconds: Long) {

    }

//    func createReminderWithAlarm(at date: Date, title: String) {
//        let eventStore = EKEventStore()
//
//        eventStore.requestAccess(to: .reminder) { (granted, error) in
//            if granted && error == nil {
//                let reminder = EKReminder(eventStore: eventStore)
//                reminder.title = title
//                reminder.calendar = eventStore.defaultCalendarForNewReminders()
//
//                let alarm = EKAlarm(absoluteDate: date)
//                reminder.addAlarm(alarm)
//
//                do {
//                    try eventStore.save(reminder, commit: true)
//                        print("Reminder with alarm created successfully.")
//                    } catch let error {
//                        print("Error saving reminder: \(error.localizedDescription)")
//                    }
//                } else {
//                print("Access to reminders denied or error: \(error?.localizedDescription ?? "Unknown error")")
//            }
//        }
//    }
}