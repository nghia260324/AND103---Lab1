package com.md18303.and103_labs1;

public class CityModel {
    private String id;
    private String name;
    private int population;
    private String country;

    public CityModel() {
    }

    public CityModel(String id, String name, int population, String country) {
        this.id = id;
        this.name = name;
        this.population = population;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
