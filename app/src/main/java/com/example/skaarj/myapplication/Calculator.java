package com.example.skaarj.myapplication;

import android.icu.text.NumberFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.LinkedList;

/**
 * Created by SkaaRJ on 21.01.2018.
 */

public class Calculator {
    private String str =""; //буфер для expression
    private String expression; //строка выражения
    private int i = 0; // счетчик итераций

    public double getResult() throws CalculationException {
        return eval();
    }





    /**
     * Конструктор с параметрами. Производит первичную замену функций
     * на их односимвольные аналоги.
     * @param expression - функция
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    Calculator(String expression){
        this.expression = expression;
        this.expression = this.expression.replaceAll("cos","c");
        this.expression = this.expression.replaceAll("sin","s");
        this.expression = this.expression.replaceAll("tan","t");
        this.expression = this.expression.replaceAll("ctg","g");
        this.expression = this.expression.replaceAll("sqrt","q");
        this.expression = this.expression.replaceAll("exp","e");
        this.expression = this.expression.replaceAll("ln","l");
        this.expression = this.expression.replaceAll("е",String.valueOf(Math.E));
        this.expression = this.expression.replaceAll("π",String.valueOf(Math.PI));


    }


    /**
     * Переводит строку в число
     * @param i - индекс в строке str, на котором находится число
     * @return double - переведенное число
     * @throws CalculationException - Генерирует исключение: Не верное выражение
     */
    private double number(int i) throws CalculationException {
        double result = 0.0;
        double div = 10.0;
        int sign = 1;
        if(isFunction(str.charAt(i)) || (isOperator(str.charAt(i)) && str.charAt(i)!='-')) {
            throw new CalculationException("Было введенно неверное выражение");
        }
        if (str.charAt(i) == '-')
        {
            sign = -1;
            ++i;
        }

        while (Character.isDigit(str.charAt(i)))
        {
            result = result * 10.0 + (str.charAt(i) - '0');
            this.i = ++i;
            if(str.length()<=i+1) return sign * result;
        }

        if (str.charAt(i) == '.' || str.charAt(i) == ',')
        {
            ++i;

            while (Character.isDigit(str.charAt(i))) {
                result = result + (str.charAt(i) - '0') / div;
                div *= 10.0;
                this.i = ++i;
                if(str.length()<=i+1) {
                    this.i = ++i;
                    return sign * result;
                }
            }

        }


        return sign * result;
    }

    /**
     *  Проверка на пробел
     * @param c - символ
     * @return возвращает true если это пробел, иначе false
     */
    private boolean isSpace(char c) { // тру если пробел
        return c == ' ';
    }

    /**
     * Функция проверяет явялется ли символ 'c' знаком операции
     * @param c символ из строки str
     * @return возвращает true, если c = {+, -, *, /, ^, %}, иначе false
     */
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^' ;
    }

    /**
     * Функция проверяет явялется ли символ 'c' символом функции
     * @param c символ из строки str
     * @return возвращает true, если c = {с, s, t, g, q, l, e, √}, иначе false
     */
    private boolean isFunction(char c) {
        return c == 'c' || c == 's' || c == 't' || c == 'g' || c == 'l' || c=='q' || c =='e' || c == '√';
    }

    /**
     * Возвращает степень важности приоритета операции. Так, чем важнее оператор, тем выше возвращаемое значение
     * @param op символ операции
     * @return возарщает число типа int - важность операции
     */
    private int getPriority(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            case 'c':
            case 's':
            case 't':
            case 'g':
            case 'l':
            case 'q':
            case 'e':
            case '√':
            case '^':
                return 4;
            default:
                return -1;
        }
    }

    /**
     * Производит операции над числами за счет операндов и функций
     * @param st стек чисел
     * @param op символ операнда или функции
     * @throws CalculationException генерирует исключение о несходимости ряда, несбалансированности скобок,
     *                                                      деления на ноль, попытка извлечь корень из отрицательного числа.
     */
    private void processOperator(LinkedList<Double> st, char op) throws CalculationException {
        if(st.isEmpty()) throw new CalculationException("Поле пусто");
        double r = st.removeLast(); //Унарные операнды или функции
        switch (op) {
            case '(':  throw new CalculationException("Скобки не сбалансированны");
            case 's':
                st.add(Math.sin(r));
                return;
            case 'c':
                st.add(Math.cos(r));
                return;
            case 't':
                st.add(Math.tan(r));
                return;
            case 'g':
                st.add(1 / Math.tan(r));
                return;
            case 'l':
                st.add(Math.log(r));
                return;
            case'√':
                if(r < 0) throw new CalculationException("Подкоренное выражение < 0");
                st.add(Math.sqrt(r));
                return;
            case 'q':
                if(r < 0) throw new CalculationException("Подкоренное выражение < 0");
                st.add(Math.sqrt(r));
                return;
            case 'e':
                st.add(Math.exp(r));
                return;
        }
        if(st.size() == 0) return;
        double l = st.removeLast(); // бинарные операции
        switch (op) { // выполняем действие между l и r в зависимости от оператора в кейсе и результат кладем в st
            case '+':
                st.add(l + r);
                break;
            case '-':
                st.add(l - r);
                break;
            case '*':
                st.add(l * r);
                break;
            case '/':
                if(r == 0) throw new CalculationException("Произошло деление на ноль");
                else
                    st.add(l / r);
                break;
            case '%':
                st.add(l % r);
                break;
            case '^':
                st.add(Math.pow(l,r));
                break;
        }
    }

    /**
     * Стековая машина. Строит обратную польскую нотацию, при это производит вычисления выражения, содержащиеся в str
     * @return значение выражения
     * @throws CalculationException может сгенерировать исключение: несбалансированность скобок
     */
    public double eval() throws CalculationException{
        this.str = this.expression;
        LinkedList<Double> st = new LinkedList<Double>(); // сюда наваливают цифры
        LinkedList<Character> op = new LinkedList<Character>(); // сюда опрераторы и st и op в порядке поступления

        for (i = 0; i < this.str.length(); i++) { // парсим строку с выражением и вычисляем
            char c = this.str.charAt(i);
            if (isSpace(c) || c == '=')
                continue;
            if (c == '(')
                op.add('(');
            else if (c == ')') {
                while (op.getLast() != '(') {
                    processOperator(st, op.removeLast());
                    if(op.isEmpty()) throw new CalculationException("Скобки не сбалансированны");
                }
                op.removeLast();

            } else if ((isOperator(c) && i!=0) || isFunction(c)) {
                if(this.str.charAt(i) == '-' && (isOperator(this.str.charAt(i-1)) || isFunction(this.str.charAt(i-1)) || this.str.charAt(i-1) == '(')){
                    st.add(number(i));
                    i--;
                }
                else {
                    while (!op.isEmpty() && (
                            getPriority(c) != 3 && getPriority(op.getLast()) >= getPriority(c)
                                    || getPriority(c) == 3 && getPriority(op.getLast()) > getPriority(c))) {
                        processOperator(st, op.removeLast());
                    }
                    op.add(c);
                }
            } else {
                st.add(number(i));
                i--;
            }
        }

        while (!op.isEmpty())
            processOperator(st, op.removeLast());
        return st.get(0);  // возврат результата
    }

}
