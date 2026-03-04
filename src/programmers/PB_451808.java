package programmers;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
// https://school.programmers.co.kr/learn/courses/30/lessons/451808

class PB_451808 {
    public class Score{
        int strikeCnt;
        int ballCnt;

        public Score(String applyResult) {
            this.strikeCnt = Integer.parseInt(String.valueOf(applyResult.charAt(0)));
            this.ballCnt = Integer.parseInt(String.valueOf(applyResult.charAt(3)));
        }

        public Score(int strikeCnt, int ballCnt) {
            this.strikeCnt = strikeCnt;
            this.ballCnt = ballCnt;
        }

        public int getStrikeCnt() {
            return strikeCnt;
        }

        public int getBallCnt() {
            return ballCnt;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Score score = (Score) o;
            return strikeCnt == score.strikeCnt && ballCnt == score.ballCnt;
        }

        @Override
        public int hashCode() {
            return Objects.hash(strikeCnt, ballCnt);
        }
    }

    public int solution(int n, Function<Integer, String> submit) {
        String guess = "1234";
        String result = submit.apply(Integer.parseInt(guess)); // "4S 0B" 형식으로 리턴

        Score score = new Score(result);
        if(score.getStrikeCnt()==4){
            return Integer.parseInt(guess);
        }

        List<String> candidates = generateAllCandidates();
        List<String> possibles = new ArrayList<>(candidates);

        while (true){
            final String finalGuess = guess;
            final Score finalScore = score;

            possibles = possibles.stream()
                    .filter(p -> isPossible(p, finalGuess, finalScore))
                    .collect(Collectors.toList());
            // Guess 와 Score를 활용해서 가능한 리스트만 필터링

            if(possibles.size()==1){
                return Integer.parseInt(possibles.get(0));
            }

            int minMaxCount = Integer.MAX_VALUE;
            for (String guessCandidate : candidates) {
                HashMap<Score,Integer> predictedResultMap = new HashMap<>();
                int maxInGroup = Integer.MIN_VALUE;
                for (String possible : possibles) {
                    Score guessedScore = calculate(possible,guessCandidate);
                    predictedResultMap.put(guessedScore,predictedResultMap.getOrDefault(guessedScore,0)+1);
                }
                for (Integer value : predictedResultMap.values()) {
                    maxInGroup = Math.max(maxInGroup, value);
                }
                if(maxInGroup < minMaxCount){
                    minMaxCount = maxInGroup;
                    guess = guessCandidate;
                }
            }

            result = submit.apply(Integer.parseInt(guess));
            score = new Score(result);
        }
    }


    private boolean isPossible(String candidate, String guess, Score score) {
        return calculate(candidate,guess).equals(score);
    }

    private Score calculate(String candidate, String guess) {
        int strikeCnt = 0, ballCnt = 0;
        for (int i = 0; i < 4; i++) {
            if(candidate.charAt(i) == guess.charAt(i)){
                strikeCnt++;
            } else if(guess.contains(String.valueOf(candidate.charAt(i)))){
                ballCnt++;
            }
        }
        return new Score(strikeCnt,ballCnt);
    }

    private List<String> generateAllCandidates() {
        List<String> candidates = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                if(i==j) continue;
                for (int k = 1; k <= 9; k++) {
                    if(i==k || j==k) continue;
                    for (int l = 1; l <= 9; l++) {
                        if(i==l|| j==l || k==l) continue;
                        candidates.add(String.valueOf(i*1000 + j*100 + k*10 + l));
                    }
                }
            }
        }

        return candidates;
    }
}