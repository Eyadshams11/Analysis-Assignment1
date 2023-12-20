public class SequenceAlignment {

    public static String[] highestScoringAlignment(String x, String y, int[][] scoringMatrix) {
        int n = x.length();
        int m = y.length();

        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int matchScore = dp[i - 1][j - 1] + scoringMatrix[charToIndex(x.charAt(i - 1))][charToIndex(y.charAt(j - 1))];
                int deleteScore = dp[i - 1][j] + scoringMatrix[charToIndex(x.charAt(i - 1))][charToIndex('-')];
                int insertScore = dp[i][j - 1] + scoringMatrix[charToIndex('-')][charToIndex(y.charAt(j - 1))];
                dp[i][j] = Math.max(Math.max(matchScore, deleteScore), insertScore);
            }
        }
        StringBuilder alignX = new StringBuilder();
        StringBuilder alignY = new StringBuilder();
        int i = n, j = m;
        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && dp[i][j] == dp[i - 1][j - 1] + scoringMatrix[charToIndex(x.charAt(i - 1))][charToIndex(y.charAt(j - 1))]) {
                alignX.insert(0, x.charAt(i - 1));
                alignY.insert(0, y.charAt(j - 1));
                i--;
                j--;
            } else if (i > 0 && dp[i][j] == dp[i - 1][j] + scoringMatrix[charToIndex(x.charAt(i - 1))][charToIndex('-')]) {
                alignX.insert(0, x.charAt(i - 1));
                alignY.insert(0, '-');
                i--;
            } else {
                alignX.insert(0, '-');
                alignY.insert(0, y.charAt(j - 1));
                j--;
            }
        }

        return new String[]{alignX.toString(), alignY.toString()};
    }

    private static int charToIndex(char c) {
        if (c == 'A') return 0;
        else if (c == 'T') return 1;
        else if (c == 'G') return 2;
        else if (c == 'C') return 3;
        else if (c == '-') return 4;
        else throw new IllegalArgumentException("Invalid character: " + c);
    }

    public static void main(String[] args) {
        String x = "TCCCAGTTATGTCAGGGGACACGAGCATGCAGAGACAATTGCCGCCGTCGTTTTCAGCAGTTATGTCAGATC";
        String y = "TCCCAGTTATGTCAGGGGACACGAGCATGCAGAGACAATTGCCGCCGTCGTTTTCAGCAGTTATGTCAGATC";
        int[][] scoringMatrix = {
                {2, -1, -2, 0, -3},
                {-1, 2, 0, -2, -3},
                {-2, 0, 2, -1, -3},
                {0, -2, -1, 2, -3},
                {-3, -3, -3, -3, -3}
        };

        String[] result = highestScoringAlignment(x, y, scoringMatrix);

        System.out.println("Optimal Alignment:");
        System.out.println(result[0]);
    }
}
