# System Resource Monitor

A simple Java Swing application that displays live CPU, RAM, and disk usage with visual progress bars and a small trend chart.

## Features

- Real-time CPU usage monitoring
- Real-time RAM usage and memory totals
- Real-time disk usage for the C: drive
- Live status alerts:
  - STABLE
  - WARNING
  - CRITICAL
- Graph view for CPU and RAM trends
## Screenshots
<img width="1919" height="1000" alt="image" src="https://github.com/user-attachments/assets/b0e07e67-9476-46e7-ae7c-b8fde39cf030" />
<img width="1919" height="1010" alt="image" src="https://github.com/user-attachments/assets/f3d1a236-8bb8-4644-a4ff-81d885218650" />

## Requirements

- Java JDK 8 or newer
- The included XChart library in `lib/xchart-3.8.8.jar`

## Build

From the project root, compile the program with:

```bash
javac -cp lib/xchart-3.8.8.jar SystemMonitor.java
```

## Run

Run the program with:

```bash
java -cp ".;lib/xchart-3.8.8.jar" SystemMonitor
```

## Notes

- The monitor refreshes every 2 seconds.
- The GUI window uses a dark theme and updates its bars and chart continuously while the program is running.
- The disk usage is measured from the current system drive (`C:`).
