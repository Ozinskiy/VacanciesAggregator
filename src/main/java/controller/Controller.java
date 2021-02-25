package controller;

import model.Model;

public class Controller {

    private Model model;

    public Controller(Model model) {
        if(model == null){
            throw new IllegalArgumentException();
        }else {
            this.model = model;
        }
    }

    public void onCitySelect(String cityName){
        model.selectCity(cityName);
    }
}
