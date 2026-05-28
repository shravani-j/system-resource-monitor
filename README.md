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
