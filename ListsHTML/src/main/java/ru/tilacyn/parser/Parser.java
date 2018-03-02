package ru.tilacyn.parser;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Stack;


/**
 * a class for transforming plain list layouts to the html format
 *
 * it has the only public constructor with arguments of type Reader and Writer
 * and the only public method evaluate which does the main job
 *
 * Input should be formatted the specified way:
 * * 1
 *    # 2
 *    * 3
 * * 4
 *
 * number of spaces is equal to the enclosure level
 * char * means that element is in the unnumbered list
 * char # means that element is in the numbered list
 *
 * after * or # should be one space, then some string data till the next line or eof
 *
 * Output is a correct html-formatted file with nested lists equal to the ones described in the input
 *
 * eg the result will be
 *
 * <ul>
 * <li>1
 * <ol>
 * <li>2
 * </ol>
 * <ul>
 * <li>3
 * </ul>
 * <li>4
 * </ul>
 *
 */
public class Parser {
    private Reader reader;
    private Writer writer;

    private Stack<List> stack = new Stack<>();

    /**
     * the only constructor
     * @param reader Reader to supply input data
     * @param writer Writer to the place where new html file should appear
     */
    public Parser(@NotNull Reader reader, @NotNull Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    private char isEnd(int c) {
        if (c == -1) {
            return '\n';
        } else {
            return (char) c;
        }
    }

    private String readWord() throws IOException {
        char c;
        reader.read();
        StringBuilder s = new StringBuilder();
        while ((c = isEnd(reader.read())) != '\n') {
            s.append(c);
        }
        return s.toString();
    }


    private boolean readLine() throws IOException {
        char c;
        int n;
        int tabs = 0;
        while ((c = (char)(n = reader.read())) == ' ') {
            tabs++;
        }

        if (n == -1) {
            endLists();
            return false;
        }

        if (c == '\n') {
            endLists();
        } else if (c == '*') {
            processListElement(false, tabs, readWord());
        } else if (c == '#') {
            processListElement(true, tabs, readWord());
        }
        System.out.println(c + "WOW");
        return true;
    }


    /**
     * transforms plain layout to the html file
     * @throws IOException if input file format is incorrect or problems with writing occur
     */
    public void evaluate() throws IOException {
        while (readLine()) {
            //System.out.println("KEK");
        }
    }

    private void endLists() throws IOException {
        while (!stack.isEmpty()) {
            writeListClose(stack.pop().isNumbered());
        }
    }

    private void processListElement(boolean isNumbered, int tabs, @NotNull String data) throws IOException {
        if (stack.isEmpty()) {
            createNewList(isNumbered, tabs);
            writeElem(data);
            return;
        }

        while (!stack.isEmpty() && stack.peek().getTabs() > tabs) {
            //System.out.println(data);
            writeListClose(stack.pop().isNumbered());
        }

        if (stack.isEmpty()) {
            createNewList(isNumbered, tabs);
            writeElem(data);
            return;
        }

        if (stack.peek().getTabs() == tabs) {
            if (stack.peek().isNumbered() == isNumbered) {
                writeElem(data);
                return;
            } else {
                writeListClose(stack.pop().isNumbered());
            }
        }

        createNewList(isNumbered, tabs);
        writeElem(data);
    }

    private void createNewList(boolean isNumbered, int tabs) throws IOException {
        stack.add(new List(isNumbered, tabs));
        writeListOpen(isNumbered);
    }

    private void writeListOpen(boolean isNumbered) throws IOException {
        if (isNumbered) {
            writer.write("<ol>\n");
        } else {
            writer.write("<ul>\n");
        }
    }

    private void writeListClose(boolean isNumbered) throws IOException {
        if (isNumbered) {
            writer.write("</ol>\n");
        } else {
            writer.write("</ul>\n");
        }
    }


    private void writeElem(@NotNull String data) throws IOException {
        writer.write("<li>" + data + '\n');
    }

    private class List {
        private boolean isNumbered;
        private int tabs;

        public List(boolean isNumbered, int tabs) {
            this.isNumbered = isNumbered;
            this.tabs = tabs;
        }

        boolean isNumbered() {
            return isNumbered;
        }

        int getTabs() {
            return tabs;
        }

    }


}
