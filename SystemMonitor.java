import com.sun.management.OperatingSystemMXBean;
import java.awt.*;
import java.io.File;
import java.lang.management.ManagementFactory;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

public class SystemMonitor {

    public static void main(String[] args) {

        JFrame frame = new JFrame("System Resource Monitor");

        frame.setSize(900, 700);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setBackground(new Color(15, 23, 42));

        frame.setLayout(new GridLayout(8, 1, 15, 15));

        // ================= TITLE =================

        JLabel titleLabel = new JLabel(
                "LIVE SYSTEM RESOURCE MONITOR",
                SwingConstants.CENTER
        );

        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));

        titleLabel.setForeground(new Color(56, 189, 248));

        // ================= CPU =================

        JLabel cpuLabel = new JLabel(
                "CPU Usage",
                SwingConstants.CENTER
        );

        cpuLabel.setFont(new Font("Arial", Font.BOLD, 20));

        cpuLabel.setForeground(Color.WHITE);

        JProgressBar cpuBar = new JProgressBar(0, 100);

        cpuBar.setStringPainted(true);

        cpuBar.setForeground(new Color(59, 130, 246));

        // ================= RAM =================

        JLabel ramLabel = new JLabel(
                "RAM Usage",
                SwingConstants.CENTER
        );

        ramLabel.setFont(new Font("Arial", Font.BOLD, 20));

        ramLabel.setForeground(Color.WHITE);

        JProgressBar ramBar = new JProgressBar(0, 100);

        ramBar.setStringPainted(true);

        ramBar.setForeground(new Color(34, 197, 94));

        // ================= DISK =================

        JLabel diskLabel = new JLabel(
                "Disk Usage",
                SwingConstants.CENTER
        );

        diskLabel.setFont(new Font("Arial", Font.BOLD, 20));

        diskLabel.setForeground(Color.WHITE);

        JProgressBar diskBar = new JProgressBar(0, 100);

        diskBar.setStringPainted(true);

        diskBar.setForeground(new Color(249, 115, 22));

        // ================= ALERT =================

        JLabel alertLabel = new JLabel(
                "System Status: STABLE",
                SwingConstants.CENTER
        );

        alertLabel.setFont(new Font("Arial", Font.BOLD, 24));

        alertLabel.setForeground(new Color(34, 197, 94));

        // ================= ADD COMPONENTS =================

        frame.add(titleLabel);

        frame.add(cpuLabel);

        frame.add(cpuBar);

        frame.add(ramLabel);

        frame.add(ramBar);

        frame.add(diskLabel);

        frame.add(diskBar);

        frame.add(alertLabel);

        frame.setVisible(true);

        // ================= SYSTEM ACCESS =================

        OperatingSystemMXBean osBean =
                (OperatingSystemMXBean)
                ManagementFactory.getOperatingSystemMXBean();

        // ================= GRAPH DATA =================

        List<Double> cpuHistory = new ArrayList<>();

        List<Double> ramHistory = new ArrayList<>();

        // ================= CHART =================

        XYChart chart = new XYChartBuilder()
                .width(700)
                .height(400)
                .title("System Usage Trends")
                .xAxisTitle("Time")
                .yAxisTitle("Usage %")
                .build();

        chart.getStyler().setChartBackgroundColor(
                new Color(15, 23, 42)
        );

        chart.getStyler().setPlotBackgroundColor(
                new Color(15, 23, 42)
        );

        chart.getStyler().setChartFontColor(Color.WHITE);

        chart.getStyler().setLegendPosition(
                Styler.LegendPosition.InsideNE
        );

        chart.addSeries(
                "CPU",
                new double[]{0},
                new double[]{0}
        );

        chart.addSeries(
                "RAM",
                new double[]{0},
                new double[]{0}
        );

        SwingWrapper<XYChart> swingWrapper =
                new SwingWrapper<>(chart);

        swingWrapper.displayChart();

        // ================= LIVE LOOP =================

        while (true) {

            // CPU

            double cpuLoad = osBean.getCpuLoad() * 100;

            // RAM

            long totalMemory = osBean.getTotalMemorySize();

            long freeMemory = osBean.getFreeMemorySize();

            long usedMemory = totalMemory - freeMemory;

            int ramUsagePercent =
                    (int)((usedMemory * 100) / totalMemory);

            // DISK

            File disk = new File("C:");

            long totalDisk = disk.getTotalSpace();

            long freeDisk = disk.getFreeSpace();

            long usedDisk = totalDisk - freeDisk;

            int diskUsagePercent =
                    (int)((usedDisk * 100) / totalDisk);

            // ================= UPDATE BARS =================

            cpuBar.setValue((int) cpuLoad);

            cpuBar.setString(
                    String.format("%.2f%%", cpuLoad)
            );

            ramBar.setValue(ramUsagePercent);

            ramBar.setString(
                    String.format(
                            "%.2f GB / %.2f GB",
                            usedMemory / 1e9,
                            totalMemory / 1e9
                    )
            );

            diskBar.setValue(diskUsagePercent);

            diskBar.setString(
                    String.format(
                            "%.2f GB / %.2f GB",
                            usedDisk / 1e9,
                            totalDisk / 1e9
                    )
            );

            // ================= ALERTS =================

            if (cpuLoad > 80 || ramUsagePercent > 90) {

                alertLabel.setText(
                        "System Status: CRITICAL"
                );

                alertLabel.setForeground(Color.RED);

            }

            else if (cpuLoad > 60 || ramUsagePercent > 70) {

                alertLabel.setText(
                        "System Status: WARNING"
                );

                alertLabel.setForeground(Color.ORANGE);

            }

            else {

                alertLabel.setText(
                        "System Status: STABLE"
                );

                alertLabel.setForeground(
                        new Color(34, 197, 94)
                );
            }

            // ================= GRAPH HISTORY =================

            cpuHistory.add(cpuLoad);

            ramHistory.add((double) ramUsagePercent);

            if (cpuHistory.size() > 20) {

                cpuHistory.remove(0);

                ramHistory.remove(0);
            }

            // ================= UPDATE GRAPH =================

            chart.updateXYSeries(
                    "CPU",
                    null,
                    cpuHistory,
                    null
            );

            chart.updateXYSeries(
                    "RAM",
                    null,
                    ramHistory,
                    null
            );

            swingWrapper.repaintChart();

            // ================= REFRESH =================

            try {

                Thread.sleep(2000);

            }

            catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }
}