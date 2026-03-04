package programmers;
// https://school.programmers.co.kr/learn/courses/30/lessons/340210
import java.util.*;
import java.util.stream.Collectors;

class PB_340210 {
    public class Expression{
        private String ancientA;
        private String ancientB;
        private String oper;
        private String ancientResult;
        private Integer maxNum;

        Expression(String ancientExpression){
            // ["14 + 3 = 17", "13 - 6 = X", "51 - 5 = 44"]
            String[] expStr = ancientExpression.split(" ");
            this.ancientA = expStr[0];
            this.oper = expStr[1];
            this.ancientB = expStr[2];
            this.ancientResult = expStr[4];
            int max = 0;
            for (Character c : ancientExpression.toCharArray()) {
                if(Character.isDigit(c)){
                    int num = Integer.parseInt(c.toString());
                    max = Math.max(num,max);
                }
            }
            this.maxNum = max;
        }

        private String calculate(int base){
            int decimalA = Integer.parseInt(this.ancientA,base);
            int decimalB = Integer.parseInt(this.ancientB,base);

            if(oper.equals("+")){
                return Integer.toString(decimalA+decimalB,base);
            }else{
                return Integer.toString(decimalA-decimalB,base);
            }
        }

        private int getDecimalResultByBase(int base){
            return Integer.parseInt(this.ancientResult,base);
        }

        public Integer getMaxNum() {
            return maxNum;
        }

        public boolean isUnknown() {
            return this.ancientResult.equals("X");
        }

        public String calculateAnswer(List<Integer> baseList) {
            Set<String> answerSet = new HashSet<>();

            for (Integer base : baseList) {
                answerSet.add(calculate(base));
            }

            if(answerSet.size()==1){
                Iterator<String > iter = answerSet.iterator();
                return iter.next();
            }else{
                return "?";
            }
        }

        public String printResult(String calculatedAnswer) {
            return this.ancientA +" "+this.oper+" "+this.ancientB +" = "+calculatedAnswer;
        }

        public String getAncientResult() {
            return ancientResult;
        }
    }

    public String[] solution(String[] expressions) {
        List<String> answer = new ArrayList<>();
        // ["13 - 6 = 5"]
        List<Expression> expressionList = new ArrayList<>();
        int maxNum = 0; // 진법의 범위를 구하기 위한 최대 숫자값
        for (String ancientExp : expressions) {
            Expression expression = new Expression(ancientExp);
            maxNum = Math.max(maxNum,expression.getMaxNum());
            expressionList.add(expression);
        }

        List<Integer> baseList = new ArrayList<>(); // 진법의 범위
        for (int i = maxNum+1; i <= 9; i++) {
            baseList.add(i);
        }
        
        // X가 아닌애들에게서 진법 범위 좁히기
        Set<Integer> impossibleSet = new HashSet<>();
        for (Expression expression : expressionList) {
            if(!expression.isUnknown()){
                for (Integer base : baseList) {
                    if(!expression.getAncientResult().equals(expression.calculate(base))){
                        impossibleSet.add(base);
                    }
                }
            }
        }

        baseList = baseList.stream().filter(p-> !impossibleSet.contains(p)).collect(Collectors.toList());

        for (Expression expression : expressionList) {
            if(expression.isUnknown()){ // X인 애들 계산하기
                String calculatedAnswer = expression.calculateAnswer(baseList);
                answer.add(expression.printResult(calculatedAnswer));
            }
        }

        return answer.toArray(new String[answer.size()]);
    }

}