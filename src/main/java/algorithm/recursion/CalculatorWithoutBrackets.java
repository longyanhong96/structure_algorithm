package algorithm.recursion;

import org.apache.commons.lang3.StringUtils;

import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Description : 输入一个没有括号的计算的式子，通过用栈，输出计算结果
 * analysis : 1、解析输入的str,数字的话就写入到数字的栈中,符号的就写在符号的栈中
 * 2、比较符号的优先级，高的先计算
 * 3、根据符号，数字算数
 *
 * @author lyh
 * @date 2020-12-06
 */
public class CalculatorWithoutBrackets {

    private static Stack<Double> numberStack = new Stack<>();
    private static Stack<String> signStack = new Stack<>();


    public static void main(String[] args) {
        String value = "71+7*8-5";
        char[] chars = value.toCharArray();
        StringBuffer numberBuffer = new StringBuffer();
//        StringBuffer signBuffer = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char preChar = chars[i];
            if (isNumber(preChar)) {
                numberBuffer.append(preChar);
            } else {
                double number = Double.parseDouble(numberBuffer.toString());
                numberStack.push(number);
                numberBuffer = new StringBuffer();

                String sign = String.valueOf(preChar);
                if (signStack.empty()) {
                    signStack.push(sign);
                } else {
                    String preSign = signStack.peek();
                    int signNum = changeSymbolToNum(sign);
                    int preSignNum = changeSymbolToNum(preSign);
                    if (signNum < preSignNum) {
                        preSign = signStack.pop();
                        Double secondNum = numberStack.pop();
                        Double firstNum = numberStack.pop();
                        double result = calculate(firstNum, secondNum, preSign);
                        numberStack.push(result);
                        signStack.push(sign);
                    } else {
                        signStack.push(sign);
                    }
                }
            }
        }

        numberStack.push(Double.parseDouble(numberBuffer.toString()));

        while (!signStack.isEmpty()) {
            String sign = signStack.pop();
            Double secondNum = numberStack.pop();
            Double firstNum = numberStack.pop();
            double result = calculate(firstNum, secondNum, sign);
            numberStack.push(result);
        }

        Double result = numberStack.pop();
        System.out.println("result = " + result);
    }

    private static boolean isNumber(char preChar) {
        String preStr = String.valueOf(preChar);
        return isNumber(preStr);
    }

    /**
     * 判断是否是数字，如果不是，就是符号
     *
     * @param preStr
     * @return
     */
    private static boolean isNumber(String preStr) {
        if (StringUtils.isBlank(preStr)) {
            return false;
        }
        String patternStr = "^(\\-|\\+)?\\d+(\\.\\d+)?$";
        Pattern pattern = Pattern.compile(patternStr);
        return pattern.matcher(preStr).matches();
    }

    /**
     * 把符号转化为数字，方便比较
     *
     * @param symbol 符号
     * @return
     */
    private static int changeSymbolToNum(String symbol) {
        if (symbol.equals("*") || symbol.equals("/")) {
            return 1;
        } else if (symbol.equals("+") || symbol.equals("-")) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * 计算的方法
     *
     * @param firstNum  第一个数字
     * @param secondNum 第二个数字
     * @param symbol    计算符号
     * @return 计算结果
     */
    private static double calculate(double firstNum, double secondNum, String symbol) {
        double result = 0;
        switch (symbol) {
            case "*":
                return firstNum * secondNum;
            case "/":
                return firstNum / secondNum;
            case "+":
                return firstNum + secondNum;
            case "-":
                return firstNum - secondNum;
            default:
                return 0;
        }
    }
}
