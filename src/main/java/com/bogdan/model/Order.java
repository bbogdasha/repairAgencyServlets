package com.bogdan.model;

public class Order {

    private int id;
    private String title;
    private String message;
    private User user;
    private double price;
    private String workerName;
    private State state;

    public Order() {
    }

    public Order(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Order(String title, String message, User user) {
        this.title = title;
        this.message = message;
        this.user = user;
    }

    public Order(String title, String message, User user, double price, String workerName, State state) {
        this.title = title;
        this.message = message;
        this.user = user;
        this.price = price;
        this.workerName = workerName;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
