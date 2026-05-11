import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

public class SystemMonitor {

    public static void main(String[] args) {

        OperatingSystemMXBean osBean =
                (OperatingSystemMXBean)
                ManagementFactory.getOperatingSystemMXBean();

        double cpuLoad = osBean.getCpuLoad() * 100;

        long totalMemory = osBean.getTotalMemorySize();
        long freeMemory = osBean.getFreeMemorySize();

        long usedMemory = totalMemory - freeMemory;

        System.out.println("===== SYSTEM RESOURCE MONITOR =====");

        System.out.printf("CPU Usage: %.2f%%\n", cpuLoad);

        System.out.printf(
                "RAM Usage: %.2f GB / %.2f GB\n",
                usedMemory / 1e9,
                totalMemory / 1e9
        );
    }
}