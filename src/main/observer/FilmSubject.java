package main.observer;

import java.util.LinkedList;
import java.util.List;

/**
 * @author lucio
 */
public abstract class FilmSubject {
    private List<FilmObserver> observers = new LinkedList<>();

    public void attach(FilmObserver observer){
        observers.add(observer);
    }

    public void detach(FilmObserver observer){
        observers.remove(observer);
    }

    public void notifyObservers(){
        for(FilmObserver observer : observers)
            observer.update();
    }
}
