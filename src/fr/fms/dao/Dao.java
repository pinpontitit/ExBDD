package fr.fms.dao;

import java.util.ArrayList;

public interface Dao<T> {
	public int create(T obj);
	public T read(int id);
	public boolean update(T obj);
	public boolean delete(T obj);
	public ArrayList<T> readAll();
}
