package com.zyl.Utils;



import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetUtils {

    //获取CPU温度
    private static String GET_CPU_TEMP = "cat /sys/class/thermal/thermal_zone1/temp";

    //获取可用内存
    private static String GET_MEM_AVAILABLE = "cat /proc/meminfo | grep MemAvailable";

    //获取总内存
    private static String GET_MEM_TOTAL = "cat /proc/meminfo | grep MemTotal";

    public static String getCPUTemp() {

        String CPUTemp;
        String rawInfo = ShellCommand.shellExec(GET_CPU_TEMP);

        if (rawInfo != "" && rawInfo.length() > 3) {
            CPUTemp = rawInfo.substring(0, 2) + "." +
                    rawInfo.substring(2, 3) + "°C";
            return CPUTemp;
        }
        return "error";
    }

    public static String getGetCpuHz() {
        String Hz;
        double result = 0;
        List<Integer> rawInfo = CpuUtils.getCpuCurFreq();
        if (rawInfo.size() == 1) {
            result = rawInfo.get(0);//单核
        } else if (rawInfo.size() == 2) {
            result = rawInfo.get(1);//双核
        } else if (rawInfo.size() == 4) {
            result = rawInfo.get(3);//四核
        } else if (rawInfo.size() == 8) {
            result = rawInfo.get(7);//八核
        } else {
            result = rawInfo.get(0);
        }
        Hz = "" + result / 1000 + "MHz";
        return Hz;
    }

    public static String getMemoryUsage() {
        int memUsage = 0;
        String rawInfo = ShellCommand.shellExec(GET_MEM_TOTAL);

        float memTotal = getDigitFromStr("MemTotal", rawInfo);
        float memAvailable = getDigitFromStr("MemAvailable",rawInfo);

        memUsage = Math.round(((memTotal-memAvailable)/memTotal)*100);

        return "" + memUsage + "%";
    }


    /**
     * @param rawInfo 要处理的字符串
     * @return 提取的数字
     */
    public static float getDigitFromStr(String str, String rawInfo) {
        String s[] = rawInfo.split("kB");
        for (String ss : s) {
            if (ss.contains(str)) {
                Pattern pattern = Pattern.compile("(\\D*)(\\d+)");
                Matcher matcher = pattern.matcher(ss);
                if (matcher.find()) {
                    return Float.parseFloat(matcher.group(2));
                }
            }
        }
        return 1.0f;
    }
}
