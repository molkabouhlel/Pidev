package com.esprit.services;

import com.esprit.models.Espace;
import com.esprit.models.Club;

import java.util.List;

public interface IService<T> {

    public void ajouter(T t);
    public void modifier(T t);
    public void supprimer(T t);
    public List<T> afficher();


}
