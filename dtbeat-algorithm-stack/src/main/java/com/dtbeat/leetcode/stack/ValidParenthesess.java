package com.dtbeat.leetcode.stack;

//
//
// 注意空字符串可被认为是有效字符串。
//
// 示例 1:
//
// 输入: "()"
//输出: true
//
//
// 示例 2:
//
// 输入: "()[]{}"
//输出: true
//
//
// 示例 3:
//
// 输入: "(]"
//输出: false
//
//
// 示例 4:
//
// 输入: "([)]"
//输出: false
//
//
// 示例 5:
//
// 输入: "{[]}"
//输出: true
// Related Topics 栈 字符串
// 👍 1836 👎 0

import java.util.Stack;

/**
 * ValidataScope
 *
 * @author elvinshang
 * @version Id: ValidataScope.java, v0.0.1 2020/9/8 10:12 dtbeat.com $
 */
public class ValidParenthesess {
    public boolean isValid(String s) {
        if (s == null || s.trim().isEmpty()) {
            return true;
        }

        boolean ret = false;
        char[] chars = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (char c : chars) {
            if (c == '{' || c == '(' || c == '[') {
                stack.push(c);
            } else if (stack.isEmpty() && (c == '}' || c == ')' || c == ']')) {
                break;
            } else {
                switch (c) {
                    case '}':
                        if (stack.peek().charValue() == '{') {
                            stack.pop();
                        } else {
                            stack.push(c);
                        }
                        break;
                    case ']':
                        if (stack.peek().charValue() == '[') {
                            stack.pop();
                        } else {
                            stack.push(c);
                        }
                        break;
                    case ')':
                        if (stack.peek().charValue() == '(') {
                            stack.pop();
                        } else {
                            stack.push(c);
                        }
                        break;
                    default:
                        break;
                }
            }
        }

        return stack.isEmpty();
    }

    public static void main(String[] args) {
        ValidParenthesess solution = new ValidParenthesess();
        boolean valid = solution.isValid("()[]{}");
        System.out.println(valid);
    }
}
