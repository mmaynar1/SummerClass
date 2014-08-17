package com.summerclass.servlet;

public enum Keywords2048
{
    save("Save"),
    quit("Quit"),
    input("input"),
    cell("cell"),
    delimiter("_");

    final private String text;

    private Keywords2048( String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return text;
    }
}