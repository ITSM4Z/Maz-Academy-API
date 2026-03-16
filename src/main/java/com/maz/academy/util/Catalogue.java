package com.maz.academy.util;

import java.util.*;

/**
 * A generic container class used to manage a collection of items.
 * This class fulfills the requirement for Generics.
 * It can store any type of object (T) and provides basic management operations.
 * Usage example: {@code Catalogue<User>} or {@code Catalogue<Course>}.
 *
 * @param <T> The type of objects stored in this catalogue.
 */

public class Catalogue<T> {
    /** Internal storage for the items. */
    private ArrayList<T> items = new ArrayList<>();

    /**
     * Adds an item to the catalogue.
     * @param item The item of type T to add.
     */
    public void addItem(T item) { items.add(item); }
    /**
     * Removes an item from the catalogue.
     *
     * @param item The item of type T to remove.
     * @return true if the item was found and removed.
     */
    public boolean removeItem(T item) { return items.remove(item); }

    /** Prints the string representation of all items in the catalogue to the console. */
    public void printAll(){
        for(T item : items){
            System.out.println(item);
        }
    }
    /** @return An unmodifiable view List containing all items in the catalogue. */
    public List<T> getItems() { return Collections.unmodifiableList(items); }
}