import java.util.*;

public class GradeStatisticsSystem {

    public static void main(String[] args) {
        // 假設學生成績（你可以改為從使用者輸入或讀檔）
        int[] grades = {85, 92, 76, 65, 58, 90, 72, 100, 81, 45};

        printReport(grades);
    }

    public static void printReport(int[] grades) {
        double average = calculateAverage(grades);
        int highest = findHighest(grades);
        int lowest = findLowest(grades);
        int aboveAverageCount = countAboveAverage(grades, average);
        Map<String, Integer> gradeDistribution = calculateGradeDistribution(grades);

        System.out.println("===== 成績統計報表 =====");
        System.out.println("成績列表：" + Arrays.toString(grades));
        System.out.printf("平均分數：%.2f\n", average);
        System.out.println("最高分： " + highest);
        System.out.println("最低分： " + lowest);
        System.out.println("高於平均分的人數： " + aboveAverageCount);
        System.out.println("等第分布：");
        for (Map.Entry<String, Integer> entry : gradeDistribution.entrySet()) {
            System.out.println("  " + entry.getKey() + "： " + entry.getValue() + " 人");
        }
        System.out.println("========================");
    }

    public static double calculateAverage(int[] grades) {
        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return (double) sum / grades.length;
    }

    public static int findHighest(int[] grades) {
        int max = grades[0];
        for (int grade : grades) {
            if (grade > max) {
                max = grade;
            }
        }
        return max;
    }

    public static int findLowest(int[] grades) {
        int min = grades[0];
        for (int grade : grades) {
            if (grade < min) {
                min = grade;
            }
        }
        return min;
    }

    public static int countAboveAverage(int[] grades, double average) {
        int count = 0;
        for (int grade : grades) {
            if (grade > average) {
                count++;
            }
        }
        return count;
    }

    public static Map<String, Integer> calculateGradeDistribution(int[] grades) {
        Map<String, Integer> distribution = new LinkedHashMap<>();
        distribution.put("A (90-100)", 0);
        distribution.put("B (80-89)", 0);
        distribution.put("C (70-79)", 0);
        distribution.put("D (60-69)", 0);
        distribution.put("F (<60)", 0);

        for (int grade : grades) {
            if (grade >= 90) {
                distribution.put("A (90-100)", distribution.get("A (90-100)") + 1);
            } else if (grade >= 80) {
                distribution.put("B (80-89)", distribution.get("B (80-89)") + 1);
            } else if (grade >= 70) {
                distribution.put("C (70-79)", distribution.get("C (70-79)") + 1);
            } else if (grade >= 60) {
                distribution.put("D (60-69)", distribution.get("D (60-69)") + 1);
            } else {
                distribution.put("F (<60)", distribution.get("F (<60)") + 1);
            }
        }

        return distribution;
    }
}