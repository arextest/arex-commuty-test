package com.arextest.agent.test.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author xu_zhou
 * @date 2023/01/05
 */
public class Request {
    private String input;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date date;


    public Request() {
    }

    public Request(String input) {
        this.input = input;
    }

    public Request(String input, Date date) {
        this.input = input;
        this.date = date;
    }

    public String getInput() {
        return this.input;
    }

    public Date getDate() {
        return this.date;
    }


    public void setInput(String input) {
        this.input = input;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Request)) {
            return false;
        } else {
            Request other = (Request)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$input = this.getInput();
                Object other$input = other.getInput();
                if (this$input == null) {
                    if (other$input != null) {
                        return false;
                    }
                } else if (!this$input.equals(other$input)) {
                    return false;
                }
            }
        }
        return false;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Request;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $input = this.getInput();
        result = result * 59 + ($input == null ? 43 : $input.hashCode());

        return result;
    }

    public String toString() {
        return "Request(input=" + this.getInput() +  ")";
    }

}
